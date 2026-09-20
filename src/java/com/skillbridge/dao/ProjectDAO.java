package com.skillbridge.dao;

import com.skillbridge.model.Project;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // =========================================================
    // 1. CREATE PROJECT
    // =========================================================

    public boolean createProject(Project project) {

        String sql =
            "INSERT INTO projects " +
            "(client_id, title, description, required_skills, " +
            "budget_min, budget_max, deadline_days, project_status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, 'OPEN')";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                project.getClientId()
            );

            statement.setString(
                2,
                project.getTitle()
            );

            statement.setString(
                3,
                project.getDescription()
            );

            statement.setString(
                4,
                project.getRequiredSkills()
            );

            statement.setDouble(
                5,
                project.getBudgetMin()
            );

            statement.setDouble(
                6,
                project.getBudgetMax()
            );

            statement.setInt(
                7,
                project.getDeadlineDays()
            );

            int result = statement.executeUpdate();

            return result > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // 2. GET PROJECTS BY CLIENT
    // =========================================================

    public List<Project> getProjectsByClient(int clientId) {

        List<Project> projects = new ArrayList<Project>();

        String sql =
            "SELECT * FROM projects " +
            "WHERE client_id = ? " +
            "ORDER BY created_at DESC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                clientId
            );

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Project project = new Project();

                    project.setProjectId(
                        result.getInt("project_id")
                    );

                    project.setClientId(
                        result.getInt("client_id")
                    );

                    project.setTitle(
                        result.getString("title")
                    );

                    project.setDescription(
                        result.getString("description")
                    );

                    project.setRequiredSkills(
                        result.getString("required_skills")
                    );

                    project.setBudgetMin(
                        result.getDouble("budget_min")
                    );

                    project.setBudgetMax(
                        result.getDouble("budget_max")
                    );

                    project.setDeadlineDays(
                        result.getInt("deadline_days")
                    );

                    project.setProjectStatus(
                        result.getString("project_status")
                    );

                    project.setCreatedAt(
                        result.getTimestamp("created_at")
                    );

                    projects.add(project);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return projects;
    }


    // =========================================================
    // 3. GET PROJECT BY ID
    // =========================================================

    public Project getProjectById(int projectId) {

        String sql =
            "SELECT * FROM projects " +
            "WHERE project_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(
                1,
                projectId
            );

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    Project project = new Project();

                    project.setProjectId(
                        result.getInt("project_id")
                    );

                    project.setClientId(
                        result.getInt("client_id")
                    );

                    project.setTitle(
                        result.getString("title")
                    );

                    project.setDescription(
                        result.getString("description")
                    );

                    project.setRequiredSkills(
                        result.getString("required_skills")
                    );

                    project.setBudgetMin(
                        result.getDouble("budget_min")
                    );

                    project.setBudgetMax(
                        result.getDouble("budget_max")
                    );

                    project.setDeadlineDays(
                        result.getInt("deadline_days")
                    );

                    project.setProjectStatus(
                        result.getString("project_status")
                    );

                    project.setCreatedAt(
                        result.getTimestamp("created_at")
                    );

                    return project;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // 4. GET ALL OPEN PROJECTS
    // =========================================================
    // Used by Freelancer module
    // =========================================================

    public List<Project> getOpenProjects() {

        List<Project> projects =
            new ArrayList<Project>();

        String sql =
            "SELECT project_id, client_id, title, description, " +
            "required_skills, budget_min, budget_max, " +
            "deadline_days, project_status, created_at " +
            "FROM projects " +
            "WHERE project_status = 'OPEN' " +
            "ORDER BY created_at DESC";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql);
            ResultSet result =
                statement.executeQuery()
        ) {

            while (result.next()) {

                Project project = new Project();

                project.setProjectId(
                    result.getInt("project_id")
                );

                project.setClientId(
                    result.getInt("client_id")
                );

                project.setTitle(
                    result.getString("title")
                );

                project.setDescription(
                    result.getString("description")
                );

                project.setRequiredSkills(
                    result.getString("required_skills")
                );

                project.setBudgetMin(
                    result.getDouble("budget_min")
                );

                project.setBudgetMax(
                    result.getDouble("budget_max")
                );

                project.setDeadlineDays(
                    result.getInt("deadline_days")
                );

                project.setProjectStatus(
                    result.getString("project_status")
                );

                project.setCreatedAt(
                    result.getTimestamp("created_at")
                );

                projects.add(project);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return projects;
    }
}