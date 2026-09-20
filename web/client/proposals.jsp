<%@ page import="java.util.List" %>
<%@ page import="com.skillbridge.model.Project" %>
<%@ page import="com.skillbridge.model.ClientProposal" %>

<%
    Project project =
            (Project) request.getAttribute("project");

    List<ClientProposal> proposals =
            (List<ClientProposal>) request.getAttribute("proposals");

    String bidHealth =
            (String) request.getAttribute("bidHealth");

    String bidHealthMessage =
            (String) request.getAttribute("bidHealthMessage");

    String success =
            request.getParameter("success");

    String error =
            request.getParameter("error");

    Double lowestBid =
            (Double) request.getAttribute("lowestBid");

    Double averageBid =
            (Double) request.getAttribute("averageBid");

    Double highestBid =
            (Double) request.getAttribute("highestBid");
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>

<div class="page-container">

    <!-- =====================================================
         SUCCESS / ERROR MESSAGES
         ===================================================== -->

    <% if ("hire".equals(success)) { %>

        <div class="alert alert-success">
            <strong>Freelancer hired successfully!</strong>
            The selected proposal has been accepted and
            the project is now in progress.
        </div>

    <% } %>


    <% if ("hire".equals(error)) { %>

        <div class="alert alert-danger">
            <strong>Unable to hire freelancer.</strong>
            The project may already have a freelancer or
            the proposal is no longer available.
        </div>

    <% } %>


    <% if ("system".equals(error)) { %>

        <div class="alert alert-danger">
            <strong>Something went wrong.</strong>
            Please try again.
        </div>

    <% } %>


    <% if (request.getAttribute("error") != null) { %>

        <div class="alert alert-danger">
            <strong>Error:</strong>
            <%=request.getAttribute("error")%>
        </div>

    <% } %>


    <!-- =====================================================
         PAGE HEADER
         ===================================================== -->

    <div class="page-header">

        <div>

            <div class="breadcrumb">
                Client
                <span>?</span>
                My Projects
                <span>?</span>
                Proposals
            </div>

            <h1>
                Project Proposals
            </h1>

            <p>
                Compare freelancer proposals and choose
                the best match for your project.
            </p>

        </div>

        <div>

            <a href="<%=request.getContextPath()%>/client/my-projects.jsp"
               class="btn btn-outline">

                 Back to My Projects

            </a>

        </div>

    </div>


    <% if (project != null) { %>

        <!-- =====================================================
             PROJECT SUMMARY
             ===================================================== -->

        <div class="project-summary-card">

            <div class="project-summary-main">

                <span class="summary-label">
                    PROJECT
                </span>

                <h2>
                    <%=project.getTitle()%>
                </h2>

                <p>
                    <%=project.getDescription()%>
                </p>

            </div>


            <div class="project-summary-meta">

                <div class="summary-item">

                    <span>Budget</span>

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


                <div class="summary-item">

                    <span>Deadline</span>

                    <strong>
                        <%=project.getDeadlineDays()%> days
                    </strong>

                </div>


                <div class="summary-item">

                    <span>Status</span>

                    <strong>

                        <%
                            String projectStatusClass =
                                    "pending";

                            if ("IN_PROGRESS".equals(
                                    project.getProjectStatus())) {

                                projectStatusClass =
                                        "accepted";

                            } else if ("COMPLETED".equals(
                                    project.getProjectStatus())) {

                                projectStatusClass =
                                        "accepted";

                            } else if ("CANCELLED".equals(
                                    project.getProjectStatus())) {

                                projectStatusClass =
                                        "rejected";
                            }
                        %>

                        <span class="status-badge
                            <%=projectStatusClass%>">

                            <%=project.getProjectStatus()%>

                        </span>

                    </strong>

                </div>

            </div>

        </div>


        <!-- =====================================================
             SMART BID HEALTH
             ===================================================== -->

        <div class="bid-health-card">

            <div class="bid-health-header">

                <div>

                    <span class="summary-label">
                        SMART BID HEALTH
                    </span>

                    <h2>
                        Bid Market Analysis
                    </h2>

                    <p>
                        SkillBridge analyzes submitted bids
                        against your project budget.
                    </p>
                    

                </div>


                <div>

                    <% if ("HEALTHY".equals(bidHealth)) { %>
 <a href="<%=request.getContextPath()%>/BidHealthServlet?projectId=<%=project.getProjectId()%>" 
   class="smart-compare-btn"
   style="display: inline-block; padding: 10px 20px; background-color: #107259; color: #ffffff; text-decoration: none; border-radius: 6px; font-weight: 600; text-align: center; cursor: pointer; border: none; box-sizing: border-box;">
   
    View Bid Health

</a>

                    <% } else if ("LOW".equals(bidHealth)) { %>

                        <span class="health-badge low">
                             BELOW BUDGET
                        </span>

                    <% } else if ("HIGH".equals(bidHealth)) { %>

                        <span class="health-badge high">
                             ABOVE BUDGET
                        </span>

                    <% } else if ("NO_BIDS".equals(bidHealth)) { %>

                        <span class="health-badge neutral">
                             NO BIDS
                        </span>

                    <% } else { %>

                        <span class="health-badge neutral">
                             UNKNOWN
                        </span>

                    <% } %>

                </div>

            </div>


            <% if (!"NO_BIDS".equals(bidHealth)
                    && lowestBid != null
                    && averageBid != null
                    && highestBid != null) { %>

                <div class="bid-stat-grid">

                    <div class="bid-stat">

                        <span>
                            Lowest Bid
                        </span>

                        <strong>
                            <%=String.format(
                                "%.2f",
                                lowestBid
                            )%>
                        </strong>

                    </div>


                    <div class="bid-stat featured">

                        <span>
                            Average Bid
                        </span>

                        <strong>
                            <%=String.format(
                                "%.2f",
                                averageBid
                            )%>
                        </strong>

                    </div>


                    <div class="bid-stat">

                        <span>
                            Highest Bid
                        </span>

                        <strong>
                            <%=String.format(
                                "%.2f",
                                highestBid
                            )%>
                        </strong>

                    </div>

                </div>

            <% } %>


            <% if (bidHealthMessage != null) { %>

                <div class="bid-health-message">

                    <strong>
                        Insight:
                    </strong>

                    <%=bidHealthMessage%>

                </div>

            <% } %>

        </div>


        <!-- =====================================================
             PROPOSAL COMPARISON
             ===================================================== -->

        <div class="section-card">

            <div class="section-card-header">

                <div>

                    <span class="summary-label">
                        PROPOSAL COMPARISON
                    </span>

                    <h2>
                        Freelancer Proposals
                    </h2>

                    <p>
                        Compare skill match, pricing,
                        delivery time and proposal details.
                    </p>

                </div>


                <div class="proposal-count">

                    <strong>
                        <%=proposals == null
                                ? 0
                                : proposals.size()%>
                    </strong>

                    <span>
                        Proposals
                    </span>

                </div>

            </div>


            <% if (proposals == null
                    || proposals.isEmpty()) { %>

                <!-- =================================================
                     EMPTY STATE
                     ================================================= -->

                <div class="empty-state">

                    <div class="empty-icon">
                        ?
                    </div>

                    <h3>
                        No proposals yet
                    </h3>

                    <p>
                        Freelancers have not submitted
                        proposals for this project yet.
                    </p>

                    <a href="<%=request.getContextPath()%>/client/my-projects.jsp"
                       class="btn btn-outline">

                         Back to My Projects

                    </a>

                </div>

            <% } else { %>

                <!-- =================================================
                     PROPOSAL TABLE
                     ================================================= -->

                <div class="table-wrapper">

                    <table class="proposal-table">

                        <thead>

                            <tr>

                                <th>
                                    Freelancer
                                </th>

                                <th>
                                    Skill Match
                                </th>

                                <th>
                                    Bid
                                </th>

                                <th>
                                    Delivery
                                </th>

                                <th>
                                    Proposal
                                </th>

                                <th>
                                    Status
                                </th>

                                <th>
                                    Action
                                </th>

                            </tr>

                        </thead>


                        <tbody>

                            <% for (ClientProposal proposal
                                    : proposals) { %>

                                <tr>

                                    <!-- Freelancer -->

                                    <td>

                                        <div class="freelancer-cell">

                                            <div class="avatar-circle">

                                                <%=proposal.getFreelancerName()
                                                        .substring(0, 1)
                                                        .toUpperCase()%>

                                            </div>

                                            <div>

                                                <strong>
                                                    <%=proposal.getFreelancerName()%>
                                                </strong>

                                                <small>
                                                    <%=proposal.getFreelancerEmail()%>
                                                </small>

                                            </div>

                                        </div>

                                    </td>


                                    <!-- Skill Match -->

                                    <td>

                                        <div class="match-wrapper">

                                            <div class="match-number">

                                                <strong>
                                                    <%=proposal.getSkillMatchScore()%>%
                                                </strong>

                                            </div>

                                            <div class="match-bar">

                                                <div class="match-fill"
                                                     style="width:<%=proposal.getSkillMatchScore()%>%;">
                                                </div>

                                            </div>

                                        </div>

                                    </td>


                                    <!-- Bid -->

                                    <td>

                                        <strong class="bid-amount">

                                            <%=String.format(
                                                "%.2f",
                                                proposal.getBidAmount()
                                            )%>

                                        </strong>

                                    </td>


                                    <!-- Delivery -->

                                    <td>

                                        <span class="delivery-value">

                                            <%=proposal.getDeliveryDays()%>
                                            days

                                        </span>

                                    </td>


                                    <!-- Proposal -->

                                    <td>

                                        <div class="proposal-preview">

                                            <%=proposal.getCoverLetter()%>

                                        </div>

                                    </td>


                                    <!-- Status -->

                                    <td>

                                        <% if ("ACCEPTED".equals(
                                                proposal.getProposalStatus())) { %>

                                            <span class="status-badge accepted">
                                                Hired
                                            </span>

                                        <% } else if ("REJECTED".equals(
                                                proposal.getProposalStatus())) { %>

                                            <span class="status-badge rejected">
                                                Rejected
                                            </span>

                                        <% } else { %>

                                            <span class="status-badge pending">
                                                Pending
                                            </span>

                                        <% } %>

                                    </td>


                                    <!-- Action -->

                                    <td>

                                        <% if ("PENDING".equals(
                                                proposal.getProposalStatus())
                                                && "OPEN".equals(
                                                project.getProjectStatus())) { %>

                                            <form method="post"
                                                  action="<%=request.getContextPath()%>/HireFreelancerServlet"
                                                  onsubmit="return confirm('Are you sure you want to hire <%=proposal.getFreelancerName()%>? This will reject all other pending proposals for this project.');">

                                                <input type="hidden"
                                                       name="projectId"
                                                       value="<%=project.getProjectId()%>">

                                                <input type="hidden"
                                                       name="proposalId"
                                                       value="<%=proposal.getProposalId()%>">

                                                <button type="submit"
                                                        class="btn btn-primary btn-small">

                                                    Hire Freelancer

                                                </button>

                                            </form>

                                        <% } else if ("ACCEPTED".equals(
                                                proposal.getProposalStatus())) { %>

                                            <span class="status-badge accepted">

                                                ? Hired

                                            </span>

                                        <% } else if ("REJECTED".equals(
                                                proposal.getProposalStatus())) { %>
                                                
                                             <%
    Integer acceptedFreelancerId =
        (Integer) request.getAttribute("acceptedFreelancerId");
%>

<% if (acceptedFreelancerId != null) { %>

    <a
        href="<%=request.getContextPath()%>/ConversationServlet?userId=<%=acceptedFreelancerId%>"
        class="profile-trust-button">

        Message Freelancer
    </a>

<% } %>   

                                            <span class="status-badge rejected">

                                                Rejected

                                            </span>

                                        <% } else { %>

                                            <span class="action-disabled">

                                                Hiring unavailable

                                            </span>

                                        <% } %>

                                    </td>

                                </tr>

                            <% } %>

                        </tbody>

                    </table>

                </div>

            <% } %>

        </div>

    <% } else { %>

        <!-- =====================================================
             PROJECT NOT FOUND
             ===================================================== -->

        <div class="empty-state">

            <div class="empty-icon">
                ?
            </div>

            <h3>
                Project Not Found
            </h3>

            <p>
                The requested project could not be found.
            </p>

            <a href="<%=request.getContextPath()%>/client/my-projects.jsp"
               class="btn btn-primary">

                Back to My Projects

            </a>

        </div>

    <% } %>

</div>


<style>

.page-container {
    max-width: 1250px;
    margin: 0 auto;
    padding: 40px 24px 70px;
}

.page-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 25px;
    margin-bottom: 30px;
}

.breadcrumb {
    color: #7b8794;
    font-size: 13px;
    margin-bottom: 10px;
}

.breadcrumb span {
    margin: 0 7px;
}

.page-header h1 {
    margin: 0 0 8px;
    font-size: 32px;
}

.page-header p {
    margin: 0;
    color: #6b7280;
}

.project-summary-card,
.bid-health-card,
.section-card {
    background: #ffffff;
    border: 1px solid #e7ece9;
    border-radius: 16px;
    margin-bottom: 25px;
    box-shadow: 0 5px 20px rgba(0,0,0,0.03);
}

.project-summary-card {
    padding: 28px;
    display: flex;
    justify-content: space-between;
    gap: 35px;
}

.project-summary-main {
    flex: 1;
}

.summary-label {
    color: #16834a;
    font-size: 11px;
    font-weight: 800;
    letter-spacing: 1px;
}

.project-summary-main h2 {
    margin: 8px 0 10px;
}

.project-summary-main p {
    color: #68737d;
    line-height: 1.7;
}

.project-summary-meta {
    min-width: 300px;
    display: grid;
    gap: 15px;
}

.summary-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
}

.summary-item span {
    color: #737d87;
    font-size: 13px;
}

.bid-health-card {
    padding: 28px;
}

.bid-health-header {
    display: flex;
    justify-content: space-between;
    gap: 20px;
}

.bid-health-header h2 {
    margin: 8px 0;
}

.bid-health-header p {
    color: #6b7280;
}

.health-badge {
    display: inline-block;
    padding: 8px 14px;
    border-radius: 20px;
    font-size: 12px;
    font-weight: 800;
}

.health-badge.healthy {
    background: #e8f7ee;
    color: #16834a;
}

.health-badge.low {
    background: #fff7df;
    color: #9a6b00;
}

.health-badge.high {
    background: #fdecec;
    color: #c0392b;
}

.health-badge.neutral {
    background: #eef1f3;
    color: #66727d;
}

.bid-stat-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 15px;
    margin-top: 25px;
}

.bid-stat {
    padding: 20px;
    background: #f8faf9;
    border-radius: 12px;
}

.bid-stat.featured {
    border: 1px solid #bfe4ce;
    background: #f0faf4;
}

.bid-stat span {
    display: block;
    color: #71808a;
    font-size: 13px;
    margin-bottom: 8px;
}

.bid-stat strong {
    font-size: 21px;
}

.bid-health-message {
    margin-top: 20px;
    padding: 14px 16px;
    border-radius: 10px;
    background: #f5f8f6;
    color: #46534b;
    font-size: 14px;
}

.section-card {
    padding: 28px;
}

.section-card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 20px;
    margin-bottom: 25px;
}

.section-card-header h2 {
    margin: 7px 0;
}

.section-card-header p {
    color: #6b7280;
    margin: 0;
}

.proposal-count {
    min-width: 90px;
    text-align: center;
    padding: 12px;
    border-radius: 12px;
    background: #f3f8f5;
}

.proposal-count strong {
    display: block;
    font-size: 22px;
    color: #16834a;
}

.proposal-count span {
    font-size: 11px;
    color: #6b7280;
}

.table-wrapper {
    overflow-x: auto;
}

.proposal-table {
    width: 100%;
    border-collapse: collapse;
    min-width: 1050px;
}

.proposal-table th {
    text-align: left;
    padding: 15px;
    background: #f7f9f8;
    color: #68737d;
    font-size: 12px;
    text-transform: uppercase;
    letter-spacing: .5px;
}

.proposal-table td {
    padding: 18px 15px;
    border-bottom: 1px solid #edf0ee;
    vertical-align: middle;
}

.freelancer-cell {
    display: flex;
    align-items: center;
    gap: 10px;
}

.freelancer-cell strong {
    display: block;
}

.freelancer-cell small {
    display: block;
    margin-top: 3px;
    color: #8a949c;
    font-size: 11px;
}

.avatar-circle {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #e8f7ee;
    color: #16834a;
    font-weight: 800;
}

.match-wrapper {
    width: 100px;
}

.match-number {
    margin-bottom: 6px;
}

.match-number strong {
    color: #16834a;
}

.match-bar {
    width: 100%;
    height: 6px;
    background: #e7ece9;
    border-radius: 10px;
    overflow: hidden;
}

.match-fill {
    height: 100%;
    background: #16834a;
    border-radius: 10px;
}

.bid-amount {
    color: #16834a;
}

.delivery-value {
    white-space: nowrap;
}

.proposal-preview {
    max-width: 220px;
    color: #626d76;
    font-size: 13px;
    line-height: 1.5;
}

.status-badge {
    display: inline-block;
    padding: 6px 12px;
    border-radius: 20px;
    font-size: 11px;
    font-weight: 800;
    white-space: nowrap;
}

.status-badge.accepted {
    background: #e8f7ee;
    color: #16834a;
}

.status-badge.rejected {
    background: #fdecec;
    color: #c0392b;
}

.status-badge.pending {
    background: #fff7df;
    color: #9a6b00;
}

.btn-small {
    padding: 8px 13px;
    font-size: 12px;
    white-space: nowrap;
}

.action-disabled {
    color: #8a949c;
    font-size: 12px;
}

.alert {
    padding: 15px 18px;
    border-radius: 10px;
    margin-bottom: 20px;
    font-size: 14px;
}

.alert-success {
    background: #e8f7ee;
    color: #176b42;
    border: 1px solid #bde8cd;
}

.alert-danger {
    background: #fdecec;
    color: #a93226;
    border: 1px solid #f2c2bd;
}

.empty-state {
    text-align: center;
    padding: 60px 20px;
}

.empty-icon {
    font-size: 40px;
    margin-bottom: 15px;
}

.empty-state h3 {
    margin-bottom: 8px;
}

.empty-state p {
    color: #737d87;
    margin-bottom: 20px;
}

@media (max-width: 800px) {

    .page-header,
    .project-summary-card,
    .bid-health-header {
        flex-direction: column;
    }

    .project-summary-meta {
        min-width: 100%;
    }

    .bid-stat-grid {
        grid-template-columns: 1fr;
    }

}

</style>


<%@ include file="../includes/footer.jsp" %>