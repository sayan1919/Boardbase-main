BoardbaseListingWebApp

Overview

A Full-Stack Web Application for Managing Board Games and Reviews
BoardbaseListingWebApp is a comprehensive platform that allows users to view, add, and manage board games along with their reviews. While visitors can browse the list of board games and read reviews without logging in, contributing content or making modifications requires authentication. The application distinguishes between two user roles:
	•	Users: Can add new board games and submit reviews.
	•	Managers: Have all user permissions and can also edit or delete reviews.

Technology Stack
	•	Backend: Java, Spring Boot, Spring MVC
	•	Frontend: Thymeleaf, Thymeleaf Fragments, HTML5, CSS, JavaScript, Twitter Bootstrap
	•	Database: H2 Database Engine (In-memory), JDBC
	•	Security: Spring Security for authentication and authorization
	•	Deployment: AWS EC2
	•	Testing: JUnit Framework
	•	Build Tool: Maven

Key Features
	•	User Roles and Permissions:
	•	Non-Members: Can only view board games and their reviews.
	•	Users: Can add board games to the list and write reviews.
	•	Managers: In addition to user permissions, can edit and delete reviews.
	•	Authentication and Authorization:
	•	Secure login using Spring Security with role-based access control.
	•	User-Friendly Interface:
	•	UI built using Thymeleaf templates, enhanced with Twitter Bootstrap for responsive design.
	•	Thymeleaf Fragments used to simplify and reuse common HTML elements like headers, footers, and navigation menus.
	•	Database Management:
	•	CRUD operations for seamless handling of board game and review data.
	•	Customizable schema and initial data loading using schema.sql.
	•	Deployment: Hosted on AWS EC2 for scalability and accessibility.
	•	Testing and Best Practices:
	•	Unit tests implemented with JUnit.
	•	Modular code structure adhering to Spring MVC design principles.

How to Get Started
	1.	Clone the Repository: Download the source code from the repository.
	2.	Open in Your IDE: Use your preferred IDE to open the project.
	3.	Run the Application: Start the application from your IDE or terminal.
	4.	Initial Credentials:
	•	User Role: username: bugs | password: bunny
	•	Manager Role: username: daffy | password: duck
	5.	Create an Account: Sign up as a new user and explore the app’s features with a custom role.

Additional Notes
	•	The application follows best practices for segregating views, controllers, and database interaction layers.
	•	Designed for scalability and easy maintainability, making it a robust solution for managing board game reviews.
	•	Perfect for board game enthusiasts who want to share their opinions or discover new games! 😊