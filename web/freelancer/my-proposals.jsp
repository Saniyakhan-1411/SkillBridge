<%@ page import="java.util.List" %>
<%@ page import="com.skillbridge.model.Proposal" %>
<%@ page import="com.skillbridge.model.User" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null ||
            !"FREELANCER".equals(loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }

    List<Proposal> proposals =
            (List<Proposal>) request.getAttribute("proposals");

    String error =
            (String) request.getAttribute("error");

    String success =
            request.getParameter("success");
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>

<style>

    body {
        background: #f5f7f6;
    }

    .page-wrapper {
        padding: 45px 7%;
        min-height: 75vh;
    }

    .page-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 30px;
        flex-wrap: wrap;
        gap: 15px;
    }

    .page-header h1 {
        margin: 0;
        color: #173b2d;
        font-size: 32px;
    }

    .page-header p {
        margin-top: 8px;
        color: #6b7280;
    }

    .back-btn {
        text-decoration: none;
        padding: 11px 20px;
        border-radius: 8px;
        border: 1px solid #d7dfdb;
        color: #174d38;
        background: white;
        font-weight: 600;
    }

    .alert {
        padding: 15px 18px;
        border-radius: 8px;
        margin-bottom: 22px;
        font-weight: 500;
    }

    .alert-success {
        background: #e9f8ef;
        color: #176b3a;
        border: 1px solid #bfe8ce;
    }

    .alert-error {
        background: #fff0f0;
        color: #a52828;
        border: 1px solid #f0c4c4;
    }

    .stats-row {
        display: grid;
        grid-template-columns:
            repeat(3, 1fr);

        gap: 20px;
        margin-bottom: 28px;
    }

    .stat-card {
        background: white;
        border-radius: 12px;
        padding: 22px;
        border: 1px solid #e2e8e4;
        box-shadow: 0 4px 15px rgba(0,0,0,0.04);
    }

    .stat-label {
        color: #6b7280;
        font-size: 14px;
        margin-bottom: 8px;
    }

    .stat-value {
        font-size: 28px;
        font-weight: 700;
        color: #174d38;
    }

    .table-container {
        background: white;
        border-radius: 14px;
        border: 1px solid #e2e8e4;
        overflow-x: auto;
        box-shadow: 0 4px 18px rgba(0,0,0,0.04);
    }

    .proposal-table {
        width: 100%;
        border-collapse: collapse;
        min-width: 850px;
    }

    .proposal-table th {
        background: #f7faf8;
        color: #496056;
        font-size: 13px;
        text-transform: uppercase;
        letter-spacing: .4px;
        padding: 17px;
        text-align: left;
        border-bottom: 1px solid #e5ebe7;
    }

    .proposal-table td {
        padding: 19px 17px;
        border-bottom: 1px solid #edf1ee;
        color: #34423b;
        vertical-align: middle;
    }

    .proposal-table tr:last-child td {
        border-bottom: none;
    }

    .project-id {
        font-weight: 700;
        color: #174d38;
    }

    .bid {
        font-weight: 700;
        color: #17251f;
    }

    .match-wrapper {
        width: 120px;
    }

    .match-bar {
        width: 100%;
        height: 7px;
        background: #e7ece9;
        border-radius: 20px;
        overflow: hidden;
        margin-top: 7px;
    }

    .match-fill {
        height: 100%;
        background: #218c5a;
        border-radius: 20px;
    }

    .match-text {
        font-weight: 700;
        color: #218c5a;
    }

    .status {
        display: inline-block;
        padding: 6px 11px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: 700;
    }

    .status-pending {
        background: #fff5d9;
        color: #956b00;
    }

    .status-accepted {
        background: #e7f8ed;
        color: #17743e;
    }

    .status-rejected {
        background: #ffe9e9;
        color: #a32929;
    }

    .empty-state {
        background: white;
        text-align: center;
        padding: 70px 30px;
        border-radius: 14px;
        border: 1px solid #e2e8e4;
    }

    .empty-state h2 {
        color: #173b2d;
        margin-bottom: 10px;
    }

    .empty-state p {
        color: #6b7280;
        margin-bottom: 22px;
    }

    .browse-btn {
        display: inline-block;
        background: #1f7a4d;
        color: white;
        padding: 12px 22px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 600;
    }

    @media (max-width: 800px) {

        .stats-row {
            grid-template-columns: 1fr;
        }

        .page-wrapper {
            padding: 30px 5%;
        }
    }

</style>


<div class="page-wrapper">

    <div class="page-header">

        <div>

            <h1>My Proposals</h1>

            <p>
                Track your submitted proposals and bidding performance.
            </p>

        </div>

        <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
           class="back-btn">
            Browse Projects
        </a>

    </div>


    <% if (success != null) { %>

        <div class="alert alert-success">
            ? Proposal submitted successfully!
        </div>

    <% } %>


    <% if (error != null) { %>

        <div class="alert alert-error">
            <%= error %>
        </div>

    <% } %>


    <%
        int totalProposals = 0;
        int pendingProposals = 0;
        int acceptedProposals = 0;

        if (proposals != null) {

            totalProposals = proposals.size();

            for (Proposal proposal : proposals) {

                if ("PENDING".equalsIgnoreCase(
                        proposal.getProposalStatus())) {

                    pendingProposals++;

                } else if ("ACCEPTED".equalsIgnoreCase(
                        proposal.getProposalStatus())) {

                    acceptedProposals++;
                }
            }
        }
    %>


    <div class="stats-row">

        <div class="stat-card">

            <div class="stat-label">
                Total Proposals
            </div>

            <div class="stat-value">
                <%= totalProposals %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-label">
                Pending
            </div>

            <div class="stat-value">
                <%= pendingProposals %>
            </div>

        </div>


        <div class="stat-card">

            <div class="stat-label">
                Accepted
            </div>

            <div class="stat-value">
                <%= acceptedProposals %>
            </div>

        </div>

    </div>


    <% if (proposals == null || proposals.isEmpty()) { %>

        <div class="empty-state">

            <h2>No proposals yet</h2>

            <p>
                You haven't submitted any project proposals.
                Explore open projects and send your first proposal.
            </p>

            <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
               class="browse-btn">
                Find Projects
            </a>

        </div>

    <% } else { %>


        <div class="table-container">

            <table class="proposal-table">

                <thead>

                    <tr>

                        <th>Proposal</th>

                        <th>Project</th>

                        <th>Bid Amount</th>

                        <th>Delivery</th>

                        <th>Skill Match</th>

                        <th>Status</th>

                    </tr>

                </thead>


                <tbody>

                <% for (Proposal proposal : proposals) { %>

                    <tr>

                        <td>
                            #<%= proposal.getProposalId() %>
                        </td>


                        <td>

                            <span class="project-id">
                                Project #<%= proposal.getProjectId() %>
                            </span>

                        </td>


                        <td>

                            <span class="bid">
                                ?<%= String.format(
                                    "%.2f",
                                    proposal.getBidAmount()
                                ) %>
                            </span>

                        </td>


                        <td>

                            <%= proposal.getDeliveryDays() %>
                            days

                        </td>


                        <td>

                            <div class="match-wrapper">

                                <span class="match-text">
                                    <%= proposal.getSkillMatchScore() %>%
                                </span>

                                <div class="match-bar">

                                    <div class="match-fill"
                                         style="width:<%= proposal.getSkillMatchScore() %>%;">
                                    </div>

                                </div>

                            </div>

                        </td>


                        <td>

                            <%
                                String statusClass =
                                    "status-pending";

                                if ("ACCEPTED".equalsIgnoreCase(
                                        proposal.getProposalStatus())) {

                                    statusClass =
                                        "status-accepted";

                                } else if ("REJECTED".equalsIgnoreCase(
                                        proposal.getProposalStatus())) {

                                    statusClass =
                                        "status-rejected";
                                }
                            %>

                            <span class="status <%=statusClass%>">

                                <%= proposal.getProposalStatus() %>

                            </span>

                        </td>

                    </tr>

                <% } %>

                </tbody>

            </table>

        </div>

    <% } %>

</div>


<%@ include file="../includes/footer.jsp" %>