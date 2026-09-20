package com.skillbridge.controller;

import com.skillbridge.dao.TrustScoreDAO;
import com.skillbridge.model.TrustScore;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/TrustScoreServlet")
public class TrustScoreServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        User loggedInUser =
                (User) session.getAttribute(
                        "loggedInUser"
                );

        if (loggedInUser == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        try {

            TrustScoreDAO trustScoreDAO =
                    new TrustScoreDAO();

            TrustScore trustScore =
                    trustScoreDAO.calculateTrustScore(
                            loggedInUser.getUserId(),
                            loggedInUser.getRole()
                    );

            request.setAttribute(
                    "trustScore",
                    trustScore
            );

            request.getRequestDispatcher(
                    "/trust-score.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to calculate Trust Score."
            );

            request.getRequestDispatcher(
                    "/trust-score.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}