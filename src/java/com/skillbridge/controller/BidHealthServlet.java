package com.skillbridge.controller;

import com.skillbridge.dao.ProjectDAO;
import com.skillbridge.dao.ProposalDAO;
import com.skillbridge.model.ClientProposal;
import com.skillbridge.model.Project;
import com.skillbridge.model.User;
import com.skillbridge.model.BidHealth;
import com.skillbridge.service.BidHealthService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BidHealthServlet")
public class BidHealthServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        /* LOGIN CHECK */
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        /* CLIENT CHECK */
        if (loggedInUser == null || !"CLIENT".equalsIgnoreCase(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        /* PROJECT ID CHECK */
        String projectIdParameter = request.getParameter("projectId");

        if (projectIdParameter == null || projectIdParameter.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdParameter);

            /* LOAD PROJECT */
            ProjectDAO projectDAO = new ProjectDAO();
            Project project = projectDAO.getProjectById(projectId);

            if (project == null) {
                response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
                return;
            }

            /* OWNERSHIP CHECK */
            if (project.getClientId() != loggedInUser.getUserId()) {
                response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
                return;
            }

            /* LOAD PROPOSALS */
            ProposalDAO proposalDAO = new ProposalDAO();
            List<ClientProposal> proposals = proposalDAO.getClientProposalsByProject(projectId);

            /* ANALYZE BID HEALTH */
            BidHealthService service = new BidHealthService();
            BidHealth bidHealth = service.analyze(project, proposals);

            /* SEND DATA TO JSP */
            request.setAttribute("project", project);
            request.setAttribute("proposals", proposals);
            request.setAttribute("bidHealth", bidHealth);

            /* FORWARD TO ROOT JSP FILE LOCATION */
            request.getRequestDispatcher("/bid-health.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/client/my-projects.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to calculate bid health.");
            request.getRequestDispatcher("/bid-health.jsp").forward(request, response);
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