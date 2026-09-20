package com.skillbridge.dao;

import com.skillbridge.model.ProjectStatusHistory;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProjectStatusHistoryDAO {

    public List<ProjectStatusHistory> getHistoryByProject(
        int projectId) throws Exception {

    List<ProjectStatusHistory> history =
            new ArrayList<ProjectStatusHistory>();

    String sql =
            "SELECT history_id, project_id, status, changed_at "
            + "FROM project_status_history "
            + "WHERE project_id = ? "
            + "ORDER BY changed_at ASC";

    try (Connection connection =
                 DBConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, projectId);

        try (ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                ProjectStatusHistory item =
                        new ProjectStatusHistory();

                item.setHistoryId(
                        resultSet.getInt("history_id")
                );

                item.setProjectId(
                        resultSet.getInt("project_id")
                );

                item.setStatus(
                        resultSet.getString("status")
                );

                item.setChangedAt(
                        resultSet.getTimestamp("changed_at")
                );

                history.add(item);
            }
        }
    }

    return history;
}

    public List<ProjectStatusHistory> getHistoryByProjectId(int projectId)
        throws Exception {

    List<ProjectStatusHistory> historyList =
            new ArrayList<ProjectStatusHistory>();

    String sql =
            "SELECT history_id, project_id, status, changed_at " +
            "FROM project_status_history " +
            "WHERE project_id = ? " +
            "ORDER BY changed_at ASC";

    try (Connection connection =
                DBConnection.getConnection();
         PreparedStatement preparedStatement =
                connection.prepareStatement(sql)) {

        preparedStatement.setInt(1, projectId);

        try (ResultSet resultSet =
                preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                ProjectStatusHistory history =
                        new ProjectStatusHistory();

                history.setHistoryId(
                        resultSet.getInt("history_id")
                );

                history.setProjectId(
                        resultSet.getInt("project_id")
                );

                history.setStatus(
                        resultSet.getString("status")
                );

                history.setChangedAt(
                        resultSet.getTimestamp("changed_at")
                );

                historyList.add(history);
            }
        }
    }

    return historyList;
}
}
