package by.epam.provider.filter;


import by.epam.provider.exception.ProviderException;
import by.epam.provider.validator.LanguageValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", servletNames = "DispatcherServlet",
        filterName = "RequestFilter")
@Log4j2
public class RequestFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            String message = req.getParameter("message");
            if (message != null) {
                req.setAttribute("message", message);
            }

            String lang = req.getParameter("lang");
            log.debug("Lang: " + lang);

            if (lang == null) {
                lang = (String) req.getSession().getAttribute("lang");
                if (lang != null) {
                    LanguageValidator.validateLang(lang);
                    req.getSession().setAttribute("lang", lang);
                    log.debug("Lang: " + lang);
                } else {
                    req.getSession().setAttribute("lang",
                            LanguageValidator.defaultLang());
                }
            } else {
                req.getSession().setAttribute("lang", lang);
                LanguageValidator.validateLang(lang);
            }
        } catch (ProviderException e) {
            req.setAttribute("errorCode", e.getErrorCode());
            req.getSession().setAttribute("query", "error");
        }
        filterChain.doFilter(req, resp);
    }
}
