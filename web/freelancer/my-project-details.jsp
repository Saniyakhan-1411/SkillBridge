<%@ page import="com.skillbridge.model.Project" %>
<%@ page import="com.skillbridge.model.ProjectStatusHistory" %>
<%@ page import="java.util.List" %>

<%
    Project project =
            (Project) request.getAttribute("project");

    List<ProjectStatusHistory> statusHistory =
            (List<ProjectStatusHistory>)
            request.getAttribute("statusHistory");

    String success =
            (String) request.getAttribute("success");

    String error =
            (String) request.getAttribute("error");
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>

<style>

    .details-page {
        background: #f5f7f9;
        min-height: 85vh;
        padding: 45px 0 70px;
    }

    .details-container {
        width: 90%;
        max-width: 1100px;
        margin: auto;
    }

    .project-header {
        background: white;
        border-radius: 16px;
        padding: 35px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.06);
        margin-bottom: 25px;
    }

    .project-header h1 {
        color: #17324d;
        margin: 0 0 10px;
        font-size: 32px;
    }

    .project-id {
        color: #7b8794;
        margin-bottom: 20px;
    }

    .status-badge {
        display: inline-block;
        padding: 9px 16px;
        border-radius: 25px;
        background: #fff4d6;
        color: #8a5a00;
        font-weight: 700;
        font-size: 13px;
    }

    .completed-badge {
        background: #e7f6ef;
        color: #087443;
    }

    .section-card {
        background: white;
        border-radius: 16px;
        padding: 30px;
        margin-bottom: 25px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.06);
    }

    .section-card h2 {
        color: #17324d;
        margin-top: 0;
        margin-bottom: 20px;
    }

    .description {
        color: #596579;
        line-height: 1.8;
    }

    .skills {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        margin-top: 15px;
    }

    .skill {
        background: #eaf6f1;
        color: #176b4d;
        padding: 7px 12px;
        border-radius: 20px;
        font-size: 13px;
        font-weight: 600;
    }

    .info-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 18px;
    }

    .info-box {
        background: #f7f9fb;
        padding: 18px;
        border-radius: 10px;
    }

    .info-label {
        color: #7b8794;
        font-size: 12px;
        margin-bottom: 6px;
    }

    .info-value {
        color: #17324d;
        font-weight: 700;
    }

    .timeline {
        position: relative;
        margin-top: 20px;
    }

    .timeline-item {
        display: flex;
        gap: 18px;
        margin-bottom: 25px;
    }

    .timeline-dot {
        width: 14px;
        height: 14px;
        background: #176b4d;
        border-radius: 50%;
        margin-top: 5px;
        flex-shrink: 0;
    }

    .timeline-content strong {
        color: #17324d;
        display: block;
        margin-bottom: 5px;
    }

    .timeline-content span {
        color: #7b8794;
        font-size: 13px;
    }

    .action-area {
        display: flex;
        gap: 12px;
        flex-wrap: wrap;
    }

    .btn {
        padding: 13px 20px;
        border-radius: 8px;
        border: none;
        cursor: pointer;
        text-decoration: none;
        font-weight: 700;
        display: inline-block;
    }

    .btn-primary {
        background: #176b4d;
        color: white;
    }

    .btn-primary:hover {
        background: #12583f;
    }

    .btn-outline {
        border: 1px solid #176b4d;
        color: #176b4d;
        background: white;
    }

    .btn-danger {
        background: #176b4d;
        color: white;
    }

    .success-box {
        background: #e7f6ef;
        color: #087443;
        border: 1px solid #b7e4ce;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    .error-box {
        background: #fff0f0;
        color: #b42318;
        border: 1px solid #f5c2c0;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    @media(max-width: 768px) {

        .info-grid {
            grid-template-columns: 1fr;
        }

        .project-header h1 {
            font-size: 25px;
        }
    }

</style>

<div class="details-page">

    <div class="details-container">

        <%
            if ("completed".equals(success)) {
        %>

            <div class="success-box">
                ? Project marked as completed successfully.
                You can now review the client.
            </div>

        <%
            }

            if ("complete".equals(error)) {
        %>

            <div class="error-box">
                Unable to complete the project.
                Please make sure the project is currently
                in progress.
            </div>

        <%
            }
        %>

        <!-- PROJECT HEADER -->

        <div class="project-header">

            <h1>
                <%= project.getTitle() %>
            </h1>

            <div class="project-id">
                Project #<%= project.getProjectId() %>
            </div>

            <%
                if ("COMPLETED".equals(
                        project.getProjectStatus())) {
            %>

                <span class="status-badge completed-badge">
                     COMPLETED
                </span>

            <%
                } else {
            %>

                <span class="status-badge">
                    ? <%= project.getProjectStatus() %>
                </span>

            <%
                }
            %>

        </div>

        <!-- OVERVIEW -->

        <div class="section-card">

            <h2>Project Overview</h2>

            <div class="description">

                <%= project.getDescription() %>

            </div>

        </div>

        <!-- PROJECT INFORMATION -->

        <div class="section-card">

            <h2>Project Information</h2>

            <div class="info-grid">

                <div class="info-box">

                    <div class="info-label">
                        Minimum Budget
                    </div>

                    <div class="info-value">
                        <%= String.format("%.2f",
                                project.getBudgetMin()) %>
                    </div>

                </div>

                <div class="info-box">

                    <div class="info-label">
                        Maximum Budget
                    </div>

                    <div class="info-value">
                        <%= String.format("%.2f",
                                project.getBudgetMax()) %>
                    </div>

                </div>

                <div class="info-box">

                    <div class="info-label">
                        Deadline
                    </div>

                    <div class="info-value">
                        <%= project.getDeadlineDays() %> days
                    </div>

                </div>

            </div>

        </div>

        <!-- SKILLS -->

        <div class="section-card">

            <h2>Required Skills</h2>

            <div class="skills">

                <%
                    String skills =
                            project.getRequiredSkills();

                    if (skills != null &&
                            !skills.trim().isEmpty()) {

                        String[] skillArray =
                                skills.split(",");

                        for (String skill : skillArray) {
                %>

                    <span class="skill">
                        <%= skill.trim() %>
                    </span>

                <%
                        }

                    } else {
                %>

                    <span class="skill">
                        General Skills
                    </span>

                <%
                    }
                %>

            </div>

        </div>

        <!-- STATUS TIMELINE -->

        <div class="section-card">

            <h2>Project Timeline</h2>

            <div class="timeline">

                <%
                    if (statusHistory != null &&
                            !statusHistory.isEmpty()) {

                        for (
                            ProjectStatusHistory history
                            : statusHistory
                        ) {
                %>

                    <div class="timeline-item">

                        <div class="timeline-dot"></div>

                        <div class="timeline-content">

                            <strong>
                                <%= history.getStatus() %>
                            </strong>

                            <span>
                                <%= history.getChangedAt() %>
                            </span>

                        </div>

                    </div>

                <%
                        }

                    } else {
                %>

                    <div class="timeline-item">

                        <div class="timeline-dot"></div>

                        <div class="timeline-content">

                            <strong>
                                Project is in progress
                            </strong>

                            <span>
                                Status history will appear here.
                            </span>

                        </div>

                    </div>

                <%
                    }
                %>

            </div>

        </div>

        <!-- ACTIONS -->

        <div class="section-card">

            <h2>Project Actions</h2>

            <div class="action-area">

                <%
                    if ("IN_PROGRESS".equals(
                            project.getProjectStatus())) {
                %>

                    <form
                        action="<%=request.getContextPath()%>/CompleteProjectServlet"
                        method="post"
                        style="display:inline;">

                        <input
                            type="hidden"
                            name="projectId"
                            value="<%=project.getProjectId()%>">

                        <button
                            type="submit"
                            class="btn btn-primary"
                            onclick="return confirm(
                                'Are you sure you want to mark this project as completed?'
                            );">

                             Mark as Completed

                        </button>

                    </form>

                <%
                    }

                    if ("COMPLETED".equals(
                            project.getProjectStatus())) {
                %>

                <% if ("IN_PROGRESS".equals(project.getProjectStatus())
       || "COMPLETED".equals(project.getProjectStatus())) { %>

    <a
        href="<%=request.getContextPath()%>/ConversationServlet?userId=<%=project.getClientId()%>"
        class="profile-trust-button">

        Message Client
    </a>

<% } %>

                    <a
                        href="<%=request.getContextPath()%>/ReviewServlet?projectId=<%=project.getProjectId()%>"
                        class="btn btn-primary">

                         Review Client

                    </a>

                <%
                    }
                %>

                <a
                    href="<%=request.getContextPath()%>/FreelancerMyProjectsServlet"
                    class="btn btn-outline">

                     Back to My Projects

                </a>

            </div>

        </div>

    </div>

</div>

<%@ include file="../includes/footer.jsp" %>