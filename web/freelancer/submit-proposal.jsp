<%@page import="com.skillbridge.model.Project"%>
<%@page import="com.skillbridge.model.User"%>

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

    Project project =
            (Project) request.getAttribute("project");

    String error =
            (String) request.getAttribute("error");

    if (project == null) {

        response.sendRedirect(
                request.getContextPath()
                + "/BrowseProjectsServlet"
        );

        return;
    }
%>

<%@include file="../includes/header.jsp"%>
<%@include file="../includes/navbar.jsp"%>

<style>

    .proposal-wrapper {
        max-width: 900px;
        margin: 45px auto;
        padding: 0 20px;
    }

    .proposal-card {
        background: white;
        border: 1px solid #e5e7eb;
        border-radius: 15px;
        padding: 35px;
        box-shadow: 0 8px 25px rgba(0,0,0,0.05);
    }

    .page-title {
        font-size: 30px;
        font-weight: 700;
        color: #1f2937;
        margin-bottom: 8px;
    }

    .page-subtitle {
        color: #6b7280;
        margin-bottom: 25px;
    }

    .project-summary {
        background: #f8fafc;
        border: 1px solid #e5e7eb;
        border-radius: 12px;
        padding: 20px;
        margin-bottom: 25px;
    }

    .project-summary h3 {
        margin: 0 0 12px;
        color: #1f2937;
    }

    .summary-row {
        display: flex;
        justify-content: space-between;
        padding: 8px 0;
        color: #4b5563;
    }

    .summary-value {
        font-weight: 700;
        color: #1f2937;
    }

    .form-group {
        margin-bottom: 22px;
    }

    .form-group label {
        display: block;
        font-weight: 600;
        color: #374151;
        margin-bottom: 8px;
    }

    .form-control {
        width: 100%;
        box-sizing: border-box;
        padding: 12px 14px;
        border: 1px solid #d1d5db;
        border-radius: 8px;
        font-size: 15px;
        outline: none;
    }

    .form-control:focus {
        border-color: #198754;
    }

    textarea.form-control {
        min-height: 170px;
        resize: vertical;
    }

    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 20px;
    }

    .help-text {
        display: block;
        margin-top: 6px;
        font-size: 12px;
        color: #6b7280;
    }

    .error-box {
        background: #fef2f2;
        border: 1px solid #fecaca;
        color: #b91c1c;
        padding: 14px;
        border-radius: 8px;
        margin-bottom: 20px;
    }

    .action-row {
        display: flex;
        gap: 12px;
        margin-top: 25px;
    }

    .btn-submit {
        background: #198754;
        color: white;
        border: none;
        padding: 13px 24px;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
    }

    .btn-submit:hover {
        background: #157347;
    }

    .btn-cancel {
        background: white;
        color: #374151;
        border: 1px solid #d1d5db;
        padding: 13px 24px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: 600;
    }

    @media(max-width: 700px) {

        .form-row {
            grid-template-columns: 1fr;
        }

        .proposal-card {
            padding: 22px;
        }

    }

</style>


<div class="proposal-wrapper">

    <div class="proposal-card">

        <div class="page-title">
            Submit Your Proposal
        </div>

        <div class="page-subtitle">
            Send a professional proposal to the client.
        </div>


        <% if (error != null) { %>

            <div class="error-box">
                <%=error%>
            </div>

        <% } %>


        <div class="project-summary">

            <h3>
                <%=project.getTitle()%>
            </h3>

            <div class="summary-row">

                <span>
                    Budget
                </span>

                <span class="summary-value">

                    ?<%=String.format(
                        "%.2f",
                        project.getBudgetMin()
                    )%>

                    -

                    ?<%=String.format(
                        "%.2f",
                        project.getBudgetMax()
                    )%>

                </span>

            </div>


            <div class="summary-row">

                <span>
                    Deadline
                </span>

                <span class="summary-value">

                    <%=project.getDeadlineDays()%> Days

                </span>

            </div>


            <div class="summary-row">

                <span>
                    Required Skills
                </span>

                <span class="summary-value">

                    <%=project.getRequiredSkills()%>

                </span>

            </div>

        </div>


        <form method="post"
              action="<%=request.getContextPath()%>/SubmitProposalServlet">

            <input type="hidden"
                   name="projectId"
                   value="<%=project.getProjectId()%>">


            <div class="form-group">

                <label>
                    Cover Letter *
                </label>

                <textarea
                    name="coverLetter"
                    class="form-control"
                    placeholder="Explain why you are the right freelancer for this project..."
                    required></textarea>

                <span class="help-text">
                    Introduce your experience, approach and why you can complete the project successfully.
                </span>

            </div>


            <div class="form-row">

                <div class="form-group">

                    <label>
                        Your Bid Amount (?) *
                    </label>

                    <input
                        type="number"
                        name="bidAmount"
                        class="form-control"
                        min="1"
                        step="0.01"
                        placeholder="15000"
                        required>

                    <span class="help-text">
                        Enter your proposed project price.
                    </span>

                </div>


                <div class="form-group">

                    <label>
                        Delivery Time (Days) *
                    </label>

                    <input
                        type="number"
                        name="deliveryDays"
                        class="form-control"
                        min="1"
                        placeholder="10"
                        required>

                    <span class="help-text">
                        How many days will you need?
                    </span>

                </div>

            </div>


            <div class="action-row">

                <button type="submit"
                        class="btn-submit">

                    Submit Proposal

                </button>


                <a href="<%=request.getContextPath()%>/FreelancerProjectDetailsServlet?projectId=<%=project.getProjectId()%>"
                   class="btn-cancel">

                    Cancel

                </a>

            </div>

        </form>

    </div>

</div>


<%@include file="../includes/footer.jsp"%>