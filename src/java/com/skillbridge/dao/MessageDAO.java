package com.skillbridge.dao;

import com.skillbridge.model.Message;
import com.skillbridge.model.User;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public boolean sendMessage(Message message)
            throws Exception {

        String sql =
                "INSERT INTO messages " +
                "(sender_id, receiver_id, message_text) " +
                "VALUES (?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    message.getSenderId()
            );

            statement.setInt(
                    2,
                    message.getReceiverId()
            );

            statement.setString(
                    3,
                    message.getMessageText()
            );

            return statement.executeUpdate() > 0;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Message> getConversation(
            int userId,
            int otherUserId) throws Exception {

        List<Message> messages =
                new ArrayList<Message>();

        String sql =
                "SELECT m.message_id, " +
                "m.sender_id, " +
                "m.receiver_id, " +
                "m.message_text, " +
                "m.is_read, " +
                "m.sent_at, " +
                "u.full_name AS sender_name " +
                "FROM messages m " +
                "INNER JOIN users u " +
                "ON m.sender_id = u.user_id " +
                "WHERE " +
                "(m.sender_id = ? AND m.receiver_id = ?) " +
                "OR " +
                "(m.sender_id = ? AND m.receiver_id = ?) " +
                "ORDER BY m.sent_at ASC";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);
            statement.setInt(2, otherUserId);
            statement.setInt(3, otherUserId);
            statement.setInt(4, userId);

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Message message =
                        new Message();

                message.setMessageId(
                        resultSet.getInt(
                                "message_id"
                        )
                );

                message.setSenderId(
                        resultSet.getInt(
                                "sender_id"
                        )
                );

                message.setReceiverId(
                        resultSet.getInt(
                                "receiver_id"
                        )
                );

                message.setMessageText(
                        resultSet.getString(
                                "message_text"
                        )
                );

                message.setIsRead(
                        resultSet.getInt(
                                "is_read"
                        )
                );

                message.setSentAt(
                        resultSet.getTimestamp(
                                "sent_at"
                        )
                );

                message.setSenderName(
                        resultSet.getString(
                                "sender_name"
                        )
                );

                messages.add(message);
            }

            return messages;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Message> getInbox(
            int userId) throws Exception {

        List<Message> messages =
                new ArrayList<Message>();

        String sql =
                "SELECT m.message_id, " +
                "m.sender_id, " +
                "m.receiver_id, " +
                "m.message_text, " +
                "m.is_read, " +
                "m.sent_at, " +
                "u.full_name AS sender_name " +
                "FROM messages m " +
                "INNER JOIN users u " +
                "ON m.sender_id = u.user_id " +
                "WHERE m.receiver_id = ? " +
                "ORDER BY m.sent_at DESC";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Message message =
                        new Message();

                message.setMessageId(
                        resultSet.getInt(
                                "message_id"
                        )
                );

                message.setSenderId(
                        resultSet.getInt(
                                "sender_id"
                        )
                );

                message.setReceiverId(
                        resultSet.getInt(
                                "receiver_id"
                        )
                );

                message.setMessageText(
                        resultSet.getString(
                                "message_text"
                        )
                );

                message.setIsRead(
                        resultSet.getInt(
                                "is_read"
                        )
                );

                message.setSentAt(
                        resultSet.getTimestamp(
                                "sent_at"
                        )
                );

                message.setSenderName(
                        resultSet.getString(
                                "sender_name"
                        )
                );

                messages.add(message);
            }

            return messages;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public void markConversationAsRead(
            int receiverId,
            int senderId) throws Exception {

        String sql =
                "UPDATE messages " +
                "SET is_read = 1 " +
                "WHERE receiver_id = ? " +
                "AND sender_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, receiverId);
            statement.setInt(2, senderId);

            statement.executeUpdate();

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public int getUnreadCount(
            int userId) throws Exception {

        String sql =
                "SELECT COUNT(*) AS unread_count " +
                "FROM messages " +
                "WHERE receiver_id = ? " +
                "AND is_read = 0";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(
                        "unread_count"
                );
            }

            return 0;

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }
    public List<User> getMessagingFreelancers(int clientUserId)
        throws Exception {

    List<User> freelancers = new ArrayList<User>();

    String sql =
        "SELECT DISTINCT u.user_id, u.full_name, u.email, u.role " +
        "FROM users u " +
        "INNER JOIN proposals p ON u.user_id = p.freelancer_id " +
        "INNER JOIN projects pr ON p.project_id = pr.project_id " +
        "WHERE pr.client_id = ? " +
        "AND p.proposal_status = 'ACCEPTED' " +
        "ORDER BY u.full_name";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(sql);
        ps.setInt(1, clientUserId);

        rs = ps.executeQuery();

        while (rs.next()) {

            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));

            freelancers.add(user);
        }

    } finally {

        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return freelancers;
}
   public List<User> getMessagingClients(int freelancerUserId)
        throws Exception {

    List<User> clients = new ArrayList<User>();

    String sql =
        "SELECT DISTINCT u.user_id, u.full_name, u.email, u.role " +
        "FROM users u " +
        "INNER JOIN projects pr ON u.user_id = pr.client_id " +
        "INNER JOIN proposals p ON pr.project_id = p.project_id " +
        "WHERE p.freelancer_id = ? " +
        "AND p.proposal_status = 'ACCEPTED' " +
        "ORDER BY u.full_name";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(sql);
        ps.setInt(1, freelancerUserId);

        rs = ps.executeQuery();

        while (rs.next()) {

            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));

            clients.add(user);
        }

    } finally {

        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return clients;
} 
   public boolean canMessage(int userOneId, int userTwoId)
        throws Exception {

    String sql =
        "SELECT COUNT(*) " +
        "FROM proposals p " +
        "INNER JOIN projects pr ON p.project_id = pr.project_id " +
        "WHERE p.proposal_status = 'ACCEPTED' " +
        "AND ( " +
        "    (pr.client_id = ? AND p.freelancer_id = ?) " +
        "    OR " +
        "    (pr.client_id = ? AND p.freelancer_id = ?) " +
        ")";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(sql);

        ps.setInt(1, userOneId);
        ps.setInt(2, userTwoId);

        ps.setInt(3, userTwoId);
        ps.setInt(4, userOneId);

        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } finally {

        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return false;
}
}