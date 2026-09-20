<%@page import="java.util.List"%>
<%@page import="com.skillbridge.model.User"%>
<%@page import="com.skillbridge.model.Project"%>
<%@page import="com.skillbridge.dao.ProjectDAO"%>

<%
    User loggedInUser =
            (User) session.getAttribute("loggedInUser");

    if (loggedInUser == null ||
            !"CLIENT".equals(loggedInUser.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/login.jsp"
        );

        return;
    }

    ProjectDAO projectDAO =
            new ProjectDAO();

    List<Project> projects =
            projectDAO.getProjectsByClient(
                    loggedInUser.getUserId()
            );

    String created =
            request.getParameter("created");
%>

<%@include file="../includes/header.jsp"%>
<%@include file="../includes/navbar.jsp"%>

<main class="page-container">

    <div class="page-header page-header-flex">

        <div>

            <span class="page-label">
                CLIENT WORKSPACE
            </span>

            <h1>My Projects</h1>

            <p>
                Manage the projects you have posted.
            </p>

        </div>

        <a href="post-project.jsp"
           class="btn btn-primary">
            + Post Project
        </a>

    </div>


    <% if ("true".equals(created)) { %>

        <div class="alert alert-success">
            Your project has been published successfully.
        </div>

    <% } %>


    <% if (projects.isEmpty()) { %>

        <div class="empty-state">

            <div class="empty-icon">
                +
            </div>

            <h2>No projects yet</h2>

            <p>
                Post your first project and start receiving
                proposals from freelancers.
            </p>

            <a href="post-project.jsp"
               class="btn btn-primary">
                Post Your First Project
            </a>

        </div>

    <% } else { %>

        <div class="project-grid">

            <% for (Project project : projects) { %>

                <div class="project-card">

                    <div class="project-card-top">

                        <span class="status-badge">
                            <%= project.getProjectStatus() %>
                        </span>

                    </div>

                    <h2>
                        <%= project.getTitle() %>
                    </h2>

                    <p class="project-description">

                        <%= project.getDescription() %>

                    </p>

                    <div class="project-meta">

                        <div>
                            <span>Budget</span>

                            <strong>
                                <%= String.format(
                                    "%.0f",
                                    project.getBudgetMin()
                                ) %>
                                -
                                <%= String.format(
                                    "%.0f",
                                    project.getBudgetMax()
                                ) %>
                            </strong>
                        </div>

                        <div>
                            <span>Deadline</span>

                            <strong>
                                <%= project.getDeadlineDays() %>
                                days
                            </strong>
                        </div>

                    </div>

                    <div class="project-skills">

                        <strong>Skills:</strong>

                        <%= project.getRequiredSkills() %>

                    </div>

                    <div class="project-card-actions">

                        <a href="<%=request.getContextPath()%>/ProjectDetailsServlet?projectId=<%=project.getProjectId()%>"
   class="btn btn-primary">
    View Project
</a>
                       <a href="<%=request.getContextPath()%>/ClientProposalsServlet?projectId=<%=project.getProjectId()%>"
   class="btn btn-primary">
    View Proposals
</a>
                    </div>

                </div>

            <% } %>

        </div>

    <% } %>

</main>

<%@include file="../includes/footer.jsp"%>