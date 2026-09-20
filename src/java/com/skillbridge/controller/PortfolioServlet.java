package com.skillbridge.controller;

import com.skillbridge.dao.PortfolioDAO;
import com.skillbridge.model.Portfolio;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/PortfolioServlet")
public class PortfolioServlet
        extends HttpServlet {

    private PortfolioDAO portfolioDAO;

    @Override
    public void init() {

        portfolioDAO =
                new PortfolioDAO();
    }


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


        if (loggedInUser == null ||
                !"FREELANCER".equalsIgnoreCase(
                        loggedInUser.getRole())) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        List<Portfolio> portfolioList =
                portfolioDAO.getPortfolioByFreelancer(
                        loggedInUser.getUserId()
                );


        request.setAttribute(
                "portfolioList",
                portfolioList
        );


        request.getRequestDispatcher(
                "/freelancer/portfolio.jsp"
        ).forward(
                request,
                response
        );
    }
}