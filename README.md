<h1 align="center" id="title">Spring Security and JWT 🪪</h1>

<p id="description">A basic stateless application was developed, using Spring Security to handle authentication and role-based authorization. In addition, the concept of a JWT token was included as a means of authentication through the different endpoints of the application.</p>

An extra branch azure-deploy is added that represents the application that was deployed to **Azure**.

<h2>🧐 Features</h2>
<div><img src="https://github.com/user-attachments/assets/0b448dc6-4b08-439d-80ac-c62b45f86f65" alt="project-screenshot"></div>
<br/>
<h3>Here're some of the project's best features:</h3>
<h4>Two endpoints were created to signup and login in the application. In case of a successful login the jwt token is received 🔐</h4>
<div><img src="https://github.com/user-attachments/assets/161b5dfa-1f95-4c23-8f0b-57e8d24b5bce" alt="project-screenshot"></div>
<br/>
<div><img src="https://github.com/user-attachments/assets/abad436a-59dc-4a04-979a-82498c805d1f" alt="project-screenshot"></div>

<h4>Other endpoints to test the role system.</h4>
<div><img src="https://github.com/user-attachments/assets/be4c0908-3ebf-45e1-8cd3-fcdd85543064"></div>

<h4>Database schema.</h4>
<div><img src="https://github.com/user-attachments/assets/8263cb35-a338-45d4-a43a-b27dfc281f6c"></div>
<p>For the roles table, the nomenclature recommended by spring security was used, using the prefix ROLE_</p>

### Deployment to Azure App Services

The application was deployed to **Azure App Services** using **Docker Compose File**, and some changes were made to the application, specifically the implementation of JIB library and Docker. The images were deployed to Docker Hub to be used by Azure App Service along with the Docker Compose file, which declares two services: first the MySQL database and second the application.

Below are some screenshots of the implementation in Azure:

![Screenshot 2024-10-11 154716](https://github.com/user-attachments/assets/287cdf42-fd43-47d5-878a-0742c90785d1)

Log from Azure App Service indicating a successful pull from docker hub:

![Screenshot 2024-10-11 154859](https://github.com/user-attachments/assets/01486808-245d-498d-bf2c-0cf959febb2a)

 
<h2>💻 Built with</h2>

Technologies used in the project:

*   Spring Boot
*   Spring MVC
*   Spring Data
*   Lombok
*   H2 Data Base
*   JUnit/AssertJ
*   Mockito
*   Postman
*   JJWT
*   Spring Security
*   Azure
*   Docker
