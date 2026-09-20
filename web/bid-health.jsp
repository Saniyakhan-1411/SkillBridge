<%@ page import="com.skillbridge.model.Project" %>
<%@ page import="com.skillbridge.model.BidHealth" %>

<%
    Project project =
            (Project) request.getAttribute("project");

    BidHealth bidHealth =
            (BidHealth) request.getAttribute("bidHealth");

    String contextPath =
            request.getContextPath();

    if (project == null || bidHealth == null) {
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Bid Health | SkillBridge</title>

    <link rel="stylesheet"
          href="<%=contextPath%>/assets/css/style.css">

</head>

<body>

<jsp:include page="/includes/navbar.jsp" />

<div style="
    max-width:900px;
    margin:60px auto;
    padding:30px;
    text-align:center;
">

    <h1>Bid Health Unavailable</h1>

    <p>
        We could not calculate the bidding health
        for this project.
    </p>

    <a
        href="<%=contextPath%>/client/my-projects.jsp">

        Back to My Projects

    </a>

</div>

<jsp:include page="/includes/footer.jsp" />

</body>

</html>

<%
        return;
    }

    String status =
            bidHealth.getHealthStatus();

    String statusClass =
            "healthy";

    if ("LOW".equalsIgnoreCase(status)) {
        statusClass = "low";
    } else if ("MODERATE".equalsIgnoreCase(status)) {
        statusClass = "moderate";
    } else if ("HIGH".equalsIgnoreCase(status)) {
        statusClass = "high";
    }
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>
        Bid Health | SkillBridge
    </title>

    <link rel="stylesheet"
          href="<%=contextPath%>/assets/css/style.css">

    <link rel="stylesheet"
          href="<%=contextPath%>/assets/css/bid-health.css">

</head>

<body>

<jsp:include page="/includes/navbar.jsp" />

<div class="bid-health-page">

    <div class="bid-health-container">

        <a
            href="<%=contextPath%>/ClientProposalsServlet?projectId=<%=project.getProjectId()%>"
            class="bid-back">

             Back to Proposals

        </a>


        <!-- HEADER -->

        <div class="bid-header">

            <div>

                <span class="bid-eyebrow">
                    SKILLBRIDGE INTELLIGENCE
                </span>

                <h1>
                    Bid Health
                </h1>

                <p>
                    Understand how freelancer bids compare
                    with your project budget.
                </p>

            </div>

        </div>


        <!-- PROJECT -->

        <div class="bid-project-card">

            <span>
                PROJECT
            </span>

            <h2>
                <%=project.getTitle()%>
            </h2>

            <div class="bid-project-budget">

                <span>
                    Your Budget
                </span>

                <strong>

                    <%=String.format(
                        "%.2f",
                        project.getBudgetMin()
                    )%>

                    -

                    <%=String.format(
                        "%.2f",
                        project.getBudgetMax()
                    )%>

                </strong>

            </div>

        </div>


        <!-- HEALTH -->

        <div class="bid-health-main <%=statusClass%>">

            <div class="bid-health-score">

                <div class="bid-health-circle">

                    <span>
                        <%=Math.round(
                            bidHealth.getBidPositionPercentage()
                        )%>%
                    </span>

                </div>

            </div>

            <div>

                <span class="bid-label">
                    CURRENT BID HEALTH
                </span>

                <h2>
                    <%=bidHealth.getHealthStatus()%>
                </h2>

                <p>
                    <%=bidHealth.getHealthMessage()%>
                </p>

            </div>

        </div>


        <!-- STATISTICS -->

        <div class="bid-stats">

            <div class="bid-stat-card">

                <span>
                    PROPOSALS
                </span>

                <strong>
                    <%=bidHealth.getProposalCount()%>
                </strong>

            </div>


            <div class="bid-stat-card">

                <span>
                    LOWEST BID
                </span>

                <strong>
                    <%=String.format(
                        "%.2f",
                        bidHealth.getLowestBid()
                    )%>
                </strong>

            </div>


            <div class="bid-stat-card">

                <span>
                    AVERAGE BID
                </span>

                <strong>
                    <%=String.format(
                        "%.2f",
                        bidHealth.getAverageBid()
                    )%>
                </strong>

            </div>


            <div class="bid-stat-card">

                <span>
                    HIGHEST BID
                </span>

                <strong>
                    <%=String.format(
                        "%.2f",
                        bidHealth.getHighestBid()
                    )%>
                </strong>

            </div>

        </div>


        <!-- COMPARISON -->

        <div class="bid-comparison-card">

            <div class="bid-section-heading">

                <span>
                    BUDGET POSITION
                </span>

                <h2>
                    Average Bid vs Your Budget
                </h2>

            </div>

            <div class="budget-row">

                <div>

                    <span>
                        Budget Range
                    </span>

                    <strong>

                        <%=String.format(
                            "%.2f",
                            bidHealth.getBudgetMin()
                        )%>

                        -

                        <%=String.format(
                            "%.2f",
                            bidHealth.getBudgetMax()
                        )%>

                    </strong>

                </div>


                <div>

                    <span>
                        Average Bid
                    </span>

                    <strong>

                        <%=String.format(
                            "%.2f",
                            bidHealth.getAverageBid()
                        )%>

                    </strong>

                </div>

            </div>

        </div>


        <!-- RECOMMENDATION -->

        <div class="bid-recommendation">

            <div class="bid-recommendation-icon">
                *
            </div>

            <div>

                <span>
                    SKILLBRIDGE RECOMMENDATION
                </span>

                <h2>
                    Before You Hire
                </h2>

                <p>
                    <%=bidHealth.getRecommendation()%>
                </p>

            </div>

        </div>


        <!-- ACTIONS -->

        <div class="bid-actions">

            <a
                href="<%=contextPath%>/ClientProposalsServlet?projectId=<%=project.getProjectId()%>"
                class="bid-secondary-btn">

                View Proposals

            </a>

           

        </div>

    </div>

</div>

<jsp:include page="/includes/footer.jsp" />

</body>

</html>