# SkillBridge 🚀

> **Smart Freelancer–Client Project Bidding & Trust Platform**

SkillBridge is a web application built using Java EE architecture designed to connect clients with skilled freelancers. It streamlines project posting, proposal submissions, bid analytics, and platform security using custom trust and risk evaluation metrics.

---

## 🛠️ Technology Stack

- **Backend:** Java (Servlets, DAO Pattern, JDBC)
- **Frontend:** JSP, HTML5, CSS3, JavaScript, Bootstrap
- **Database:** Apache Derby
- **Server:** GlassFish Server / Apache Tomcat
- **IDE & Build:** NetBeans IDE, Apache Ant (`build.xml`)

---

## ✨ Features

### 👤 Role-Based Access
- Separate workflows for **Clients** and **Freelancers**.
- Session-managed authentication and security checks.

### 💼 Client Features
- **Project Posting:** Publish projects with categories, budgets, and required skills.
- **Proposal Comparison:** Review, compare, and accept freelancer bids.
- **Trust Evaluation:** View applicant reputation and Trust Scores.

### 👨‍💻 Freelancer Features
- **Project Browsing:** Discover active open listings and submit bids.
- **Skill Match Score:** Automated evaluation matching project skills against profile skills.
- **Bid Health Indicator:** Real-time feedback on proposal competitiveness.

### 🛡️ Platform Security & Tools
- **Project Risk Detector:** Automated heuristics to flag suspicious postings.
- **Messaging & Notifications:** Direct client-freelancer messaging and system updates.
- **Reviews & Ratings:** Mutual feedback loop after project completion.

---

## 📁 Repository Structure

```text
SkillBridge/
├── src/                    # Java Source Files (Servlets, Models, DAOs)
├── web/                    # JSP pages, CSS styles, JS scripts, Assets
├── nbproject/              # NetBeans project configuration
├── build.xml               # Apache Ant build script
└── README.md               # Project documentation
