<%@ page import="com.skillbridge.model.ClientReliabilityScore" %>

<%
    ClientReliabilityScore score =
            (ClientReliabilityScore)
            request.getAttribute("reliabilityScore");
%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/reliability-score.css">


<div class="reliability-page">

    <div class="reliability-container">

        <!-- =====================================================
             PAGE HEADER
             ===================================================== -->

        <div class="page-header">

            <div>

                <div class="breadcrumb">
                    Client
                    <span></span>
                    Reliability
                </div>

                <h1>
                    Client Reliability Score
                </h1>

                <p>
                    Understand your reliability as a client
                    based on your project history, ratings,
                    profile quality and activity.
                </p>

            </div>

            <div>

                <a href="<%=request.getContextPath()%>/client/dashboard.jsp"
                   class="back-button">

                     Back to Dashboard

                </a>

            </div>

        </div>


        <% if (request.getAttribute("error") != null) { %>

            <div class="error-card">

                <strong>Error:</strong>

                <%=request.getAttribute("error")%>

            </div>

        <% } %>


        <% if (score != null) { %>


            <!-- =================================================
                 MAIN SCORE
                 ================================================= -->

            <div class="score-hero">

                <div class="score-circle">

                    <span class="score-number">
                        <%=score.getReliabilityScore()%>
                    </span>

                    <span class="score-label">
                        / 100
                    </span>

                </div>


                <div class="score-content">

                    <span class="eyebrow">
                        CLIENT RELIABILITY
                    </span>

                    <h2>
                        <%=score.getReliabilityLevel()%>
                    </h2>

                    <p>
                        Your reliability score helps
                        freelancers understand your
                        platform history and working
                        reputation.
                    </p>

                </div>

            </div>


            <!-- =================================================
                 SUMMARY CARDS
                 ================================================= -->

            <div class="summary-grid">

                <div class="summary-card">

                    <span class="summary-icon">
                        *
                    </span>

                    <div>

                        <span class="summary-label">
                            Completed Projects
                        </span>

                        <strong>
                            <%=score.getCompletedProjects()%>
                        </strong>

                    </div>

                </div>


                <div class="summary-card">

                    <span class="summary-icon">
                        *
                    </span>

                    <div>

                        <span class="summary-label">
                            Average Rating
                        </span>

                        <strong>
                            <%=String.format(
                                "%.1f",
                                score.getAverageRating())%>
                        </strong>

                    </div>

                </div>


                <div class="summary-card">

                    <span class="summary-icon">
                        *
                    </span>

                    <div>

                        <span class="summary-label">
                            Reviews
                        </span>

                        <strong>
                            <%=score.getReviewCount()%>
                        </strong>

                    </div>

                </div>


                <div class="summary-card">

                    <span class="summary-icon">
                        *
                    </span>

                    <div>

                        <span class="summary-label">
                            Projects Posted
                        </span>

                        <strong>
                            <%=score.getTotalProjects()%>
                        </strong>

                    </div>

                </div>

            </div>


            <!-- =================================================
                 SCORE BREAKDOWN
                 ================================================= -->

            <div class="section-card">

                <div class="section-header">

                    <div>

                        <span class="eyebrow">
                            SCORE BREAKDOWN
                        </span>

                        <h2>
                            How your score is calculated
                        </h2>

                        <p>
                            Your score is based on real activity
                            recorded on SkillBridge.
                        </p>

                    </div>

                </div>


                <!-- Completed Projects -->

                <div class="metric-row">

                    <div class="metric-info">

                        <span>
                            Completed Projects
                        </span>

                        <strong>
                            <%=score.getCompletedProjectScore()%>
                            / 30
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=(
                                score.getCompletedProjectScore()
                                * 100 / 30
                             )%>%;">
                        </div>

                    </div>

                </div>


                <!-- Rating -->

                <div class="metric-row">

                    <div class="metric-info">

                        <span>
                            Average Rating
                        </span>

                        <strong>
                            <%=score.getRatingScore()%>
                            / 30
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=(
                                score.getRatingScore()
                                * 100 / 30
                             )%>%;">
                        </div>

                    </div>

                </div>


                <!-- Reviews -->

                <div class="metric-row">

                    <div class="metric-info">

                        <span>
                            Review History
                        </span>

                        <strong>
                            <%=score.getReviewScore()%>
                            / 10
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=(
                                score.getReviewScore()
                                * 100 / 10
                             )%>%;">
                        </div>

                    </div>

                </div>


                <!-- Profile -->

                <div class="metric-row">

                    <div class="metric-info">

                        <span>
                            Profile Quality
                        </span>

                        <strong>
                            <%=score.getProfileScore()%>
                            / 10
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=(
                                score.getProfileScore()
                                * 100 / 10
                             )%>%;">
                        </div>

                    </div>

                </div>


                <!-- Activity -->

                <div class="metric-row">

                    <div class="metric-info">

                        <span>
                            Client Activity
                        </span>

                        <strong>
                            <%=score.getActivityScore()%>
                            / 20
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=(
                                score.getActivityScore()
                                * 100 / 20
                             )%>%;">
                        </div>

                    </div>

                </div>

            </div>


            <!-- =================================================
                 RELIABILITY INSIGHT
                 ================================================= -->

            <div class="insight-card">

                <div class="insight-icon">
                    !
                </div>

                <div>

                    <span class="eyebrow">
                        SKILLBRIDGE INSIGHT
                    </span>

                    <h3>
                        Your reliability matters
                    </h3>

                    <p>

                        A strong reliability score can help
                        freelancers feel more confident when
                        deciding whether to submit proposals
                        and work with you.

                    </p>

                </div>

            </div>


            <!-- =================================================
                 IMPROVEMENT TIPS
                 ================================================= -->

            <div class="section-card">

                <div class="section-header">

                    <div>

                        <span class="eyebrow">
                            IMPROVE YOUR SCORE
                        </span>

                        <h2>
                            Recommended actions
                        </h2>

                    </div>

                </div>


                <div class="tips-grid">


                    <% if (score.getCompletedProjects() < 5) { %>

                        <div class="tip-card">

                            <span class="tip-number">
                                01
                            </span>

                            <h3>
                                Complete more projects
                            </h3>

                            <p>
                                Successfully completing projects
                                increases your reliability history.
                            </p>

                        </div>

                    <% } %>


                    <% if (score.getAverageRating() < 4.5) { %>

                        <div class="tip-card">

                            <span class="tip-number">
                                02
                            </span>

                            <h3>
                                Improve project experience
                            </h3>

                            <p>
                                Clear requirements and smooth
                                communication can help improve
                                future client reviews.
                            </p>

                        </div>

                    <% } %>


                    <% if (score.getProfileScore() < 10) { %>

                        <div class="tip-card">

                            <span class="tip-number">
                                03
                            </span>

                            <h3>
                                Complete your company profile
                            </h3>

                            <p>
                                Add your company name,
                                description and location.
                            </p>

                        </div>

                    <% } %>


                    <% if (score.getTotalProjects() < 5) { %>

                        <div class="tip-card">

                            <span class="tip-number">
                                04
                            </span>

                            <h3>
                                Build platform activity
                            </h3>

                            <p>
                                Posting and successfully managing
                                projects builds your platform history.
                            </p>

                        </div>

                    <% } %>

                </div>

            </div>


        <% } %>

    </div>

</div>


<%@ include file="includes/footer.jsp" %>