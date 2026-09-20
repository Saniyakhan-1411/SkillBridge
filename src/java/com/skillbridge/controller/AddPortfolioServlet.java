package com.skillbridge.controller;

import com.skillbridge.dao.PortfolioDAO;
import com.skillbridge.model.Portfolio;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/AddPortfolioServlet")
public class AddPortfolioServlet
        extends HttpServlet {

    private PortfolioDAO portfolioDAO;


    @Override
    public void init() {

        portfolioDAO =
                new PortfolioDAO();
    }


    @Override
    protected void doPost(
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


        String title =
                request.getParameter(
                        "projectTitle"
                );

        String description =
                request.getParameter(
                        "projectDescription"
                );

        String technologies =
                request.getParameter(
                        "technologies"
                );

        String projectUrl =
                request.getParameter(
                        "projectUrl"
                );

        String githubUrl =
                request.getParameter(
                        "githubUrl"
                );


        if (title == null ||
                title.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Project title is required."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/freelancer/portfolio.jsp"
            );

            return;
        }


        Portfolio portfolio =
                new Portfolio();


        portfolio.setFreelancerId(
                loggedInUser.getUserId()
        );

        portfolio.setProjectTitle(
                title.trim()
        );

        portfolio.setProjectDescription(
                description
        );

        portfolio.setTechnologies(
                technologies
        );

        portfolio.setProjectUrl(
                projectUrl
        );

        portfolio.setGithubUrl(
                githubUrl
        );


        boolean success =
                portfolioDAO.addPortfolio(
                        portfolio
                );


        if (success) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/PortfolioServlet?success=added"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                    + "/freelancer/portfolio.jsp?error=failed"
            );
        }
    }
}