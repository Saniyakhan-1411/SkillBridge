<%@ page import="com.skillbridge.model.User" %>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null) {
        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }

    if (!"FREELANCER".equalsIgnoreCase(
            loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath() + "/login.jsp"
        );
        return;
    }
%>

<%@ include file="../includes/header.jsp" %>
<%@ include file="../includes/navbar.jsp" %>

<style>

    * {
        box-sizing: border-box;
    }

    .freelancer-dashboard {
        min-height: 100vh;
        background: #f5f7f9;
        padding: 40px 0 70px;
    }

    .dashboard-container {
        width: 90%;
        max-width: 1200px;
        margin: auto;
    }

    /* ================================
       WELCOME
       ================================ */

    .welcome-card {
        background: #ffffff;
        border-radius: 18px;
        padding: 35px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 28px;
        box-shadow: 0 5px 20px rgba(0,0,0,0.06);
    }

    .welcome-text h1 {
        margin: 0 0 8px;
        font-size: 32px;
        color: #17324d;
    }

    .welcome-text h1 span {
        color: #176b4d;
    }

    .welcome-text p {
        margin: 0;
        color: #667085;
        font-size: 15px;
        line-height: 1.6;
    }

    .role-badge {
        display: inline-block;
        margin-top: 15px;
        padding: 7px 14px;
        border-radius: 20px;
        background: #e7f6ef;
        color: #176b4d;
        font-size: 12px;
        font-weight: 700;
        letter-spacing: .5px;
    }

    .welcome-icon {
        width: 80px;
        height: 80px;
        border-radius: 20px;
        background: #eaf6f1;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-shrink: 0;
    }

    /* ================================
       STATS
       ================================ */

    .stats-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-bottom: 35px;
    }

    .stat-card {
        background: #ffffff;
        border: 1px solid #e6e9ed;
        border-radius: 15px;
        padding: 24px;
        box-shadow: 0 4px 16px rgba(0,0,0,0.04);
    }

    .stat-icon {
        width: 45px;
        height: 45px;
        border-radius: 11px;
        background: #eaf6f1;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 15px;
    }

    .stat-card h3 {
        margin: 0 0 6px;
        font-size: 20px;
        color: #17324d;
    }

    .stat-card p {
        margin: 0;
        color: #667085;
        font-size: 13px;
    }

    /* ================================
       SECTION TITLE
       ================================ */

    .section-title {
        margin-bottom: 18px;
    }

    .section-title h2 {
        margin: 0 0 5px;
        color: #17324d;
        font-size: 24px;
    }

    .section-title p {
        margin: 0;
        color: #667085;
        font-size: 14px;
    }

    /* ================================
       DASHBOARD CARDS
       ================================ */

    .dashboard-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-bottom: 35px;
    }

    .dashboard-card {
        display: block;
        background: #ffffff;
        border: 1px solid #e5e7eb;
        border-radius: 16px;
        padding: 25px;
        text-decoration: none;
        color: inherit;
        box-shadow: 0 4px 15px rgba(0,0,0,0.04);
        transition: all .2s ease;
    }

    .dashboard-card:hover {
        transform: translateY(-4px);
        border-color: #176b4d;
        box-shadow: 0 10px 25px rgba(0,0,0,0.09);
    }

    .dashboard-card-icon {
        width: 48px;
        height: 48px;
        border-radius: 12px;
        background: #eaf6f1;
        border: 1px solid #d1e7dd;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 18px;
    }

    .dashboard-card-icon svg {
        width: 24px;
        height: 24px;
        stroke: #107259;
    }

    .dashboard-card h3 {
        margin: 0 0 8px;
        color: #17324d;
        font-size: 18px;
    }

    .dashboard-card p {
        margin: 0;
        min-height: 48px;
        color: #667085;
        font-size: 13px;
        line-height: 1.6;
    }

    .dashboard-card-link {
        display: block;
        margin-top: 18px;
        color: #176b4d;
        font-size: 13px;
        font-weight: 700;
    }

    /* ================================
       FEATURED BANNER
       ================================ */

    .opportunity-card {
        background: #ffffff;
        border-radius: 17px;
        padding: 30px;
        margin-bottom: 30px;
        box-shadow: 0 4px 18px rgba(0,0,0,0.05);
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 25px;
    }

    .opportunity-content h2 {
        margin: 0 0 8px;
        color: #17324d;
        font-size: 23px;
    }

    .opportunity-content p {
        margin: 0;
        max-width: 700px;
        color: #667085;
        font-size: 14px;
        line-height: 1.7;
    }

    .browse-btn {
        display: inline-block;
        padding: 13px 22px;
        background: #176b4d;
        color: #ffffff;
        text-decoration: none;
        border-radius: 8px;
        font-size: 14px;
        font-weight: 700;
        white-space: nowrap;
    }

    .browse-btn:hover {
        background: #12583f;
    }

    /* ================================
       RESPONSIVE
       ================================ */

    @media (max-width: 950px) {
        .dashboard-grid {
            grid-template-columns: repeat(2, 1fr);
        }
        .stats-grid {
            grid-template-columns: repeat(2, 1fr);
        }
    }

    @media (max-width: 700px) {
        .welcome-card {
            flex-direction: column;
            align-items: flex-start;
        }
        .welcome-icon {
            display: none;
        }
        .dashboard-grid {
            grid-template-columns: 1fr;
        }
        .stats-grid {
            grid-template-columns: 1fr;
        }
        .opportunity-card {
            flex-direction: column;
            align-items: flex-start;
        }
    }

</style>


<div class="freelancer-dashboard">

    <div class="dashboard-container">

        <!-- WELCOME CARD -->
        <div class="welcome-card">
            <div class="welcome-text">
                <h1>
                    Welcome back,
                    <span>
                        <%= loggedInUser.getFullName() %>
                    </span>
                </h1>
                <p>
                    Manage your freelance work, proposals,
                    projects and professional profile from
                    your SkillBridge workspace.
                </p>
                <span class="role-badge">
                    FREELANCER
                </span>
            </div>

            <div class="welcome-icon">
                <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#107259" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <rect x="2" y="7" width="20" height="14" rx="2" ry="2"></rect>
                    <path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"></path>
                </svg>
            </div>
        </div>

        <!-- WORKSPACE TITLE -->
        <div class="section-title">
            <h2>Freelancer Workspace</h2>
            <p>Access your most important freelancer tools.</p>
        </div>

        <div class="dashboard-grid">

            <!-- BROWSE PROJECTS -->
            <a href="<%=request.getContextPath()%>/BrowseProjectsServlet" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="11" cy="11" r="8"></circle>
                        <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                    </svg>
                </div>
                <h3>Browse Projects</h3>
                <p>Discover projects that match your skills and submit professional proposals.</p>
                <span class="dashboard-card-link">Browse Projects </span>
            </a>

            <!-- MY PROPOSALS -->
            <a href="<%=request.getContextPath()%>/freelancer/my-proposals.jsp" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <line x1="16" y1="13" x2="8" y2="13"></line>
                        <line x1="16" y1="17" x2="8" y2="17"></line>
                        <polyline points="10 9 9 9 8 9"></polyline>
                    </svg>
                </div>
                <h3>My Proposals</h3>
                <p>View your submitted proposals, bid amounts, delivery timelines and skill match scores.</p>
                <span class="dashboard-card-link">View Proposals </span>
            </a>

            <!-- MY PROJECTS -->
            <a href="<%=request.getContextPath()%>/FreelancerMyProjectsServlet" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
                    </svg>
                </div>
                <h3>My Projects</h3>
                <p>View your hired projects, track their status and mark completed work.</p>
                <span class="dashboard-card-link">Manage Projects </span>
            </a>

            <!-- PROFILE -->
            <a href="<%=request.getContextPath()%>/freelancer/profile.jsp" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                </div>
                <h3>My Profile</h3>
                <p>Manage your headline, skills, experience, location and hourly rate.</p>
                <span class="dashboard-card-link">Manage Profile </span>
            </a>

            <!-- PORTFOLIO -->
            <a href="<%=request.getContextPath()%>/PortfolioServlet" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
                        <line x1="8" y1="21" x2="16" y2="21"></line>
                        <line x1="12" y1="17" x2="12" y2="21"></line>
                    </svg>
                </div>
                <h3>Portfolio</h3>
                <p>Showcase your projects, skills and professional work to potential clients.</p>
                <span class="dashboard-card-link">Manage Portfolio </span>
            </a>

            <!-- MESSAGES -->
            <a href="<%=request.getContextPath()%>/MessagesServlet" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                    </svg>
                </div>
                <h3>Messages</h3>
                <p>Communicate with clients and manage your project conversations.</p>
                <span class="dashboard-card-link">Open Messages </span>
            </a>

            <!-- NOTIFICATIONS -->
            <a href="<%=request.getContextPath()%>/NotificationServlet" class="dashboard-card">
                <div class="dashboard-card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
                        <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
                    </svg>
                </div>
                <h3>Notifications</h3>
                <p>View updates about proposals, projects, messages and reviews.</p>
                <span class="dashboard-card-link">View Notifications </span>
            </a>

        </div>

        <!-- OPPORTUNITY BANNER -->
        <div class="opportunity-card">
            <div class="opportunity-content">
                <h2>Find your next opportunity</h2>
                <p>Explore new projects, submit strong proposals and grow your freelancing career with SkillBridge.</p>
            </div>
            <a href="<%=request.getContextPath()%>/BrowseProjectsServlet" class="browse-btn">
                Browse Projects
            </a>
        </div>

    </div>

</div>

<%@ include file="../includes/footer.jsp" %>