<%@page import="com.skillbridge.model.User"%>

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
%>

<%@include file="../includes/header.jsp"%>
<%@include file="../includes/navbar.jsp"%>

<main class="page-container">

    <div class="page-header">

        <div>
            <span class="page-label">
                CLIENT WORKSPACE
            </span>

            <h1>Post a Project</h1>

            <p>
                Tell skilled freelancers what you need
                and receive quality proposals.
            </p>
        </div>

    </div>


    <div class="form-card">

        <%
            String error =
                    (String) request.getAttribute("error");

            if (error != null) {
        %>

            <div class="alert alert-error">
                <%= error %>
            </div>

        <%
            }
        %>


        <form action="<%=request.getContextPath()%>/PostProjectServlet"
              method="post">


            <div class="form-group">

                <label>
                    Project Title
                </label>

                <input type="text"
                       name="title"
                       placeholder="Example: Build a responsive business website"
                       required>

            </div>


            <div class="form-group">

                <label>
                    Project Description
                </label>

                <textarea
                    name="description"
                    rows="7"
                    placeholder="Describe your project, requirements, goals and expected outcome..."
                    required></textarea>

            </div>


            <div class="form-group">

                <label>
                    Required Skills
                </label>

                <input type="text"
                       name="requiredSkills"
                       placeholder="Java, JSP, JDBC, HTML, CSS">

                <small>
                    Separate multiple skills using commas.
                </small>

            </div>


            <div class="form-row">

                <div class="form-group">

                    <label>
                        Minimum Budget (?)
                    </label>

                    <input type="number"
                           name="budgetMin"
                           min="0"
                           step="0.01"
                           placeholder="5000"
                           required>

                </div>


                <div class="form-group">

                    <label>
                        Maximum Budget (?)
                    </label>

                    <input type="number"
                           name="budgetMax"
                           min="0"
                           step="0.01"
                           placeholder="15000"
                           required>

                </div>

            </div>


            <div class="form-group">

                <label>
                    Delivery Deadline
                </label>

                <div class="input-with-suffix">

                    <input type="number"
                           name="deadlineDays"
                           min="1"
                           placeholder="15"
                           required>

                    <span>days</span>

                </div>

            </div>


            <div class="form-actions">

                <a href="dashboard.jsp"
                   class="btn btn-outline">
                    Cancel
                </a>

                <button type="submit"
                        class="btn btn-primary">
                    Publish Project
                </button>

            </div>

        </form>

    </div>

</main>

<%@include file="../includes/footer.jsp"%>