package com.skillbridge.service;

import com.skillbridge.model.BidHealth;
import com.skillbridge.model.ClientProposal;
import com.skillbridge.model.Project;

import java.util.List;

/**
 * Calculates bidding health for a project.
 */
public class BidHealthService {

    public BidHealth analyze(
            Project project,
            List<ClientProposal> proposals) {

        BidHealth health =
                new BidHealth();

        health.setProjectId(
                project.getProjectId()
        );

        health.setBudgetMin(
                project.getBudgetMin()
        );

        health.setBudgetMax(
                project.getBudgetMax()
        );

        /*
         * --------------------------------------------------
         * NO PROPOSALS
         * --------------------------------------------------
         */

        if (proposals == null ||
                proposals.isEmpty()) {

            health.setProposalCount(0);

            health.setLowestBid(0);
            health.setHighestBid(0);
            health.setAverageBid(0);

            health.setBudgetMidpoint(
                    (project.getBudgetMin()
                    + project.getBudgetMax()) / 2.0
            );

            health.setBidPositionPercentage(0);

            health.setHealthStatus(
                    "NO DATA"
            );

            health.setHealthMessage(
                    "No proposals have been received yet."
            );

            health.setRecommendation(
                    "Wait for freelancers to submit proposals before evaluating bid health."
            );

            return health;
        }

        /*
         * --------------------------------------------------
         * CALCULATE BID VALUES
         * --------------------------------------------------
         */

        double lowest =
                Double.MAX_VALUE;

        double highest =
                Double.MIN_VALUE;

        double total = 0;

        int validBids = 0;

        for (ClientProposal proposal :
                proposals) {

            double bid =
                    proposal.getBidAmount();

            if (bid <= 0) {
                continue;
            }

            if (bid < lowest) {
                lowest = bid;
            }

            if (bid > highest) {
                highest = bid;
            }

            total += bid;

            validBids++;
        }

        if (validBids == 0) {

            health.setProposalCount(
                    proposals.size()
            );

            health.setHealthStatus(
                    "NO DATA"
            );

            health.setHealthMessage(
                    "No valid bid amounts are available."
            );

            health.setRecommendation(
                    "Review the submitted proposals."
            );

            return health;
        }

        double average =
                total / validBids;

        health.setProposalCount(
                proposals.size()
        );

        health.setLowestBid(lowest);
        health.setHighestBid(highest);
        health.setAverageBid(average);

        /*
         * --------------------------------------------------
         * BUDGET MIDPOINT
         * --------------------------------------------------
         */

        double budgetMidpoint =
                (project.getBudgetMin()
                + project.getBudgetMax()) / 2.0;

        health.setBudgetMidpoint(
                budgetMidpoint
        );

        /*
         * --------------------------------------------------
         * BID POSITION
         *
         * Shows average bid relative
         * to the budget midpoint.
         * --------------------------------------------------
         */

        double position = 0;

        if (budgetMidpoint > 0) {

            position =
                    (average / budgetMidpoint) * 100;
        }

        health.setBidPositionPercentage(
                position
        );

        /*
         * --------------------------------------------------
         * BID HEALTH
         * --------------------------------------------------
         */

        if (average <
                project.getBudgetMin() * 0.70) {

            health.setHealthStatus(
                    "LOW"
            );

            health.setHealthMessage(
                    "Most bids are significantly below the client's budget range."
            );

            health.setRecommendation(
                    "Review very low bids carefully and verify scope, quality, and delivery expectations."
            );

        } else if (average <=
                project.getBudgetMax()) {

            health.setHealthStatus(
                    "HEALTHY"
            );

            health.setHealthMessage(
                    "The average bid is within a reasonable range for the project budget."
            );

            health.setRecommendation(
                    "The bidding environment looks healthy. Compare skill match, trust score, delivery time, and proposal quality before hiring."
            );

        } else if (average <=
                project.getBudgetMax() * 1.20) {

            health.setHealthStatus(
                    "MODERATE"
            );

            health.setHealthMessage(
                    "The average bid is slightly above the project budget."
            );

            health.setRecommendation(
                    "Consider negotiating with strong freelancers or reviewing whether the project scope requires additional budget."
            );

        } else {

            health.setHealthStatus(
                    "HIGH"
            );

            health.setHealthMessage(
                    "The average bid is significantly above the project budget."
            );

            health.setRecommendation(
                    "Consider increasing the budget, reducing project scope, or negotiating with selected freelancers."
            );
        }

        return health;
    }
}