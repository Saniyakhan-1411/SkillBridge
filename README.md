# SkillBridge

### Freelancer–Client Project Bidding Platform

SkillBridge is an Enterprise Java web application that connects **clients** with **freelancers** through project posting, browsing, and bidding workflows.

The platform is designed around a professional freelancing experience, with separate workflows for clients and freelancers and a structured backend for managing users, projects, and bids.

---

## 🚀 Project Overview

Finding suitable freelance talent and managing project opportunities can be difficult when communication, project requirements, and bidding are handled across different platforms.

**SkillBridge** provides a centralized web platform where:

* Clients can create and manage project opportunities.
* Freelancers can discover available projects.
* Freelancers can submit bids for suitable projects.
* Users can register and log in to their accounts.
* Different user roles receive different dashboard experiences.
* Project and bidding information can be managed through the application backend.

The project is developed as an **Enterprise Java web application** and is designed to run with **NetBeans IDE 8.2** and **GlassFish/Payara-compatible Java EE infrastructure**.

---

## ✨ Key Features

### 👤 User Management

* User registration
* User login
* Session management
* Role-based access
* Client and Freelancer accounts
* Profile information

### 💼 Client Features

* Client dashboard
* Create projects
* Manage posted projects
* View freelancer bids
* Review project-related information

### 🧑‍💻 Freelancer Features

* Freelancer dashboard
* Browse available projects
* View project details
* Submit project bids
* Manage bidding activity

### 📋 Project Management

* Project listing
* Project details
* Project requirements
* Project budget information
* Project status management

### 🤝 Bidding Workflow

```text
Client
  ↓
Create Project
  ↓
Project Published
  ↓
Freelancer Browses Project
  ↓
Freelancer Submits Bid
  ↓
Client Reviews Bid
```

---

## 🏗️ Application Architecture

SkillBridge follows a structured Enterprise Java architecture:

```text
                    SkillBridge
                        │
              ┌─────────┴─────────┐
              │                   │
           Client             Freelancer
              │                   │
              └─────────┬─────────┘
                        │
                   JSP Interface
                        │
                  Servlets / Controllers
                        │
                     DAO Layer
                        │
                    Model Layer
                        │
                    Database
```

### Main Layers

| Layer              | Responsibility                        |
| ------------------ | ------------------------------------- |
| JSP                | User interface and web pages          |
| Servlet            | Request handling and application flow |
| DAO                | Database operations                   |
| Model              | Application data objects              |
| Database           | Persistent application data           |
| Application Server | Runs the Java web application         |

---

## 🛠️ Technology Stack

### Backend

* Java
* Java Servlets
* JDBC
* DAO Pattern
* Session Management

### Frontend

* JSP
* HTML5
* CSS3
* JavaScript

### Database

* Apache Derby

### Development Environment

* NetBeans IDE 8.2
* JDK 8
* Enterprise Java Web Application

### Application Server

* GlassFish / Payara Server

### Deployment

* Docker
* WAR deployment

---

## 📁 Project Structure

```text
SkillBridge/
│
├── build/
│
├── dist/
│   └── SkillBridge.war
│
├── nbproject/
│
├── src/
│   └── java/
│       └── com/
│           └── skillbridge/
│               ├── controller/
│               ├── dao/
│               └── model/
│
├── test/
│
├── web/
│   ├── index.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── browse-projects.jsp
│   ├── how-it-works.jsp
│   ├── about.jsp
│   │
│   ├── client/
│   │   └── dashboard.jsp
│   │
│   └── freelancer/
│       └── dashboard.jsp
│
├── build.xml
├── Dockerfile
└── README.md
```

---

## 🔐 User Roles

SkillBridge supports role-based application flows.

### Client

Clients can:

* Register an account
* Log in
* Access their dashboard
* Create projects
* Manage project information
* Review freelancer bids

### Freelancer

Freelancers can:

* Register an account
* Log in
* Access their dashboard
* Browse projects
* View project requirements
* Submit bids

---

## 🔄 Application Flow

### Registration

```text
User
 ↓
Registration Page
 ↓
Enter Account Information
 ↓
Validation
 ↓
DAO
 ↓
Database
 ↓
Account Created
```

### Login

```text
User
 ↓
Login Page
 ↓
Email + Password
 ↓
LoginServlet
 ↓
UserDAO
 ↓
Database
 ↓
Session Created
 ↓
Role-Based Dashboard
```

### Project Bidding

```text
Client
 ↓
Create Project
 ↓
Project Stored
 ↓
Freelancer
 ↓
Browse Projects
 ↓
Select Project
 ↓
Submit Bid
 ↓
Client Reviews Bid
```

---

## 🐳 Docker Deployment

SkillBridge can also be packaged as a WAR file and run inside a containerized Java application server.

The project contains:

```text
Dockerfile
dist/
└── SkillBridge.war
```

The Docker image uses a Java EE-compatible Payara Server environment for the current `javax.servlet`-based application.

### Build the Docker image

```bash
docker build -t skillbridge .
```

### Run the application

```bash
docker run -d --name skillbridge-container -p 8081:8080 skillbridge
```

### Open locally

```text
http://localhost:8081/SkillBridge
```

To check the running container:

```bash
docker ps
```

To view application-server logs:

```bash
docker logs skillbridge-container
```

---

## 💻 Running the Project Locally

### Prerequisites

Install:

* JDK 8
* NetBeans IDE 8.2
* GlassFish or compatible Java EE application server
* Apache Derby
* Git
* Docker Desktop, if using container deployment

### Database

The application uses Apache Derby through JDBC.

The development environment uses a Derby connection similar to:

```text
jdbc:derby://localhost:1527/skillbridge_db
```

Make sure the Derby database/server is running before testing database-dependent features locally.

### Run with NetBeans

1. Open NetBeans IDE 8.2.
2. Select **File → Open Project**.
3. Select the `SkillBridge` project folder.
4. Configure the Java EE application server.
5. Start the Derby database.
6. Build the project.
7. Run the project.
8. Open the application in the browser.

---

## 📸 Application Pages

The project includes the following major pages:

* Home
* Login
* Registration
* Browse Projects
* How It Works
* About
* Client Dashboard
* Freelancer Dashboard

Screenshots can be added here as the project UI is finalized.

Example:

```text
docs/
└── screenshots/
    ├── home.png
    ├── login.png
    ├── register.png
    ├── client-dashboard.png
    └── freelancer-dashboard.png
```

---

## 🎯 Project Objectives

SkillBridge was developed to demonstrate how an Enterprise Java application can support a complete freelancer-client workflow.

The main objectives are:

* Build a structured freelancing platform.
* Implement role-based user workflows.
* Apply MVC-style separation between UI, controllers, models, and database operations.
* Implement JDBC-based database connectivity.
* Manage user sessions securely.
* Provide project and bidding workflows.
* Package and deploy the application as a WAR.
* Explore containerized deployment using Docker.

---

## 🔒 Security Considerations

The application includes basic account and session management.

For a production deployment, additional security improvements should be implemented, including:

* Password hashing with a modern password-hashing algorithm
* Strong input validation
* CSRF protection
* Secure session configuration
* HTTPS
* Database credentials through environment variables
* Production database configuration
* Server-side authorization checks
* Error handling that does not expose internal details

---

## 🚧 Current Development Status

### Implemented

* Enterprise Java web application structure
* JSP-based interface
* Client and Freelancer roles
* Registration workflow
* Login workflow
* Session handling
* Project-related pages
* Bidding workflow structure
* WAR generation
* Docker containerization

### Deployment Work

The project is being prepared for public hosting using:

```text
GitHub
   ↓
Cloud Hosting
   ↓
Docker
   ↓
Payara Server
   ↓
SkillBridge.war
```

Public hosting and online database configuration should be treated separately from the local Docker setup.

---

## 🔮 Future Enhancements

Potential future improvements include:

* Advanced freelancer search
* Project category filtering
* Bid comparison
* Client-freelancer messaging
* Notifications
* File attachments
* Reviews and ratings
* Payment integration
* Admin dashboard
* Advanced project analytics
* Email notifications
* Production-grade authentication
* Cloud database migration

---

## 📚 Concepts Demonstrated

This project demonstrates practical use of:

* Enterprise Java
* JSP
* Servlets
* JDBC
* DAO Pattern
* MVC-style application structure
* HTTP request/response handling
* Session management
* Role-based access
* CRUD operations
* Database connectivity
* WAR packaging
* Application-server deployment
* Docker containerization

---

## 👩‍💻 Author

**Saniya Khan**

BSc IT Student
Building real-world software solutions with Java, web technologies, databases, and modern development tools.

---

## 📄 License

This project is intended for educational and academic purposes.

If you plan to publish the project for public use, add an appropriate open-source license such as MIT after confirming that the project's dependencies and included assets allow it.
