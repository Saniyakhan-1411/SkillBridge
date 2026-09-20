package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.model.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/BrowseProjectsServlet")
public class BrowseProjectsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 1. If not logged in, redirect guest user to login page
        if (session == null || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );

            return;
        }

        String role = (String) session.getAttribute("role");

        // 2. If logged in as CLIENT, redirect to client dashboard instead of throwing 403
        if ("CLIENT".equalsIgnoreCase(role)) {

            response.sendRedirect(
                request.getContextPath() + "/client/dashboard.jsp"
            );

            return;
        }

        // 3. Process request for FREELANCER
        ProjectDAO projectDAO = new ProjectDAO();

        List<Project> projects =
            projectDAO.getOpenProjects();

        request.setAttribute(
            "projects",
            projects
        );

        request.getRequestDispatcher(
            "/freelancer/browse-projects.jsp"
        ).forward(request, response);
    }
}