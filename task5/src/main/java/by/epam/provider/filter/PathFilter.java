package by.epam.provider.filter;


import by.epam.provider.exception.ProviderException;
import by.epam.provider.validator.PathValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", servletNames = "DispatcherServlet",
        filterName = "PathFilter")
@Log4j2
public class PathFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI()
                .substring(req.getContextPath().length())
                .substring(1);

        try {
            path = PathValidator.validatePath(path);
            req.setAttribute("query", path);

        } catch (ProviderException e) {
            req.setAttribute("errorCode", 404);
            req.setAttribute("query", "error");

        } finally {
            filterChain.doFilter(req, resp);
        }
    }
}
