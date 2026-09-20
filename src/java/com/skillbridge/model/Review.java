package com.skillbridge.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Review implements Serializable {

    private int reviewId;
    private int projectId;
    private int reviewerId;
    private int reviewedUserId;
    private int rating;
    private String reviewText;
    private Timestamp createdAt;

    // Display information
    private String reviewerName;
    private String reviewedUserName;

    public Review() {
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public int getReviewedUserId() {
        return reviewedUserId;
    }

    public void setReviewedUserId(int reviewedUserId) {
        this.reviewedUserId = reviewedUserId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewedUserName() {
        return reviewedUserName;
    }

    public void setReviewedUserName(String reviewedUserName) {
        this.reviewedUserName = reviewedUserName;
    }
}