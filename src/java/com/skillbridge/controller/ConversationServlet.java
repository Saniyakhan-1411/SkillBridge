package com.skillbridge.controller;

import com.skillbridge.dao.MessageDAO;
import com.skillbridge.dao.UserDAO;
import com.skillbridge.model.Message;
import com.skillbridge.model.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ConversationServlet")
public class ConversationServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Get existing session
         */
        HttpSession session =
                request.getSession(false);

        /*
         * Check login
         */
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

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

        try {

            /*
             * Get other user's ID
             */
            String userIdParameter =
                    request.getParameter("userId");

            if (userIdParameter == null ||
                    userIdParameter.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/MessagesServlet"
                );

                return;
            }

            int otherUserId =
                    Integer.parseInt(
                            userIdParameter
                    );

            /*
             * Prevent user from opening
             * conversation with themselves.
             */
            if (otherUserId ==
                    loggedInUser.getUserId()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/MessagesServlet"
                );

                return;
            }

            MessageDAO messageDAO =
                    new MessageDAO();

            /*
             * SECURITY CHECK
             *
             * Messaging is allowed only between
             * users connected through an accepted
             * project relationship.
             */
            if (!messageDAO.canMessage(
                    loggedInUser.getUserId(),
                    otherUserId)) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Messaging is only available between users connected through a project."
                );

                return;
            }

            /*
             * Get receiver/other user
             */
            UserDAO userDAO =
                    new UserDAO();

            User otherUser =
                    userDAO.getUserById(
                            otherUserId
                    );

            if (otherUser == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User not found."
                );

                return;
            }

            /*
             * Get conversation
             */
            List<Message> conversation =
                    messageDAO.getConversation(
                            loggedInUser.getUserId(),
                            otherUserId
                    );

            /*
             * Mark messages received by the
             * logged-in user as read.
             */
            messageDAO.markConversationAsRead(
                    loggedInUser.getUserId(),
                    otherUserId
            );

            /*
             * Send data to JSP
             */
            request.setAttribute(
                    "messages",
                    conversation
            );

            request.setAttribute(
                    "otherUser",
                    otherUser
            );

            request.setAttribute(
                    "otherUserId",
                    otherUserId
            );

            /*
             * Open conversation page
             */
            request.getRequestDispatcher(
                    "/conversation.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid user ID."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to open conversation."
            );
        }
    }
}