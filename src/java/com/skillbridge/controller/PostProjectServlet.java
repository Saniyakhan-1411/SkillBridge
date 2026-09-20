package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.model.Project;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/PostProjectServlet")
public class PostProjectServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute(
                        "loggedInUser"
                ) == null) {

            response.sendRedirect(
                    "login.jsp"
            );

            return;
        }

        Integer clientId =
                (Integer) session.getAttribute(
                        "userId"
                );

        String title =
                request.getParameter("title");

        String description =
                request.getParameter("description");

        String requiredSkills =
                request.getParameter(
                        "requiredSkills"
                );

        String budgetMinText =
                request.getParameter("budgetMin");

        String budgetMaxText =
                request.getParameter("budgetMax");

        String deadlineText =
                request.getParameter("deadlineDays");

        if (title == null ||
                title.trim().isEmpty() ||
                description == null ||
                description.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please complete all required fields."
            );

            request.getRequestDispatcher(
                    "client/post-project.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }

        try {

            double budgetMin =
                    Double.parseDouble(
                            budgetMinText
                    );

            double budgetMax =
                    Double.parseDouble(
                            budgetMaxText
                    );

            int deadlineDays =
                    Integer.parseInt(
                            deadlineText
                    );

            if (budgetMin < 0 ||
                    budgetMax < budgetMin ||
                    deadlineDays <= 0) {

                request.setAttribute(
                        "error",
                        "Please enter valid budget and deadline values."
                );

                request.getRequestDispatcher(
                        "client/post-project.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }

            Project project =
                    new Project();

            project.setClientId(
                    clientId
            );

            project.setTitle(
                    title.trim()
            );

            project.setDescription(
                    description.trim()
            );

            project.setRequiredSkills(
                    requiredSkills == null
                    ? ""
                    : requiredSkills.trim()
            );

            project.setBudgetMin(
                    budgetMin
            );

            project.setBudgetMax(
                    budgetMax
            );

            project.setDeadlineDays(
                    deadlineDays
            );

            ProjectDAO projectDAO =
                    new ProjectDAO();

            boolean created =
                    projectDAO.createProject(
                            project
                    );

            if (created) {

                response.sendRedirect(
                        "client/my-projects.jsp?created=true"
                );

            } else {

                request.setAttribute(
                        "error",
                        "Unable to create project. Please try again."
                );

                request.getRequestDispatcher(
                        "client/post-project.jsp"
                ).forward(
                        request,
                        response
                );
            }

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Please enter valid numbers for budget and deadline."
            );

            request.getRequestDispatcher(
                    "client/post-project.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}