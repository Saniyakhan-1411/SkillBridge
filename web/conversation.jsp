<%@ page import="com.skillbridge.model.User" %>
<%@ page import="com.skillbridge.model.Message" %>
<%@ page import="java.util.List" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }

    List<Message> messages =
            (List<Message>) request.getAttribute(
                    "messages"
            );

    Integer otherUserId =
            (Integer) request.getAttribute(
                    "otherUserId"
            );

    String otherUserName =
            "SkillBridge User";

    if (messages != null
            && !messages.isEmpty()) {

        for (Message message : messages) {

            if (message.getSenderId()
                    == otherUserId) {

                otherUserName =
                        message.getSenderName();

                break;
            }
        }
    }
%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<style>

    .conversation-page {
        min-height: 100vh;
        background: #f5f7f9;
        padding: 40px 20px 70px;
    }

    .conversation-container {
        width: 92%;
        max-width: 850px;
        margin: auto;
    }

    .conversation-card {
        background: #ffffff;
        border: 1px solid #e5e7eb;
        border-radius: 18px;
        overflow: hidden;
    }

    .conversation-header {
        padding: 22px 25px;
        border-bottom: 1px solid #edf0f2;
        background: #ffffff;
    }

    .conversation-header h1 {
        margin: 0 0 5px;
        color: #17324d;
        font-size: 20px;
    }

    .conversation-header p {
        margin: 0;
        color: #667085;
        font-size: 12px;
    }

    .chat-body {
        min-height: 420px;
        max-height: 550px;
        overflow-y: auto;
        padding: 25px;
        background: #f8faf9;
    }

    .chat-message {
        display: flex;
        margin-bottom: 15px;
    }

    .chat-message.mine {
        justify-content: flex-end;
    }

    .chat-bubble {
        max-width: 70%;
        padding: 12px 16px;
        border-radius: 14px;
        background: #ffffff;
        border: 1px solid #e1e6e3;
    }

    .chat-message.mine .chat-bubble {
        background: #176b4d;
        border-color: #176b4d;
        color: #ffffff;
    }

    .chat-text {
        margin: 0;
        font-size: 13px;
        line-height: 1.6;
        white-space: pre-wrap;
        word-break: break-word;
    }

    .chat-time {
        margin-top: 6px;
        color: #98a2b3;
        font-size: 10px;
    }

    .chat-message.mine .chat-time {
        color: #d9eee5;
    }

    .message-form {
        padding: 20px;
        border-top: 1px solid #edf0f2;
        background: #ffffff;
    }

    .message-input {
        width: 100%;
        min-height: 90px;
        padding: 13px;
        border: 1px solid #d7dde3;
        border-radius: 10px;
        resize: vertical;
        font-family: Arial, sans-serif;
        font-size: 13px;
        outline: none;
    }

    .message-input:focus {
        border-color: #176b4d;
    }

    .message-form-bottom {
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 15px;
        margin-top: 12px;
    }

    .message-limit {
        color: #98a2b3;
        font-size: 11px;
    }

    .send-button {
        padding: 11px 22px;
        background: #176b4d;
        border: 1px solid #176b4d;
        color: #ffffff;
        border-radius: 8px;
        font-size: 13px;
        font-weight: 700;
        cursor: pointer;
    }

    .send-button:hover {
        background: #12583f;
    }

    .empty-chat {
        text-align: center;
        color: #667085;
        padding: 100px 20px;
        font-size: 14px;
    }

    @media (max-width: 600px) {

        .chat-bubble {
            max-width: 88%;
        }

        .message-form-bottom {
            align-items: flex-start;
            flex-direction: column;
        }

        .send-button {
            width: 100%;
        }
    }

</style>

<div class="conversation-page">

    <div class="conversation-container">

        <div class="conversation-card">

            <div class="conversation-header">

                <h1>
                    <%= otherUserName %>
                </h1>

                <p>
                    SkillBridge secure conversation
                </p>

            </div>

            <div class="chat-body">

                <%
                    if (messages != null
                            && !messages.isEmpty()) {

                        for (Message message : messages) {

                            boolean mine =
                                    message.getSenderId()
                                    == loggedInUser.getUserId();
                %>

                <div class="chat-message
                    <%= mine ? "mine" : "" %>">

                    <div class="chat-bubble">

                        <p class="chat-text">
                            <%= message.getMessageText() %>
                        </p>

                        <div class="chat-time">

                            <%= message.getSentAt() %>

                        </div>

                    </div>

                </div>

                <%
                        }

                    } else {
                %>

                <div class="empty-chat">

                    Start your conversation with
                    <%= otherUserName %>.

                </div>

                <%
                    }
                %>

            </div>

            <div class="message-form">

                <form
                    method="post"
                    action="<%=request.getContextPath()%>/SendMessageServlet">

                    <input
                        type="hidden"
                        name="receiverId"
                        value="<%=otherUserId%>"
                    >

                    <textarea
                        name="messageText"
                        class="message-input"
                        maxlength="2000"
                        placeholder="Write your message..."
                        required></textarea>

                    <div class="message-form-bottom">

                        <span class="message-limit">
                            Maximum 2000 characters
                        </span>

                        <button
                            type="submit"
                            class="send-button">

                            Send Message

                        </button>

                    </div>

                </form>

            </div>

        </div>

    </div>

</div>

<%@ include file="includes/footer.jsp" %>