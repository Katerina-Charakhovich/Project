package by.epam.project.tag;



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Displays a message depending on the role on the main page
 */
@SuppressWarnings("serial")
public class StartPageGreetingTag extends TagSupport {
    private String role;
    public static final Logger LOGGER = LogManager.getLogger();



    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        try {
//            String to;
//            if (User.UserRole.ADMIN.name().equals(role)) {
//                to = "Label.WelcomeAdmin";
//            } else if (User.UserRole.USER.name().equals(role)) {
//                to = "Label.WelcomeUser";
//            } else {
////                to = (String) pageContext.getSession().getAttribute(RequestAttribute.LANGUAGE);
//            }
            pageContext.getOut().write("<h4>" + "to" + "</h4>");
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "StartPageGreetingException ", e);
        }
        return SKIP_BODY;
    }
}
