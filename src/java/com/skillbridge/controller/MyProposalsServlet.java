package com.skillbridge.controller;

import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.Proposal;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/MyProposalsServlet")
public class MyProposalsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Check login
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        // Check role
        String role =
                (String) session.getAttribute("role");

        if (!"FREELANCER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        try {

            User freelancer =
                    (User) session.getAttribute(
                            "loggedInUser"
                    );

            ProposalDAO proposalDAO =
                    new ProposalDAO();

            List<Proposal> proposals =
                    proposalDAO.getProposalsByFreelancer(
                            freelancer.getUserId()
                    );

            request.setAttribute(
                    "proposals",
                    proposals
            );

            request.getRequestDispatcher(
                    "/freelancer/my-proposals.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to load your proposals. Please try again."
            );

            request.getRequestDispatcher(
                    "/freelancer/my-proposals.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}