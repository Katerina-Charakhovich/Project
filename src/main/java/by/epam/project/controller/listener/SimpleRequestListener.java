package by.epam.project.controller.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;


@WebListener
public class SimpleRequestListener implements ServletRequestListener {

    public static final Logger LOGGER = LogManager.getLogger();
    private static final String URI = "Request Initialized for ";
    private static final String ID = "Request Initialized with ID=";
    private static final String COUNTER = "counter";
    private static final String REQUEST_COUNTER = ", Request Counter =";
    private static final String REQUEST_DESTROYED = "Request Destroyed: ";
    private static final String LIFECYCLE = "lifecycle";

    /**
     * The type Simple request listener.
     */
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        String uri = URI + request.getRequestURI();
        String id = ID + request.getRequestedSessionId();
        LOGGER.log(Level.INFO, uri + "\n" + id);
        ServletContext context = event.getServletContext();
        Integer reqCount = (Integer) request.getSession().getAttribute(COUNTER);
        if (reqCount == null) {
            reqCount = 0;
        }
        LOGGER.log(Level.INFO, uri + "\n" + id + REQUEST_COUNTER + reqCount);
    }

    public void requestDestroyed(ServletRequestEvent event) {
        LOGGER.log(Level.INFO, REQUEST_DESTROYED
                + event.getServletRequest().getAttribute(LIFECYCLE));
    }
}
