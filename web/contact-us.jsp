<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>

    <title>Contact Us | SkillBridge</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">

    <style>
        .contact-page {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 25px;
        }

        .contact-header {
            text-align: center;
            margin-bottom: 35px;
        }

        .contact-header h1 {
            color: #176b4d;
            font-size: 34px;
        }

        .contact-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 25px;
        }

        .contact-card {
            background: white;
            border: 1px solid #e5e7eb;
            border-radius: 15px;
            padding: 28px;
            box-shadow: 0 5px 18px rgba(0,0,0,0.05);
        }

        .contact-card h3 {
            color: #176b4d;
            margin-bottom: 12px;
        }

        .contact-card p {
            line-height: 1.7;
        }

        .contact-form {
            margin-top: 30px;
        }

        .contact-form input,
        .contact-form textarea {
            width: 100%;
            padding: 12px;
            margin-top: 7px;
            margin-bottom: 18px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            box-sizing: border-box;
        }

        .contact-form textarea {
            min-height: 130px;
            resize: vertical;
        }

        .contact-btn {
            background: #176b4d;
            color: white;
            border: none;
            padding: 12px 22px;
            border-radius: 8px;
            cursor: pointer;
        }

        @media(max-width: 700px) {
            .contact-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>

</head>

<body>

<div class="contact-page">

    <div class="contact-header">
        <h1>Contact Us</h1>
        <p>
            Have a question or need assistance with SkillBridge
        </p>
    </div>

    <div class="contact-grid">

        <div class="contact-card">
            <h3>SkillBridge Support</h3>

            <p>
                Our support team can help with account access,
                projects, proposals, hiring and platform-related issues.
            </p>

            <p>
                <strong>Email:</strong>
                support@skillbridge.com
            </p>

            <p>
                <strong>Response Time:</strong>
                Within 24–48 hours
            </p>
        </div>

        <div class="contact-card">
            <h3>Platform Assistance</h3>

            <p>
                For questions about finding work, posting projects,
                submitting proposals or managing active projects,
                please contact SkillBridge support.
            </p>

            <p>
                We recommend including your registered email and
                a short description of your issue.
            </p>
        </div>

    </div>

    <div class="contact-card contact-form">

        <h3>Send Us a Message</h3>

        <form action="#" method="post">

            <label>Name</label>
            <input type="text"
                   name="name"
                   placeholder="Enter your name">

            <label>Email</label>
            <input type="email"
                   name="email"
                   placeholder="Enter your email">

            <label>Message</label>
            <textarea name="message"
                      placeholder="Describe your question or issue"></textarea>

            <button type="submit" class="contact-btn">
                Send Message
            </button>

        </form>

    </div>

</div>

</body>
</html>