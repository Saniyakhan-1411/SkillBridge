<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String contextPath = request.getContextPath();
%>

<jsp:include page="includes/header.jsp" />

<jsp:include page="includes/navbar.jsp" />

<main class="content-page">

    <section class="page-hero">
        <div class="page-hero-content">

            <span class="page-label">FIND WORK</span>

            <h1>Find Projects That Match Your Skills</h1>

            <p>
                Discover freelance opportunities, explore project requirements,
                and submit proposals that match your expertise.
            </p>

        </div>
    </section>

    <section class="content-section">

        <div class="section-heading">
            <span class="section-label">POPULAR OPPORTUNITIES</span>

            <h2>Explore Freelance Projects</h2>

            <p>
                Browse projects across different skills and industries.
            </p>
        </div>

        <div class="project-grid">

            <div class="project-card">
                <h3>Web Application Development</h3>

                <p>
                    Build a modern responsive web application for a growing
                    business.
                </p>

                <div class="project-meta">
                    <span>Java</span>
                    <span>JSP</span>
                    <span>MySQL</span>
                </div>

                <div class="project-bottom">
                    <strong>₹25,000 - ₹40,000</strong>
                    <span>15 Days</span>
                </div>
            </div>


            <div class="project-card">
                <h3>UI/UX Design</h3>

                <p>
                    Design a professional user experience for a digital
                    product.
                </p>

                <div class="project-meta">
                    <span>Figma</span>
                    <span>UI Design</span>
                    <span>UX</span>
                </div>

                <div class="project-bottom">
                    <strong>₹15,000 - ₹30,000</strong>
                    <span>10 Days</span>
                </div>
            </div>


            <div class="project-card">
                <h3>Mobile App Development</h3>

                <p>
                    Develop a feature-rich mobile application for Android
                    users.
                </p>

                <div class="project-meta">
                    <span>Android</span>
                    <span>Java</span>
                    <span>Firebase</span>
                </div>

                <div class="project-bottom">
                    <strong>₹30,000 - ₹60,000</strong>
                    <span>25 Days</span>
                </div>
            </div>

        </div>

    </section>

</main>

<jsp:include page="includes/footer.jsp" />