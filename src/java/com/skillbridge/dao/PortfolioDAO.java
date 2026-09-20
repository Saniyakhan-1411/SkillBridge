package com.skillbridge.dao;

import com.skillbridge.model.Portfolio;
import com.skillbridge.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDAO {

    /*
     * ============================================================
     * CREATE PORTFOLIO PROJECT
     * ============================================================
     */

    public boolean addPortfolio(Portfolio portfolio) {

        String sql =
                "INSERT INTO portfolios "
                + "(freelancer_id, project_title, "
                + "project_description, technologies, "
                + "project_url, github_url) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(
                    1,
                    portfolio.getFreelancerId()
            );

            ps.setString(
                    2,
                    portfolio.getProjectTitle()
            );

            ps.setString(
                    3,
                    portfolio.getProjectDescription()
            );

            ps.setString(
                    4,
                    portfolio.getTechnologies()
            );

            ps.setString(
                    5,
                    portfolio.getProjectUrl()
            );

            ps.setString(
                    6,
                    portfolio.getGithubUrl()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    /*
     * ============================================================
     * GET PORTFOLIO PROJECTS
     * ============================================================
     */

    public List<Portfolio> getPortfolioByFreelancer(
            int freelancerId) {

        List<Portfolio> portfolioList =
                new ArrayList<Portfolio>();

        String sql =
                "SELECT portfolio_id, freelancer_id, "
                + "project_title, project_description, "
                + "technologies, project_url, github_url, "
                + "created_at "
                + "FROM portfolios "
                + "WHERE freelancer_id = ? "
                + "ORDER BY created_at DESC";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, freelancerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Portfolio portfolio =
                            new Portfolio();

                    portfolio.setPortfolioId(
                            rs.getInt("portfolio_id")
                    );

                    portfolio.setFreelancerId(
                            rs.getInt("freelancer_id")
                    );

                    portfolio.setProjectTitle(
                            rs.getString("project_title")
                    );

                    portfolio.setProjectDescription(
                            rs.getString(
                                    "project_description"
                            )
                    );

                    portfolio.setTechnologies(
                            rs.getString("technologies")
                    );

                    portfolio.setProjectUrl(
                            rs.getString("project_url")
                    );

                    portfolio.setGithubUrl(
                            rs.getString("github_url")
                    );

                    portfolio.setCreatedAt(
                            rs.getTimestamp("created_at")
                    );

                    portfolioList.add(portfolio);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return portfolioList;
    }


    /*
     * ============================================================
     * GET SINGLE PORTFOLIO
     * ============================================================
     */

    public Portfolio getPortfolioById(
            int portfolioId,
            int freelancerId) {

        String sql =
                "SELECT portfolio_id, freelancer_id, "
                + "project_title, project_description, "
                + "technologies, project_url, github_url, "
                + "created_at "
                + "FROM portfolios "
                + "WHERE portfolio_id = ? "
                + "AND freelancer_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, portfolioId);
            ps.setInt(2, freelancerId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Portfolio portfolio =
                            new Portfolio();

                    portfolio.setPortfolioId(
                            rs.getInt("portfolio_id")
                    );

                    portfolio.setFreelancerId(
                            rs.getInt("freelancer_id")
                    );

                    portfolio.setProjectTitle(
                            rs.getString("project_title")
                    );

                    portfolio.setProjectDescription(
                            rs.getString(
                                    "project_description"
                            )
                    );

                    portfolio.setTechnologies(
                            rs.getString("technologies")
                    );

                    portfolio.setProjectUrl(
                            rs.getString("project_url")
                    );

                    portfolio.setGithubUrl(
                            rs.getString("github_url")
                    );

                    portfolio.setCreatedAt(
                            rs.getTimestamp("created_at")
                    );

                    return portfolio;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }


    /*
     * ============================================================
     * UPDATE PORTFOLIO
     * ============================================================
     */

    public boolean updatePortfolio(
            Portfolio portfolio) {

        String sql =
                "UPDATE portfolios SET "
                + "project_title = ?, "
                + "project_description = ?, "
                + "technologies = ?, "
                + "project_url = ?, "
                + "github_url = ? "
                + "WHERE portfolio_id = ? "
                + "AND freelancer_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    portfolio.getProjectTitle()
            );

            ps.setString(
                    2,
                    portfolio.getProjectDescription()
            );

            ps.setString(
                    3,
                    portfolio.getTechnologies()
            );

            ps.setString(
                    4,
                    portfolio.getProjectUrl()
            );

            ps.setString(
                    5,
                    portfolio.getGithubUrl()
            );

            ps.setInt(
                    6,
                    portfolio.getPortfolioId()
            );

            ps.setInt(
                    7,
                    portfolio.getFreelancerId()
            );

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }


    /*
     * ============================================================
     * DELETE PORTFOLIO
     * ============================================================
     */

    public boolean deletePortfolio(
            int portfolioId,
            int freelancerId) {

        String sql =
                "DELETE FROM portfolios "
                + "WHERE portfolio_id = ? "
                + "AND freelancer_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {

            ps.setInt(1, portfolioId);

            ps.setInt(2, freelancerId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }
}