package com.skillbridge.dao;

import com.skillbridge.model.ClientProposal;
import com.skillbridge.model.Proposal;
import com.skillbridge.model.Project;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProposalDAO {

    // ============================================================
    // CHECK WHETHER FREELANCER ALREADY SUBMITTED A PROPOSAL
    // ============================================================

    public boolean hasAlreadySubmitted(
            int projectId,
            int freelancerId) throws Exception {

        String sql =
                "SELECT proposal_id " +
                "FROM proposals " +
                "WHERE project_id = ? " +
                "AND freelancer_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);
            statement.setInt(2, freelancerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    // ============================================================
    // CREATE NEW PROPOSAL
    // ============================================================

    public boolean createProposal(
            Proposal proposal) throws Exception {

        String sql =
                "INSERT INTO proposals " +
                "(project_id, freelancer_id, cover_letter, " +
                "bid_amount, delivery_days, proposal_status, " +
                "skill_match_score) " +
                "VALUES (?, ?, ?, ?, ?, 'PENDING', ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, proposal.getProjectId());
            statement.setInt(2, proposal.getFreelancerId());
            statement.setString(3, proposal.getCoverLetter());
            statement.setDouble(4, proposal.getBidAmount());
            statement.setInt(5, proposal.getDeliveryDays());
            statement.setInt(6, proposal.getSkillMatchScore());

            return statement.executeUpdate() > 0;
        }
    }


    // ============================================================
    // GET PROPOSALS BY FREELANCER
    // ============================================================

    public List<Proposal> getProposalsByFreelancer(
            int freelancerId) throws Exception {

        List<Proposal> proposals = new ArrayList<Proposal>();

        String sql =
                "SELECT p.proposal_id, " +
                "p.project_id, " +
                "p.freelancer_id, " +
                "p.cover_letter, " +
                "p.bid_amount, " +
                "p.delivery_days, " +
                "p.proposal_status, " +
                "p.skill_match_score, " +
                "p.submitted_at " +
                "FROM proposals p " +
                "WHERE p.freelancer_id = ? " +
                "ORDER BY p.submitted_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, freelancerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Proposal proposal = new Proposal();

                    proposal.setProposalId(
                            resultSet.getInt("proposal_id"));

                    proposal.setProjectId(
                            resultSet.getInt("project_id"));

                    proposal.setFreelancerId(
                            resultSet.getInt("freelancer_id"));

                    proposal.setCoverLetter(
                            resultSet.getString("cover_letter"));

                    proposal.setBidAmount(
                            resultSet.getDouble("bid_amount"));

                    proposal.setDeliveryDays(
                            resultSet.getInt("delivery_days"));

                    proposal.setProposalStatus(
                            resultSet.getString("proposal_status"));

                    proposal.setSkillMatchScore(
                            resultSet.getInt("skill_match_score"));

                    proposal.setSubmittedAt(
                            resultSet.getTimestamp("submitted_at"));

                    proposals.add(proposal);
                }
            }
        }

        return proposals;
    }


    // ============================================================
    // GET PROPOSALS BY PROJECT
    // ============================================================

    public List<Proposal> getProposalsByProject(
            int projectId) throws Exception {

        List<Proposal> proposals = new ArrayList<Proposal>();

        String sql =
                "SELECT p.proposal_id, " +
                "p.project_id, " +
                "p.freelancer_id, " +
                "p.cover_letter, " +
                "p.bid_amount, " +
                "p.delivery_days, " +
                "p.proposal_status, " +
                "p.skill_match_score, " +
                "p.submitted_at " +
                "FROM proposals p " +
                "WHERE p.project_id = ? " +
                "ORDER BY p.skill_match_score DESC, " +
                "p.submitted_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Proposal proposal = new Proposal();

                    proposal.setProposalId(
                            resultSet.getInt("proposal_id"));

                    proposal.setProjectId(
                            resultSet.getInt("project_id"));

                    proposal.setFreelancerId(
                            resultSet.getInt("freelancer_id"));

                    proposal.setCoverLetter(
                            resultSet.getString("cover_letter"));

                    proposal.setBidAmount(
                            resultSet.getDouble("bid_amount"));

                    proposal.setDeliveryDays(
                            resultSet.getInt("delivery_days"));

                    proposal.setProposalStatus(
                            resultSet.getString("proposal_status"));

                    proposal.setSkillMatchScore(
                            resultSet.getInt("skill_match_score"));

                    proposal.setSubmittedAt(
                            resultSet.getTimestamp("submitted_at"));

                    proposals.add(proposal);
                }
            }
        }

        return proposals;
    }


    // ============================================================
    // GET CLIENT PROPOSALS WITH FREELANCER DETAILS
    // ============================================================

    public List<ClientProposal> getClientProposalsByProject(
            int projectId) throws Exception {

        List<ClientProposal> proposals =
                new ArrayList<ClientProposal>();

        String sql =
                "SELECT p.proposal_id, " +
                "p.project_id, " +
                "p.freelancer_id, " +
                "u.full_name AS freelancer_name, " +
                "u.email AS freelancer_email, " +
                "p.cover_letter, " +
                "p.bid_amount, " +
                "p.delivery_days, " +
                "p.proposal_status, " +
                "p.skill_match_score, " +
                "p.submitted_at " +
                "FROM proposals p " +
                "INNER JOIN users u " +
                "ON p.freelancer_id = u.user_id " +
                "WHERE p.project_id = ? " +
                "ORDER BY p.skill_match_score DESC, " +
                "p.submitted_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, projectId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    ClientProposal proposal =
                            new ClientProposal();

                    proposal.setProposalId(
                            resultSet.getInt("proposal_id"));

                    proposal.setProjectId(
                            resultSet.getInt("project_id"));

                    proposal.setFreelancerId(
                            resultSet.getInt("freelancer_id"));

                    proposal.setFreelancerName(
                            resultSet.getString("freelancer_name"));

                    proposal.setFreelancerEmail(
                            resultSet.getString("freelancer_email"));

                    proposal.setCoverLetter(
                            resultSet.getString("cover_letter"));

                    proposal.setBidAmount(
                            resultSet.getDouble("bid_amount"));

                    proposal.setDeliveryDays(
                            resultSet.getInt("delivery_days"));

                    proposal.setProposalStatus(
                            resultSet.getString("proposal_status"));

                    proposal.setSkillMatchScore(
                            resultSet.getInt("skill_match_score"));

                    proposal.setSubmittedAt(
                            resultSet.getTimestamp("submitted_at"));

                    proposals.add(proposal);
                }
            }
        }

        return proposals;
    }


    // ============================================================
    // GET ACCEPTED FREELANCER ID
    // ============================================================

    public int getAcceptedFreelancerId(int projectId)
        throws Exception {

    String sql =
            "SELECT freelancer_id " +
            "FROM proposals " +
            "WHERE project_id = ? " +
            "AND proposal_status = 'ACCEPTED'";

    try (Connection connection =
                DBConnection.getConnection();
         PreparedStatement preparedStatement =
                connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, projectId);

        try (ResultSet resultSet =
                preparedStatement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(
                        "freelancer_id"
                );
            }
        }
    }

    return 0;
}


    // ============================================================
    // HIRE FREELANCER
    //
    // IMPORTANT:
    // This is the corrected method.
    //
    // Return value:
    // > 0  = freelancer ID who was hired
    // -1   = hiring failed
    // ============================================================

    public int hireFreelancer(
            int projectId,
            int proposalId,
            int clientId) throws Exception {

        Connection connection = null;

        PreparedStatement projectCheck = null;
        PreparedStatement proposalCheck = null;
        PreparedStatement acceptStatement = null;
        PreparedStatement rejectStatement = null;
        PreparedStatement projectUpdate = null;
        PreparedStatement historyStatement = null;

        ResultSet projectResult = null;
        ResultSet proposalResult = null;

        try {

            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);


            // ----------------------------------------------------
            // STEP 1: Verify project belongs to client and is OPEN
            // ----------------------------------------------------

            String projectSQL =
                    "SELECT project_id " +
                    "FROM projects " +
                    "WHERE project_id = ? " +
                    "AND client_id = ? " +
                    "AND project_status = 'OPEN'";

            projectCheck =
                    connection.prepareStatement(projectSQL);

            projectCheck.setInt(1, projectId);
            projectCheck.setInt(2, clientId);

            projectResult =
                    projectCheck.executeQuery();

            if (!projectResult.next()) {

                connection.rollback();

                return -1;
            }


            // ----------------------------------------------------
            // STEP 2: Verify selected proposal
            // ----------------------------------------------------

            String proposalSQL =
                    "SELECT proposal_id, freelancer_id " +
                    "FROM proposals " +
                    "WHERE proposal_id = ? " +
                    "AND project_id = ? " +
                    "AND proposal_status = 'PENDING'";

            proposalCheck =
                    connection.prepareStatement(proposalSQL);

            proposalCheck.setInt(1, proposalId);
            proposalCheck.setInt(2, projectId);

            proposalResult =
                    proposalCheck.executeQuery();

            if (!proposalResult.next()) {

                connection.rollback();

                return -1;
            }

            int freelancerId =
                    proposalResult.getInt("freelancer_id");


            // ----------------------------------------------------
            // STEP 3: Accept selected proposal
            // ----------------------------------------------------

            String acceptSQL =
                    "UPDATE proposals " +
                    "SET proposal_status = 'ACCEPTED' " +
                    "WHERE proposal_id = ? " +
                    "AND project_id = ? " +
                    "AND proposal_status = 'PENDING'";

            acceptStatement =
                    connection.prepareStatement(acceptSQL);

            acceptStatement.setInt(1, proposalId);
            acceptStatement.setInt(2, projectId);

            int accepted =
                    acceptStatement.executeUpdate();

            if (accepted == 0) {

                connection.rollback();

                return -1;
            }


            // ----------------------------------------------------
            // STEP 4: Reject all other pending proposals
            // ----------------------------------------------------

            String rejectSQL =
                    "UPDATE proposals " +
                    "SET proposal_status = 'REJECTED' " +
                    "WHERE project_id = ? " +
                    "AND proposal_id <> ? " +
                    "AND proposal_status = 'PENDING'";

            rejectStatement =
                    connection.prepareStatement(rejectSQL);

            rejectStatement.setInt(1, projectId);
            rejectStatement.setInt(2, proposalId);

            rejectStatement.executeUpdate();


            // ----------------------------------------------------
            // STEP 5: Change project status
            // OPEN → IN_PROGRESS
            // ----------------------------------------------------

            String projectUpdateSQL =
                    "UPDATE projects " +
                    "SET project_status = 'IN_PROGRESS' " +
                    "WHERE project_id = ? " +
                    "AND client_id = ? " +
                    "AND project_status = 'OPEN'";

            projectUpdate =
                    connection.prepareStatement(
                            projectUpdateSQL);

            projectUpdate.setInt(1, projectId);
            projectUpdate.setInt(2, clientId);

            int updated =
                    projectUpdate.executeUpdate();

            if (updated == 0) {

                connection.rollback();

                return -1;
            }


            // ----------------------------------------------------
            // STEP 6: Add status history
            // ----------------------------------------------------

            String historySQL =
                    "INSERT INTO project_status_history " +
                    "(project_id, status) " +
                    "VALUES (?, 'IN_PROGRESS')";

            historyStatement =
                    connection.prepareStatement(historySQL);

            historyStatement.setInt(1, projectId);

            historyStatement.executeUpdate();


            // ----------------------------------------------------
            // STEP 7: Commit transaction
            // ----------------------------------------------------

            connection.commit();


            // Return freelancer who was hired
            return freelancerId;

        } catch (Exception e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (Exception ignored) {
                }
            }

            throw e;

        } finally {

            // Close ResultSets
            if (projectResult != null) {
                try {
                    projectResult.close();
                } catch (Exception ignored) {
                }
            }

            if (proposalResult != null) {
                try {
                    proposalResult.close();
                } catch (Exception ignored) {
                }
            }


            // Close PreparedStatements
            if (projectCheck != null) {
                try {
                    projectCheck.close();
                } catch (Exception ignored) {
                }
            }

            if (proposalCheck != null) {
                try {
                    proposalCheck.close();
                } catch (Exception ignored) {
                }
            }

            if (acceptStatement != null) {
                try {
                    acceptStatement.close();
                } catch (Exception ignored) {
                }
            }

            if (rejectStatement != null) {
                try {
                    rejectStatement.close();
                } catch (Exception ignored) {
                }
            }

            if (projectUpdate != null) {
                try {
                    projectUpdate.close();
                } catch (Exception ignored) {
                }
            }

            if (historyStatement != null) {
                try {
                    historyStatement.close();
                } catch (Exception ignored) {
                }
            }


            // Reset and close connection
            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                } catch (Exception ignored) {
                }

                try {
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }
    }


    // ============================================================
    // GET PROJECTS FOR FREELANCER
    // ============================================================

    public List<Project> getProjectsForFreelancer(
            int freelancerId) throws Exception {

        List<Project> projects =
                new ArrayList<Project>();

        String sql =
                "SELECT DISTINCT p.project_id, " +
                "p.client_id, " +
                "p.title, " +
                "p.description, " +
                "p.required_skills, " +
                "p.budget_min, " +
                "p.budget_max, " +
                "p.deadline_days, " +
                "p.project_status, " +
                "p.created_at " +
                "FROM projects p " +
                "INNER JOIN proposals pr " +
                "ON p.project_id = pr.project_id " +
                "WHERE pr.freelancer_id = ? " +
                "AND pr.proposal_status = 'ACCEPTED' " +
                "ORDER BY p.created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, freelancerId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Project project = new Project();

                    project.setProjectId(
                            resultSet.getInt("project_id"));

                    project.setClientId(
                            resultSet.getInt("client_id"));

                    project.setTitle(
                            resultSet.getString("title"));

                    project.setDescription(
                            resultSet.getString("description"));

                    project.setRequiredSkills(
                            resultSet.getString("required_skills"));

                    project.setBudgetMin(
                            resultSet.getDouble("budget_min"));

                    project.setBudgetMax(
                            resultSet.getDouble("budget_max"));

                    project.setDeadlineDays(
                            resultSet.getInt("deadline_days"));

                    project.setProjectStatus(
                            resultSet.getString("project_status"));

                    project.setCreatedAt(
                            resultSet.getTimestamp("created_at"));

                    projects.add(project);
                }
            }
        }

        return projects;
    }


    // ============================================================
    // COMPLETE PROJECT
    // ============================================================

    public boolean completeProject(
            int projectId,
            int freelancerId) throws Exception {

        Connection connection = null;

        PreparedStatement verifyStatement = null;
        PreparedStatement updateStatement = null;
        PreparedStatement historyStatement = null;

        ResultSet resultSet = null;

        try {

            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);


            // ----------------------------------------------------
            // STEP 1: Verify freelancer is accepted freelancer
            // ----------------------------------------------------

            String verifySQL =
                    "SELECT p.proposal_id " +
                    "FROM proposals p " +
                    "INNER JOIN projects pr " +
                    "ON p.project_id = pr.project_id " +
                    "WHERE p.project_id = ? " +
                    "AND p.freelancer_id = ? " +
                    "AND p.proposal_status = 'ACCEPTED' " +
                    "AND pr.project_status = 'IN_PROGRESS'";

            verifyStatement =
                    connection.prepareStatement(verifySQL);

            verifyStatement.setInt(1, projectId);
            verifyStatement.setInt(2, freelancerId);

            resultSet =
                    verifyStatement.executeQuery();

            if (!resultSet.next()) {

                connection.rollback();

                return false;
            }


            // ----------------------------------------------------
            // STEP 2: Complete project
            // ----------------------------------------------------

            String updateSQL =
                    "UPDATE projects " +
                    "SET project_status = 'COMPLETED' " +
                    "WHERE project_id = ? " +
                    "AND project_status = 'IN_PROGRESS'";

            updateStatement =
                    connection.prepareStatement(updateSQL);

            updateStatement.setInt(1, projectId);

            int updated =
                    updateStatement.executeUpdate();

            if (updated == 0) {

                connection.rollback();

                return false;
            }


            // ----------------------------------------------------
            // STEP 3: Add status history
            // ----------------------------------------------------

            String historySQL =
                    "INSERT INTO project_status_history " +
                    "(project_id, status) " +
                    "VALUES (?, 'COMPLETED')";

            historyStatement =
                    connection.prepareStatement(historySQL);

            historyStatement.setInt(1, projectId);

            historyStatement.executeUpdate();


            // ----------------------------------------------------
            // STEP 4: Commit
            // ----------------------------------------------------

            connection.commit();

            return true;

        } catch (Exception e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (Exception ignored) {
                }
            }

            throw e;

        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception ignored) {
                }
            }

            if (verifyStatement != null) {
                try {
                    verifyStatement.close();
                } catch (Exception ignored) {
                }
            }

            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (Exception ignored) {
                }
            }

            if (historyStatement != null) {
                try {
                    historyStatement.close();
                } catch (Exception ignored) {
                }
            }

            if (connection != null) {

                try {
                    connection.setAutoCommit(true);
                } catch (Exception ignored) {
                }

                try {
                    connection.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}