package com.skillbridge.controller;

import com.skillbridge.dao.FreelancerCareerGrowthDAO;
import com.skillbridge.model.FreelancerCareerGrowth;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/FreelancerCareerGrowthServlet")
public class FreelancerCareerGrowthServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);


        // =====================================================
        // SESSION CHECK
        // =====================================================

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp");

            return;
        }


        User loggedInUser =
                (User) session.getAttribute(
                        "loggedInUser");


        // =====================================================
        // ROLE CHECK
        // =====================================================

        if (!"FREELANCER".equalsIgnoreCase(
                loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/");

            return;
        }


        try {

            int userId =
                    loggedInUser.getUserId();


            FreelancerCareerGrowthDAO dao =
                    new FreelancerCareerGrowthDAO();


            FreelancerCareerGrowth growth =
                    dao.calculateGrowth(userId);


            request.setAttribute(
                    "careerGrowth",
                    growth);


            request.getRequestDispatcher(
                    "/freelancer-career-growth.jsp")
                    .forward(
                            request,
                            response);


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to calculate career growth information.");

            request.getRequestDispatcher(
                    "/freelancer-career-growth.jsp")
                    .forward(
                            request,
                            response);
        }
    }
}