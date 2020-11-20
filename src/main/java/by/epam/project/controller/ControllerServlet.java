package by.epam.project.controller;

import by.epam.project.command.Command;
import by.epam.project.command.exception.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.command.Router;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.pool.exception.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The main servlet.
 */
@WebServlet(urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException {
        try {
            Command command = ActionFactory.defineCommand(request);
            Router router = command.execute(request);
            String currentPage = router.getPage();
            if (currentPage == null) {
                router.setPage(PathToPage.ERROR_PAGE);
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
            if (Router.Type.FORWARD == router.getType()) {
                currentPage = resolveForward(request, router, currentPage);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(router.getPage());
                dispatcher.forward(request, response);
            } else {
                request.getSession().setAttribute(RequestAttribute.REDIRECTED_PAGE, router.getPage());
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
            request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, currentPage);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);
            request.setAttribute(RequestAttribute.ERROR, e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(PathToPage.ERROR_PAGE);
            dispatcher.forward(request, response);
        }
    }

    private String resolveForward(HttpServletRequest request, Router router, String currentPage) {
        currentPage = resolvePageUpdate(request, router, currentPage);
        if (request.getSession().getAttribute(RequestAttribute.REDIRECTED_PAGE) != null) {
            router.setPage((String) request.getSession().getAttribute(RequestAttribute.REDIRECTED_PAGE));
            request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE_AFTER_REDIRECT, router.getPage());
            request.getSession().removeAttribute(RequestAttribute.REDIRECTED_PAGE);
        }
        return currentPage;
    }

    private String resolvePageUpdate(HttpServletRequest request, Router router, String routerPage) {
        if (PathToPage.INDEX_PAGE.equals(routerPage) && request.getSession().getAttribute(RequestAttribute.REDIRECTED_PAGE) == null) {
            String currentPageAfterRedirect = (String) request.getSession().getAttribute(RequestAttribute.CURRENT_PAGE_AFTER_REDIRECT);
            if (currentPageAfterRedirect != null) {
                routerPage = currentPageAfterRedirect;

            } else {
                HttpSession session = request.getSession();
                session.removeAttribute(RequestAttribute.EMAIL);
                session.removeAttribute(RequestAttribute.ROLE);
            }
            router.setPage(routerPage);
        } else {
            request.getSession().removeAttribute(RequestAttribute.CURRENT_PAGE_AFTER_REDIRECT);
        }
        return routerPage;
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (PoolException e) {
            LOGGER.log(Level.ERROR, e);
        }
    }
}
