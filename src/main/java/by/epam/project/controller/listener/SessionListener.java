package by.epam.project.controller.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Session listener.
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        LOGGER.log(Level.INFO, "remove: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        LOGGER.log(Level.INFO, "add: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        LOGGER.log(Level.INFO, "replace: " + event.getClass().getSimpleName() + " : " + event.getName()
                + " : " + event.getValue());
    }
}
