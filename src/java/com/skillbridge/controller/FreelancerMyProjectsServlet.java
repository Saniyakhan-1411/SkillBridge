package com.skillbridge.controller;

import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Project;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/FreelancerMyProjectsServlet")
public class FreelancerMyProjectsServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

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

        if (!"FREELANCER".equalsIgnoreCase(
                loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");

            return;
        }

        try {

            ProposalDAO proposalDAO =
                    new ProposalDAO();

            List<Project> projects =
                    proposalDAO
                    .getProjectsForFreelancer(
                            loggedInUser.getUserId());

            request.setAttribute(
                    "projects",
                    projects);

            request.getRequestDispatcher(
                    "/freelancer/my-projects.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to load your projects.");

            request.getRequestDispatcher(
                    "/freelancer/my-projects.jsp")
                    .forward(request, response);
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