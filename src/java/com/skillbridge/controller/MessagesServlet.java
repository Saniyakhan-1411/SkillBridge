package com.skillbridge.controller;

import com.skillbridge.dao.MessageDAO;
import com.skillbridge.dao.UserDAO;
import com.skillbridge.model.Message;
import com.skillbridge.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MessagesServlet")
public class MessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session == null ||
        session.getAttribute("loggedInUser") == null) {

        response.sendRedirect(
            request.getContextPath() + "/login.jsp"
        );

        return;
    }

    User loggedInUser =
        (User) session.getAttribute("loggedInUser");

    MessageDAO messageDAO = new MessageDAO();

    try {

        List<Message> messages =
            messageDAO.getInbox(loggedInUser.getUserId());

        int unreadCount =
            messageDAO.getUnreadCount(
                loggedInUser.getUserId()
            );

        List<User> messagingUsers;

        if ("CLIENT".equals(loggedInUser.getRole())) {

            messagingUsers =
                messageDAO.getMessagingFreelancers(
                    loggedInUser.getUserId()
                );

        } else if ("FREELANCER".equals(loggedInUser.getRole())) {

            messagingUsers =
                messageDAO.getMessagingClients(
                    loggedInUser.getUserId()
                );

        } else {

            messagingUsers = new ArrayList<User>();
        }

        request.setAttribute(
            "messages",
            messages
        );

        request.setAttribute(
            "unreadCount",
            unreadCount
        );

        request.setAttribute(
            "messagingUsers",
            messagingUsers
        );

        request.getRequestDispatcher(
            "/messages.jsp"
        ).forward(request, response);

    } catch (Exception e) {

        e.printStackTrace();

        response.sendError(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Unable to load messages."
        );
    }
    }
}