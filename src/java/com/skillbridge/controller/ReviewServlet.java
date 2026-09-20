package com.skillbridge.controller;

import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.dao.ReviewDAO;
import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.model.Project;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ReviewServlet")
public class ReviewServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        String projectIdText =
                request.getParameter("projectId");

        if (projectIdText == null
                || projectIdText.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/"
            );

            return;
        }

        try {

            int projectId =
                    Integer.parseInt(
                            projectIdText
                    );

            ProjectDAO projectDAO =
                    new ProjectDAO();

            Project project =
                    projectDAO.getProjectById(
                            projectId
                    );

            if (project == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/"
                );

                return;
            }

            /*
             * Project must be completed.
             */

            if (!"COMPLETED".equals(
                    project.getProjectStatus())) {

                request.setAttribute(
                        "error",
                        "Reviews are available only after project completion."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/review.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            ProposalDAO proposalDAO =
                    new ProposalDAO();

            int freelancerId =
                    proposalDAO.getAcceptedFreelancerId(
                            projectId
                    );

            if (freelancerId <= 0) {

                request.setAttribute(
                        "error",
                        "No hired freelancer was found."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/review.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            int reviewedUserId;

            String reviewedRole;

            /*
             * Client → Freelancer
             */

            if ("CLIENT".equals(user.getRole())) {

                if (project.getClientId()
                        != user.getUserId()) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/client/my-projects.jsp"
                    );

                    return;
                }

                reviewedUserId =
                        freelancerId;

                reviewedRole =
                        "Freelancer";

            }

            /*
             * Freelancer → Client
             */

            else if ("FREELANCER".equals(
                    user.getRole())) {

                if (freelancerId
                        != user.getUserId()) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/freelancer/my-projects.jsp"
                    );

                    return;
                }

                reviewedUserId =
                        project.getClientId();

                reviewedRole =
                        "Client";

            }

            else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );

                return;
            }

            ReviewDAO reviewDAO =
                    new ReviewDAO();

            boolean alreadyReviewed =
                    reviewDAO.hasReviewed(
                            projectId,
                            user.getUserId()
                    );

            request.setAttribute(
                    "project",
                    project
            );

            request.setAttribute(
                    "reviewedUserId",
                    reviewedUserId
            );

            request.setAttribute(
                    "reviewedRole",
                    reviewedRole
            );

            request.setAttribute(
                    "alreadyReviewed",
                    alreadyReviewed
            );

            request.setAttribute(
                    "success",
                    request.getParameter("success")
            );

            request.setAttribute(
                    "error",
                    request.getParameter("error")
            );

            request.getRequestDispatcher(
                    "/review.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/"
            );
        }
    }
}