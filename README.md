# Spring Security and JWT

A crud stateless application was developed, using Spring Security to handle authentication and role-based authorization. In addition, the concept of a JWT token was included as a means of authentication through the different endpoints of the application. Spring Security was integrated with JJWT to implement a token-based authentication system with role-based access control. An extra branch azure-deploy is added that represents the application that was deployed to **Azure**.

## Features
#### Two endpoints were created for user registration and login to the application. The latter generates a JWT token that allows access, according to the user's role, to other endpoints that are protected and require authentication
#### Below are the endpoints and a screenshot of the token generated after successful registration.
<div><img src="https://github.com/user-attachments/assets/0b448dc6-4b08-439d-80ac-c62b45f86f65" alt="project-screenshot"></div>
<br/>
<div><img src="https://github.com/user-attachments/assets/ebc064bd-72c9-4457-89d9-2724410ade30" alt="project-screenshot"></div>
<br/>
<div><img src="https://github.com/user-attachments/assets/abad436a-59dc-4a04-979a-82498c805d1f" alt="project-screenshot"></div>
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
*   MySQL
*   Swagger
