package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Notification;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HireFreelancerServlet")
public class HireFreelancerServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // -------------------------------------------------
        // 1. Authentication
        // -------------------------------------------------

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        User client =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        // -------------------------------------------------
        // 2. Client role check
        // -------------------------------------------------

        if (!"CLIENT".equalsIgnoreCase(
                client.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        // -------------------------------------------------
        // 3. Get parameters
        // -------------------------------------------------

        String projectIdText =
                request.getParameter("projectId");

        String proposalIdText =
                request.getParameter("proposalId");

        if (projectIdText == null
                || proposalIdText == null
                || projectIdText.trim().isEmpty()
                || proposalIdText.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );

            return;
        }

        try {

            int projectId =
                    Integer.parseInt(
                            projectIdText
                    );

            int proposalId =
                    Integer.parseInt(
                            proposalIdText
                    );

            ProposalDAO proposalDAO =
                    new ProposalDAO();

            // -------------------------------------------------
            // 4. Hire freelancer
            // -------------------------------------------------

            int acceptedFreelancerId =
                    proposalDAO.hireFreelancer(
                            projectId,
                            proposalId,
                            client.getUserId()
                    );

            // -------------------------------------------------
            // 5. Hiring successful
            // -------------------------------------------------

            if (acceptedFreelancerId > 0) {

                // Send notification to hired freelancer
                try {

                    NotificationDAO notificationDAO =
                            new NotificationDAO();

                    Notification notification =
                            new Notification(
                                    acceptedFreelancerId,
                                    "You Have Been Hired",
                                    "Congratulations! You have been hired for the project."
                            );

                    notificationDAO.createNotification(
                            notification
                    );

                } catch (Exception notificationError) {

                    // Notification failure should not
                    // cancel successful hiring
                    notificationError.printStackTrace();
                }

                response.sendRedirect(
                        request.getContextPath()
                        + "/ClientProposalsServlet?projectId="
                        + projectId
                        + "&success=hire"
                );

                return;
            }

            // -------------------------------------------------
            // 6. Hiring failed
            // -------------------------------------------------

            response.sendRedirect(
                    request.getContextPath()
                    + "/ClientProposalsServlet?projectId="
                    + projectId
                    + "&error=hire"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/ClientProposalsServlet?projectId="
                    + projectIdText
                    + "&error=system"
            );
        }
    }
}