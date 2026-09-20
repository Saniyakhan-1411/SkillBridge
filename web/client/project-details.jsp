<%@ page import="java.util.List" %>
<%@ page import="com.skillbridge.model.Project" %>
<%@ page import="com.skillbridge.model.ProjectStatusHistory" %>

<%
    Project project =
            (Project) request.getAttribute("project");

    List<ProjectStatusHistory> statusHistory =
            (List<ProjectStatusHistory>)
            request.getAttribute("statusHistory");

    Integer acceptedFreelancerId =
            (Integer) request.getAttribute(
                    "acceptedFreelancerId"
            );

    String contextPath =
            request.getContextPath();

    if (project == null) {
%>

<!DOCTYPE html>
<html>
<head>

    <title>Project Not Found | SkillBridge</title>

    <link rel="stylesheet"
          href="<%=contextPath%>/assets/css/style.css">

    <style>

        .sb-error-page {
            min-height: 70vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 50px 20px;
            background: #f8fafc;
        }

        .sb-error-card {
            width: 100%;
            max-width: 520px;
            padding: 45px;
            text-align: center;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(15,23,42,.06);
        }

        .sb-error-icon {
            width: 60px;
            height: 60px;
            margin: 0 auto 20px;
            border-radius: 50%;
            background: #fef2f2;
            color: #dc2626;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 25px;
            font-weight: 700;
        }

        .sb-error-card h2 {
            margin: 0 0 10px;
            color: #111827;
        }

        .sb-error-card p {
            margin: 0 0 25px;
            color: #6b7280;
            line-height: 1.6;
        }

        .sb-error-card a {
            display: inline-block;
            padding: 12px 22px;
            background: #176b4d;
            color: #ffffff;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
        }

    </style>

</head>

<body>

<div class="sb-error-page">

    <div class="sb-error-card">

        <div class="sb-error-icon">
            !
        </div>

        <h2>
            Project Not Found
        </h2>

        <p>
            The requested project could not be loaded.
            It may have been removed or is no longer available.
        </p>

        <a href="<%=contextPath%>/client/my-projects.jsp">
            Back to My Projects
        </a>

    </div>

</div>

</body>
</html>

<%
        return;
    }


    /*
     * ============================================================
     * PROJECT STATUS
     * ============================================================
     */

    String currentStatus =
            project.getProjectStatus();

    if (currentStatus == null ||
            currentStatus.trim().isEmpty()) {

        currentStatus = "OPEN";
    }


    String statusClass = "open";

    String statusDescription =
            "Your project is open and accepting freelancer proposals.";

    if ("IN_PROGRESS".equalsIgnoreCase(currentStatus)) {

        statusClass = "progress";

        statusDescription =
                "A freelancer has been hired and work is currently in progress.";

    } else if ("COMPLETED".equalsIgnoreCase(currentStatus)) {

        statusClass = "completed";

        statusDescription =
                "This project has been completed successfully.";
    }


    double budgetMin =
            project.getBudgetMin();

    double budgetMax =
            project.getBudgetMax();

%>


<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/navbar.jsp" %>


<style>

/* ============================================================
   SKILLBRIDGE PROJECT DETAILS
   ============================================================ */

.project-details-page {

    max-width: 1220px;

    margin: 0 auto;

    padding: 42px 22px 70px;

}


/* ============================================================
   BREADCRUMB
   ============================================================ */

.project-breadcrumb {

    display: flex;

    align-items: center;

    flex-wrap: wrap;

    gap: 8px;

    margin-bottom: 12px;

    font-size: 13px;

    color: #94a3b8;

}

.project-breadcrumb a {

    color: #64748b;

    text-decoration: none;

}

.project-breadcrumb a:hover {

    color: #176b4d;

}


/* ============================================================
   HEADER
   ============================================================ */

.project-page-header {

    display: flex;

    justify-content: space-between;

    align-items: flex-end;

    gap: 25px;

    margin-bottom: 24px;

}

.project-header-content {

    min-width: 0;

}

.project-eyebrow {

    display: inline-block;

    margin-bottom: 8px;

    font-size: 11px;

    font-weight: 800;

    letter-spacing: .9px;

    color: #176b4d;

    text-transform: uppercase;

}

.project-page-header h1 {

    margin: 0;

    font-size: 32px;

    line-height: 1.25;

    color: #111827;

    font-weight: 750;

}

.project-page-header p {

    margin: 9px 0 0;

    color: #64748b;

    font-size: 14px;

}

.project-header-actions {

    display: flex;

    gap: 10px;

    flex-shrink: 0;

}


/* ============================================================
   BUTTONS
   ============================================================ */

.sb-btn {

    display: inline-flex;

    align-items: center;

    justify-content: center;

    min-height: 42px;

    padding: 0 17px;

    border-radius: 8px;

    font-size: 13px;

    font-weight: 700;

    text-decoration: none;

    border: 1px solid transparent;

    box-sizing: border-box;

    transition: .2s ease;

    white-space: nowrap;

}

.sb-btn-primary {

    background: #176b4d;

    color: #ffffff;

    border-color: #176b4d;

}

.sb-btn-primary:hover {

    background: #12553d;

    border-color: #12553d;

}

.sb-btn-secondary {

    background: #ffffff;

    color: #334155;

    border-color: #d1d5db;

}

.sb-btn-secondary:hover {

    border-color: #176b4d;

    color: #176b4d;

}


/* ============================================================
   STATUS
   ============================================================ */

.project-status-card {

    display: flex;

    justify-content: space-between;

    align-items: center;

    gap: 20px;

    padding: 20px 23px;

    margin-bottom: 25px;

    background: #ffffff;

    border: 1px solid #e5e7eb;

    border-radius: 12px;

    box-shadow: 0 3px 12px rgba(15,23,42,.035);

}

.project-status-left {

    display: flex;

    align-items: center;

    gap: 14px;

}

.status-icon {

    width: 42px;

    height: 42px;

    border-radius: 10px;

    display: flex;

    align-items: center;

    justify-content: center;

    font-weight: 800;

    font-size: 15px;

}

.status-icon.open {

    background: #ecfdf5;

    color: #15803d;

}

.status-icon.progress {

    background: #eff6ff;

    color: #2563eb;

}

.status-icon.completed {

    background: #f3f4f6;

    color: #475569;

}

.status-title {

    margin: 0 0 4px;

    font-size: 14px;

    font-weight: 750;

    color: #111827;

}

.status-description {

    margin: 0;

    color: #64748b;

    font-size: 13px;

    line-height: 1.5;

}

.status-badge {

    display: inline-flex;

    align-items: center;

    padding: 8px 13px;

    border-radius: 999px;

    font-size: 11px;

    font-weight: 800;

    letter-spacing: .4px;

    text-transform: uppercase;

}

.status-badge.open {

    background: #ecfdf5;

    color: #166534;

}

.status-badge.progress {

    background: #eff6ff;

    color: #1d4ed8;

}

.status-badge.completed {

    background: #f1f5f9;

    color: #475569;

}


/* ============================================================
   MAIN GRID
   ============================================================ */

.project-content-grid {

    display: grid;

    grid-template-columns: minmax(0, 1fr) 350px;

    gap: 25px;

    align-items: start;

}


/* ============================================================
   CONTENT CARD
   ============================================================ */

.project-card {

    background: #ffffff;

    border: 1px solid #e5e7eb;

    border-radius: 12px;

    padding: 27px;

    margin-bottom: 22px;

    box-shadow: 0 3px 12px rgba(15,23,42,.035);

}

.project-card:last-child {

    margin-bottom: 0;

}

.project-card-header {

    margin-bottom: 20px;

}

.project-card-label {

    display: block;

    margin-bottom: 6px;

    color: #176b4d;

    font-size: 10px;

    font-weight: 800;

    letter-spacing: 1px;

    text-transform: uppercase;

}

.project-card h2 {

    margin: 0;

    color: #111827;

    font-size: 19px;

    font-weight: 750;

}

.project-card-subtitle {

    margin: 7px 0 0;

    color: #64748b;

    font-size: 13px;

    line-height: 1.6;

}


/* ============================================================
   DESCRIPTION
   ============================================================ */

.project-description {

    margin: 0;

    color: #475569;

    font-size: 14px;

    line-height: 1.85;

    white-space: pre-line;

}


/* ============================================================
   SKILLS
   ============================================================ */

.skills-list {

    display: flex;

    flex-wrap: wrap;

    gap: 8px;

}

.skill-tag {

    padding: 7px 11px;

    background: #f0fdf4;

    border: 1px solid #bbf7d0;

    border-radius: 7px;

    color: #166534;

    font-size: 12px;

    font-weight: 700;

}


/* ============================================================
   PROJECT SNAPSHOT
   ============================================================ */

.snapshot-grid {

    display: grid;

    grid-template-columns: repeat(2, 1fr);

    gap: 12px;

}

.snapshot-item {

    padding: 17px;

    background: #f8fafc;

    border: 1px solid #e5e7eb;

    border-radius: 9px;

}

.snapshot-label {

    display: block;

    margin-bottom: 7px;

    color: #94a3b8;

    font-size: 10px;

    font-weight: 800;

    letter-spacing: .7px;

    text-transform: uppercase;

}

.snapshot-value {

    display: block;

    color: #111827;

    font-size: 14px;

    font-weight: 750;

    line-height: 1.4;

}


/* ============================================================
   TIMELINE
   ============================================================ */

.project-timeline {

    position: relative;

}

.timeline-item {

    display: flex;

    position: relative;

    gap: 15px;

    padding-bottom: 26px;

}

.timeline-item:last-child {

    padding-bottom: 0;

}

.timeline-marker-column {

    position: relative;

    flex: 0 0 34px;

}

.timeline-marker {

    width: 34px;

    height: 34px;

    display: flex;

    align-items: center;

    justify-content: center;

    border-radius: 50%;

    background: #176b4d;

    color: #ffffff;

    font-size: 12px;

    font-weight: 800;

    position: relative;

    z-index: 2;

}

.timeline-line {

    position: absolute;

    top: 34px;

    bottom: -26px;

    left: 16px;

    width: 2px;

    background: #dbe4df;

}

.timeline-content {

    padding-top: 2px;

}

.timeline-content strong {

    display: block;

    margin-bottom: 5px;

    color: #111827;

    font-size: 14px;

}

.timeline-content span {

    color: #94a3b8;

    font-size: 12px;

}


/* ============================================================
   WORKFLOW
   ============================================================ */

.workflow-grid {

    display: grid;

    grid-template-columns:
        repeat(5, minmax(0, 1fr));

    gap: 9px;

}

.workflow-step {

    position: relative;

    padding: 16px 9px;

    text-align: center;

    background: #f8fafc;

    border: 1px solid #e5e7eb;

    border-radius: 9px;

}

.workflow-number {

    width: 25px;

    height: 25px;

    margin: 0 auto 8px;

    display: flex;

    align-items: center;

    justify-content: center;

    border-radius: 50%;

    background: #e8f5ef;

    color: #176b4d;

    font-size: 11px;

    font-weight: 800;

}

.workflow-step strong {

    display: block;

    color: #111827;

    font-size: 11px;

}

.workflow-step span {

    display: block;

    margin-top: 4px;

    color: #94a3b8;

    font-size: 10px;

    line-height: 1.4;

}


/* ============================================================
   SIDEBAR
   ============================================================ */

.project-sidebar {

    position: sticky;

    top: 20px;

}

.management-card {

    background: #ffffff;

    border: 1px solid #e5e7eb;

    border-radius: 12px;

    padding: 23px;

    margin-bottom: 18px;

    box-shadow: 0 3px 12px rgba(15,23,42,.035);

}

.management-card h3 {

    margin: 0 0 7px;

    color: #111827;

    font-size: 17px;

    font-weight: 750;

}

.management-card > p {

    margin: 0 0 18px;

    color: #64748b;

    font-size: 13px;

    line-height: 1.6;

}


/* ============================================================
   PRIMARY ACTION
   ============================================================ */

.primary-action {

    display: flex;

    align-items: center;

    gap: 12px;

    padding: 15px;

    background: #176b4d;

    border: 1px solid #176b4d;

    border-radius: 9px;

    color: #ffffff;

    text-decoration: none;

    transition: .2s ease;

}

.primary-action:hover {

    background: #12553d;

}

.action-icon {

    width: 36px;

    height: 36px;

    flex: 0 0 36px;

    display: flex;

    align-items: center;

    justify-content: center;

    border-radius: 8px;

    background: rgba(255,255,255,.14);

    font-size: 15px;

}

.action-text strong {

    display: block;

    margin-bottom: 3px;

    font-size: 13px;

}

.action-text span {

    display: block;

    font-size: 11px;

    opacity: .82;

}


/* ============================================================
   RISK ACTION
   ============================================================ */

.risk-action {

    display: flex;

    align-items: center;

    gap: 12px;

    margin-top: 10px;

    padding: 15px;

    background: #f8fafc;

    border: 1px solid #dbe4df;

    border-radius: 9px;

    color: #176b4d;

    text-decoration: none;

    transition: .2s ease;

}

.risk-action:hover {

    background: #f0fdf4;

    border-color: #176b4d;

}

.risk-action .action-icon {

    background: #e8f5ef;

    color: #176b4d;

}


/* ============================================================
   SECONDARY ACTIONS
   ============================================================ */

.sidebar-divider {

    height: 1px;

    margin: 20px 0;

    background: #e5e7eb;

}

.sidebar-label {

    display: block;

    margin-bottom: 9px;

    color: #64748b;

    font-size: 10px;

    font-weight: 800;

    letter-spacing: .8px;

    text-transform: uppercase;

}

.sidebar-btn {

    display: flex;

    align-items: center;

    justify-content: center;

    width: 100%;

    min-height: 40px;

    margin-top: 8px;

    padding: 0 14px;

    box-sizing: border-box;

    border: 1px solid #d1d5db;

    border-radius: 8px;

    background: #ffffff;

    color: #334155;

    text-decoration: none;

    font-size: 12px;

    font-weight: 700;

    transition: .2s ease;

}

.sidebar-btn:hover {

    border-color: #176b4d;

    color: #176b4d;

}


/* ============================================================
   INFORMATION CARD
   ============================================================ */

.workflow-info {

    padding: 19px;

    background: #f8fafc;

    border: 1px solid #e5e7eb;

    border-radius: 10px;

}

.workflow-info-title {

    margin-bottom: 6px;

    color: #111827;

    font-size: 13px;

    font-weight: 750;

}

.workflow-info-text {

    color: #64748b;

    font-size: 12px;

    line-height: 1.65;

}


/* ============================================================
   RESPONSIVE
   ============================================================ */

@media (max-width: 950px) {

    .project-content-grid {

        grid-template-columns: 1fr;

    }

    .project-sidebar {

        position: static;

    }

    .workflow-grid {

        grid-template-columns:
            repeat(3, minmax(0, 1fr));

    }

}


@media (max-width: 700px) {

    .project-page-header {

        flex-direction: column;

        align-items: flex-start;

    }

    .project-header-actions {

        width: 100%;

    }

    .project-header-actions .sb-btn {

        flex: 1;

    }

    .project-status-card {

        align-items: flex-start;

        flex-direction: column;

    }

    .workflow-grid {

        grid-template-columns:
            repeat(2, minmax(0, 1fr));

    }

}


@media (max-width: 520px) {

    .project-details-page {

        padding: 28px 15px 55px;

    }

    .project-page-header h1 {

        font-size: 25px;

    }

    .project-header-actions {

        flex-direction: column;

    }

    .project-header-actions .sb-btn {

        width: 100%;

    }

    .project-card {

        padding: 20px;

    }

    .snapshot-grid {

        grid-template-columns: 1fr;

    }

    .workflow-grid {

        grid-template-columns: 1fr;

    }

}

</style>


<!-- ============================================================
     MAIN PAGE
     ============================================================ -->

<div class="project-details-page">


    <!-- ========================================================
         HEADER
         ======================================================== -->

    <div class="project-page-header">

        <div class="project-header-content">

            <div class="project-breadcrumb">

                <a href="<%=contextPath%>/client/dashboard.jsp">
                    Dashboard
                </a>

                <span>/</span>

                <a href="<%=contextPath%>/client/my-projects.jsp">
                    My Projects
                </a>

                <span>/</span>

                <span>Project Details</span>

            </div>


            <span class="project-eyebrow">
                Client Project
            </span>


            <h1>
                <%=project.getTitle()%>
            </h1>


            <p>
                Manage your project, review proposals and monitor progress.
            </p>

        </div>


        <div class="project-header-actions">

            <a
                href="<%=contextPath%>/client/my-projects.jsp"
                class="sb-btn sb-btn-secondary">

                 Back to Projects

            </a>


            <a
                href="<%=contextPath%>/ClientProposalsServlet?projectId=<%=project.getProjectId()%>"
                class="sb-btn sb-btn-primary">

                View Proposals

            </a>

        </div>

    </div>


    <!-- ========================================================
         STATUS
         ======================================================== -->

    <div class="project-status-card">

        <div class="project-status-left">

            <div class="status-icon <%=statusClass%>">

                <%
                    if ("OPEN".equalsIgnoreCase(currentStatus)) {
                %>
                    O
                <%
                    } else if (
                            "IN_PROGRESS".equalsIgnoreCase(
                                    currentStatus)) {
                %>
                    P
                <%
                    } else {
                %>
                    ?
                <%
                    }
                %>

            </div>


            <div>

                <div class="status-title">
                    Current Project Status
                </div>

                <p class="status-description">
                    <%=statusDescription%>
                </p>

            </div>

        </div>


        <span class="status-badge <%=statusClass%>">

            <%=currentStatus.replace("_", " ")%>

        </span>

    </div>


    <!-- ========================================================
         CONTENT
         ======================================================== -->

    <div class="project-content-grid">


        <!-- ====================================================
             LEFT CONTENT
             ==================================================== -->

        <main>


            <!-- PROJECT OVERVIEW -->

            <section class="project-card">

                <div class="project-card-header">

                    <span class="project-card-label">
                        Overview
                    </span>

                    <h2>
                        Project Description
                    </h2>

                    <p class="project-card-subtitle">
                        Requirements and information provided for this project.
                    </p>

                </div>


                <p class="project-description">
                    <%=project.getDescription()%>
                </p>


                <div style="margin-top:25px;">

                    <div class="project-card-header"
                         style="margin-bottom:12px;">

                        <span class="project-card-label">
                            Skills
                        </span>

                        <h2 style="font-size:16px;">
                            Required Skills
                        </h2>

                    </div>


                    <div class="skills-list">

                        <%
                            String skills =
                                    project.getRequiredSkills();

                            if (skills != null &&
                                    !skills.trim().isEmpty()) {

                                String[] skillArray =
                                        skills.split(",");

                                for (String skill :
                                        skillArray) {

                                    if (skill != null &&
                                            !skill.trim().isEmpty()) {
                        %>

                        <span class="skill-tag">
                            <%=skill.trim()%>
                        </span>

                        <%
                                    }
                                }

                            } else {
                        %>

                        <span class="skill-tag">
                            No specific skills listed
                        </span>

                        <%
                            }
                        %>

                    </div>

                </div>

            </section>


            <!-- PROJECT SNAPSHOT -->

            <section class="project-card">

                <div class="project-card-header">

                    <span class="project-card-label">
                        Snapshot
                    </span>

                    <h2>
                        Project Information
                    </h2>

                </div>


                <div class="snapshot-grid">


                    <div class="snapshot-item">

                        <span class="snapshot-label">
                            Budget
                        </span>

                        <span class="snapshot-value">

                            <%=String.format(
                                "%.2f",
                                budgetMin
                            )%>

                            -

                            <%=String.format(
                                "%.2f",
                                budgetMax
                            )%>

                        </span>

                    </div>


                    <div class="snapshot-item">

                        <span class="snapshot-label">
                            Deadline
                        </span>

                        <span class="snapshot-value">

                            <%=project.getDeadlineDays()%> days

                        </span>

                    </div>


                    <div class="snapshot-item">

                        <span class="snapshot-label">
                            Project ID
                        </span>

                        <span class="snapshot-value">

                            #<%=project.getProjectId()%>

                        </span>

                    </div>


                    <div class="snapshot-item">

                        <span class="snapshot-label">
                            Created
                        </span>

                        <span class="snapshot-value">

                            <%
                                if (project.getCreatedAt() != null) {
                            %>

                                <%=project.getCreatedAt()%>

                            <%
                                } else {
                            %>

                                Not available

                            <%
                                }
                            %>

                        </span>

                    </div>

                </div>

            </section>


            <!-- STATUS TIMELINE -->

            <section class="project-card">

                <div class="project-card-header">

                    <span class="project-card-label">
                        Progress
                    </span>

                    <h2>
                        Project Timeline
                    </h2>

                    <p class="project-card-subtitle">
                        Track important project status changes.
                    </p>

                </div>


                <div class="project-timeline">

                    <%
                        if (statusHistory != null &&
                                !statusHistory.isEmpty()) {

                            int number = 1;

                            for (ProjectStatusHistory history :
                                    statusHistory) {
                    %>

                    <div class="timeline-item">

                        <div class="timeline-marker-column">

                            <div class="timeline-marker">
                                <%=number%>
                            </div>

                            <%
                                if (number < statusHistory.size()) {
                            %>

                            <div class="timeline-line"></div>

                            <%
                                }
                            %>

                        </div>


                        <div class="timeline-content">

                            <strong>
                                <%=history.getStatus()
                                    .replace("_", " ")%>
                            </strong>

                            <span>
                                <%=history.getChangedAt()%>
                            </span>

                        </div>

                    </div>

                    <%
                                number++;
                            }

                        } else {
                    %>

                    <div class="timeline-item">

                        <div class="timeline-marker-column">

                            <div class="timeline-marker">
                                1
                            </div>

                        </div>


                        <div class="timeline-content">

                            <strong>
                                OPEN
                            </strong>

                            <span>
                                Project created and opened for proposals.
                            </span>

                        </div>

                    </div>

                    <%
                        }
                    %>

                </div>

            </section>


            <!-- WORKFLOW -->

            <section class="project-card">

                <div class="project-card-header">

                    <span class="project-card-label">
                        Workflow
                    </span>

                    <h2>
                        SkillBridge Project Process
                    </h2>

                    <p class="project-card-subtitle">
                        A secure workflow from posting to completion.
                    </p>

                </div>


                <div class="workflow-grid">


                    <div class="workflow-step">

                        <div class="workflow-number">
                            1
                        </div>

                        <strong>
                            Open
                        </strong>

                        <span>
                            Project posted
                        </span>

                    </div>


                    <div class="workflow-step">

                        <div class="workflow-number">
                            2
                        </div>

                        <strong>
                            Proposal
                        </strong>

                        <span>
                            Freelancers apply
                        </span>

                    </div>


                    <div class="workflow-step">

                        <div class="workflow-number">
                            3
                        </div>

                        <strong>
                            Hire
                        </strong>

                        <span>
                            Client selects
                        </span>

                    </div>


                    <div class="workflow-step">

                        <div class="workflow-number">
                            4
                        </div>

                        <strong>
                            In Progress
                        </strong>

                        <span>
                            Work begins
                        </span>

                    </div>


                    <div class="workflow-step">

                        <div class="workflow-number">
                            5
                        </div>

                        <strong>
                            Completed
                        </strong>

                        <span>
                            Project finished
                        </span>

                    </div>

                </div>

            </section>

        </main>


        <!-- ====================================================
             RIGHT SIDEBAR
             ==================================================== -->

        <aside class="project-sidebar">


            <!-- PROJECT MANAGEMENT -->

            <div class="management-card">

                <h3>
                    Manage Project
                </h3>

                <p>
                    Review proposals, evaluate project risk
                    and make your hiring decision.
                </p>


                <!-- PROPOSALS -->

                <a
                    href="<%=contextPath%>/ClientProposalsServlet?projectId=<%=project.getProjectId()%>"
                    class="primary-action">

                    <div class="action-icon">
                        ?
                    </div>

                    <div class="action-text">

                        <strong>
                            View &amp; Compare Proposals
                        </strong>

                        <span>
                            Compare freelancers and bids
                        </span>

                    </div>

                </a>
                    <br>
                  <!-- BID HEALTH ANALYSIS -->

<a href="<%=request.getContextPath()%>/BidHealthServlet?projectId=<%=project.getProjectId()%>" 
   style="display: flex; align-items: center; gap: 12px; padding: 14px 16px; background-color: #f4f8f5; border: 1px solid #d1e7dd; border-radius: 12px; text-decoration: none; color: inherit; margin-bottom: 12px; transition: all 0.2s ease;">
    
    <!-- ICON BOX -->
    <div style="width: 42px; height: 42px; background-color: #d1e7dd; border-radius: 10px; display: flex; align-items: center; justify-content: center; font-size: 18px; flex-shrink: 0;">
        &#10084;&#65039;
    </div>
    
    <!-- TEXT BOX -->
    <div style="display: flex; flex-direction: column;">
        <strong style="font-size: 14px; font-weight: 700; color: #1b4332; line-height: 1.2;">
            Bid Health Analysis
        </strong>
        <span style="font-size: 12px; color: #52796f; margin-top: 3px;">
            Evaluate bidding behavior vs budget
        </span>
    </div>

</a>
                <!-- PROJECT RISK -->

                <a
                    href="<%=contextPath%>/ProjectRiskServlet?projectId=<%=project.getProjectId()%>"
                    class="risk-action">

                    <div class="action-icon">
                        !
                    </div>

                    <div class="action-text">

                        <strong>
                            Project Risk Analysis
                        </strong>

                        <span>
                            Identify deadline, budget and scope risks
                        </span>

                    </div>

                </a>


                <!-- HIRED FREELANCER -->

                <%
                    if (acceptedFreelancerId != null &&
                            acceptedFreelancerId > 0 &&
                            (
                                "IN_PROGRESS".equals(currentStatus)
                                ||
                                "COMPLETED".equals(currentStatus)
                            )) {
                %>

                <div class="sidebar-divider"></div>

                <span class="sidebar-label">
                    Active Freelancer
                </span>


                <a
                    href="<%=contextPath%>/ConversationServlet?userId=<%=acceptedFreelancerId%>"
                    class="sidebar-btn">

                    Message Freelancer

                </a>

                <%
                    }
                %>


                <!-- REVIEW -->

                <%
                    if ("COMPLETED".equals(currentStatus)) {
                %>

                <div class="sidebar-divider"></div>

                <span class="sidebar-label">
                    Project Completed
                </span>


                <a
                    href="<%=contextPath%>/ReviewServlet?projectId=<%=project.getProjectId()%>"
                    class="sidebar-btn">

                    Leave Freelancer Review

                </a>

                <%
                    }
                %>

            </div>


            <!-- WORKFLOW INFORMATION -->

            <div class="workflow-info">

                <div class="workflow-info-title">

                    How SkillBridge Works

                </div>


                <div class="workflow-info-text">

                    Your project follows a secure process:

                    <strong>
                        Open ? Proposal ? Hire ?
                        In Progress ? Completed
                    </strong>

                    <br><br>

                    Use Project Risk Analysis before hiring
                    to identify potential project risks.

                </div>

            </div>

        </aside>

    </div>

</div>


<jsp:include page="/includes/footer.jsp" />