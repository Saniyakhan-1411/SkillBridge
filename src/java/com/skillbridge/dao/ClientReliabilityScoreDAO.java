package com.skillbridge.dao;

import com.skillbridge.model.ClientReliabilityScore;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientReliabilityScoreDAO {

    public ClientReliabilityScore calculateScore(int userId)
            throws Exception {

        ClientReliabilityScore score =
                new ClientReliabilityScore();

        score.setUserId(userId);

        Connection connection = null;

        try {

            connection = DBConnection.getConnection();

            // =====================================================
            // 1. TOTAL PROJECTS
            // =====================================================

            String totalProjectsSQL =
                    "SELECT COUNT(*) AS total_projects "
                    + "FROM projects "
                    + "WHERE client_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 totalProjectsSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        score.setTotalProjects(
                                resultSet.getInt(
                                        "total_projects"));
                    }
                }
            }


            // =====================================================
            // 2. COMPLETED PROJECTS
            // =====================================================

            String completedProjectsSQL =
                    "SELECT COUNT(*) AS completed_projects "
                    + "FROM projects "
                    + "WHERE client_id = ? "
                    + "AND project_status = 'COMPLETED'";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 completedProjectsSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        score.setCompletedProjects(
                                resultSet.getInt(
                                        "completed_projects"));
                    }
                }
            }


            // =====================================================
            // 3. REVIEW COUNT + AVERAGE RATING
            // =====================================================

            String reviewSQL =
                    "SELECT COUNT(*) AS review_count, "
                    + "COALESCE(AVG(rating), 0) AS average_rating "
                    + "FROM reviews "
                    + "WHERE reviewed_user_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(reviewSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        score.setReviewCount(
                                resultSet.getInt(
                                        "review_count"));

                        score.setAverageRating(
                                resultSet.getDouble(
                                        "average_rating"));
                    }
                }
            }


            // =====================================================
            // 4. PROFILE SCORE
            // =====================================================

            String profileSQL =
                    "SELECT company_name, "
                    + "company_description, "
                    + "location "
                    + "FROM client_profiles "
                    + "WHERE user_id = ?";

            int profileScore = 0;

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 profileSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        String companyName =
                                resultSet.getString(
                                        "company_name");

                        String companyDescription =
                                resultSet.getString(
                                        "company_description");

                        String location =
                                resultSet.getString(
                                        "location");


                        if (companyName != null
                                && !companyName.trim().isEmpty()) {

                            profileScore += 4;
                        }


                        if (companyDescription != null
                                && !companyDescription.trim().isEmpty()) {

                            profileScore += 3;
                        }


                        if (location != null
                                && !location.trim().isEmpty()) {

                            profileScore += 3;
                        }
                    }
                }
            }


            // =====================================================
            // 5. COMPLETED PROJECT SCORE
            // =====================================================

            int completedProjectScore =
                    Math.min(
                            score.getCompletedProjects() * 6,
                            30
                    );


            // =====================================================
            // 6. RATING SCORE
            // =====================================================

            int ratingScore =
                    (int) Math.round(
                            (score.getAverageRating() / 5.0)
                            * 30
                    );

            ratingScore =
                    Math.min(
                            Math.max(ratingScore, 0),
                            30
                    );


            // =====================================================
            // 7. REVIEW SCORE
            // =====================================================

            int reviewScore =
                    Math.min(
                            score.getReviewCount() * 2,
                            10
                    );


            // =====================================================
            // 8. ACTIVITY SCORE
            // =====================================================

            int activityScore =
                    Math.min(
                            score.getTotalProjects() * 4,
                            20
                    );


            // =====================================================
            // 9. FINAL SCORE
            // =====================================================

            int finalScore =
                    completedProjectScore
                    + ratingScore
                    + reviewScore
                    + profileScore
                    + activityScore;

            finalScore =
                    Math.min(
                            Math.max(finalScore, 0),
                            100
                    );


            // =====================================================
            // 10. RELIABILITY LEVEL
            // =====================================================

            String reliabilityLevel;

            if (finalScore >= 90) {

                reliabilityLevel =
                        "Excellent";

            } else if (finalScore >= 75) {

                reliabilityLevel =
                        "Very Reliable";

            } else if (finalScore >= 60) {

                reliabilityLevel =
                        "Reliable";

            } else if (finalScore >= 40) {

                reliabilityLevel =
                        "Developing";

            } else {

                reliabilityLevel =
                        "New / Limited History";
            }


            // =====================================================
            // 11. STORE RESULTS
            // =====================================================

            score.setCompletedProjectScore(
                    completedProjectScore);

            score.setRatingScore(
                    ratingScore);

            score.setReviewScore(
                    reviewScore);

            score.setProfileScore(
                    profileScore);

            score.setActivityScore(
                    activityScore);

            score.setReliabilityScore(
                    finalScore);

            score.setReliabilityLevel(
                    reliabilityLevel);


            return score;

        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }
}