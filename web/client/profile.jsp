<%@ page import="com.skillbridge.model.User" %>
<%@ page import="com.skillbridge.model.TrustScore" %>
<%@ page import="com.skillbridge.dao.TrustScoreDAO" %>
<%@ page import="com.skillbridge.model.Review" %>
<%@ page import="com.skillbridge.dao.ReviewDAO" %>
<%@ page import="java.util.List" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }

    if (!"CLIENT".equalsIgnoreCase(
            loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }


    /*
     * =========================================
     * TRUST SCORE
     * =========================================
     */

    TrustScore clientTrustScore = null;

    try {

        TrustScoreDAO trustScoreDAO =
                new TrustScoreDAO();

        clientTrustScore =
                trustScoreDAO.calculateTrustScore(
                        loggedInUser.getUserId(),
                        "CLIENT"
                );

    } catch (Exception e) {

        e.printStackTrace();
    }


    /*
     * =========================================
     * REVIEWS
     * =========================================
     */

    List<Review> reviews = null;

    try {

        ReviewDAO reviewDAO =
                new ReviewDAO();

        reviews =
                reviewDAO.getReviewsForUser(
                        loggedInUser.getUserId()
                );

    } catch (Exception e) {

        e.printStackTrace();
    }


    double averageRating = 0.0;

    if (clientTrustScore != null) {

        averageRating =
                clientTrustScore.getAverageRating();
    }

%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>


<style>

    * {
        box-sizing: border-box;
    }


    .client-profile-page {

        min-height: 100vh;

        background: #f5f7f9;

        padding: 45px 20px 70px;
    }


    .profile-container {

        width: 92%;

        max-width: 1150px;

        margin: 0 auto;
    }


    /* =========================================
       PROFILE HEADER
       ========================================= */

    .profile-header {

        background: #ffffff;

        border-radius: 18px;

        padding: 35px;

        display: flex;

        align-items: center;

        justify-content: space-between;

        gap: 30px;

        border: 1px solid #e5e7eb;

        box-shadow:
            0 5px 20px rgba(0,0,0,0.04);

        margin-bottom: 25px;
    }


    .profile-main {

        display: flex;

        align-items: center;

        gap: 22px;
    }


    .profile-avatar {

        width: 90px;

        height: 90px;

        border-radius: 50%;

        background: #e7f6ef;

        color: #176b4d;

        display: flex;

        align-items: center;

        justify-content: center;

        font-size: 30px;

        font-weight: 800;

        border: 4px solid #d5eee3;
    }


    .profile-info h1 {

        margin: 0 0 7px;

        color: #17324d;

        font-size: 30px;
    }


    .profile-info p {

        margin: 0 0 12px;

        color: #667085;

        font-size: 14px;
    }


    .client-badge {

        display: inline-block;

        padding: 7px 14px;

        border-radius: 20px;

        background: #e7f6ef;

        color: #176b4d;

        font-size: 12px;

        font-weight: 700;

        letter-spacing: .4px;
    }


    .profile-actions {

        display: flex;

        gap: 10px;

        flex-wrap: wrap;
    }


    .action-button {

        display: inline-block;

        padding: 11px 17px;

        border-radius: 8px;

        text-decoration: none;

        font-size: 13px;

        font-weight: 700;
    }


    .primary-button {

        background: #176b4d;

        color: #ffffff;
    }


    .primary-button:hover {

        background: #12583f;
    }


    .secondary-button {

        background: #ffffff;

        border: 1px solid #d7dde3;

        color: #344054;
    }


    .secondary-button:hover {

        border-color: #176b4d;

        color: #176b4d;
    }


    /* =========================================
       MAIN GRID
       ========================================= */

    .profile-grid {

        display: grid;

        grid-template-columns:
            minmax(0, 1.5fr)
            minmax(300px, .8fr);

        gap: 25px;

        align-items: start;
    }


    .profile-card {

        background: #ffffff;

        border: 1px solid #e5e7eb;

        border-radius: 17px;

        padding: 28px;

        box-shadow:
            0 4px 18px rgba(0,0,0,0.04);

        margin-bottom: 25px;
    }


    .card-title {

        margin: 0 0 22px;

        color: #17324d;

        font-size: 21px;
    }


    .card-subtitle {

        margin: -14px 0 22px;

        color: #667085;

        font-size: 13px;

        line-height: 1.6;
    }


    /* =========================================
       INFORMATION
       ========================================= */

    .info-row {

        display: grid;

        grid-template-columns: 160px 1fr;

        gap: 15px;

        padding: 15px 0;

        border-bottom: 1px solid #edf0f2;
    }


    .info-row:last-child {

        border-bottom: none;
    }


    .info-label {

        color: #667085;

        font-size: 13px;

        font-weight: 600;
    }


    .info-value {

        color: #344054;

        font-size: 14px;

        font-weight: 600;

        word-break: break-word;
    }


    /* =========================================
       TRUST SCORE
       ========================================= */

    .trust-card {

        background: #ffffff;

        border: 1px solid #e5e7eb;

        border-radius: 17px;

        padding: 28px;

        box-shadow:
            0 4px 18px rgba(0,0,0,0.04);

        margin-bottom: 25px;
    }


    .trust-card-header {

        display: flex;

        align-items: center;

        justify-content: space-between;

        gap: 15px;

        margin-bottom: 20px;
    }


    .trust-card-header h2 {

        margin: 0 0 5px;

        color: #17324d;

        font-size: 21px;
    }


    .trust-card-header p {

        margin: 0;

        color: #667085;

        font-size: 12px;
    }


    .trust-score-number {

        color: #176b4d;

        font-size: 32px;

        font-weight: 800;

        white-space: nowrap;
    }


    .trust-score-number span {

        color: #98a2b3;

        font-size: 12px;

        font-weight: 600;
    }


    .trust-progress {

        width: 100%;

        height: 10px;

        background: #edf0f2;

        border-radius: 20px;

        overflow: hidden;

        margin-bottom: 15px;
    }


    .trust-progress-bar {

        height: 100%;

        background: #176b4d;

        border-radius: 20px;
    }


    .trust-bottom {

        display: flex;

        align-items: center;

        justify-content: space-between;

        gap: 15px;
    }


    .trust-level {

        color: #176b4d;

        font-size: 13px;

        font-weight: 700;
    }


    .trust-link {

        color: #176b4d;

        text-decoration: none;

        font-size: 13px;

        font-weight: 700;
    }


    /* =========================================
       STATS
       ========================================= */

    .stats-grid {

        display: grid;

        grid-template-columns:
            repeat(3, 1fr);

        gap: 15px;
    }


    .stat-card {

        background: #f8faf9;

        border: 1px solid #e4eee9;

        border-radius: 13px;

        padding: 20px;
    }


    .stat-card strong {

        display: block;

        color: #17324d;

        font-size: 25px;

        margin-bottom: 6px;
    }


    .stat-card span {

        color: #667085;

        font-size: 12px;
    }


    /* =========================================
       REVIEWS
       ========================================= */

    .review-item {

        padding: 20px 0;

        border-bottom: 1px solid #edf0f2;
    }


    .review-item:first-child {

        padding-top: 0;
    }


    .review-item:last-child {

        border-bottom: none;

        padding-bottom: 0;
    }


    .review-top {

        display: flex;

        justify-content: space-between;

        gap: 15px;

        margin-bottom: 8px;
    }


    .reviewer-name {

        color: #17324d;

        font-size: 14px;

        font-weight: 700;
    }


    .review-rating {

        color: #176b4d;

        font-size: 13px;

        font-weight: 700;
    }


    .review-text {

        color: #667085;

        font-size: 13px;

        line-height: 1.7;

        margin: 0;
    }


    .no-reviews {

        padding: 20px;

        background: #f8faf9;

        border-radius: 10px;

        color: #667085;

        font-size: 13px;

        text-align: center;
    }


    /* =========================================
       RESPONSIVE
       ========================================= */

    @media (max-width: 900px) {

        .profile-grid {

            grid-template-columns: 1fr;
        }
    }


    @media (max-width: 650px) {

        .profile-header {

            flex-direction: column;

            align-items: flex-start;
        }


        .profile-main {

            align-items: flex-start;
        }


        .info-row {

            grid-template-columns: 1fr;

            gap: 5px;
        }


        .stats-grid {

            grid-template-columns: 1fr;
        }
    }


    @media (max-width: 500px) {

        .profile-main {

            flex-direction: column;
        }


        .profile-actions {

            width: 100%;
        }


        .action-button {

            width: 100%;

            text-align: center;
        }
    }

</style>


<div class="client-profile-page">

    <div class="profile-container">


        <!-- =====================================
             PROFILE HEADER
             ===================================== -->

        <div class="profile-header">

            <div class="profile-main">


                <div class="profile-avatar">

                    <%
                        String fullName =
                                loggedInUser.getFullName();

                        String initials = "";

                        if (fullName != null
                                && !fullName.trim().isEmpty()) {

                            String[] nameParts =
                                    fullName.trim()
                                            .split("\\s+");

                            initials =
                                    nameParts[0]
                                    .substring(0, 1)
                                    .toUpperCase();

                            if (nameParts.length > 1) {

                                initials +=
                                    nameParts[
                                        nameParts.length - 1
                                    ]
                                    .substring(0, 1)
                                    .toUpperCase();
                            }
                        }
                    %>

                    <%= initials %>

                </div>


                <div class="profile-info">

                    <h1>
                        <%= loggedInUser.getFullName() %>
                    </h1>

                    <p>
                        <%= loggedInUser.getEmail() %>
                    </p>

                    <span class="client-badge">
                        CLIENT
                    </span>

                </div>

            </div>


            <div class="profile-actions">

                <a
                    href="<%=request.getContextPath()%>/client/dashboard.jsp"
                    class="action-button secondary-button">

                    Dashboard

                </a>


                <a
                    href="<%=request.getContextPath()%>/TrustScoreServlet"
                    class="action-button primary-button">

                    Trust Score

                </a>

            </div>

        </div>



        <!-- =====================================
             MAIN CONTENT
             ===================================== -->

        <div class="profile-grid">


            <!-- =================================
                 LEFT COLUMN
                 ================================= -->

            <div>


                <!-- ACCOUNT INFORMATION -->

                <div class="profile-card">

                    <h2 class="card-title">
                        Account Information
                    </h2>

                    <p class="card-subtitle">
                        Basic information associated
                        with your SkillBridge account.
                    </p>


                    <div class="info-row">

                        <div class="info-label">
                            Full Name
                        </div>

                        <div class="info-value">
                            <%= loggedInUser.getFullName() %>
                        </div>

                    </div>


                    <div class="info-row">

                        <div class="info-label">
                            Email Address
                        </div>

                        <div class="info-value">
                            <%= loggedInUser.getEmail() %>
                        </div>

                    </div>


                    <div class="info-row">

                        <div class="info-label">
                            Account Type
                        </div>

                        <div class="info-value">
                            Client
                        </div>

                    </div>


                    <div class="info-row">

                        <div class="info-label">
                            User ID
                        </div>

                        <div class="info-value">
                            #<%= loggedInUser.getUserId() %>
                        </div>

                    </div>

                </div>



                <!-- CLIENT ACTIVITY -->

                <div class="profile-card">

                    <h2 class="card-title">
                        Client Activity
                    </h2>

                    <p class="card-subtitle">
                        Your current reputation and
                        platform activity.
                    </p>


                    <div class="stats-grid">


                        <div class="stat-card">

                            <strong>

                                <%
                                    if (clientTrustScore != null) {
                                %>

                                    <%= clientTrustScore
                                            .getCompletedProjects() %>

                                <%
                                    } else {
                                %>

                                    0

                                <%
                                    }
                                %>

                            </strong>

                            <span>
                                Completed Projects
                            </span>

                        </div>


                        <div class="stat-card">

                            <strong>

                                <%
                                    if (clientTrustScore != null) {
                                %>

                                    <%= clientTrustScore
                                            .getReviewCount() %>

                                <%
                                    } else {
                                %>

                                    0

                                <%
                                    }
                                %>

                            </strong>

                            <span>
                                Reviews Received
                            </span>

                        </div>


                        <div class="stat-card">

                            <strong>

                                <%
                                    if (clientTrustScore != null) {
                                %>

                                    <%= String.format(
                                        "%.1f",
                                        clientTrustScore
                                            .getAverageRating()
                                    ) %>

                                <%
                                    } else {
                                %>

                                    0.0

                                <%
                                    }
                                %>

                            </strong>

                            <span>
                                Average Rating
                            </span>

                        </div>

                    </div>

                </div>



                <!-- REVIEWS -->

                <div class="profile-card">

                    <h2 class="card-title">
                        Client Reviews
                    </h2>

                    <p class="card-subtitle">
                        Feedback received from freelancers
                        after completed projects.
                    </p>


                    <%
                        if (reviews != null
                                && !reviews.isEmpty()) {

                            for (Review review : reviews) {
                    %>


                    <div class="review-item">

                        <div class="review-top">

                            <span class="reviewer-name">

                                <%
                                    String reviewerName =
                                            review.getReviewerName();

                                    if (reviewerName != null
                                            && !reviewerName
                                                .trim()
                                                .isEmpty()) {

                                        out.print(
                                            reviewerName
                                        );

                                    } else {

                                        out.print(
                                            "SkillBridge User"
                                        );
                                    }
                                %>

                            </span>


                            <span class="review-rating">

                                Rating:
                                <%= review.getRating() %>/5

                            </span>

                        </div>


                        <p class="review-text">

                            <%
                                String reviewText =
                                        review.getReviewText();

                                if (reviewText != null
                                        && !reviewText
                                            .trim()
                                            .isEmpty()) {

                                    out.print(
                                        reviewText
                                    );

                                } else {

                                    out.print(
                                        "No written feedback provided."
                                    );
                                }
                            %>

                        </p>

                    </div>


                    <%
                            }

                        } else {
                    %>


                    <div class="no-reviews">

                        No reviews have been received yet.
                        Complete projects and build your
                        SkillBridge reputation to receive
                        client feedback.

                    </div>


                    <%
                        }
                    %>

                </div>

            </div>



            <!-- =================================
                 RIGHT COLUMN
                 ================================= -->

            <div>


                <!-- TRUST SCORE -->

                <div class="trust-card">

                    <div class="trust-card-header">

                        <div>

                            <h2>
                                Trust Score
                            </h2>

                            <p>
                                Your professional reputation
                            </p>

                        </div>


                        <%
                            if (clientTrustScore != null) {
                        %>

                        <div class="trust-score-number">

                            <%= clientTrustScore
                                    .getTrustScore() %>

                            <span>/100</span>

                        </div>

                        <%
                            }
                        %>

                    </div>


                    <%
                        if (clientTrustScore != null) {
                    %>


                    <div class="trust-progress">

                        <div
                            class="trust-progress-bar"
                            style="width:<%= clientTrustScore
                                .getTrustScore() %>%;">
                        </div>

                    </div>


                    <div class="trust-bottom">

                        <span class="trust-level">

                            <%= clientTrustScore
                                    .getTrustLevel() %>

                        </span>


                        <a
                            href="<%=request.getContextPath()%>/TrustScoreServlet"
                            class="trust-link">

                            View Breakdown

                        </a>

                    </div>


                    <%
                        } else {
                    %>


                    <div class="no-reviews">

                        Trust Score is currently
                        unavailable.

                    </div>


                    <%
                        }
                    %>

                </div>



                <!-- PROFILE COMPLETION -->

                <div class="profile-card">

                    <h2 class="card-title">
                        Profile Strength
                    </h2>

                    <p class="card-subtitle">
                        Keep your client information
                        complete to build trust.
                    </p>


                    <%
                        int profileCompletion = 0;

                        if (clientTrustScore != null) {

                            profileCompletion =
                                clientTrustScore
                                    .getProfileCompletion();
                        }
                    %>


                    <div class="trust-progress">

                        <div
                            class="trust-progress-bar"
                            style="width:<%= profileCompletion %>%;">
                        </div>

                    </div>


                    <div class="trust-bottom">

                        <span class="trust-level">

                            <%= profileCompletion %>%
                            Complete

                        </span>

                        <span
                            style="
                                color:#667085;
                                font-size:12px;
                            ">

                            Keep information updated

                        </span>

                    </div>

                </div>



                <!-- TRUST INFORMATION -->

                <div class="profile-card">

                    <h2 class="card-title">
                        Build Your Reputation
                    </h2>

                    <p class="card-subtitle">

                        Your Trust Score improves when
                        you successfully complete projects,
                        maintain a complete profile and
                        receive positive reviews from
                        freelancers.

                    </p>


                    <a
    href="<%=request.getContextPath()%>/TrustScoreServlet"
    class="profile-trust-button">

    View Trust Score
                    </a>
    
<a href="<%=request.getContextPath()%>/ClientReliabilityScoreServlet"
   class="btn btn-outline">

    View Reliability Score

</a>
                </div>


            </div>

        </div>


    </div>

</div>


<%@ include file="../includes/footer.jsp" %>