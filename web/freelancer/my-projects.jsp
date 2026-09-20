<%@ page import="java.util.List" %>
<%@ page import="com.skillbridge.model.Project" %>

<%
    List<Project> projects =
            (List<Project>) request.getAttribute("projects");

    if (projects == null) {
        projects = new java.util.ArrayList<Project>();
    }
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>

<style>

    .projects-page {
        background: #f5f7f9;
        min-height: 85vh;
        padding: 45px 0 70px;
    }

    .projects-container {
        width: 90%;
        max-width: 1200px;
        margin: auto;
    }

    .page-header {
        background: white;
        border-radius: 16px;
        padding: 35px;
        margin-bottom: 30px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.06);
    }

    .page-header h1 {
        margin: 0 0 8px;
        color: #17324d;
        font-size: 34px;
    }

    .page-header p {
        margin: 0;
        color: #667085;
        font-size: 16px;
    }

    .project-count {
        display: inline-block;
        margin-top: 20px;
        padding: 9px 16px;
        background: #e7f6ef;
        color: #087443;
        border-radius: 30px;
        font-weight: 700;
    }

    .project-card {
        background: white;
        border-radius: 16px;
        padding: 28px;
        margin-bottom: 22px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.06);
    }

    .project-top {
        display: flex;
        justify-content: space-between;
        gap: 20px;
        align-items: flex-start;
    }

    .project-title {
        margin: 0 0 8px;
        color: #17324d;
        font-size: 23px;
    }

    .project-id {
        color: #7b8794;
        font-size: 13px;
    }

    .status {
        padding: 7px 13px;
        border-radius: 20px;
        font-size: 12px;
        font-weight: 700;
        white-space: nowrap;
    }

    .status-progress {
        background: #fff4d6;
        color: #8a5a00;
    }

    .status-completed {
        background: #e7f6ef;
        color: #087443;
    }

    .status-open {
        background: #eaf2ff;
        color: #175cd3;
    }

    .project-description {
        margin: 20px 0;
        color: #596579;
        line-height: 1.7;
    }

    .project-info {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 15px;
        margin: 20px 0;
    }

    .info-box {
        background: #f7f9fb;
        border-radius: 10px;
        padding: 15px;
    }

    .info-label {
        color: #7b8794;
        font-size: 12px;
        margin-bottom: 5px;
    }

    .info-value {
        color: #17324d;
        font-weight: 700;
    }

    .project-actions {
        display: flex;
        gap: 12px;
        margin-top: 20px;
    }

    .btn {
        display: inline-block;
        padding: 11px 18px;
        border-radius: 8px;
        text-decoration: none;
        border: none;
        cursor: pointer;
        font-weight: 700;
        font-size: 14px;
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

    .btn-outline:hover {
        background: #eaf6f1;
    }

    .empty-state {
        background: white;
        border: 1px dashed #cbd5df;
        border-radius: 16px;
        padding: 75px 30px;
        text-align: center;
    }

    .empty-icon {
        font-size: 55px;
        margin-bottom: 15px;
    }

    .empty-state h2 {
        color: #17324d;
        margin-bottom: 10px;
    }

    .empty-state p {
        color: #667085;
        margin-bottom: 25px;
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

        .project-top {
            flex-direction: column;
        }

        .project-info {
            grid-template-columns: 1fr;
        }

        .project-actions {
            flex-direction: column;
        }

        .btn {
            text-align: center;
        }
    }

</style>


<div class="projects-page">

    <div class="projects-container">

        <!-- PAGE HEADER -->

        <div class="page-header">

            <h1>My Projects</h1>

            <p>
                Manage the projects you have been hired to work on.
            </p>

            <div class="project-count">

                <%= projects.size() %>
                Active / Completed Project(s)

            </div>

        </div>


        <!-- ERROR MESSAGE -->

        <%
            String error =
                    (String) request.getAttribute("error");

            if (error != null) {
        %>

            <div class="error-box">
                <%= error %>
            </div>

        <%
            }
        %>


        <!-- EMPTY STATE -->

        <%
            if (projects.isEmpty()) {
        %>

            <div class="empty-state">

                <div class="empty-icon">
                    ?
                </div>

                <h2>No Projects Yet</h2>

                <p>
                    You haven't been hired for any project yet.
                    Browse available projects and submit your proposals.
                </p>

                <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
                   class="btn btn-primary">

                    Browse Projects

                </a>

            </div>

        <%
            } else {

                for (Project project : projects) {

                    String status =
                            project.getProjectStatus();

                    String statusClass =
                            "status-progress";

                    if ("COMPLETED".equalsIgnoreCase(status)) {

                        statusClass =
                                "status-completed";

                    } else if ("OPEN".equalsIgnoreCase(status)) {

                        statusClass =
                                "status-open";
                    }
        %>


        <!-- PROJECT CARD -->

        <div class="project-card">

            <div class="project-top">

                <div>

                    <h2 class="project-title">
                        <%= project.getTitle() %>
                    </h2>

                    <div class="project-id">
                        Project #<%= project.getProjectId() %>
                    </div>

                </div>

                <span class="status <%= statusClass %>">

                    <%= status %>

                </span>

            </div>


            <!-- DESCRIPTION -->

            <div class="project-description">

                <%= project.getDescription() %>

            </div>


            <!-- PROJECT INFORMATION -->

            <div class="project-info">

                <div class="info-box">

                    <div class="info-label">
                        Budget
                    </div>

                    <div class="info-value">

                        <%= String.format(
                                "%.2f",
                                project.getBudgetMin()
                        ) %>

                        -

                        <%= String.format(
                                "%.2f",
                                project.getBudgetMax()
                        ) %>

                    </div>

                </div>


                <div class="info-box">

                    <div class="info-label">
                        Deadline
                    </div>

                    <div class="info-value">

                        <%= project.getDeadlineDays() %>
                        days

                    </div>

                </div>


                <div class="info-box">

                    <div class="info-label">
                        Required Skills
                    </div>

                    <div class="info-value">

                        <%= project.getRequiredSkills() %>

                    </div>

                </div>

            </div>


            <!-- ACTIONS -->

            <div class="project-actions">

                <!-- IMPORTANT:
                     This must call
                     FreelancerMyProjectDetailsServlet
                -->

                <a href="<%=request.getContextPath()%>/FreelancerMyProjectDetailsServlet?projectId=<%=project.getProjectId()%>"
                   class="btn btn-primary">

                    View Project

                </a>


                <!-- REVIEW CLIENT -->

                <%
                    if ("COMPLETED".equalsIgnoreCase(status)) {
                %>

                    <a href="<%=request.getContextPath()%>/ReviewServlet?projectId=<%=project.getProjectId()%>"
                       class="btn btn-outline">

                        Review Client

                    </a>

                <%
                    }
                %>

            </div>

        </div>


        <%
                }
            }
        %>

    </div>

</div>


<%@ include file="../includes/footer.jsp" %>