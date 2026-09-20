<%@ page import="com.skillbridge.dao.TrustScoreDAO" %>
<%@ page import="com.skillbridge.model.TrustScore" %>
<%@ page import="com.skillbridge.model.FreelancerProfile" %>
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


    FreelancerProfile profile =
            (FreelancerProfile)
            request.getAttribute("profile");


    String error =
            (String) request.getAttribute("error");

    String success =
            request.getParameter("success");
%>
<%
    TrustScore freelancerTrustScore = null;

    try {

        TrustScoreDAO trustScoreDAO =
                new TrustScoreDAO();

        freelancerTrustScore =
                trustScoreDAO.calculateTrustScore(
                        loggedInUser.getUserId(),
                        "FREELANCER"
                );

    } catch (Exception e) {

        e.printStackTrace();
    }
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>


<style>

    body {
        background: #f5f7f6;
    }


    .profile-wrapper {
        padding: 45px 7%;
        min-height: 75vh;
    }


    .profile-header {
        margin-bottom: 28px;
    }


    .profile-header h1 {
        margin: 0;
        color: #173b2d;
        font-size: 32px;
    }


    .profile-header p {
        color: #6b7280;
        margin-top: 8px;
    }


    .alert {
        padding: 14px 18px;
        border-radius: 8px;
        margin-bottom: 20px;
    }


    .success {
        background: #e8f7ed;
        color: #176b3a;
        border: 1px solid #bfe6cb;
    }


    .error {
        background: #fff0f0;
        color: #a52828;
        border: 1px solid #efc5c5;
    }


    .profile-layout {
        display: grid;
        grid-template-columns: 2fr 1fr;
        gap: 25px;
    }


    .card {
        background: white;
        border: 1px solid #e1e8e4;
        border-radius: 14px;
        padding: 28px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.04);
    }


    .card h2 {
        color: #173b2d;
        margin-top: 0;
        margin-bottom: 22px;
    }


    .form-group {
        margin-bottom: 20px;
    }


    .form-group label {
        display: block;
        margin-bottom: 7px;
        font-weight: 600;
        color: #34423b;
    }


    .form-group input,
    .form-group textarea {
        width: 100%;
        box-sizing: border-box;
        border: 1px solid #d6dfda;
        border-radius: 8px;
        padding: 12px 14px;
        font-size: 14px;
        outline: none;
    }


    .form-group textarea {
        min-height: 120px;
        resize: vertical;
    }


    .form-group input:focus,
    .form-group textarea:focus {
        border-color: #218c5a;
    }


    .hint {
        color: #7b8580;
        font-size: 12px;
        margin-top: 6px;
    }


    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 18px;
    }


    .save-btn {
        border: none;
        background: #1f7a4d;
        color: white;
        padding: 13px 25px;
        border-radius: 8px;
        font-weight: 700;
        cursor: pointer;
        font-size: 14px;
    }


    .completion-card {
        text-align: center;
    }


    .completion-circle {
        width: 140px;
        height: 140px;
        border-radius: 50%;
        margin: 10px auto 22px;

        display: flex;
        align-items: center;
        justify-content: center;

        background: #edf7f1;
        border: 10px solid #d5eddf;

        font-size: 30px;
        font-weight: 700;
        color: #1f7a4d;
    }


    .completion-card h3 {
        color: #173b2d;
        margin-bottom: 8px;
    }


    .completion-card p {
        color: #6b7280;
        font-size: 14px;
        line-height: 1.6;
    }


    .skill-info {
        margin-top: 25px;
        padding-top: 22px;
        border-top: 1px solid #e7ece9;
        text-align: left;
    }


    .skill-info h3 {
        color: #173b2d;
        font-size: 17px;
    }


    .skill-info p {
        font-size: 13px;
        color: #6b7280;
        line-height: 1.6;
    }


    @media (max-width: 850px) {

        .profile-layout {
            grid-template-columns: 1fr;
        }

        .form-row {
            grid-template-columns: 1fr;
        }

        .profile-wrapper {
            padding: 30px 5%;
        }
    }

</style>


<div class="profile-wrapper">

    <div class="profile-header">

        <h1>Freelancer Profile</h1>

        <p>
            Build your professional profile and improve your project matches.
        </p>

    </div>


    <% if (success != null) { %>

        <div class="alert success">
            ? Your freelancer profile has been saved successfully.
        </div>

    <% } %>


    <% if (error != null) { %>

        <div class="alert error">
            <%= error %>
        </div>

    <% } %>


    <div class="profile-layout">


        <!-- PROFILE FORM -->

        <div class="card">

            <h2>Professional Information</h2>


            <form method="post"
                  action="<%=request.getContextPath()%>/FreelancerProfileServlet">


                <div class="form-group">

                    <label>
                        Professional Headline *
                    </label>

                    <input type="text"
                           name="headline"
                           maxlength="150"
                           placeholder="e.g. Full Stack Java Developer"
                           value="<%= profile != null && profile.getHeadline() != null
                                   ? profile.getHeadline()
                                   : "" %>"
                           required>

                </div>


                <div class="form-group">

                    <label>
                        About You
                    </label>

                    <textarea name="bio"
                              maxlength="1000"
                              placeholder="Tell clients about your experience, strengths and expertise..."><%= profile != null && profile.getBio() != null
                                      ? profile.getBio()
                                      : "" %></textarea>

                </div>


                <div class="form-group">

                    <label>
                        Skills *
                    </label>

                    <input type="text"
                           name="skills"
                           maxlength="500"
                           placeholder="Java, JSP, JDBC, HTML, CSS, JavaScript"
                           value="<%= profile != null && profile.getSkills() != null
                                   ? profile.getSkills()
                                   : "" %>"
                           required>

                    <div class="hint">
                        Separate multiple skills using commas.
                    </div>

                </div>


                <div class="form-row">


                    <div class="form-group">

                        <label>
                            Hourly Rate (?)
                        </label>

                        <input type="number"
                               name="hourlyRate"
                               min="0"
                               step="0.01"
                               placeholder="500"
                               value="<%= profile != null
                                       ? profile.getHourlyRate()
                                       : "" %>">

                    </div>


                    <div class="form-group">

                        <label>
                            Experience (Years)
                        </label>

                        <input type="number"
                               name="experienceYears"
                               min="0"
                               max="50"
                               placeholder="2"
                               value="<%= profile != null
                                       ? profile.getExperienceYears()
                                       : "" %>">

                    </div>

                </div>


                <div class="form-group">

                    <label>
                        Location
                    </label>

                    <input type="text"
                           name="location"
                           maxlength="100"
                           placeholder="Mumbai, Maharashtra"
                           value="<%= profile != null && profile.getLocation() != null
                                   ? profile.getLocation()
                                   : "" %>">

                </div>


                <button type="submit"
                        class="save-btn">

                    Save Profile

                </button>


            </form>

        </div>
                                 


        <!-- COMPLETION -->

        <div class="card completion-card">

            <h2>Profile Strength</h2>


            <%
                int completion = 0;

                if (profile != null) {
                    completion =
                            profile.getProfileCompletion();
                }
            %>


            <div class="completion-circle">

                <%= completion %>%

            </div>


            <h3>
                Profile Completion
            </h3>


            <p>

                Complete your profile to make your
                freelancer account more attractive to clients.

            </p>


            <div class="skill-info">

                <h3>
                    Smart Skill Match
                </h3>

                <p>

                    SkillBridge compares your listed skills
                    with the skills required by a project.

                    Your match percentage will be calculated
                    automatically when you submit a proposal.

                </p>

            </div>
            
            <% if (freelancerTrustScore != null) { %>

<div class="profile-trust-card">

    <div class="trust-card-header">

        <div>
            <h3>SkillBridge Trust Score</h3>

            <p>
                Professional reliability indicator
            </p>
        </div>

        <div class="trust-score-number">
            <%= freelancerTrustScore.getTrustScore() %>
            <span>/100</span>
        </div>

    </div>

    <div class="trust-progress">

        <div
            class="trust-progress-bar"
            style="width:<%= freelancerTrustScore.getTrustScore() %>%;">
        </div>

    </div>

    <div class="trust-card-bottom">

        <span>
            <%= freelancerTrustScore.getTrustLevel() %>
        </span>

        <a href="<%=request.getContextPath()%>/TrustScoreServlet">
            View Breakdown
        </a>

    </div>

</div>
            <br><br>
            <a
    href="<%=request.getContextPath()%>/MessagesServlet"
    class="profile-trust-button">

    Messages
</a>
     <a href="<%=request.getContextPath()%>/FreelancerCareerGrowthServlet"
   class="btn btn-outline">

    Career Growth

</a>

<% } %>

        </div>


    </div>

</div>


<%@ include file="../includes/footer.jsp" %>