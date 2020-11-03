package by.epam.project.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Prevents direct access to the jsp page via a browser string
 * forward into INDEX if someone tries to get on the jsp page through a browser string
 */
@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {
                @WebInitParam(name = "LOGIN", value = "/controller")})
public class PageSecurityFilter implements Filter {

    private String path;

    @Override
    public void init(FilterConfig filterConfig) {
        path = filterConfig.getInitParameter("LOGIN");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!httpServletResponse.isCommitted()) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + path);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
