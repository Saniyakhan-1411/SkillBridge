package com.skillbridge.controller;

import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ReviewDAO;

import com.skillbridge.model.Notification;
import com.skillbridge.model.Project;
import com.skillbridge.model.Review;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet
        extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Check login
         */
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

        User reviewer =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        /*
         * Get form values
         */
        String projectIdText =
                request.getParameter("projectId");

        String ratingText =
                request.getParameter("rating");

        String reviewText =
                request.getParameter("reviewText");

        if (projectIdText == null
                || ratingText == null
                || reviewText == null) {

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

            int rating =
                    Integer.parseInt(
                            ratingText
                    );

            reviewText =
                    reviewText.trim();

            /*
             * Validate rating
             */
            if (rating < 1 || rating > 5) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=rating"
                );

                return;
            }

            /*
             * Validate review text
             */
            if (reviewText.isEmpty()
                    || reviewText.length() > 1000) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=text"
                );

                return;
            }

            /*
             * Get project
             */
            ProjectDAO projectDAO =
                    new ProjectDAO();

            Project project =
                    projectDAO.getProjectById(
                            projectId
                    );

            if (project == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/client/my-projects.jsp"
                );

                return;
            }

            /*
             * Reviews are allowed only after
             * project completion.
             */
            if (!"COMPLETED".equals(
                    project.getProjectStatus())) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=notcompleted"
                );

                return;
            }

            /*
             * Get accepted freelancer
             */
            ProposalDAO proposalDAO =
                    new ProposalDAO();

            int freelancerId =
                    proposalDAO.getAcceptedFreelancerId(
                            projectId
                    );

            if (freelancerId <= 0) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=nofreelancer"
                );

                return;
            }

            int reviewedUserId;

            /*
             * Validate reviewer role
             */
            if (reviewer.getRole() == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );

                return;
            }

            /*
             * CLIENT reviews FREELANCER
             */
            if ("CLIENT".equalsIgnoreCase(
                    reviewer.getRole())) {

                /*
                 * Only project owner can review
                 */
                if (project.getClientId()
                        != reviewer.getUserId()) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/client/my-projects.jsp"
                    );

                    return;
                }

                reviewedUserId =
                        freelancerId;
            }

            /*
             * FREELANCER reviews CLIENT
             */
            else if ("FREELANCER".equalsIgnoreCase(
                    reviewer.getRole())) {

                /*
                 * Freelancer must be the
                 * accepted freelancer.
                 */
                if (freelancerId
                        != reviewer.getUserId()) {

                    response.sendRedirect(
                            request.getContextPath()
                            + "/freelancer/my-projects.jsp"
                    );

                    return;
                }

                reviewedUserId =
                        project.getClientId();
            }

            /*
             * Invalid role
             */
            else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/login.jsp"
                );

                return;
            }

            /*
             * Prevent self-review
             */
            if (reviewedUserId
                    == reviewer.getUserId()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=self"
                );

                return;
            }

            /*
             * One review per participant
             * per project
             */
            ReviewDAO reviewDAO =
                    new ReviewDAO();

            if (reviewDAO.hasReviewed(
                    projectId,
                    reviewer.getUserId())) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=duplicate"
                );

                return;
            }

            /*
             * Create Review object
             */
            Review review =
                    new Review();

            review.setProjectId(
                    projectId
            );

            review.setReviewerId(
                    reviewer.getUserId()
            );

            review.setReviewedUserId(
                    reviewedUserId
            );

            review.setRating(
                    rating
            );

            review.setReviewText(
                    reviewText
            );

            /*
             * Save review
             */
            boolean created =
                    reviewDAO.createReview(
                            review
                    );

            if (created) {

                /*
                 * =================================
                 * CREATE REVIEW NOTIFICATION
                 * =================================
                 */
                NotificationDAO notificationDAO =
                        new NotificationDAO();

                Notification notification =
                        new Notification(
                                reviewedUserId,
                                "New Review Received",
                                reviewer.getFullName()
                                + " left a new review on your completed project."
                        );

                notificationDAO.createNotification(
                        notification
                );

                /*
                 * Redirect after successful review
                 */
                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&success=1"
                );

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/ReviewServlet?projectId="
                        + projectId
                        + "&error=system"
                );
            }

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );
        }
    }
}