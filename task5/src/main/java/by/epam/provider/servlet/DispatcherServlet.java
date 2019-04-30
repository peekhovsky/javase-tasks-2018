package by.epam.provider.servlet;

import by.epam.provider.command.CommandMethod;
import by.epam.provider.exception.ProviderException;
import by.epam.provider.factory.CommandManagerFactory;
import by.epam.provider.transaction.ConnectionPool;
import lombok.extern.log4j.Log4j2;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@WebServlet("/")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@Log4j2
public class DispatcherServlet extends HttpServlet {

    private static final int CONNECTION_POOL_INIT_CAPACITY = 10;

    @Override
    public void init() {
        ConnectionPool.setInitCapacity(CONNECTION_POOL_INIT_CAPACITY);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().closeConnections();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(CommandMethod.GET, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(CommandMethod.POST, req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        process(CommandMethod.PUT, req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        process(CommandMethod.DELETE, req, resp);
    }


    private void process(CommandMethod method, HttpServletRequest req,
                         HttpServletResponse resp) {
        try {
            CommandManagerFactory.commandManager().execute(method, req, resp);
        } catch (ProviderException e) {
            log.warn(e);
            errorPage(e.getErrorCode(), req, resp);
        }
    }

    private void errorPage(int code, HttpServletRequest req,
                           HttpServletResponse resp) {
        try {
            req.setAttribute("query", "error");
            req.setAttribute("errorCode", code);
            CommandManagerFactory.commandManager().execute(CommandMethod.GET,
                    req, resp);
        } catch (ProviderException e) {
            log.fatal("Fatal unsolvable error. ");
            log.error(e);
        }
    }
}
