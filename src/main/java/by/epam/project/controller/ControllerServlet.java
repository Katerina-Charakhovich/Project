package by.epam.project.controller;

import by.epam.project.command.Command;
import by.epam.project.command.CommandException;
import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.command.factory.ActionFactory;
import by.epam.project.command.Router;
import by.epam.project.pool.ConnectionPool;
import by.epam.project.pool.PoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/controller")
public class ControllerServlet extends HttpServlet {

    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * The main servlet.
     */
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
            Router router;
            RequestDispatcher dispatcher;

            router = command.execute(request);
            String currentPage = router.getPage();
            if (currentPage == null) {
                router.setPage(PathToPage.ERROR_PAGE);
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
            if (Router.Type.FORWARD == router.getType()) {
                if (request.getSession().getAttribute(RequestAttribute.REDIRECTED_PAGE) != null) {
                    router.setPage((String) request.getSession().getAttribute(RequestAttribute.REDIRECTED_PAGE));
                    request.getSession().removeAttribute(RequestAttribute.REDIRECTED_PAGE);
                }
                dispatcher = getServletContext().getRequestDispatcher(router.getPage());
                dispatcher.forward(request, response);
            } else {
                request.getSession().setAttribute(RequestAttribute.REDIRECTED_PAGE, router.getPage());
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
            request.getSession().setAttribute(RequestAttribute.CURRENT_PAGE, currentPage);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);
            response.sendRedirect(PathToPage.ERROR_PAGE);
        }
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
