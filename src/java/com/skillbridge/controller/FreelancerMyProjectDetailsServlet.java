package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProjectStatusHistoryDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Project;
import com.skillbridge.model.ProjectStatusHistory;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FreelancerMyProjectDetailsServlet")
public class FreelancerMyProjectDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------------------
        // 1. CHECK SESSION
        // -----------------------------------------
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // -----------------------------------------
        // 2. GET LOGGED-IN USER & CHECK ROLE
        // -----------------------------------------
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (!"FREELANCER".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied"
            );
            return;
        }

        // -----------------------------------------
        // 3. GET PROJECT ID
        // -----------------------------------------
        String projectIdParam = request.getParameter("projectId");

        if (projectIdParam == null || projectIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/FreelancerMyProjectsServlet");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParam);

            // -----------------------------------------
            // 4. LOAD PROJECT
            // -----------------------------------------
            ProjectDAO projectDAO = new ProjectDAO();
            Project project = projectDAO.getProjectById(projectId);

            if (project == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Project not found"
                );
                return;
            }

            // -----------------------------------------
            // 5. VERIFY FREELANCER AUTHORIZATION
            // -----------------------------------------
            ProposalDAO proposalDAO = new ProposalDAO();
            int acceptedFreelancerId = proposalDAO.getAcceptedFreelancerId(projectId);

            // If the project is IN_PROGRESS or COMPLETED, verify this freelancer is the assigned worker
            if (acceptedFreelancerId > 0 && acceptedFreelancerId != loggedInUser.getUserId()) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You are not authorized to view this project."
                );
                return;
            }

            // -----------------------------------------
            // 6. LOAD PROJECT STATUS HISTORY
            // -----------------------------------------
            ProjectStatusHistoryDAO historyDAO = new ProjectStatusHistoryDAO();
            List<ProjectStatusHistory> statusHistory = historyDAO.getHistoryByProject(projectId);

            // -----------------------------------------
            // 7. SET REQUEST ATTRIBUTES
            // -----------------------------------------
            request.setAttribute("project", project);
            request.setAttribute("statusHistory", statusHistory);

            String success = request.getParameter("success");
            if ("completed".equalsIgnoreCase(success)) {
                request.setAttribute(
                        "successMessage",
                        "Project has been marked as completed successfully."
                );
            }

            // -----------------------------------------
            // 8. FORWARD TO JSP
            // -----------------------------------------
            request.getRequestDispatcher("/freelancer/my-project-details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/FreelancerMyProjectsServlet");
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect back to project dashboard on unexpected failure to avoid JSP rendering errors
            response.sendRedirect(request.getContextPath() + "/FreelancerMyProjectsServlet?error=load_failed");
        }
    }
}