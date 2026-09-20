package com.skillbridge.dao;

import com.skillbridge.model.User;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean registerUser(User user) {

        String sql =
            "INSERT INTO users " +
            "(full_name, email, password, role) " +
            "VALUES (?, ?, ?, ?)";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(
                    1,
                    user.getFullName()
            );

            statement.setString(
                    2,
                    user.getEmail()
            );

            statement.setString(
                    3,
                    user.getPassword()
            );

            statement.setString(
                    4,
                    user.getRole()
            );

            int result =
                    statement.executeUpdate();

            statement.close();
            connection.close();

            return result > 0;

        } catch (Exception e) {

            System.out.println(
                    "Registration Error: "
                    + e.getMessage()
            );

            return false;
        }
    }


    public boolean emailExists(String email) {

        String sql =
            "SELECT user_id FROM users " +
            "WHERE email = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet result =
                    statement.executeQuery();

            boolean exists =
                    result.next();

            result.close();
            statement.close();
            connection.close();

            return exists;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    public User loginUser(
            String email,
            String password) {

        String sql =
            "SELECT * FROM users " +
            "WHERE email = ? AND password = ?";

        try {

            Connection connection =
                    DBConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                User user = new User();

                user.setUserId(
                    result.getInt("user_id")
                );

                user.setFullName(
                    result.getString("full_name")
                );

                user.setEmail(
                    result.getString("email")
                );

                user.setPassword(
                    result.getString("password")
                );

                user.setRole(
                    result.getString("role")
                );

                user.setProfileImage(
                    result.getString("profile_image")
                );

                user.setCreatedAt(
                    result.getTimestamp("created_at")
                );

                result.close();
                statement.close();
                connection.close();

                return user;
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
   public List<User> getAllFreelancers() throws Exception {

    List<User> freelancers =
            new ArrayList<User>();

    String sql =
            "SELECT user_id, full_name, email, role " +
            "FROM users " +
            "WHERE role = 'FREELANCER' " +
            "ORDER BY full_name";

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {

        connection =
                DBConnection.getConnection();

        statement =
                connection.prepareStatement(sql);

        resultSet =
                statement.executeQuery();

        while (resultSet.next()) {

            User user = new User();

            user.setUserId(
                    resultSet.getInt("user_id")
            );

            user.setFullName(
                    resultSet.getString("full_name")
            );

            user.setEmail(
                    resultSet.getString("email")
            );

            user.setRole(
                    resultSet.getString("role")
            );

            freelancers.add(user);
        }

        return freelancers;

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

    public User getUserById(int userId)
        throws Exception {

    User user = null;

    String sql =
        "SELECT user_id, full_name, email, role " +
        "FROM users " +
        "WHERE user_id = ?";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {

        con = DBConnection.getConnection();

        ps = con.prepareStatement(sql);

        ps.setInt(1, userId);

        rs = ps.executeQuery();

        if (rs.next()) {

            user = new User();

            user.setUserId(
                rs.getInt("user_id")
            );

            user.setFullName(
                rs.getString("full_name")
            );

            user.setEmail(
                rs.getString("email")
            );

            user.setRole(
                rs.getString("role")
            );
        }

    } finally {

        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return user;
}
}