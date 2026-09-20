package com.skillbridge.controller;

import com.skillbridge.dao.FreelancerProfileDAO;
import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProposalDAO;

import com.skillbridge.model.FreelancerProfile;
import com.skillbridge.model.Notification;
import com.skillbridge.model.Project;
import com.skillbridge.model.Proposal;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitProposalServlet")
public class SubmitProposalServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // -----------------------------------------
        // LOGIN CHECK
        // -----------------------------------------
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        User freelancer =
                (User) session.getAttribute("loggedInUser");

        // -----------------------------------------
        // ROLE CHECK
        // -----------------------------------------
        if (!"FREELANCER".equalsIgnoreCase(
                freelancer.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        String projectIdText =
                request.getParameter("projectId");

        if (projectIdText == null ||
                projectIdText.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/BrowseProjectsServlet"
            );

            return;
        }

        try {

            int projectId =
                    Integer.parseInt(projectIdText);

            ProjectDAO projectDAO =
                    new ProjectDAO();

            Project project =
                    projectDAO.getProjectById(projectId);

            if (project == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BrowseProjectsServlet"
                );

                return;
            }

            // -----------------------------------------
            // PROJECT MUST BE OPEN
            // -----------------------------------------
            if (!"OPEN".equalsIgnoreCase(
                    project.getProjectStatus())) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/FreelancerProjectDetailsServlet?projectId="
                        + projectId
                );

                return;
            }

            request.setAttribute(
                    "project",
                    project
            );

            request.getRequestDispatcher(
                    "/freelancer/submit-proposal.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/BrowseProjectsServlet"
            );
        }
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // -----------------------------------------
        // LOGIN CHECK
        // -----------------------------------------
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        User freelancer =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        // -----------------------------------------
        // ROLE CHECK
        // -----------------------------------------
        if (!"FREELANCER".equalsIgnoreCase(
                freelancer.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        String projectIdText =
                request.getParameter("projectId");

        String coverLetter =
                request.getParameter("coverLetter");

        String bidAmountText =
                request.getParameter("bidAmount");

        String deliveryDaysText =
                request.getParameter("deliveryDays");


        // -----------------------------------------
        // BASIC VALIDATION
        // -----------------------------------------
        if (projectIdText == null ||
                coverLetter == null ||
                bidAmountText == null ||
                deliveryDaysText == null ||
                coverLetter.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please complete all required fields."
            );

            request.setAttribute(
                    "project",
                    getProject(projectIdText)
            );

            request.getRequestDispatcher(
                    "/freelancer/submit-proposal.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }


        try {

            int projectId =
                    Integer.parseInt(
                            projectIdText
                    );

            double bidAmount =
                    Double.parseDouble(
                            bidAmountText
                    );

            int deliveryDays =
                    Integer.parseInt(
                            deliveryDaysText
                    );


            // -----------------------------------------
            // POSITIVE VALUE VALIDATION
            // -----------------------------------------
            if (bidAmount <= 0 ||
                    deliveryDays <= 0) {

                request.setAttribute(
                        "error",
                        "Bid amount and delivery days must be greater than zero."
                );

                request.setAttribute(
                        "project",
                        getProject(projectIdText)
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // -----------------------------------------
            // GET PROJECT
            // -----------------------------------------
            ProjectDAO projectDAO =
                    new ProjectDAO();

            Project project =
                    projectDAO.getProjectById(
                            projectId
                    );


            if (project == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/BrowseProjectsServlet"
                );

                return;
            }


            // -----------------------------------------
            // PROJECT STATUS CHECK
            // -----------------------------------------
            if (!"OPEN".equalsIgnoreCase(
                    project.getProjectStatus())) {

                request.setAttribute(
                        "error",
                        "This project is no longer accepting proposals."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // -----------------------------------------
            // PREVENT OWN PROJECT BIDDING
            // -----------------------------------------
            if (project.getClientId() ==
                    freelancer.getUserId()) {

                request.setAttribute(
                        "error",
                        "You cannot submit a proposal to your own project."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // -----------------------------------------
            // DUPLICATE PROPOSAL CHECK
            // -----------------------------------------
            ProposalDAO proposalDAO =
                    new ProposalDAO();

            if (proposalDAO.hasAlreadySubmitted(
                    projectId,
                    freelancer.getUserId())) {

                request.setAttribute(
                        "error",
                        "You have already submitted a proposal for this project."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // -----------------------------------------
            // GET FREELANCER PROFILE
            // -----------------------------------------
            FreelancerProfileDAO profileDAO =
                    new FreelancerProfileDAO();

            FreelancerProfile freelancerProfile =
                    profileDAO.getProfileByUserId(
                            freelancer.getUserId()
                    );


            // -----------------------------------------
            // CHECK FREELANCER SKILLS
            // -----------------------------------------
            if (freelancerProfile == null ||
                    freelancerProfile.getSkills() == null ||
                    freelancerProfile.getSkills()
                            .trim()
                            .isEmpty()) {

                request.setAttribute(
                        "error",
                        "Please complete your freelancer profile and add your skills before submitting a proposal."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            // -----------------------------------------
            // CALCULATE SKILL MATCH SCORE
            // -----------------------------------------
            int skillMatchScore =
                    calculateSkillMatch(
                            project.getRequiredSkills(),
                            freelancerProfile.getSkills()
                    );


            // -----------------------------------------
            // CREATE PROPOSAL
            // -----------------------------------------
            Proposal proposal =
                    new Proposal();

            proposal.setProjectId(
                    projectId
            );

            proposal.setFreelancerId(
                    freelancer.getUserId()
            );

            proposal.setCoverLetter(
                    coverLetter.trim()
            );

            proposal.setBidAmount(
                    bidAmount
            );

            proposal.setDeliveryDays(
                    deliveryDays
            );

            proposal.setSkillMatchScore(
                    skillMatchScore
            );


            // -----------------------------------------
            // SAVE PROPOSAL
            // -----------------------------------------
            boolean success =
                    proposalDAO.createProposal(
                            proposal
                    );


            // -----------------------------------------
            // SUCCESS
            // -----------------------------------------
            if (success) {

                // -------------------------------------
                // CREATE NOTIFICATION FOR CLIENT
                // -------------------------------------
                try {

                    NotificationDAO notificationDAO =
                            new NotificationDAO();

                    Notification notification =
                            new Notification(
                                    project.getClientId(),
                                    "New Proposal Received",
                                    freelancer.getFullName()
                                    + " submitted a proposal for your project."
                            );

                    notificationDAO.createNotification(
                            notification
                    );

                } catch (Exception notificationError) {

                    /*
                     * Proposal was already successfully
                     * submitted. Therefore, notification
                     * failure should not make the
                     * proposal fail.
                     */
                    notificationError.printStackTrace();
                }


                // -------------------------------------
                // REDIRECT AFTER EVERYTHING IS DONE
                // -------------------------------------
                response.sendRedirect(
                        request.getContextPath()
                        + "/MyProposalsServlet?success=1"
                );

                return;

            } else {

                request.setAttribute(
                        "error",
                        "Unable to submit proposal. Please try again."
                );

                request.setAttribute(
                        "project",
                        project
                );

                request.getRequestDispatcher(
                        "/freelancer/submit-proposal.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Please enter valid numbers for bid amount and delivery days."
            );

            request.setAttribute(
                    "project",
                    getProject(projectIdText)
            );

            request.getRequestDispatcher(
                    "/freelancer/submit-proposal.jsp"
            ).forward(
                    request,
                    response
            );

            return;


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "An unexpected error occurred while submitting your proposal."
            );

            request.setAttribute(
                    "project",
                    getProject(projectIdText)
            );

            request.getRequestDispatcher(
                    "/freelancer/submit-proposal.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }
    }


    // =========================================
    // GET PROJECT
    // =========================================

    private Project getProject(
            String projectIdText) {

        try {

            int projectId =
                    Integer.parseInt(
                            projectIdText
                    );

            ProjectDAO projectDAO =
                    new ProjectDAO();

            return projectDAO.getProjectById(
                    projectId
            );

        } catch (Exception e) {

            return null;
        }
    }


    // =========================================
    // SKILL MATCH CALCULATION
    // =========================================

    private int calculateSkillMatch(
            String requiredSkills,
            String freelancerSkills) {

        if (requiredSkills == null ||
                requiredSkills.trim().isEmpty()) {

            return 0;
        }

        if (freelancerSkills == null ||
                freelancerSkills.trim().isEmpty()) {

            return 0;
        }


        Set<String> freelancerSkillSet =
                new HashSet<String>();


        String[] freelancerSkillsArray =
                freelancerSkills.split(",");


        for (String skill :
                freelancerSkillsArray) {

            String normalizedSkill =
                    normalizeSkill(skill);

            if (!normalizedSkill.isEmpty()) {

                freelancerSkillSet.add(
                        normalizedSkill
                );
            }
        }


        int totalRequiredSkills = 0;

        int matchedSkills = 0;


        String[] requiredSkillsArray =
                requiredSkills.split(",");


        for (String skill :
                requiredSkillsArray) {

            String normalizedRequiredSkill =
                    normalizeSkill(skill);


            if (normalizedRequiredSkill.isEmpty()) {

                continue;
            }


            totalRequiredSkills++;


            if (freelancerSkillSet.contains(
                    normalizedRequiredSkill)) {

                matchedSkills++;
            }
        }


        if (totalRequiredSkills == 0) {

            return 0;
        }


        int score =
                (matchedSkills * 100)
                / totalRequiredSkills;


        return Math.min(
                score,
                100
        );
    }


    // =========================================
    // NORMALIZE SKILL
    // =========================================

    private String normalizeSkill(
            String skill) {

        if (skill == null) {

            return "";
        }

        return skill
                .trim()
                .toLowerCase()
                .replaceAll(
                        "\\s+",
                        " "
                );
    }
}