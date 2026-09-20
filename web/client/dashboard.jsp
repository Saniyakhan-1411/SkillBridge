<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (session.getAttribute("loggedInUser") == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    String role = (String) session.getAttribute("role");

    if (!"CLIENT".equals(role)) {
        response.sendRedirect("../login.jsp");
        return;
    }

    String userName = (String) session.getAttribute("userName");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Client Dashboard | SkillBridge</title>

    <link rel="stylesheet" href="../assets/css/style.css">

    <style>

        * {
            box-sizing: border-box;
        }

        .dashboard {
            min-height: 80vh;
            background: #f5faf7;
            padding: 50px 20px 70px;
        }

        .dashboard-container {
            max-width: 1150px;
            margin: auto;
        }

        /* ================================
           WELCOME CARD
           ================================ */

        .welcome-card {
            background: #ffffff;
            border-radius: 16px;
            padding: 30px;
            margin-bottom: 25px;
            border: 1px solid #e7eaf0;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.03);
        }

        .welcome-card h1 {
            margin: 0 0 8px;
            color: #101828;
            font-size: 28px;
        }

        .welcome-card p {
            color: #667085;
            margin: 0;
            font-size: 15px;
        }

        /* ================================
           DASHBOARD GRID & CARDS
           ================================ */

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .dashboard-card {
            display: block;
            background: #ffffff;
            padding: 25px;
            border-radius: 14px;
            border: 1px solid #e7eaf0;
            text-decoration: none;
            color: inherit;
            transition: all 0.2s ease;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.02);
        }

        .dashboard-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(23, 32, 51, 0.08);
            border-color: #176b4d;
        }

        /* UNIFIED PROFESSIONAL ICON BOX STYLE */
        .card-icon {
            width: 44px;
            height: 44px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #e6f4ea; /* Light Mint */
            border: 1px solid #d1e7dd; /* Sage Accent Border */
            border-radius: 10px;
            margin-bottom: 18px;
            flex-shrink: 0;
        }

        .card-icon svg {
            width: 22px;
            height: 22px;
            stroke: #107259; /* Deep Emerald Green */
        }

        .dashboard-card h3 {
            margin: 0 0 8px;
            color: #101828;
            font-size: 18px;
        }

        .dashboard-card p {
            color: #667085;
            font-size: 14px;
            line-height: 1.6;
            margin-bottom: 18px;
            min-height: 44px;
        }

        .dashboard-card span, 
        .dashboard-card-link {
            color: #176b4d;
            font-size: 14px;
            font-weight: 600;
        }

        .logout-btn {
            display: inline-block;
            margin-top: 25px;
        }

        /* ================================
           RESPONSIVE DESIGN
           ================================ */

        @media(max-width: 900px) {
            .dashboard-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media(max-width: 600px) {
            .dashboard-grid {
                grid-template-columns: 1fr;
            }
        }

    </style>

</head>

<body>

<%@include file="../includes/navbar.jsp"%>

<section class="dashboard">

    <div class="dashboard-container">

        <!-- WELCOME CARD -->
        <div class="welcome-card">
            <h1>Welcome, <%= userName %>!</h1>
            <p>Manage your projects, review proposals and hire skilled freelancers from one place.</p>
        </div>

        <!-- DASHBOARD CARDS GRID -->
        <div class="dashboard-grid">

            <!-- MY PROFILE -->
            <a href="<%=request.getContextPath()%>/client/profile.jsp" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                </div>
                <h3>My Profile</h3>
                <p>View your client information, reputation, reviews and Trust Score.</p>
                <span class="dashboard-card-link">View Profile →</span>
            </a>

            <!-- POST PROJECT -->
            <a href="post-project.jsp" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="12" y1="5" x2="12" y2="19"></line>
                        <line x1="5" y1="12" x2="19" y2="12"></line>
                    </svg>
                </div>
                <h3>Post a Project</h3>
                <p>Publish a new project and receive proposals from skilled freelancers.</p>
                <span>Post Project →</span>
            </a>

            <!-- MY PROJECTS -->
            <a href="my-projects.jsp" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
                    </svg>
                </div>
                <h3>My Projects</h3>
                <p>View and manage all the projects you have posted.</p>
                <span>View Projects →</span>
            </a>

            <!-- PROPOSALS -->
            <a href="proposals.jsp" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
                        <polyline points="14 2 14 8 20 8"></polyline>
                        <polyline points="9 15 11 17 15 13"></polyline>
                    </svg>
                </div>
                <h3>Proposals</h3>
                <p>Compare freelancer proposals and find the best match for your project.</p>
                <span>View Proposals →</span>
            </a>

            <!-- TRUST SCORE -->
            <a href="<%=request.getContextPath()%>/TrustScoreServlet" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
                    </svg>
                </div>
                <h3>Trust Score</h3>
                <p>View your client reputation, project history and review performance.</p>
                <span class="dashboard-card-link">View Trust Score →</span>
            </a>

            <!-- MESSAGES -->
            <a href="<%=request.getContextPath()%>/MessagesServlet" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
                    </svg>
                </div>
                <h3>Messages</h3>
                <p>Communicate with freelancers and manage your project conversations.</p>
                <span class="dashboard-card-link">Open Messages →</span>
            </a>

            <!-- NOTIFICATIONS -->
            <a href="<%=request.getContextPath()%>/NotificationServlet" class="dashboard-card">
                <div class="card-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
                        <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
                    </svg>
                </div>
                <h3>Notifications</h3>
                <p>Stay updated about proposals, projects, messages and reviews.</p>
                <span class="dashboard-card-link">View Notifications →</span>
            </a>

        </div>

        <!-- LOGOUT BUTTON -->
        <a href="../LogoutServlet" class="btn btn-primary logout-btn">
            Logout
        </a>

    </div>

</section>

<%@include file="../includes/footer.jsp"%>

</body>
</html>