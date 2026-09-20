<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.skillbridge.model.User"%>
<%@page import="com.skillbridge.model.Project"%>

<%
    /*
     * ============================================================
     * FREELANCER SESSION CHECK
     * ============================================================
     */

    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null ||
            !"FREELANCER".equalsIgnoreCase(
                    loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }


    /*
     * ============================================================
     * GET PROJECT DATA
     * ============================================================
     */

    List<Project> projects =
            (List<Project>)
            request.getAttribute("projects");


    /*
     * ============================================================
     * GET ERROR MESSAGE
     * ============================================================
     */

    String error =
            (String) request.getAttribute("error");


    /*
     * ============================================================
     * CORRECT FREELANCER DASHBOARD URL
     * ============================================================
     */

    String dashboardUrl =
            request.getContextPath()
            + "/freelancer/dashboard.jsp";
%>


<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Browse Projects | SkillBridge
    </title>


    <!-- SkillBridge Main CSS -->

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">


    <style>

        /* =========================================================
           PAGE
           ========================================================= */

        .projects-page {

            min-height: 80vh;

            background: #f5faf7;

            padding: 45px 20px;
        }


        .projects-container {

            max-width: 1150px;

            margin: auto;
        }


        /* =========================================================
           PAGE HEADER
           ========================================================= */

        .projects-header {

            margin-bottom: 30px;
        }


        .projects-header .page-label {

            display: inline-block;

            color: #176b4d;

            font-size: 12px;

            font-weight: 700;

            letter-spacing: 1px;

            margin-bottom: 8px;
        }


        .projects-header h1 {

            margin: 0 0 8px;

            color: #101828;

            font-size: 32px;

            line-height: 1.3;
        }


        .projects-header p {

            margin: 0;

            color: #667085;

            font-size: 15px;

            line-height: 1.6;
        }


        /* =========================================================
           TOOLBAR
           ========================================================= */

        .projects-toolbar {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 14px;

            padding: 18px 20px;

            margin-bottom: 25px;

            display: flex;

            align-items: center;

            justify-content: space-between;

            gap: 15px;
        }


        .projects-count {

            color: #475467;

            font-size: 14px;

            font-weight: 600;
        }


        .projects-count strong {

            color: #176b4d;
        }


        /* =========================================================
           PROJECT GRID
           ========================================================= */

        .project-grid {

            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 20px;
        }


        /* =========================================================
           PROJECT CARD
           ========================================================= */

        .project-card {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 16px;

            padding: 25px;

            transition: 0.2s ease;
        }


        .project-card:hover {

            transform: translateY(-3px);

            box-shadow:
                0 10px 25px
                rgba(23, 32, 51, 0.08);

            border-color: #176b4d;
        }


        .project-card-top {

            display: flex;

            align-items: center;

            justify-content: space-between;

            margin-bottom: 16px;
        }


        /* =========================================================
           STATUS
           ========================================================= */

        .status-badge {

            display: inline-block;

            padding: 5px 10px;

            border-radius: 20px;

            background: #ecfdf3;

            color: #176b4d;

            font-size: 11px;

            font-weight: 700;

            letter-spacing: 0.4px;
        }


        /* =========================================================
           PROJECT TITLE
           ========================================================= */

        .project-card h2 {

            margin: 0 0 12px;

            color: #101828;

            font-size: 20px;

            line-height: 1.4;
        }


        /* =========================================================
           DESCRIPTION
           ========================================================= */

        .project-description {

            color: #667085;

            font-size: 14px;

            line-height: 1.7;

            margin: 0 0 20px;

            display: -webkit-box;

            -webkit-line-clamp: 3;

            -webkit-box-orient: vertical;

            overflow: hidden;
        }


        /* =========================================================
           PROJECT INFORMATION
           ========================================================= */

        .project-info {

            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 12px;

            margin-bottom: 20px;
        }


        .info-box {

            background: #f8fafc;

            border-radius: 10px;

            padding: 13px;
        }


        .info-box span {

            display: block;

            color: #667085;

            font-size: 11px;

            margin-bottom: 5px;
        }


        .info-box strong {

            color: #101828;

            font-size: 14px;
        }


        /* =========================================================
           SKILLS
           ========================================================= */

        .skills-section {

            margin-bottom: 22px;
        }


        .skills-label {

            display: block;

            color: #475467;

            font-size: 12px;

            font-weight: 600;

            margin-bottom: 8px;
        }


        .skills-text {

            color: #667085;

            font-size: 13px;

            line-height: 1.6;
        }


        /* =========================================================
           CARD ACTIONS
           ========================================================= */

        .project-card-actions {

            display: flex;

            gap: 10px;

            align-items: center;
        }


        .project-card-actions .btn {

            flex: 1;

            text-align: center;
        }


        /* =========================================================
           EMPTY STATE
           ========================================================= */

        .empty-state {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 16px;

            padding: 60px 25px;

            text-align: center;
        }


        .empty-icon {

            width: 60px;

            height: 60px;

            margin: 0 auto 18px;

            border-radius: 50%;

            display: flex;

            align-items: center;

            justify-content: center;

            background: #ecfdf3;

            color: #176b4d;

            font-size: 14px;

            font-weight: 700;
        }


        .empty-state h2 {

            margin: 0 0 8px;

            color: #101828;
        }


        .empty-state p {

            color: #667085;

            font-size: 14px;

            margin-bottom: 20px;
        }


        /* =========================================================
           ERROR MESSAGE
           ========================================================= */

        .alert-error {

            background: #fef3f2;

            color: #b42318;

            border: 1px solid #fecdca;

            padding: 14px 16px;

            border-radius: 10px;

            margin-bottom: 20px;

            font-size: 14px;
        }


        /* =========================================================
           DASHBOARD BUTTON
           ========================================================= */

        .dashboard-button {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            padding: 10px 18px;

            background: #ffffff;

            color: #17324d !important;

            border: 1px solid #dfe5e8;

            border-radius: 7px;

            text-decoration: none !important;

            font-size: 14px;

            font-weight: 700;

            white-space: nowrap;

            transition: all 0.2s ease;
        }


        .dashboard-button:hover {

            color: #176b4d !important;

            border-color: #176b4d;

            background: #f8fffb;

            text-decoration: none !important;
        }


        /* =========================================================
           BACK DASHBOARD BUTTON
           ========================================================= */

        .back-dashboard-button {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            padding: 11px 20px;

            background: #176b4d;

            color: #ffffff !important;

            border: 1px solid #176b4d;

            border-radius: 7px;

            text-decoration: none !important;

            font-size: 14px;

            font-weight: 700;

            transition: all 0.2s ease;
        }


        .back-dashboard-button:hover {

            background: #12583f;

            border-color: #12583f;

            color: #ffffff !important;

            text-decoration: none !important;

            transform: translateY(-1px);
        }


        .back-dashboard-button:active {

            transform: translateY(0);
        }


        /* =========================================================
           RESPONSIVE
           ========================================================= */

        @media(max-width: 800px) {

            .project-grid {

                grid-template-columns: 1fr;
            }
        }


        @media(max-width: 600px) {

            .projects-page {

                padding: 30px 15px;
            }


            .projects-header h1 {

                font-size: 26px;
            }


            .projects-toolbar {

                align-items: flex-start;

                flex-direction: column;
            }


            .project-card-actions {

                flex-direction: column;
            }


            .project-card-actions .btn {

                width: 100%;
            }


            .dashboard-button {

                width: 100%;
            }


            .back-dashboard-button {

                width: 100%;
            }
        }

    </style>

</head>


<body>


    <!-- =========================================================
         NAVBAR
         ========================================================= -->

    <%@include file="../includes/navbar.jsp"%>


    <!-- =========================================================
         MAIN PROJECT PAGE
         ========================================================= -->

    <section class="projects-page">

        <div class="projects-container">


            <!-- =================================================
                 PAGE HEADER
                 ================================================= -->

            <div class="projects-header">

                <span class="page-label">
                    FREELANCER WORKSPACE
                </span>


                <h1>
                    Browse Projects
                </h1>


                <p>
                    Discover open projects and find opportunities
                    that match your skills and experience.
                </p>

            </div>


            <!-- =================================================
                 ERROR MESSAGE
                 ================================================= -->

            <% if (error != null && !error.trim().isEmpty()) { %>

                <div class="alert-error">

                    <%= error %>

                </div>

            <% } %>


            <!-- =================================================
                 PROJECT TOOLBAR
                 ================================================= -->

            <div class="projects-toolbar">


                <div class="projects-count">

                    Available Projects:

                    <strong>

                        <%= projects == null
                            ? 0
                            : projects.size() %>

                    </strong>

                </div>


                <!-- CORRECT DASHBOARD LINK -->

                <a href="<%=dashboardUrl%>"
                   class="dashboard-button">

                    Dashboard

                </a>


            </div>


            <!-- =================================================
                 PROJECT LIST
                 ================================================= -->

            <% if (projects == null ||
                   projects.isEmpty()) { %>


                <!-- =============================================
                     EMPTY STATE
                     ============================================= -->

                <div class="empty-state">


                    <div class="empty-icon">

                        Search

                    </div>


                    <h2>

                        No Open Projects

                    </h2>


                    <p>

                        There are currently no open projects.
                        Please check again later for new
                        opportunities.

                    </p>


                    <!-- CORRECT DASHBOARD LINK -->

                    <a href="<%=dashboardUrl%>"
                       class="back-dashboard-button">

                        Back to Dashboard

                    </a>


                </div>


            <% } else { %>


                <!-- =============================================
                     PROJECT GRID
                     ============================================= -->

                <div class="project-grid">


                    <% for (Project project : projects) { %>


                        <div class="project-card">


                            <!-- ===============================
                                 STATUS
                                 =============================== -->

                            <div class="project-card-top">

                                <span class="status-badge">

                                    <%= project.getProjectStatus() %>

                                </span>

                            </div>


                            <!-- ===============================
                                 TITLE
                                 =============================== -->

                            <h2>

                                <%= project.getTitle() %>

                            </h2>


                            <!-- ===============================
                                 DESCRIPTION
                                 =============================== -->

                            <p class="project-description">

                                <%= project.getDescription() %>

                            </p>


                            <!-- ===============================
                                 PROJECT INFORMATION
                                 =============================== -->

                            <div class="project-info">


                                <!-- BUDGET -->

                                <div class="info-box">

                                    <span>
                                        Budget
                                    </span>


                                    <strong>

                                        ₹<%= String.format(
                                            "%.0f",
                                            project.getBudgetMin()
                                        ) %>

                                        -

                                        ₹<%= String.format(
                                            "%.0f",
                                            project.getBudgetMax()
                                        ) %>

                                    </strong>

                                </div>


                                <!-- DELIVERY -->

                                <div class="info-box">

                                    <span>
                                        Delivery
                                    </span>


                                    <strong>

                                        <%= project.getDeadlineDays() %>
                                        days

                                    </strong>

                                </div>


                            </div>


                            <!-- ===============================
                                 REQUIRED SKILLS
                                 =============================== -->

                            <div class="skills-section">

                                <span class="skills-label">

                                    Required Skills

                                </span>


                                <div class="skills-text">

                                    <%= project.getRequiredSkills() == null ||
                                        project.getRequiredSkills()
                                               .trim()
                                               .isEmpty()

                                        ? "Not specified"

                                        : project.getRequiredSkills() %>

                                </div>

                            </div>


                            <!-- ===============================
                                 ACTIONS
                                 =============================== -->

                            <div class="project-card-actions">


                                <!-- VIEW DETAILS -->

                                <a href="<%=request.getContextPath()%>/FreelancerMyProjectDetailsServlet?projectId=<%=project.getProjectId()%>"
   class="btn btn-outline">
    View Details
</a>

                                <!-- SUBMIT PROPOSAL -->

                                <a href="<%=request.getContextPath()%>/SubmitProposalServlet?projectId=<%=project.getProjectId()%>"
                                   class="btn btn-primary">

                                    Submit Proposal

                                </a>


                            </div>


                        </div>


                    <% } %>


                </div>


            <% } %>


        </div>

    </section>


    <!-- =========================================================
         FOOTER
         ========================================================= -->

    <%@include file="../includes/footer.jsp"%>


</body>

</html>