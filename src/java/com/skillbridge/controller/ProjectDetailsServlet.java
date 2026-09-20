package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProjectStatusHistoryDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Project;
import com.skillbridge.model.ProjectStatusHistory;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProjectDetailsServlet", urlPatterns = {"/ProjectDetailsServlet"})
public class ProjectDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("========================================");
        System.out.println("ProjectDetailsServlet STARTED");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Project ID: " + request.getParameter("projectId"));

        /*
         * STEP 1: SESSION CHECK
         */

        HttpSession session = request.getSession(false);

        if (session == null) {

            System.out.println("ERROR: Session is NULL");

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return;
        }

        /*
         * STEP 2: USER CHECK
         */

        User loggedInUser =
                (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {

            System.out.println("ERROR: loggedInUser is NULL");

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return;
        }

        System.out.println(
                "Logged User ID: "
                + loggedInUser.getUserId()
        );

        System.out.println(
                "Logged User Role: "
                + loggedInUser.getRole()
        );

        /*
         * STEP 3: CLIENT ROLE CHECK
         */

        if (loggedInUser.getRole() == null
                || !"CLIENT".equalsIgnoreCase(
                        loggedInUser.getRole())) {

            System.out.println("ERROR: User is not CLIENT");

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Client access required."
            );

            return;
        }

        /*
         * STEP 4: PROJECT ID
         */

        String projectIdText =
                request.getParameter("projectId");

        if (projectIdText == null
                || projectIdText.trim().isEmpty()) {

            System.out.println(
                    "ERROR: projectId parameter missing"
            );

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Project ID is missing."
            );

            return;
        }

        /*
         * STEP 5: PARSE PROJECT ID
         */

        int projectId;

        try {

            projectId =
                    Integer.parseInt(
                            projectIdText.trim()
                    );

        } catch (NumberFormatException e) {

            System.out.println(
                    "ERROR: Invalid projectId = "
                    + projectIdText
            );

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid project ID."
            );

            return;
        }

        System.out.println(
                "Parsed Project ID: "
                + projectId
        );

        /*
         * STEP 6: LOAD PROJECT
         */

        try {

            ProjectDAO projectDAO =
                    new ProjectDAO();

            System.out.println(
                    "Calling ProjectDAO.getProjectById()..."
            );

            Project project =
                    projectDAO.getProjectById(
                            projectId
                    );

            /*
             * CHECK PROJECT
             */

            if (project == null) {

                System.out.println(
                        "ERROR: ProjectDAO returned NULL"
                );

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Project not found for ID: "
                        + projectId
                );

                return;
            }

            System.out.println(
                    "Project FOUND"
            );

            System.out.println(
                    "Project ID: "
                    + project.getProjectId()
            );

            System.out.println(
                    "Project Title: "
                    + project.getTitle()
            );

            System.out.println(
                    "Project Client ID: "
                    + project.getClientId()
            );

            /*
             * STEP 7: OWNER CHECK
             */

            if (project.getClientId()
                    != loggedInUser.getUserId()) {

                System.out.println(
                        "SECURITY ERROR: Project does not belong to logged-in client"
                );

                System.out.println(
                        "Project Client ID = "
                        + project.getClientId()
                );

                System.out.println(
                        "Logged User ID = "
                        + loggedInUser.getUserId()
                );

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You are not authorized to view this project."
                );

                return;
            }

            System.out.println(
                    "Project ownership verified."
            );

            /*
             * STEP 8: LOAD STATUS HISTORY
             */

            ProjectStatusHistoryDAO historyDAO =
                    new ProjectStatusHistoryDAO();

            List<ProjectStatusHistory> history =
                    historyDAO.getHistoryByProjectId(
                            projectId
                    );

            if (history == null) {

                history =
                        new ArrayList<ProjectStatusHistory>();
            }

            System.out.println(
                    "Status history loaded: "
                    + history.size()
                    + " records"
            );

            /*
             * STEP 9: LOAD ACCEPTED FREELANCER
             */

            Integer acceptedFreelancerId = null;

            try {

                ProposalDAO proposalDAO =
                        new ProposalDAO();

                int freelancerId =
                        proposalDAO.getAcceptedFreelancerId(
                                projectId
                        );

                if (freelancerId > 0) {

                    acceptedFreelancerId =
                            freelancerId;
                }

                System.out.println(
                        "Accepted Freelancer ID: "
                        + acceptedFreelancerId
                );

            } catch (Exception e) {

                /*
                 * Do not stop the whole project details
                 * page if this optional feature fails.
                 */

                System.out.println(
                        "WARNING: Could not load accepted freelancer."
                );

                e.printStackTrace();
            }

            /*
             * STEP 10: SET REQUEST ATTRIBUTES
             */

            request.setAttribute(
                    "project",
                    project
            );

            request.setAttribute(
                    "statusHistory",
                    history
            );

            request.setAttribute(
                    "acceptedFreelancerId",
                    acceptedFreelancerId
            );

            /*
             * STEP 11: FORWARD TO JSP
             */

            System.out.println(
                    "Forwarding to:"
                    + " /client/project-details.jsp"
            );

            request.getRequestDispatcher(
                    "/client/project-details.jsp"
            ).forward(
                    request,
                    response
            );

            System.out.println(
                    "ProjectDetailsServlet COMPLETED"
            );

            System.out.println("========================================");

        } catch (Exception e) {

            /*
             * REAL ERROR
             */

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "ProjectDetailsServlet ERROR"
            );

            e.printStackTrace();

            System.out.println(
                    "========================================"
            );

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load project details: "
                    + e.getMessage()
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