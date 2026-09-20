package com.skillbridge.model;

/**
 * Represents the calculated bidding health
 * of a SkillBridge project.
 */
public class BidHealth {

    private int projectId;

    private int proposalCount;

    private double budgetMin;
    private double budgetMax;

    private double lowestBid;
    private double highestBid;
    private double averageBid;

    private double budgetMidpoint;

    private double bidPositionPercentage;

    private String healthStatus;
    private String healthMessage;

    private String recommendation;

    public BidHealth() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProposalCount() {
        return proposalCount;
    }

    public void setProposalCount(int proposalCount) {
        this.proposalCount = proposalCount;
    }

    public double getBudgetMin() {
        return budgetMin;
    }

    public void setBudgetMin(double budgetMin) {
        this.budgetMin = budgetMin;
    }

    public double getBudgetMax() {
        return budgetMax;
    }

    public void setBudgetMax(double budgetMax) {
        this.budgetMax = budgetMax;
    }

    public double getLowestBid() {
        return lowestBid;
    }

    public void setLowestBid(double lowestBid) {
        this.lowestBid = lowestBid;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public double getAverageBid() {
        return averageBid;
    }

    public void setAverageBid(double averageBid) {
        this.averageBid = averageBid;
    }

    public double getBudgetMidpoint() {
        return budgetMidpoint;
    }

    public void setBudgetMidpoint(double budgetMidpoint) {
        this.budgetMidpoint = budgetMidpoint;
    }

    public double getBidPositionPercentage() {
        return bidPositionPercentage;
    }

    public void setBidPositionPercentage(
            double bidPositionPercentage) {

        this.bidPositionPercentage =
                bidPositionPercentage;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(
            String healthStatus) {

        this.healthStatus = healthStatus;
    }

    public String getHealthMessage() {
        return healthMessage;
    }

    public void setHealthMessage(
            String healthMessage) {

        this.healthMessage =
                healthMessage;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(
            String recommendation) {

        this.recommendation =
                recommendation;
    }
}