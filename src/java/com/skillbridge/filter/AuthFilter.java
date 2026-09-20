package com.skillbridge.filter;

import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
    "/client/*",
    "/freelancer/*"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        boolean authenticated = false;

        if (session != null) {

            User user =
                    (User) session.getAttribute(
                            "loggedInUser"
                    );

            if (user != null) {
                authenticated = true;
            }
        }

        if (!authenticated) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        String requestURI =
                httpRequest.getRequestURI();

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        String role =
                user.getRole();

        if (requestURI.contains("/client/")
                && !"CLIENT".equals(role)) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        if (requestURI.contains("/freelancer/")
                && !"FREELANCER".equals(role)) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        chain.doFilter(
                request,
                response
        );
    }

    @Override
    public void destroy() {

    }
}