<%@page import="com.skillbridge.model.Project"%>
<%@page import="com.skillbridge.model.User"%>

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

    Project project =
            (Project) request.getAttribute("project");

    if (project == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/BrowseProjectsServlet"
        );

        return;
    }

    String error =
            (String) request.getAttribute("error");
%>

<%@include file="../includes/header.jsp"%>
<%@include file="../includes/navbar.jsp"%>

<style>

    .details-wrapper {
        max-width: 1150px;
        margin: 45px auto;
        padding: 0 20px;
    }

    .back-link {
        display: inline-block;
        margin-bottom: 25px;
        color: #198754;
        text-decoration: none;
        font-weight: 600;
    }

    .details-grid {
        display: grid;
        grid-template-columns: 2fr 1fr;
        gap: 25px;
    }

    .details-card {
        background: #ffffff;
        border: 1px solid #e5e7eb;
        border-radius: 14px;
        padding: 30px;
        box-shadow: 0 8px 25px rgba(0,0,0,0.05);
    }

    .project-title {
        font-size: 32px;
        font-weight: 700;
        color: #1f2937;
        margin-bottom: 15px;
    }

    .status-badge {
        display: inline-block;
        padding: 7px 15px;
        border-radius: 20px;
        background: #dcfce7;
        color: #166534;
        font-size: 13px;
        font-weight: 700;
        margin-bottom: 25px;
    }

    .section-title {
        font-size: 19px;
        font-weight: 700;
        color: #1f2937;
        margin-top: 25px;
        margin-bottom: 12px;
    }

    .description {
        color: #4b5563;
        line-height: 1.8;
        font-size: 15px;
    }

    .skill-list {
        display: flex;
        flex-wrap: wrap;
        gap: 8px;
        margin-top: 10px;
    }

    .skill-tag {
        background: #f0fdf4;
        color: #15803d;
        border: 1px solid #bbf7d0;
        padding: 7px 12px;
        border-radius: 20px;
        font-size: 13px;
        font-weight: 600;
    }

    .info-item {
        padding: 16px 0;
        border-bottom: 1px solid #eeeeee;
    }

    .info-label {
        display: block;
        font-size: 13px;
        color: #6b7280;
        margin-bottom: 5px;
    }

    .info-value {
        font-size: 18px;
        font-weight: 700;
        color: #1f2937;
    }

    .match-box {
        margin-top: 25px;
        background: #f8fafc;
        border: 1px solid #e5e7eb;
        border-radius: 12px;
        padding: 20px;
    }

    .match-title {
        font-weight: 700;
        color: #1f2937;
        margin-bottom: 8px;
    }

    .match-text {
        color: #6b7280;
        font-size: 14px;
        line-height: 1.6;
    }

    .action-buttons {
        display: flex;
        gap: 12px;
        margin-top: 25px;
    }

    .btn-primary-custom {
        background: #198754;
        color: white;
        border: none;
        padding: 12px 22px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 600;
    }

    .btn-primary-custom:hover {
        background: #157347;
        color: white;
    }

    .btn-secondary-custom {
        background: white;
        color: #374151;
        border: 1px solid #d1d5db;
        padding: 12px 22px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 600;
    }

    .btn-secondary-custom:hover {
        background: #f9fafb;
        color: #374151;
    }

    .alert-box {
        background: #fff7ed;
        border: 1px solid #fed7aa;
        color: #9a3412;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    @media(max-width: 800px) {

        .details-grid {
            grid-template-columns: 1fr;
        }

        .project-title {
            font-size: 25px;
        }
    }

</style>


<div class="details-wrapper">

    <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
       class="back-link">

         Back to Browse Projects

    </a>


    <% if (error != null) { %>

        <div class="alert-box">
            <%=error%>
        </div>

    <% } %>


    <div class="details-grid">

        <!-- LEFT SIDE -->

        <div class="details-card">

            <div class="project-title">
                <%=project.getTitle()%>
            </div>

            <span class="status-badge">
                <%=project.getProjectStatus()%>
            </span>


            <div class="section-title">
                Project Description
            </div>

            <div class="description">
                <%=project.getDescription()%>
            </div>


            <div class="section-title">
                Required Skills
            </div>

            <div class="skill-list">

                <%
                    String skills =
                            project.getRequiredSkills();

                    if (skills != null &&
                            !skills.trim().isEmpty()) {

                        String[] skillArray =
                                skills.split(",");

                        for (String skill : skillArray) {
                %>

                    <span class="skill-tag">
                        <%=skill.trim()%>
                    </span>

                <%
                        }

                    } else {
                %>

                    <span class="description">
                        No specific skills listed.
                    </span>

                <%
                    }
                %>

            </div>


            <div class="match-box">

                <div class="match-title">
                    Smart Skill Match
                </div>

                <div class="match-text">
                    SkillBridge will compare your profile
                    skills with this project's required skills
                    and calculate a Skill Match Score when
                    you submit your proposal.
                </div>

            </div>


            <div class="action-buttons">

                <% if ("OPEN".equalsIgnoreCase(
                        project.getProjectStatus())) { %>

                    <a href="<%=request.getContextPath()%>/SubmitProposalServlet?projectId=<%=project.getProjectId()%>"
                       class="btn-primary-custom">

                        Submit Proposal

                    </a>

                <% } %>

                <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
                   class="btn-secondary-custom">

                    Back

                </a>

            </div>

        </div>


        <!-- RIGHT SIDE -->

        <div class="details-card">

            <div class="section-title"
                 style="margin-top:0;">

                Project Overview

            </div>


            <div class="info-item">

                <span class="info-label">
                    Budget Range
                </span>

                <span class="info-value">

                    <%=String.format(
                        "%.2f",
                        project.getBudgetMin()
                    )%>

                    -

                    <%=String.format(
                        "%.2f",
                        project.getBudgetMax()
                    )%>

                </span>

            </div>


            <div class="info-item">

                <span class="info-label">
                    Delivery Time
                </span>

                <span class="info-value">

                    <%=project.getDeadlineDays()%> Days

                </span>

            </div>


            <div class="info-item">

                <span class="info-label">
                    Project Status
                </span>

                <span class="info-value">

                    <%=project.getProjectStatus()%>

                </span>

            </div>


            <div class="info-item">

                <span class="info-label">
                    Project ID
                </span>

                <span class="info-value">

                    #<%=project.getProjectId()%>

                </span>

            </div>


            <div class="info-item">

                <span class="info-label">
                    Client ID
                </span>

                <span class="info-value">

                    #<%=project.getClientId()%>

                </span>

            </div>

        </div>

    </div>

</div>


<%@include file="../includes/footer.jsp"%>