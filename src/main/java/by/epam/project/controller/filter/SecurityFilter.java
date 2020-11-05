package by.epam.project.controller.filter;

import by.epam.project.command.PathToPage;
import by.epam.project.command.RequestAttribute;
import by.epam.project.entity.impl.User;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(dispatcherTypes = {
        DispatcherType.FORWARD,
}, urlPatterns = {"/jsp/admin/*"})
public class SecurityFilter implements Filter {

    public void destroy() {
    }

    /**
     * Filter denying access to the admin page
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute(RequestAttribute.ROLE);
        if (!userRole.equals(User.UserRole.ADMIN.name())) {
            String language = (String) request.getSession().getAttribute(RequestAttribute.LANGUAGE);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher(PathToPage.INDEX_PAGE);
            request.getSession().invalidate();
            request.getSession().setAttribute(RequestAttribute.LANGUAGE, language);
            dispatcher.forward(req, resp);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) {

    }

}
