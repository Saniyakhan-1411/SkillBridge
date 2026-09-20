package com.skillbridge.dao;

import com.skillbridge.model.FreelancerCareerGrowth;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FreelancerCareerGrowthDAO {

    public FreelancerCareerGrowth calculateGrowth(int userId)
            throws Exception {

        FreelancerCareerGrowth growth =
                new FreelancerCareerGrowth();

        growth.setUserId(userId);

        Connection connection = null;

        try {

            connection = DBConnection.getConnection();

            // =====================================================
            // 1. PROPOSALS SUBMITTED
            // =====================================================

            String proposalSQL =
                    "SELECT COUNT(*) AS total_proposals "
                    + "FROM proposals "
                    + "WHERE freelancer_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(proposalSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        growth.setProposalsSubmitted(
                                resultSet.getInt("total_proposals"));
                    }
                }
            }


            // =====================================================
            // 2. ACCEPTED PROPOSALS
            // =====================================================

            String acceptedSQL =
                    "SELECT COUNT(*) AS accepted_proposals "
                    + "FROM proposals "
                    + "WHERE freelancer_id = ? "
                    + "AND proposal_status = 'ACCEPTED'";

            try (PreparedStatement statement =
                         connection.prepareStatement(acceptedSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        growth.setAcceptedProposals(
                                resultSet.getInt(
                                        "accepted_proposals"));
                    }
                }
            }


            // =====================================================
            // 3. COMPLETED PROJECTS
            // =====================================================

            String completedSQL =
                    "SELECT COUNT(*) AS completed_projects "
                    + "FROM proposals p "
                    + "INNER JOIN projects pr "
                    + "ON p.project_id = pr.project_id "
                    + "WHERE p.freelancer_id = ? "
                    + "AND p.proposal_status = 'ACCEPTED' "
                    + "AND pr.project_status = 'COMPLETED'";

            try (PreparedStatement statement =
                         connection.prepareStatement(completedSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        growth.setCompletedProjects(
                                resultSet.getInt(
                                        "completed_projects"));
                    }
                }
            }


            // =====================================================
            // 4. REVIEWS + AVERAGE RATING
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

                        growth.setReviewCount(
                                resultSet.getInt("review_count"));

                        growth.setAverageRating(
                                resultSet.getDouble(
                                        "average_rating"));
                    }
                }
            }


            // =====================================================
            // 5. PORTFOLIO COUNT
            // =====================================================

            String portfolioSQL =
                    "SELECT COUNT(*) AS portfolio_count "
                    + "FROM portfolios "
                    + "WHERE freelancer_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 portfolioSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        growth.setPortfolioCount(
                                resultSet.getInt(
                                        "portfolio_count"));
                    }
                }
            }


            // =====================================================
            // 6. PROFILE COMPLETION
            // =====================================================

            String profileSQL =
                    "SELECT profile_completion "
                    + "FROM freelancer_profiles "
                    + "WHERE user_id = ?";

            try (PreparedStatement statement =
                         connection.prepareStatement(profileSQL)) {

                statement.setInt(1, userId);

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    if (resultSet.next()) {

                        growth.setProfileCompletion(
                                resultSet.getInt(
                                        "profile_completion"));
                    }
                }
            }


            // =====================================================
            // 7. PROPOSAL SUCCESS RATE
            // =====================================================

            double proposalSuccessRate = 0;

            if (growth.getProposalsSubmitted() > 0) {

                proposalSuccessRate =
                        (growth.getAcceptedProposals() * 100.0)
                        / growth.getProposalsSubmitted();
            }

            growth.setProposalSuccessRate(
                    proposalSuccessRate);


            // =====================================================
            // 8. PROJECT SCORE - MAX 25
            // =====================================================

            int projectScore =
                    Math.min(
                            growth.getCompletedProjects() * 5,
                            25
                    );


            // =====================================================
            // 9. PROPOSAL SCORE - MAX 20
            // =====================================================

            int proposalScore =
                    (int) Math.round(
                            (proposalSuccessRate / 100.0)
                            * 20
                    );


            // =====================================================
            // 10. RATING SCORE - MAX 20
            // =====================================================

            int ratingScore =
                    (int) Math.round(
                            (growth.getAverageRating() / 5.0)
                            * 20
                    );

            ratingScore =
                    Math.min(
                            Math.max(ratingScore, 0),
                            20
                    );


            // =====================================================
            // 11. PORTFOLIO SCORE - MAX 15
            // =====================================================

            int portfolioScore =
                    Math.min(
                            growth.getPortfolioCount() * 5,
                            15
                    );


            // =====================================================
            // 12. PROFILE SCORE - MAX 10
            // =====================================================

            int profileScore =
                    (int) Math.round(
                            (growth.getProfileCompletion()
                            / 100.0) * 10
                    );


            // =====================================================
            // 13. REVIEW SCORE - MAX 10
            // =====================================================

            int reviewScore =
                    Math.min(
                            growth.getReviewCount() * 2,
                            10
                    );


            // =====================================================
            // 14. FINAL CAREER SCORE
            // =====================================================

            int careerScore =
                    projectScore
                    + proposalScore
                    + ratingScore
                    + portfolioScore
                    + profileScore
                    + reviewScore;

            careerScore =
                    Math.min(
                            Math.max(careerScore, 0),
                            100
                    );


            // =====================================================
            // 15. CAREER LEVEL
            // =====================================================

            String careerLevel;

            if (careerScore >= 90) {

                careerLevel = "Outstanding";

            } else if (careerScore >= 75) {

                careerLevel = "Strong Growth";

            } else if (careerScore >= 60) {

                careerLevel = "Good Progress";

            } else if (careerScore >= 40) {

                careerLevel = "Developing";

            } else {

                careerLevel = "Getting Started";
            }


            // =====================================================
            // 16. STORE VALUES
            // =====================================================

            growth.setProjectScore(projectScore);

            growth.setProposalScore(proposalScore);

            growth.setRatingScore(ratingScore);

            growth.setPortfolioScore(portfolioScore);

            growth.setProfileScore(profileScore);

            growth.setReviewScore(reviewScore);

            growth.setCareerScore(careerScore);

            growth.setCareerLevel(careerLevel);


            return growth;

        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }
}