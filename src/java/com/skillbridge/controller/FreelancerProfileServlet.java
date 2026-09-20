package com.skillbridge.controller;

import com.skillbridge.dao.FreelancerProfileDAO;
import com.skillbridge.model.FreelancerProfile;
import com.skillbridge.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/FreelancerProfileServlet")
public class FreelancerProfileServlet
        extends HttpServlet {


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session =
                request.getSession(false);


        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        String role =
                (String) session.getAttribute("role");


        if (!"FREELANCER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );


        FreelancerProfileDAO profileDAO =
                new FreelancerProfileDAO();


        FreelancerProfile profile =
                profileDAO.getProfileByUserId(
                        user.getUserId()
                );


        request.setAttribute(
                "profile",
                profile
        );


        request.getRequestDispatcher(
                "/freelancer/profile.jsp"
        ).forward(
                request,
                response
        );
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session =
                request.getSession(false);


        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        String role =
                (String) session.getAttribute("role");


        if (!"FREELANCER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }


        User user =
                (User) session.getAttribute(
                        "loggedInUser"
                );


        String headline =
                request.getParameter("headline");

        String bio =
                request.getParameter("bio");

        String skills =
                request.getParameter("skills");

        String hourlyRateText =
                request.getParameter("hourlyRate");

        String experienceText =
                request.getParameter("experienceYears");

        String location =
                request.getParameter("location");


        if (headline == null ||
                skills == null ||
                headline.trim().isEmpty() ||
                skills.trim().isEmpty()) {


            request.setAttribute(
                    "error",
                    "Headline and skills are required."
            );


            request.getRequestDispatcher(
                    "/freelancer/profile.jsp"
            ).forward(
                    request,
                    response
            );

            return;
        }


        try {

            double hourlyRate = 0;

            int experienceYears = 0;


            if (hourlyRateText != null &&
                    !hourlyRateText.trim().isEmpty()) {

                hourlyRate =
                        Double.parseDouble(
                                hourlyRateText
                        );
            }


            if (experienceText != null &&
                    !experienceText.trim().isEmpty()) {

                experienceYears =
                        Integer.parseInt(
                                experienceText
                        );
            }


            if (hourlyRate < 0 ||
                    experienceYears < 0) {

                request.setAttribute(
                        "error",
                        "Rate and experience cannot be negative."
                );


                request.getRequestDispatcher(
                        "/freelancer/profile.jsp"
                ).forward(
                        request,
                        response
                );

                return;
            }


            FreelancerProfile profile =
                    new FreelancerProfile();


            profile.setUserId(
                    user.getUserId()
            );

            profile.setHeadline(
                    headline.trim()
            );

            profile.setBio(
                    bio == null
                    ? ""
                    : bio.trim()
            );

            profile.setSkills(
                    skills.trim()
            );

            profile.setHourlyRate(
                    hourlyRate
            );

            profile.setExperienceYears(
                    experienceYears
            );

            profile.setLocation(
                    location == null
                    ? ""
                    : location.trim()
            );


            int completion =
                    calculateProfileCompletion(
                            headline,
                            bio,
                            skills,
                            hourlyRate,
                            experienceYears,
                            location
                    );


            profile.setProfileCompletion(
                    completion
            );


            FreelancerProfileDAO profileDAO =
                    new FreelancerProfileDAO();


            FreelancerProfile existingProfile =
                    profileDAO.getProfileByUserId(
                            user.getUserId()
                    );


            boolean success;


            if (existingProfile == null) {

                success =
                        profileDAO.createProfile(
                                profile
                        );

            } else {

                success =
                        profileDAO.updateProfile(
                                profile
                        );
            }


            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/FreelancerProfileServlet?success=1"
                );

            } else {

                request.setAttribute(
                        "error",
                        "Unable to save profile. Please try again."
                );


                request.setAttribute(
                        "profile",
                        profile
                );


                request.getRequestDispatcher(
                        "/freelancer/profile.jsp"
                ).forward(
                        request,
                        response
                );
            }


        } catch (NumberFormatException e) {

            request.setAttribute(
                    "error",
                    "Please enter valid numbers for rate and experience."
            );


            request.getRequestDispatcher(
                    "/freelancer/profile.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            e.printStackTrace();


            request.setAttribute(
                    "error",
                    "Something went wrong while saving your profile."
            );


            request.getRequestDispatcher(
                    "/freelancer/profile.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }


    private int calculateProfileCompletion(
            String headline,
            String bio,
            String skills,
            double hourlyRate,
            int experienceYears,
            String location) {


        int completed = 0;


        if (headline != null &&
                !headline.trim().isEmpty()) {

            completed++;
        }


        if (bio != null &&
                !bio.trim().isEmpty()) {

            completed++;
        }


        if (skills != null &&
                !skills.trim().isEmpty()) {

            completed++;
        }


        if (hourlyRate > 0) {

            completed++;
        }


        if (experienceYears > 0) {

            completed++;
        }


        if (location != null &&
                !location.trim().isEmpty()) {

            completed++;
        }


        return (completed * 100) / 6;
    }
}