package com.skillbridge.model;

import java.io.Serializable;

public class TrustScore implements Serializable {

    private int userId;
    private String role;

    private double averageRating;
    private int completedProjects;
    private int profileCompletion;
    private int reviewCount;

    private double ratingScore;
    private double completionScore;
    private double profileScore;
    private double reviewScore;

    private int trustScore;
    private String trustLevel;

    public TrustScore() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getCompletedProjects() {
        return completedProjects;
    }

    public void setCompletedProjects(int completedProjects) {
        this.completedProjects = completedProjects;
    }

    public int getProfileCompletion() {
        return profileCompletion;
    }

    public void setProfileCompletion(int profileCompletion) {
        this.profileCompletion = profileCompletion;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public double getCompletionScore() {
        return completionScore;
    }

    public void setCompletionScore(double completionScore) {
        this.completionScore = completionScore;
    }

    public double getProfileScore() {
        return profileScore;
    }

    public void setProfileScore(double profileScore) {
        this.profileScore = profileScore;
    }

    public double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public int getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(int trustScore) {
        this.trustScore = trustScore;
    }

    public String getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(String trustLevel) {
        this.trustLevel = trustLevel;
    }
}