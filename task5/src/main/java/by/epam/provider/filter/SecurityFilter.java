package by.epam.provider.filter;


import by.epam.provider.security.UserType;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = "/*", servletNames = "DispatcherServlet",
        filterName = "SecurityFilter")
@Log4j2
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();

        Optional<UserType> userTypeOptional = Optional.ofNullable(
                (UserType) session.getAttribute("user_type"));

        UserType userType = userTypeOptional.orElse(UserType.GUEST);

        log.debug("Security filter, user type: " + userType.toString());
        session.setAttribute("user_type", userType);
        filterChain.doFilter(req, resp);
    }
}
