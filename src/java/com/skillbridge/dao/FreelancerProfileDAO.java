package com.skillbridge.dao;

import com.skillbridge.model.FreelancerProfile;
import com.skillbridge.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FreelancerProfileDAO {


    public FreelancerProfile getProfileByUserId(
            int userId) {

        String sql =
            "SELECT * FROM freelancer_profiles " +
            "WHERE user_id = ?";


        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;


        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);

            statement.setInt(
                    1,
                    userId
            );

            result =
                    statement.executeQuery();


            if (result.next()) {

                return mapProfile(result);
            }


        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            closeResources(
                    connection,
                    statement,
                    result
            );
        }


        return null;
    }


    public boolean createProfile(
            FreelancerProfile profile) {

        String sql =
            "INSERT INTO freelancer_profiles " +
            "(user_id, headline, bio, skills, " +
            "hourly_rate, experience_years, location, " +
            "profile_completion) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        Connection connection = null;
        PreparedStatement statement = null;


        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);


            statement.setInt(
                    1,
                    profile.getUserId()
            );

            statement.setString(
                    2,
                    profile.getHeadline()
            );

            statement.setString(
                    3,
                    profile.getBio()
            );

            statement.setString(
                    4,
                    profile.getSkills()
            );

            statement.setDouble(
                    5,
                    profile.getHourlyRate()
            );

            statement.setInt(
                    6,
                    profile.getExperienceYears()
            );

            statement.setString(
                    7,
                    profile.getLocation()
            );

            statement.setInt(
                    8,
                    profile.getProfileCompletion()
            );


            return statement.executeUpdate() > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        } finally {

            closeResources(
                    connection,
                    statement,
                    null
            );
        }
    }


    public boolean updateProfile(
            FreelancerProfile profile) {

        String sql =
            "UPDATE freelancer_profiles SET " +
            "headline = ?, " +
            "bio = ?, " +
            "skills = ?, " +
            "hourly_rate = ?, " +
            "experience_years = ?, " +
            "location = ?, " +
            "profile_completion = ? " +
            "WHERE user_id = ?";


        Connection connection = null;
        PreparedStatement statement = null;


        try {

            connection =
                    DBConnection.getConnection();

            statement =
                    connection.prepareStatement(sql);


            statement.setString(
                    1,
                    profile.getHeadline()
            );

            statement.setString(
                    2,
                    profile.getBio()
            );

            statement.setString(
                    3,
                    profile.getSkills()
            );

            statement.setDouble(
                    4,
                    profile.getHourlyRate()
            );

            statement.setInt(
                    5,
                    profile.getExperienceYears()
            );

            statement.setString(
                    6,
                    profile.getLocation()
            );

            statement.setInt(
                    7,
                    profile.getProfileCompletion()
            );

            statement.setInt(
                    8,
                    profile.getUserId()
            );


            return statement.executeUpdate() > 0;


        } catch (Exception e) {

            e.printStackTrace();

            return false;

        } finally {

            closeResources(
                    connection,
                    statement,
                    null
            );
        }
    }


    private FreelancerProfile mapProfile(
            ResultSet result)
            throws Exception {

        FreelancerProfile profile =
                new FreelancerProfile();


        profile.setFreelancerId(
                result.getInt("freelancer_id")
        );

        profile.setUserId(
                result.getInt("user_id")
        );

        profile.setHeadline(
                result.getString("headline")
        );

        profile.setBio(
                result.getString("bio")
        );

        profile.setSkills(
                result.getString("skills")
        );

        profile.setHourlyRate(
                result.getDouble("hourly_rate")
        );

        profile.setExperienceYears(
                result.getInt("experience_years")
        );

        profile.setLocation(
                result.getString("location")
        );

        profile.setProfileCompletion(
                result.getInt("profile_completion")
        );

        profile.setCreatedAt(
                result.getTimestamp("created_at")
        );


        return profile;
    }


    private void closeResources(
            Connection connection,
            PreparedStatement statement,
            ResultSet result) {

        try {

            if (result != null) {
                result.close();
            }

        } catch (Exception e) {
        }


        try {

            if (statement != null) {
                statement.close();
            }

        } catch (Exception e) {
        }


        try {

            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {
        }
    }
}