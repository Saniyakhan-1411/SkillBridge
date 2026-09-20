package com.skillbridge.model;

/**
 * Represents the calculated risk analysis
 * of a SkillBridge project.
 */
public class ProjectRisk {

    private int projectId;

    private int overallRiskScore;
    private String riskLevel;

    private int deadlineRisk;
    private int skillComplexityRisk;
    private int budgetRisk;
    private int descriptionRisk;
    private int scopeRisk;

    private String deadlineMessage;
    private String skillMessage;
    private String budgetMessage;
    private String descriptionMessage;
    private String scopeMessage;

    private String overallMessage;

    public ProjectRisk() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getOverallRiskScore() {
        return overallRiskScore;
    }

    public void setOverallRiskScore(int overallRiskScore) {
        this.overallRiskScore = overallRiskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getDeadlineRisk() {
        return deadlineRisk;
    }

    public void setDeadlineRisk(int deadlineRisk) {
        this.deadlineRisk = deadlineRisk;
    }

    public int getSkillComplexityRisk() {
        return skillComplexityRisk;
    }

    public void setSkillComplexityRisk(int skillComplexityRisk) {
        this.skillComplexityRisk = skillComplexityRisk;
    }

    public int getBudgetRisk() {
        return budgetRisk;
    }

    public void setBudgetRisk(int budgetRisk) {
        this.budgetRisk = budgetRisk;
    }

    public int getDescriptionRisk() {
        return descriptionRisk;
    }

    public void setDescriptionRisk(int descriptionRisk) {
        this.descriptionRisk = descriptionRisk;
    }

    public int getScopeRisk() {
        return scopeRisk;
    }

    public void setScopeRisk(int scopeRisk) {
        this.scopeRisk = scopeRisk;
    }

    public String getDeadlineMessage() {
        return deadlineMessage;
    }

    public void setDeadlineMessage(String deadlineMessage) {
        this.deadlineMessage = deadlineMessage;
    }

    public String getSkillMessage() {
        return skillMessage;
    }

    public void setSkillMessage(String skillMessage) {
        this.skillMessage = skillMessage;
    }

    public String getBudgetMessage() {
        return budgetMessage;
    }

    public void setBudgetMessage(String budgetMessage) {
        this.budgetMessage = budgetMessage;
    }

    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    public void setDescriptionMessage(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    public String getScopeMessage() {
        return scopeMessage;
    }

    public void setScopeMessage(String scopeMessage) {
        this.scopeMessage = scopeMessage;
    }

    public String getOverallMessage() {
        return overallMessage;
    }

    public void setOverallMessage(String overallMessage) {
        this.overallMessage = overallMessage;
    }
}