package com.skillbridge.service;

import com.skillbridge.model.Project;
import com.skillbridge.model.ProjectRisk;

/**
 * Rule-based project risk analysis engine.
 *
 * This service analyzes:
 *
 * 1. Deadline
 * 2. Skill complexity
 * 3. Budget
 * 4. Description quality
 * 5. Project scope
 *
 * It produces an overall risk score from 0 to 100.
 */
public class ProjectRiskAnalyzer {

    /**
     * Analyze a project and return its risk profile.
     */
    public ProjectRisk analyze(Project project) {

        ProjectRisk risk = new ProjectRisk();

        risk.setProjectId(
                project.getProjectId()
        );

        /*
         * --------------------------------------------------
         * 1. DEADLINE RISK
         * --------------------------------------------------
         */

        int deadlineRisk =
                calculateDeadlineRisk(
                        project.getDeadlineDays()
                );

        risk.setDeadlineRisk(deadlineRisk);

        risk.setDeadlineMessage(
                getDeadlineMessage(
                        project.getDeadlineDays()
                )
        );


        /*
         * --------------------------------------------------
         * 2. SKILL COMPLEXITY RISK
         * --------------------------------------------------
         */

        int skillRisk =
                calculateSkillRisk(
                        project.getRequiredSkills()
                );

        risk.setSkillComplexityRisk(
                skillRisk
        );

        risk.setSkillMessage(
                getSkillMessage(
                        project.getRequiredSkills()
                )
        );


        /*
         * --------------------------------------------------
         * 3. BUDGET RISK
         * --------------------------------------------------
         */

        int budgetRisk =
                calculateBudgetRisk(
                        project.getBudgetMin(),
                        project.getBudgetMax(),
                        project.getRequiredSkills()
                );

        risk.setBudgetRisk(budgetRisk);

        risk.setBudgetMessage(
                getBudgetMessage(
                        budgetRisk
                )
        );


        /*
         * --------------------------------------------------
         * 4. DESCRIPTION RISK
         * --------------------------------------------------
         */

        int descriptionRisk =
                calculateDescriptionRisk(
                        project.getDescription()
                );

        risk.setDescriptionRisk(
                descriptionRisk
        );

        risk.setDescriptionMessage(
                getDescriptionMessage(
                        descriptionRisk
                )
        );


        /*
         * --------------------------------------------------
         * 5. SCOPE RISK
         * --------------------------------------------------
         */

        int scopeRisk =
                calculateScopeRisk(
                        project.getDescription()
                );

        risk.setScopeRisk(scopeRisk);

        risk.setScopeMessage(
                getScopeMessage(
                        scopeRisk
                )
        );


        /*
         * --------------------------------------------------
         * OVERALL RISK
         *
         * Weighted calculation:
         *
         * Deadline       25%
         * Skills         20%
         * Budget         20%
         * Description    15%
         * Scope          20%
         * --------------------------------------------------
         */

        int overallScore =
                (int) Math.round(
                        deadlineRisk * 0.25
                        + skillRisk * 0.20
                        + budgetRisk * 0.20
                        + descriptionRisk * 0.15
                        + scopeRisk * 0.20
                );

        risk.setOverallRiskScore(
                overallScore
        );


        /*
         * --------------------------------------------------
         * RISK LEVEL
         * --------------------------------------------------
         */

        String riskLevel;

        if (overallScore <= 30) {

            riskLevel = "LOW";

        } else if (overallScore <= 60) {

            riskLevel = "MEDIUM";

        } else {

            riskLevel = "HIGH";
        }

        risk.setRiskLevel(
                riskLevel
        );


        /*
         * --------------------------------------------------
         * OVERALL RECOMMENDATION
         * --------------------------------------------------
         */

        risk.setOverallMessage(
                getOverallMessage(
                        riskLevel
                )
        );

        return risk;
    }


    /*
     * ======================================================
     * DEADLINE ANALYSIS
     * ======================================================
     */

    private int calculateDeadlineRisk(
            int deadlineDays) {

        if (deadlineDays <= 0) {
            return 100;
        }

        if (deadlineDays <= 3) {
            return 90;
        }

        if (deadlineDays <= 7) {
            return 60;
        }

        if (deadlineDays <= 14) {
            return 35;
        }

        return 15;
    }


    private String getDeadlineMessage(
            int deadlineDays) {

        if (deadlineDays <= 0) {

            return "No valid project deadline has been provided.";

        }

        if (deadlineDays <= 3) {

            return "Very tight deadline. The project may require additional time.";

        }

        if (deadlineDays <= 7) {

            return "Moderate deadline pressure. Careful planning is recommended.";

        }

        if (deadlineDays <= 14) {

            return "Reasonable deadline for a typical project scope.";

        }

        return "The deadline provides a comfortable development window.";
    }


    /*
     * ======================================================
     * SKILL COMPLEXITY ANALYSIS
     * ======================================================
     */

    private int calculateSkillRisk(
            String requiredSkills) {

        if (requiredSkills == null ||
                requiredSkills.trim().isEmpty()) {

            return 30;
        }

        String[] skills =
                requiredSkills.split(",");

        int count = 0;

        for (String skill : skills) {

            if (skill != null &&
                    !skill.trim().isEmpty()) {

                count++;
            }
        }

        if (count <= 2) {
            return 20;
        }

        if (count <= 4) {
            return 50;
        }

        if (count <= 6) {
            return 75;
        }

        return 90;
    }


    private String getSkillMessage(
            String requiredSkills) {

        if (requiredSkills == null ||
                requiredSkills.trim().isEmpty()) {

            return "No specific skills have been listed.";

        }

        int count =
                requiredSkills.split(",").length;

        if (count <= 2) {

            return "The project requires a focused set of skills.";

        }

        if (count <= 4) {

            return "The project requires several technical skills.";

        }

        if (count <= 6) {

            return "The project has a relatively high skill requirement.";

        }

        return "A large number of skills may increase project complexity.";
    }


    /*
     * ======================================================
     * BUDGET ANALYSIS
     * ======================================================
     */

    private int calculateBudgetRisk(
            double budgetMin,
            double budgetMax,
            String requiredSkills) {

        if (budgetMin <= 0 ||
                budgetMax <= 0 ||
                budgetMax < budgetMin) {

            return 80;
        }

        /*
         * We use the midpoint of the budget
         * as a simple scope-to-budget indicator.
         */

        double averageBudget =
                (budgetMin + budgetMax) / 2.0;

        int skillCount = 0;

        if (requiredSkills != null &&
                !requiredSkills.trim().isEmpty()) {

            String[] skills =
                    requiredSkills.split(",");

            for (String skill : skills) {

                if (skill != null &&
                        !skill.trim().isEmpty()) {

                    skillCount++;
                }
            }
        }

        /*
         * This is a prototype heuristic.
         *
         * More skills require a larger budget.
         */

        double expectedBudget =
                2000 + (skillCount * 1500);

        if (averageBudget >=
                expectedBudget * 1.5) {

            return 15;
        }

        if (averageBudget >=
                expectedBudget) {

            return 35;
        }

        if (averageBudget >=
                expectedBudget * 0.60) {

            return 65;
        }

        return 90;
    }


    private String getBudgetMessage(
            int budgetRisk) {

        if (budgetRisk <= 30) {

            return "The budget appears reasonable for the estimated project complexity.";

        }

        if (budgetRisk <= 60) {

            return "The budget may require careful scope and effort management.";

        }

        return "The budget may be low compared with the expected project complexity.";
    }


    /*
     * ======================================================
     * DESCRIPTION ANALYSIS
     * ======================================================
     */

    private int calculateDescriptionRisk(
            String description) {

        if (description == null ||
                description.trim().isEmpty()) {

            return 100;
        }

        int length =
                description.trim().length();

        if (length < 100) {

            return 85;

        }

        if (length < 250) {

            return 55;

        }

        if (length < 500) {

            return 30;
        }

        return 15;
    }


    private String getDescriptionMessage(
            int descriptionRisk) {

        if (descriptionRisk <= 30) {

            return "The project description provides useful scope information.";

        }

        if (descriptionRisk <= 60) {

            return "The project description could provide more detail.";

        }

        return "The project description is short and may leave important requirements unclear.";
    }


    /*
     * ======================================================
     * SCOPE ANALYSIS
     * ======================================================
     */

    private int calculateScopeRisk(
            String description) {

        if (description == null ||
                description.trim().isEmpty()) {

            return 80;
        }

        String text =
                description.toLowerCase();

        int taskCount = 0;

        String[] scopeKeywords = {
            "design",
            "develop",
            "development",
            "build",
            "test",
            "testing",
            "deploy",
            "deployment",
            "integrate",
            "integration",
            "maintain",
            "maintenance",
            "api",
            "database",
            "authentication",
            "dashboard",
            "payment",
            "notification"
        };

        for (String keyword :
                scopeKeywords) {

            if (text.contains(keyword)) {
                taskCount++;
            }
        }

        if (taskCount <= 2) {

            return 20;

        }

        if (taskCount <= 4) {

            return 50;

        }

        if (taskCount <= 6) {

            return 75;

        }

        return 90;
    }


    private String getScopeMessage(
            int scopeRisk) {

        if (scopeRisk <= 30) {

            return "The project scope appears relatively focused.";

        }

        if (scopeRisk <= 60) {

            return "Several responsibilities are included in the project scope.";

        }

        return "The project contains many responsibilities and may require clearer scope boundaries.";
    }


    /*
     * ======================================================
     * OVERALL MESSAGE
     * ======================================================
     */

    private String getOverallMessage(
            String riskLevel) {

        if ("LOW".equals(riskLevel)) {

            return "This project appears relatively well-defined and manageable.";

        }

        if ("MEDIUM".equals(riskLevel)) {

            return "Review the highlighted risk factors before hiring and consider clarifying the project scope.";

        }

        return "This project contains several risk indicators. Consider adjusting the deadline, budget, or scope before hiring.";
    }
}