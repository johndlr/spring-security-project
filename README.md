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

The application was deployed to **Azure App Services** using **GitHub Actions** and for the data base we use **Azure MySQL**.

Some screeenshots of the deployment:

![Screenshot 2024-10-14 153655](https://github.com/user-attachments/assets/415afb64-0a58-4222-9e86-52b888a9719f)

![Screenshot 2024-10-14 153710](https://github.com/user-attachments/assets/e8760854-7256-4280-b39e-71a4ee4ecfce)

![Screenshot 2024-10-14 153749](https://github.com/user-attachments/assets/86cd7e78-e0a8-40da-a3f8-553dafbac38f)

 
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
