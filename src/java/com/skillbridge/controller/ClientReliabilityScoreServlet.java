package com.skillbridge.controller;

import com.skillbridge.dao.ClientReliabilityScoreDAO;
import com.skillbridge.model.ClientReliabilityScore;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ClientReliabilityScoreServlet")
public class ClientReliabilityScoreServlet
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

        if (!"CLIENT".equalsIgnoreCase(
                loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/");

            return;
        }


        try {

            int userId =
                    loggedInUser.getUserId();


            // =================================================
            // CALCULATE RELIABILITY SCORE
            // =================================================

            ClientReliabilityScoreDAO dao =
                    new ClientReliabilityScoreDAO();

            ClientReliabilityScore reliabilityScore =
                    dao.calculateScore(userId);


            request.setAttribute(
                    "reliabilityScore",
                    reliabilityScore);


            request.getRequestDispatcher(
                    "/client-reliability-score.jsp")
                    .forward(
                            request,
                            response);


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to calculate client reliability score.");

            request.getRequestDispatcher(
                    "/client-reliability-score.jsp")
                    .forward(
                            request,
                            response);
        }
    }
}