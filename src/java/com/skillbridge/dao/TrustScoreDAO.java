package com.skillbridge.dao;

import com.skillbridge.model.TrustScore;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TrustScoreDAO {

    public TrustScore calculateTrustScore(
            int userId,
            String role) throws Exception {

        TrustScore trustScore = new TrustScore();

        trustScore.setUserId(userId);
        trustScore.setRole(role);

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            double averageRating =
                    getAverageRating(connection, userId);

            int reviewCount =
                    getReviewCount(connection, userId);

            int completedProjects =
                    getCompletedProjects(
                            connection,
                            userId,
                            role
                    );

            int profileCompletion =
                    getProfileCompletion(
                            connection,
                            userId,
                            role
                    );

            /*
             * -----------------------------
             * 1. RATING SCORE
             * Maximum = 40
             * -----------------------------
             */

            double ratingScore =
                    (averageRating / 5.0) * 40.0;

            /*
             * -----------------------------
             * 2. COMPLETION SCORE
             * Maximum = 30
             *
             * 10 completed projects
             * gives full 30 points.
             * -----------------------------
             */

            double completionScore =
                    Math.min(completedProjects, 10)
                    / 10.0 * 30.0;

            /*
             * -----------------------------
             * 3. PROFILE SCORE
             * Maximum = 20
             * -----------------------------
             */

            double profileScore =
                    (profileCompletion / 100.0) * 20.0;

            /*
             * -----------------------------
             * 4. REVIEW SCORE
             * Maximum = 10
             *
             * 10 reviews = full score
             * -----------------------------
             */

            double reviewScore =
                    Math.min(reviewCount, 10)
                    / 10.0 * 10.0;

            /*
             * -----------------------------
             * FINAL SCORE
             * -----------------------------
             */

            int finalScore =
                    (int) Math.round(
                            ratingScore
                            + completionScore
                            + profileScore
                            + reviewScore
                    );

            if (finalScore > 100) {
                finalScore = 100;
            }

            if (finalScore < 0) {
                finalScore = 0;
            }

            trustScore.setAverageRating(
                    averageRating
            );

            trustScore.setReviewCount(
                    reviewCount
            );

            trustScore.setCompletedProjects(
                    completedProjects
            );

            trustScore.setProfileCompletion(
                    profileCompletion
            );

            trustScore.setRatingScore(
                    ratingScore
            );

            trustScore.setCompletionScore(
                    completionScore
            );

            trustScore.setProfileScore(
                    profileScore
            );

            trustScore.setReviewScore(
                    reviewScore
            );

            trustScore.setTrustScore(
                    finalScore
            );

            trustScore.setTrustLevel(
                    getTrustLevel(finalScore)
            );

            return trustScore;

        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }


    /*
     * =========================================
     * AVERAGE RATING
     * =========================================
     */

    private double getAverageRating(
            Connection connection,
            int userId) throws Exception {

        String sql =
                "SELECT AVG(rating) AS average_rating " +
                "FROM reviews " +
                "WHERE reviewed_user_id = ?";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        double averageRating = 0.0;

        if (resultSet.next()) {

            averageRating =
                    resultSet.getDouble(
                            "average_rating"
                    );

        }

        resultSet.close();
        statement.close();

        return averageRating;
    }


    /*
     * =========================================
     * REVIEW COUNT
     * =========================================
     */

    private int getReviewCount(
            Connection connection,
            int userId) throws Exception {

        String sql =
                "SELECT COUNT(*) AS review_count " +
                "FROM reviews " +
                "WHERE reviewed_user_id = ?";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        int count = 0;

        if (resultSet.next()) {

            count =
                    resultSet.getInt(
                            "review_count"
                    );
        }

        resultSet.close();
        statement.close();

        return count;
    }


    /*
     * =========================================
     * COMPLETED PROJECTS
     * =========================================
     */

    private int getCompletedProjects(
            Connection connection,
            int userId,
            String role) throws Exception {

        String sql;

        /*
         * Freelancer:
         * Find completed projects where
         * accepted proposal belongs to freelancer.
         */

        if ("FREELANCER".equalsIgnoreCase(role)) {

            sql =
                    "SELECT COUNT(*) AS completed_count " +
                    "FROM projects p " +
                    "INNER JOIN proposals pr " +
                    "ON p.project_id = pr.project_id " +
                    "WHERE pr.freelancer_id = ? " +
                    "AND pr.proposal_status = 'ACCEPTED' " +
                    "AND p.project_status = 'COMPLETED'";

        } else {

            /*
             * Client:
             * Find completed projects owned
             * by the client.
             */

            sql =
                    "SELECT COUNT(*) AS completed_count " +
                    "FROM projects " +
                    "WHERE client_id = ? " +
                    "AND project_status = 'COMPLETED'";
        }

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        int count = 0;

        if (resultSet.next()) {

            count =
                    resultSet.getInt(
                            "completed_count"
                    );
        }

        resultSet.close();
        statement.close();

        return count;
    }


    /*
     * =========================================
     * PROFILE COMPLETION
     * =========================================
     */

    private int getProfileCompletion(
            Connection connection,
            int userId,
            String role) throws Exception {

        /*
         * Freelancer
         */

        if ("FREELANCER".equalsIgnoreCase(role)) {

            String sql =
                    "SELECT profile_completion " +
                    "FROM freelancer_profiles " +
                    "WHERE user_id = ?";

            PreparedStatement statement =
                    connection.prepareStatement(sql);

            statement.setInt(1, userId);

            ResultSet resultSet =
                    statement.executeQuery();

            int completion = 0;

            if (resultSet.next()) {

                completion =
                        resultSet.getInt(
                                "profile_completion"
                        );
            }

            resultSet.close();
            statement.close();

            return completion;
        }

        /*
         * Client
         *
         * Client profiles do not have a
         * profile_completion column.
         *
         * We calculate it from:
         *
         * company name
         * company description
         * location
         */

        String sql =
                "SELECT company_name, " +
                "company_description, " +
                "location " +
                "FROM client_profiles " +
                "WHERE user_id = ?";

        PreparedStatement statement =
                connection.prepareStatement(sql);

        statement.setInt(1, userId);

        ResultSet resultSet =
                statement.executeQuery();

        int completion = 0;

        if (resultSet.next()) {

            String companyName =
                    resultSet.getString(
                            "company_name"
                    );

            String companyDescription =
                    resultSet.getString(
                            "company_description"
                    );

            String location =
                    resultSet.getString(
                            "location"
                    );

            int filledFields = 0;

            if (companyName != null
                    && !companyName.trim().isEmpty()) {

                filledFields++;
            }

            if (companyDescription != null
                    && !companyDescription.trim().isEmpty()) {

                filledFields++;
            }

            if (location != null
                    && !location.trim().isEmpty()) {

                filledFields++;
            }

            completion =
                    (int) Math.round(
                            (filledFields / 3.0)
                            * 100
                    );
        }

        resultSet.close();
        statement.close();

        return completion;
    }


    /*
     * =========================================
     * TRUST LEVEL
     * =========================================
     */

    private String getTrustLevel(int score) {

        if (score >= 90) {
            return "Excellent";
        }

        if (score >= 75) {
            return "Very Good";
        }

        if (score >= 60) {
            return "Good";
        }

        if (score >= 40) {
            return "Developing";
        }

        return "New Member";
    }
}