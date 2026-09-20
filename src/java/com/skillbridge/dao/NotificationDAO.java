package com.skillbridge.dao;

import com.skillbridge.model.Notification;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    /*
     * Create a new notification
     */
    public boolean createNotification(Notification notification) {

        String sql =
            "INSERT INTO notifications " +
            "(user_id, title, message, is_read) " +
            "VALUES (?, ?, ?, 0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getTitle());
            ps.setString(3, notification.getMessage());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /*
     * Get all notifications for a user
     */
    public List<Notification> getNotificationsByUser(int userId) {

        List<Notification> notifications =
                new ArrayList<Notification>();

        String sql =
            "SELECT notification_id, user_id, title, " +
            "message, is_read, created_at " +
            "FROM notifications " +
            "WHERE user_id = ? " +
            "ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Notification notification =
                            new Notification();

                    notification.setNotificationId(
                            rs.getInt("notification_id"));

                    notification.setUserId(
                            rs.getInt("user_id"));

                    notification.setTitle(
                            rs.getString("title"));

                    notification.setMessage(
                            rs.getString("message"));

                    notification.setIsRead(
                            rs.getInt("is_read"));

                    notification.setCreatedAt(
                            rs.getTimestamp("created_at"));

                    notifications.add(notification);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }


    /*
     * Get unread notification count
     */
    public int getUnreadCount(int userId) {

        String sql =
            "SELECT COUNT(*) " +
            "FROM notifications " +
            "WHERE user_id = ? " +
            "AND is_read = 0";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /*
     * Mark one notification as read
     */
    public boolean markAsRead(int notificationId,
                              int userId) {

        String sql =
            "UPDATE notifications " +
            "SET is_read = 1 " +
            "WHERE notification_id = ? " +
            "AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /*
     * Mark all notifications as read
     */
    public boolean markAllAsRead(int userId) {

        String sql =
            "UPDATE notifications " +
            "SET is_read = 1 " +
            "WHERE user_id = ? " +
            "AND is_read = 0";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /*
     * Delete one notification
     */
    public boolean deleteNotification(int notificationId,
                                      int userId) {

        String sql =
            "DELETE FROM notifications " +
            "WHERE notification_id = ? " +
            "AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, notificationId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}