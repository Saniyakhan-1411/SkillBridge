package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.model.Project;
import com.skillbridge.model.ProjectRisk;
import com.skillbridge.model.User;
import com.skillbridge.service.ProjectRiskAnalyzer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProjectRiskServlet")
public class ProjectRiskServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("====================================");
        System.out.println("ProjectRiskServlet STARTED");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Project ID: " + request.getParameter("projectId"));
        System.out.println("====================================");

        HttpSession session = request.getSession(false);

        /* LOGIN CHECK */
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        /* CLIENT ROLE CHECK */
        if (loggedInUser == null || !"CLIENT".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        /* PROJECT ID CHECK */
        String projectIdParameter = request.getParameter("projectId");

        if (projectIdParameter == null || projectIdParameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParameter);

            /* LOAD PROJECT */
            ProjectDAO projectDAO = new ProjectDAO();
            Project project = projectDAO.getProjectById(projectId);

            if (project == null) {
                request.setAttribute("errorMessage", "Project not found.");
                request.getRequestDispatcher("/project-risk.jsp").forward(request, response);
                return;
            }

            /* OWNERSHIP CHECK */
            if (project.getClientId() != loggedInUser.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
                return;
            }

            /* RUN PROJECT RISK ANALYZER */
            ProjectRiskAnalyzer analyzer = new ProjectRiskAnalyzer();
            ProjectRisk risk = analyzer.analyze(project);

            /* SEND DATA TO JSP */
            request.setAttribute("project", project);
            request.setAttribute("risk", risk);

            /* OPEN RISK ANALYSIS PAGE */
            request.getRequestDispatcher("/project-risk.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to analyze project risk.");
            request.getRequestDispatcher("/project-risk.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}