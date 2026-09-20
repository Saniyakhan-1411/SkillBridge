package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.ClientProposal;
import com.skillbridge.model.Project;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ClientProposalsServlet")
public class ClientProposalsServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * ============================================
         * STEP 1: CHECK LOGIN
         * ============================================
         */

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        /*
         * ============================================
         * STEP 2: GET LOGGED-IN USER
         * ============================================
         */

        User loggedInUser =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        /*
         * ============================================
         * STEP 3: CLIENT ONLY
         * ============================================
         */

        if (!"CLIENT".equalsIgnoreCase(
                loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        /*
         * ============================================
         * STEP 4: GET PROJECT ID
         * ============================================
         */

        String projectIdText =
                request.getParameter("projectId");

        if (projectIdText == null ||
                projectIdText.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );

            return;
        }

        int projectId;

        try {

            projectId =
                    Integer.parseInt(
                            projectIdText.trim()
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/client/my-projects.jsp"
            );

            return;
        }

        /*
         * ============================================
         * STEP 5: LOAD PROJECT
         * ============================================
         */

        try {

            ProjectDAO projectDAO =
                    new ProjectDAO();

            Project project =
                    projectDAO.getProjectById(
                            projectId
                    );

            if (project == null) {

                request.setAttribute(
                        "error",
                        "Project not found."
                );

                request.getRequestDispatcher(
                        "/client/my-projects.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            /*
             * ========================================
             * STEP 6: SECURITY
             *
             * Only the client who owns the project
             * can see its proposals.
             * ========================================
             */

            if (project.getClientId()
                    != loggedInUser.getUserId()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/client/my-projects.jsp"
                );

                return;
            }

            /*
             * ============================================
             * STEP 7: LOAD CLIENT PROPOSALS
             * ============================================
             */

            ProposalDAO proposalDAO =
                    new ProposalDAO();

            List<ClientProposal> proposals =
                    proposalDAO
                    .getClientProposalsByProject(
                            projectId
                    );

            /*
             * ============================================
             * STEP 8: CALCULATE BID HEALTH
             * ============================================
             */

            double averageBid = 0;
            double lowestBid = 0;
            double highestBid = 0;

            if (proposals != null &&
                    !proposals.isEmpty()) {

                double totalBid = 0;

                lowestBid =
                        proposals.get(0)
                                .getBidAmount();

                highestBid =
                        proposals.get(0)
                                .getBidAmount();

                for (ClientProposal proposal
                        : proposals) {

                    double bid =
                            proposal.getBidAmount();

                    totalBid += bid;

                    if (bid < lowestBid) {
                        lowestBid = bid;
                    }

                    if (bid > highestBid) {
                        highestBid = bid;
                    }
                }

                averageBid =
                        totalBid / proposals.size();
            }

            /*
             * ============================================
             * STEP 9: DETERMINE BID HEALTH
             * ============================================
             */

            String bidHealth =
                    "NO_BIDS";

            String bidHealthMessage =
                    "Freelancers have not submitted "
                    + "proposals yet.";

            if (proposals != null &&
                    !proposals.isEmpty()) {

                if (averageBid <
                        project.getBudgetMin()) {

                    bidHealth = "LOW";

                    bidHealthMessage =
                            "The average bid is below "
                            + "your minimum budget.";
                }

                else if (averageBid <=
                        project.getBudgetMax()) {

                    bidHealth = "HEALTHY";

                    bidHealthMessage =
                            "Most bids are within "
                            + "your project budget.";
                }

                else {

                    bidHealth = "HIGH";

                    bidHealthMessage =
                            "The average bid is above "
                            + "your maximum budget.";
                }
            }

            /*
             * ============================================
             * STEP 10: SEND DATA TO JSP
             * ============================================
             */

            request.setAttribute(
                    "project",
                    project
            );

            request.setAttribute(
                    "proposals",
                    proposals
            );

            request.setAttribute(
                    "averageBid",
                    averageBid
            );

            request.setAttribute(
                    "lowestBid",
                    lowestBid
            );

            request.setAttribute(
                    "highestBid",
                    highestBid
            );

            request.setAttribute(
                    "bidHealth",
                    bidHealth
            );

            request.setAttribute(
                    "bidHealthMessage",
                    bidHealthMessage
            );

            /*
             * ============================================
             * STEP 11: FORWARD TO PROPOSALS PAGE
             * ============================================
             */

            request.getRequestDispatcher(
                    "/client/proposals.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to load proposals. "
                    + "Please try again."
            );

            request.getRequestDispatcher(
                    "/client/my-projects.jsp"
            ).forward(
                    request,
                    response
            );
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