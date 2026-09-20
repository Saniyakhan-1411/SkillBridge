package com.skillbridge.dao;

import com.skillbridge.model.Review;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public boolean createReview(Review review)
            throws Exception {

        String sql =
                "INSERT INTO reviews "
                + "(project_id, reviewer_id, reviewed_user_id, "
                + "rating, review_text) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    review.getProjectId()
            );

            statement.setInt(
                    2,
                    review.getReviewerId()
            );

            statement.setInt(
                    3,
                    review.getReviewedUserId()
            );

            statement.setInt(
                    4,
                    review.getRating()
            );

            statement.setString(
                    5,
                    review.getReviewText()
            );

            return statement.executeUpdate() == 1;

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }


    public boolean hasReviewed(
            int projectId,
            int reviewerId) throws Exception {

        String sql =
                "SELECT review_id "
                + "FROM reviews "
                + "WHERE project_id = ? "
                + "AND reviewer_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, projectId);
            statement.setInt(2, reviewerId);

            resultSet =
                    statement.executeQuery();

            return resultSet.next();

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


    public List<Review> getReviewsForUser(
            int reviewedUserId) throws Exception {

        List<Review> reviews =
                new ArrayList<Review>();

        String sql =
                "SELECT r.review_id, "
                + "r.project_id, "
                + "r.reviewer_id, "
                + "r.reviewed_user_id, "
                + "r.rating, "
                + "r.review_text, "
                + "r.created_at, "
                + "u.full_name "
                + "FROM reviews r "
                + "INNER JOIN users u "
                + "ON r.reviewer_id = u.user_id "
                + "WHERE r.reviewed_user_id = ? "
                + "ORDER BY r.created_at DESC";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    reviewedUserId
            );

            resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Review review =
                        new Review();

                review.setReviewId(
                        resultSet.getInt("review_id")
                );

                review.setProjectId(
                        resultSet.getInt("project_id")
                );

                review.setReviewerId(
                        resultSet.getInt("reviewer_id")
                );

                review.setReviewedUserId(
                        resultSet.getInt("reviewed_user_id")
                );

                review.setRating(
                        resultSet.getInt("rating")
                );

                review.setReviewText(
                        resultSet.getString("review_text")
                );

                review.setCreatedAt(
                        resultSet.getTimestamp("created_at")
                );

                review.setReviewerName(
                        resultSet.getString("full_name")
                );

                reviews.add(review);
            }

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

        return reviews;
    }


    public double getAverageRating(
            int reviewedUserId) throws Exception {

        String sql =
                "SELECT AVG(rating) AS average_rating "
                + "FROM reviews "
                + "WHERE reviewed_user_id = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    reviewedUserId
            );

            resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                double average =
                        resultSet.getDouble(
                                "average_rating"
                        );

                if (resultSet.wasNull()) {
                    return 0.0;
                }

                return average;
            }

            return 0.0;

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
}