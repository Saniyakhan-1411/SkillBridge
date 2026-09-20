package com.skillbridge.model;

public class ClientReliabilityScore {

    private int userId;

    private int totalProjects;
    private int completedProjects;
    private int reviewCount;

    private double averageRating;

    private int completedProjectScore;
    private int ratingScore;
    private int reviewScore;
    private int profileScore;
    private int activityScore;

    private int reliabilityScore;

    private String reliabilityLevel;

    public ClientReliabilityScore() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    public int getCompletedProjects() {
        return completedProjects;
    }

    public void setCompletedProjects(int completedProjects) {
        this.completedProjects = completedProjects;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getCompletedProjectScore() {
        return completedProjectScore;
    }

    public void setCompletedProjectScore(int completedProjectScore) {
        this.completedProjectScore = completedProjectScore;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public int getProfileScore() {
        return profileScore;
    }

    public void setProfileScore(int profileScore) {
        this.profileScore = profileScore;
    }

    public int getActivityScore() {
        return activityScore;
    }

    public void setActivityScore(int activityScore) {
        this.activityScore = activityScore;
    }

    public int getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(int reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    public String getReliabilityLevel() {
        return reliabilityLevel;
    }

    public void setReliabilityLevel(String reliabilityLevel) {
        this.reliabilityLevel = reliabilityLevel;
    }
}