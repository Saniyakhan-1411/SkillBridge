<%@page contentType="text/html"
        pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.skillbridge.model.User"%>
<%@page import="com.skillbridge.model.Portfolio"%>


<%
    User loggedInUser =
            (User) session.getAttribute(
                    "loggedInUser"
            );


    if (loggedInUser == null ||
            !"FREELANCER".equalsIgnoreCase(
                    loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }


    List<Portfolio> portfolioList =
            (List<Portfolio>)
            request.getAttribute(
                    "portfolioList"
            );


    String error =
            request.getParameter("error");

    String success =
            request.getParameter("success");
%>


<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        My Portfolio | SkillBridge
    </title>


    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">


    <style>

        .portfolio-page {

            min-height: 80vh;

            background: #f5faf7;

            padding: 45px 20px;
        }


        .portfolio-container {

            max-width: 1150px;

            margin: auto;
        }


        .portfolio-header {

            display: flex;

            align-items: flex-start;

            justify-content: space-between;

            gap: 20px;

            margin-bottom: 30px;
        }


        .portfolio-label {

            display: inline-block;

            color: #176b4d;

            font-size: 12px;

            font-weight: 700;

            letter-spacing: 1px;

            margin-bottom: 8px;
        }


        .portfolio-header h1 {

            margin: 0 0 8px;

            color: #101828;

            font-size: 32px;
        }


        .portfolio-header p {

            margin: 0;

            color: #667085;

            font-size: 15px;

            line-height: 1.6;
        }


        .portfolio-actions {

            display: flex;

            gap: 10px;

            flex-shrink: 0;
        }


        .portfolio-grid {

            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 22px;
        }


        .portfolio-card {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 16px;

            padding: 25px;

            transition: all .2s ease;
        }


        .portfolio-card:hover {

            transform: translateY(-3px);

            border-color: #176b4d;

            box-shadow:
                0 10px 25px
                rgba(23,32,51,.08);
        }


        .portfolio-card-icon {

            width: 48px;

            height: 48px;

            border-radius: 10px;

            background: #ecfdf3;

            color: #176b4d;

            display: flex;

            align-items: center;

            justify-content: center;

            font-size: 14px;

            font-weight: 800;

            margin-bottom: 18px;
        }


        .portfolio-card h2 {

            margin: 0 0 10px;

            color: #101828;

            font-size: 20px;
        }


        .portfolio-description {

            color: #667085;

            font-size: 14px;

            line-height: 1.7;

            margin: 0 0 18px;
        }


        .technology-label {

            display: block;

            color: #475467;

            font-size: 12px;

            font-weight: 700;

            margin-bottom: 8px;
        }


        .technology-list {

            color: #176b4d;

            background: #f5faf7;

            border-radius: 8px;

            padding: 10px 12px;

            font-size: 13px;

            line-height: 1.6;

            margin-bottom: 20px;
        }


        .portfolio-links {

            display: flex;

            gap: 10px;

            flex-wrap: wrap;
        }


        .portfolio-link {

            display: inline-flex;

            align-items: center;

            justify-content: center;

            padding: 9px 14px;

            border-radius: 7px;

            border: 1px solid #dfe5e8;

            color: #17324d !important;

            text-decoration: none !important;

            font-size: 13px;

            font-weight: 700;

            transition: all .2s ease;
        }


        .portfolio-link:hover {

            border-color: #176b4d;

            color: #176b4d !important;

            background: #f8fffb;
        }


        .empty-portfolio {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 16px;

            padding: 65px 25px;

            text-align: center;
        }


        .empty-portfolio-icon {

            width: 65px;

            height: 65px;

            border-radius: 50%;

            background: #ecfdf3;

            color: #176b4d;

            display: flex;

            align-items: center;

            justify-content: center;

            margin: 0 auto 18px;

            font-size: 13px;

            font-weight: 800;
        }


        .empty-portfolio h2 {

            margin: 0 0 8px;

            color: #101828;
        }


        .empty-portfolio p {

            color: #667085;

            font-size: 14px;

            margin-bottom: 22px;
        }


        .form-panel {

            background: #ffffff;

            border: 1px solid #e7eaf0;

            border-radius: 16px;

            padding: 28px;

            margin-bottom: 28px;
        }


        .form-panel h2 {

            margin: 0 0 6px;

            color: #101828;

            font-size: 21px;
        }


        .form-panel > p {

            margin: 0 0 22px;

            color: #667085;

            font-size: 14px;
        }


        .portfolio-form {

            display: grid;

            grid-template-columns:
                repeat(2, 1fr);

            gap: 18px;
        }


        .form-group {

            display: flex;

            flex-direction: column;

            gap: 7px;
        }


        .form-group.full-width {

            grid-column: 1 / -1;
        }


        .form-group label {

            color: #344054;

            font-size: 13px;

            font-weight: 700;
        }


        .form-group input,

        .form-group textarea {

            width: 100%;

            box-sizing: border-box;

            border: 1px solid #d0d5dd;

            border-radius: 8px;

            padding: 11px 13px;

            font-family: inherit;

            font-size: 14px;

            color: #101828;

            outline: none;

            transition: border .2s ease;
        }


        .form-group textarea {

            min-height: 110px;

            resize: vertical;
        }


        .form-group input:focus,

        .form-group textarea:focus {

            border-color: #176b4d;

            box-shadow:
                0 0 0 3px
                rgba(23,107,77,.08);
        }


        .form-actions {

            grid-column: 1 / -1;

            display: flex;

            justify-content: flex-end;

            gap: 10px;

            margin-top: 5px;
        }


        .alert-success {

            background: #ecfdf3;

            border: 1px solid #abefc6;

            color: #067647;

            padding: 13px 16px;

            border-radius: 9px;

            margin-bottom: 20px;

            font-size: 14px;
        }


        .alert-error {

            background: #fef3f2;

            border: 1px solid #fecdca;

            color: #b42318;

            padding: 13px 16px;

            border-radius: 9px;

            margin-bottom: 20px;

            font-size: 14px;
        }


        @media(max-width: 800px) {

            .portfolio-grid {

                grid-template-columns: 1fr;
            }


            .portfolio-form {

                grid-template-columns: 1fr;
            }


            .form-group.full-width {

                grid-column: auto;
            }
        }


        @media(max-width: 600px) {

            .portfolio-page {

                padding: 30px 15px;
            }


            .portfolio-header {

                flex-direction: column;
            }


            .portfolio-actions {

                width: 100%;
            }


            .portfolio-actions a {

                flex: 1;

                text-align: center;
            }
        }

    </style>

</head>


<body>


<%@include file="../includes/navbar.jsp"%>


<section class="portfolio-page">

    <div class="portfolio-container">


        <!-- =====================================================
             HEADER
             ===================================================== -->

        <div class="portfolio-header">

            <div>

                <span class="portfolio-label">
                    FREELANCER WORKSPACE
                </span>


                <h1>
                    My Portfolio
                </h1>


                <p>
                    Showcase your best work, technical skills
                    and completed projects to potential clients.
                </p>

            </div>


            <div class="portfolio-actions">

                <a href="<%=request.getContextPath()%>/freelancer/dashboard.jsp"
                   class="btn btn-outline">

                    Dashboard

                </a>

            </div>

        </div>


        <!-- =====================================================
             ALERTS
             ===================================================== -->

        <% if ("added".equals(success)) { %>

            <div class="alert-success">

                Portfolio project added successfully.

            </div>

        <% } %>


        <% if ("failed".equals(error)) { %>

            <div class="alert-error">

                Unable to add portfolio project.
                Please try again.

            </div>

        <% } %>


        <!-- =====================================================
             ADD PORTFOLIO FORM
             ===================================================== -->

        <div class="form-panel">

            <h2>
                Add a Portfolio Project
            </h2>


            <p>
                Highlight a project that demonstrates your
                experience and skills.
            </p>


            <form method="post"
                  action="<%=request.getContextPath()%>/AddPortfolioServlet"
                  class="portfolio-form">


                <div class="form-group full-width">

                    <label>
                        Project Title
                    </label>

                    <input type="text"
                           name="projectTitle"
                           maxlength="200"
                           placeholder="Example: E-Commerce Website"
                           required>

                </div>


                <div class="form-group full-width">

                    <label>
                        Project Description
                    </label>

                    <textarea
                        name="projectDescription"
                        maxlength="2000"
                        placeholder="Describe what you built, your role and the outcome."></textarea>

                </div>


                <div class="form-group">

                    <label>
                        Technologies / Skills
                    </label>

                    <input type="text"
                           name="technologies"
                           maxlength="500"
                           placeholder="Java, JSP, JDBC, Derby">

                </div>


                <div class="form-group">

                    <label>
                        Project URL
                    </label>

                    <input type="url"
                           name="projectUrl"
                           maxlength="500"
                           placeholder="https://example.com">

                </div>


                <div class="form-group">

                    <label>
                        GitHub URL
                    </label>

                    <input type="url"
                           name="githubUrl"
                           maxlength="500"
                           placeholder="https://github.com/username/project">

                </div>


                <div class="form-actions">

                    <button type="reset"
                            class="btn btn-outline">

                        Clear

                    </button>


                    <button type="submit"
                            class="btn btn-primary">

                        Add Project

                    </button>

                </div>


            </form>

        </div>


        <!-- =====================================================
             PORTFOLIO PROJECTS
             ===================================================== -->

        <% if (portfolioList == null ||
               portfolioList.isEmpty()) { %>


            <div class="empty-portfolio">


                <div class="empty-portfolio-icon">

                    WORK

                </div>


                <h2>
                    Your Portfolio is Empty
                </h2>


                <p>
                    Add your first project above to start
                    building your professional portfolio.
                </p>


            </div>


        <% } else { %>


            <div class="portfolio-grid">


                <% for (Portfolio portfolio :
                        portfolioList) { %>


                    <div class="portfolio-card">


                        <div class="portfolio-card-icon">

                            PROJECT

                        </div>


                        <h2>

                            <%= portfolio.getProjectTitle() %>

                        </h2>


                        <p class="portfolio-description">

                            <%= portfolio.getProjectDescription()
                                == null ||
                                portfolio.getProjectDescription()
                                         .trim()
                                         .isEmpty()

                                ? "No project description provided."

                                : portfolio.getProjectDescription()
                            %>

                        </p>


                        <% if (portfolio.getTechnologies()
                                != null &&
                            !portfolio.getTechnologies()
                                     .trim()
                                     .isEmpty()) { %>


                            <span class="technology-label">

                                Technologies & Skills

                            </span>


                            <div class="technology-list">

                                <%= portfolio.getTechnologies() %>

                            </div>


                        <% } %>


                        <div class="portfolio-links">


                            <% if (portfolio.getProjectUrl()
                                    != null &&
                                !portfolio.getProjectUrl()
                                     .trim()
                                     .isEmpty()) { %>


                                <a href="<%=portfolio.getProjectUrl()%>"
                                   target="_blank"
                                   rel="noopener"
                                   class="portfolio-link">

                                    Live Project

                                </a>


                            <% } %>


                            <% if (portfolio.getGithubUrl()
                                    != null &&
                                !portfolio.getGithubUrl()
                                     .trim()
                                     .isEmpty()) { %>


                                <a href="<%=portfolio.getGithubUrl()%>"
                                   target="_blank"
                                   rel="noopener"
                                   class="portfolio-link">

                                    GitHub

                                </a>


                            <% } %>


                        </div>


                    </div>


                <% } %>


            </div>


        <% } %>


    </div>

</section>


<%@include file="../includes/footer.jsp"%>


</body>

</html>