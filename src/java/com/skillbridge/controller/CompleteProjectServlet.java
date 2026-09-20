package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Notification;
import com.skillbridge.model.Project;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/CompleteProjectServlet")
public class CompleteProjectServlet
        extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        /*
         * Check login
         */
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");

            return;
        }

        User loggedInUser =
                (User) session.getAttribute(
                        "loggedInUser");

        /*
         * Only Freelancer can complete a project
         */
        if (!"FREELANCER".equalsIgnoreCase(
                loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");

            return;
        }

        /*
         * Get project ID
         */
        String projectIdParameter =
                request.getParameter("projectId");

        if (projectIdParameter == null ||
                projectIdParameter.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/FreelancerMyProjectsServlet");

            return;
        }

        int projectId;

        try {

            projectId =
                    Integer.parseInt(
                            projectIdParameter);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/FreelancerMyProjectsServlet");

            return;
        }

        try {

            /*
             * Complete project
             */
            ProposalDAO proposalDAO =
                    new ProposalDAO();

            boolean completed =
                    proposalDAO.completeProject(
                            projectId,
                            loggedInUser.getUserId());

            if (completed) {

                /*
                 * Get project details
                 * so we can identify the client.
                 */
                ProjectDAO projectDAO =
                        new ProjectDAO();

                Project project =
                        projectDAO.getProjectById(
                                projectId);

                /*
                 * Create notification for client
                 */
                if (project != null) {

                    NotificationDAO notificationDAO =
                            new NotificationDAO();

                    Notification notification =
                            new Notification(
                                    project.getClientId(),
                                    "Project Completed",
                                    loggedInUser.getFullName()
                                    + " marked the project as completed."
                            );

                    notificationDAO.createNotification(
                            notification);
                }

                /*
                 * Redirect after successful completion
                 */
                response.sendRedirect(
                        request.getContextPath()
                        + "/FreelancerMyProjectDetailsServlet"
                        + "?projectId="
                        + projectId
                        + "&success=completed");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/FreelancerMyProjectDetailsServlet"
                        + "?projectId="
                        + projectId
                        + "&error=complete");
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/FreelancerMyProjectDetailsServlet"
                    + "?projectId="
                    + projectId
                    + "&error=complete");
        }
    }
}