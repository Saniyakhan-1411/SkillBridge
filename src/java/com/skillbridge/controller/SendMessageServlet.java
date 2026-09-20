package com.skillbridge.controller;

import com.skillbridge.dao.MessageDAO;
import com.skillbridge.dao.NotificationDAO;
import com.skillbridge.model.Message;
import com.skillbridge.model.Notification;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
            request.getSession(false);

        // -----------------------------------------
        // LOGIN CHECK
        // -----------------------------------------
        if (session == null ||
            session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
            );

            return;
        }

        User loggedInUser =
            (User) session.getAttribute("loggedInUser");

        try {

            // -----------------------------------------
            // GET RECEIVER
            // -----------------------------------------
            int receiverId =
                Integer.parseInt(
                    request.getParameter("receiverId")
                );

            // -----------------------------------------
            // GET MESSAGE
            // -----------------------------------------
            String messageText =
                request.getParameter("messageText");

            if (messageText == null) {
                messageText = "";
            }

            messageText = messageText.trim();

            // -----------------------------------------
            // EMPTY MESSAGE CHECK
            // -----------------------------------------
            if (messageText.isEmpty()) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/ConversationServlet?userId="
                    + receiverId
                );

                return;
            }

            // -----------------------------------------
            // MAXIMUM MESSAGE LENGTH
            // -----------------------------------------
            if (messageText.length() > 2000) {

                messageText =
                    messageText.substring(0, 2000);
            }

            // -----------------------------------------
            // SELF MESSAGE CHECK
            // -----------------------------------------
            if (receiverId ==
                loggedInUser.getUserId()) {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "You cannot message yourself."
                );

                return;
            }

            MessageDAO messageDAO =
                new MessageDAO();

            // -----------------------------------------
            // SECURITY CHECK
            // Only users connected through an
            // accepted project can communicate.
            // -----------------------------------------
            if (!messageDAO.canMessage(
                    loggedInUser.getUserId(),
                    receiverId)) {

                response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You can only message users connected through an accepted project."
                );

                return;
            }

            // -----------------------------------------
            // CREATE MESSAGE
            // -----------------------------------------
            Message message =
                new Message();

            message.setSenderId(
                loggedInUser.getUserId()
            );

            message.setReceiverId(
                receiverId
            );

            message.setMessageText(
                messageText
            );

            // -----------------------------------------
            // SEND MESSAGE
            // -----------------------------------------
            boolean sent =
                messageDAO.sendMessage(message);

            // -----------------------------------------
            // CREATE NOTIFICATION
            // Only create notification if the
            // message was successfully sent.
            // -----------------------------------------
            if (sent) {

                NotificationDAO notificationDAO =
                    new NotificationDAO();

                Notification notification =
                    new Notification(
                        receiverId,
                        "New Message",
                        loggedInUser.getFullName()
                        + " sent you a new message."
                    );

                notificationDAO.createNotification(
                    notification
                );

                // -----------------------------------------
                // RETURN TO CONVERSATION
                // -----------------------------------------
                response.sendRedirect(
                    request.getContextPath()
                    + "/ConversationServlet?userId="
                    + receiverId
                );

                return;
            }

            // -----------------------------------------
            // MESSAGE SEND FAILED
            // -----------------------------------------
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to send message."
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid receiver ID."
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to send message."
            );
        }
    }
}