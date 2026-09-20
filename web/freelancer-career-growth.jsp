<%@ page import="com.skillbridge.model.FreelancerCareerGrowth" %>

<%
    FreelancerCareerGrowth growth =
            (FreelancerCareerGrowth)
            request.getAttribute("careerGrowth");
%>

<%@ include file="includes/header.jsp" %>
<%@ include file="includes/navbar.jsp" %>

<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/career-growth.css">


<div class="career-page">

    <div class="career-container">


        <!-- =====================================================
             HEADER
             ===================================================== -->

        <div class="page-header">

            <div>

                <div class="breadcrumb">
                    Freelancer
                    <span>?</span>
                    Career Growth
                </div>

                <h1>
                    Career Growth Center
                </h1>

                <p>
                    Track your freelance performance,
                    identify your strengths and discover
                    opportunities to grow.
                </p>

            </div>


            <a href="<%=request.getContextPath()%>/freelancer/dashboard.jsp"
               class="back-button">

                 Dashboard

            </a>

        </div>


        <% if (request.getAttribute("error") != null) { %>

            <div class="error-card">

                <strong>Error:</strong>
                <%=request.getAttribute("error")%>

            </div>

        <% } %>


        <% if (growth != null) { %>


        <!-- =====================================================
             CAREER SCORE HERO
             ===================================================== -->

        <div class="career-hero">

            <div class="career-score-circle">

                <span class="career-score">
                    <%=growth.getCareerScore()%>
                </span>

                <span class="out-of">
                    / 100
                </span>

            </div>


            <div class="career-hero-content">

                <span class="eyebrow">
                    YOUR CAREER SCORE
                </span>

                <h2>
                    <%=growth.getCareerLevel()%>
                </h2>

                <p>
                    Your Career Growth Score combines
                    project success, proposal performance,
                    client ratings, portfolio strength and
                    profile development.
                </p>

            </div>


            <div class="hero-action">

                <a href="<%=request.getContextPath()%>/TrustScoreServlet"
                   class="secondary-button">

                    View Trust Score

                </a>

            </div>

        </div>


        <!-- =====================================================
             PERFORMANCE CARDS
             ===================================================== -->

        <div class="stats-grid">


            <div class="stat-card">

                <span class="stat-icon">
                    *
                </span>

                <span class="stat-label">
                    Proposals Submitted
                </span>

                <strong>
                    <%=growth.getProposalsSubmitted()%>
                </strong>

            </div>


            <div class="stat-card">

                <span class="stat-icon">
                    *
                </span>

                <span class="stat-label">
                    Projects Completed
                </span>

                <strong>
                    <%=growth.getCompletedProjects()%>
                </strong>

            </div>


            <div class="stat-card">

                <span class="stat-icon">
                    *
                </span>

                <span class="stat-label">
                    Average Rating
                </span>

                <strong>
                    <%=String.format(
                        "%.1f",
                        growth.getAverageRating())%>
                </strong>

            </div>


            <div class="stat-card">

                <span class="stat-icon">
                    %
                </span>

                <span class="stat-label">
                    Proposal Success
                </span>

                <strong>
                    <%=String.format(
                        "%.0f",
                        growth.getProposalSuccessRate())%>%
                </strong>

            </div>

        </div>


        <!-- =====================================================
             CAREER METRICS
             ===================================================== -->

        <div class="content-grid">


            <div class="section-card">

                <div class="section-heading">

                    <span class="eyebrow">
                        CAREER PERFORMANCE
                    </span>

                    <h2>
                        Your Progress
                    </h2>

                </div>


                <!-- Projects -->

                <div class="metric">

                    <div class="metric-top">

                        <span>
                            Completed Projects
                        </span>

                        <strong>
                            <%=growth.getProjectScore()%>
                            / 25
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=growth.getProjectScore()
                             * 100 / 25%>%;">
                        </div>

                    </div>

                </div>


                <!-- Proposals -->

                <div class="metric">

                    <div class="metric-top">

                        <span>
                            Proposal Performance
                        </span>

                        <strong>
                            <%=growth.getProposalScore()%>
                            / 20
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=growth.getProposalScore()
                             * 100 / 20%>%;">
                        </div>

                    </div>

                </div>


                <!-- Rating -->

                <div class="metric">

                    <div class="metric-top">

                        <span>
                            Client Rating
                        </span>

                        <strong>
                            <%=growth.getRatingScore()%>
                            / 20
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=growth.getRatingScore()
                             * 100 / 20%>%;">
                        </div>

                    </div>

                </div>


                <!-- Portfolio -->

                <div class="metric">

                    <div class="metric-top">

                        <span>
                            Portfolio Strength
                        </span>

                        <strong>
                            <%=growth.getPortfolioScore()%>
                            / 15
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=growth.getPortfolioScore()
                             * 100 / 15%>%;">
                        </div>

                    </div>

                </div>


                <!-- Profile -->

                <div class="metric">

                    <div class="metric-top">

                        <span>
                            Profile Completion
                        </span>

                        <strong>
                            <%=growth.getProfileCompletion()%>%
                        </strong>

                    </div>

                    <div class="progress-track">

                        <div class="progress-bar"
                             style="width:
                             <%=growth.getProfileCompletion()%>%;">
                        </div>

                    </div>

                </div>

            </div>


            <!-- =================================================
                 CAREER SNAPSHOT
                 ================================================= -->

            <div class="section-card">

                <div class="section-heading">

                    <span class="eyebrow">
                        CAREER SNAPSHOT
                    </span>

                    <h2>
                        Your Professional Journey
                    </h2>

                </div>


                <div class="journey-item">

                    <span class="journey-number">
                        01
                    </span>

                    <div>

                        <h3>
                            Proposals
                        </h3>

                        <p>
                            You have submitted
                            <strong>
                                <%=growth.getProposalsSubmitted()%>
                            </strong>
                            proposals.
                        </p>

                    </div>

                </div>


                <div class="journey-item">

                    <span class="journey-number">
                        02
                    </span>

                    <div>

                        <h3>
                            Hiring Success
                        </h3>

                        <p>
                            <strong>
                                <%=growth.getAcceptedProposals()%>
                            </strong>
                            of your proposals were accepted.
                        </p>

                    </div>

                </div>


                <div class="journey-item">

                    <span class="journey-number">
                        03
                    </span>

                    <div>

                        <h3>
                            Project Delivery
                        </h3>

                        <p>
                            You have successfully completed
                            <strong>
                                <%=growth.getCompletedProjects()%>
                            </strong>
                            projects.
                        </p>

                    </div>

                </div>


                <div class="journey-item">

                    <span class="journey-number">
                        04
                    </span>

                    <div>

                        <h3>
                            Client Feedback
                        </h3>

                        <p>
                            You have received
                            <strong>
                                <%=growth.getReviewCount()%>
                            </strong>
                            reviews.
                        </p>

                    </div>

                </div>

            </div>

        </div>


        <!-- =====================================================
             STRENGTHS + OPPORTUNITIES
             ===================================================== -->

        <div class="growth-grid">


            <!-- STRENGTHS -->

            <div class="growth-card">

                <span class="eyebrow">
                    YOUR STRENGTHS
                </span>

                <h2>
                    What you are doing well
                </h2>


                <% if (growth.getAverageRating() >= 4.5) { %>

                    <div class="growth-item success">

                        <span>?</span>

                        <div>

                            <strong>
                                Excellent client ratings
                            </strong>

                            <p>
                                Your average rating is
                                <%=String.format(
                                    "%.1f",
                                    growth.getAverageRating())%>.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getCompletedProjects() >= 3) { %>

                    <div class="growth-item success">

                        <span>?</span>

                        <div>

                            <strong>
                                Strong project history
                            </strong>

                            <p>
                                You have completed
                                <%=growth.getCompletedProjects()%>
                                projects.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getProposalSuccessRate() >= 25) { %>

                    <div class="growth-item success">

                        <span>?</span>

                        <div>

                            <strong>
                                Competitive proposals
                            </strong>

                            <p>
                                Your proposal success rate is
                                <%=String.format(
                                    "%.0f",
                                    growth.getProposalSuccessRate())%>%.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getPortfolioCount() >= 2) { %>

                    <div class="growth-item success">

                        <span></span>

                        <div>

                            <strong>
                                Portfolio established
                            </strong>

                            <p>
                                You have
                                <%=growth.getPortfolioCount()%>
                                portfolio projects.
                            </p>

                        </div>

                    </div>

                <% } %>

            </div>


            <!-- OPPORTUNITIES -->

            <div class="growth-card">

                <span class="eyebrow">
                    GROWTH OPPORTUNITIES
                </span>

                <h2>
                    Recommended actions
                </h2>


                <% if (growth.getProfileCompletion() < 100) { %>

                    <div class="growth-item">

                        <span></span>

                        <div>

                            <strong>
                                Complete your profile
                            </strong>

                            <p>
                                Your profile is currently
                                <%=growth.getProfileCompletion()%>%
                                complete.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getPortfolioCount() < 3) { %>

                    <div class="growth-item">

                        <span></span>

                        <div>

                            <strong>
                                Add portfolio projects
                            </strong>

                            <p>
                                Showcase more work to improve
                                your professional credibility.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getProposalSuccessRate() < 25) { %>

                    <div class="growth-item">

                        <span></span>

                        <div>

                            <strong>
                                Improve proposal strategy
                            </strong>

                            <p>
                                Focus on projects that closely
                                match your skills.
                            </p>

                        </div>

                    </div>

                <% } %>


                <% if (growth.getReviewCount() < 5) { %>

                    <div class="growth-item">

                        <span></span>

                        <div>

                            <strong>
                                Build your review history
                            </strong>

                            <p>
                                Successful project delivery can
                                help build your client feedback.
                            </p>

                        </div>

                    </div>

                <% } %>

            </div>

        </div>


        <!-- =====================================================
             QUICK ACTIONS
             ===================================================== -->

        <div class="quick-actions">

            <a href="<%=request.getContextPath()%>/PortfolioServlet"
               class="quick-action">

                <strong>
                    Portfolio
                </strong>

                <span>
                    Manage your work
                </span>

            </a>


            <a href="<%=request.getContextPath()%>/freelancer/profile.jsp"
               class="quick-action">

                <strong>
                    Profile
                </strong>

                <span>
                    Improve your profile
                </span>

            </a>


            <a href="<%=request.getContextPath()%>/BrowseProjectsServlet"
               class="quick-action">

                <strong>
                    Find Projects
                </strong>

                <span>
                    Discover new opportunities
                </span>

            </a>


            <a href="<%=request.getContextPath()%>/TrustScoreServlet"
               class="quick-action">

                <strong>
                    Trust Score
                </strong>

                <span>
                    View your reputation
                </span>

            </a>

        </div>


        <% } %>

    </div>

</div>


<%@ include file="includes/footer.jsp" %>