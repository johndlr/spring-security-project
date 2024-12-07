# CRUD Application Using Spring Security and JWT

This project is a CRUD application built with `Spring Boot`, secured with `Spring Security` and `JWT` authentication. It demonstrates creating, reading, updating, and deleting resources while ensuring secure access control with token-based authentication. This setup allows users to perform CRUD operations only when authenticated, providing a foundation for secure RESTful applications. 

## Features
Two endpoints were created for user registration and application login. The latter generates a JWT token that allows access, depending on the user's role, to other endpoints that are protected and require authentication. The rest of the endpoints are made up of different **CRUD operations**. The database schema consists of two tables, one related to the Users and the other to their roles. It was chosen as a business rule that a user can only have one role. For the roles table, the nomenclature recommended by spring security was used, using the prefix **ROLE_**. There are two roles: **USER** and **ADMIN**. The **USER** role allows access to the endpoints to fetch user information, while the **ADMIN** role has **USER** permissions and can edit users and delete them from the database.

Below are some screenshots:

**Documentation made using OpenApi and Swagger**

![Captura de pantalla 2024-10-27 134849](https://github.com/user-attachments/assets/cc2ce0dc-f156-4d14-b7d4-dca426b9bfac)

![Captura de pantalla 2024-10-27 134915](https://github.com/user-attachments/assets/cc5e9915-866c-4c89-99d9-6c2d8d8b5ea7)

![Captura de pantalla 2024-10-27 134921](https://github.com/user-attachments/assets/11486031-514a-4a27-b6ce-cb65d10391df)

**Database ER Diagram**

![Captura de pantalla 2024-10-27 134921](https://github.com/user-attachments/assets/8263cb35-a338-45d4-a43a-b27dfc281f6c)

**JWT generated after a successful login**

![Captura de pantalla 2024-10-27 134921](https://github.com/user-attachments/assets/abad436a-59dc-4a04-979a-82498c805d1f)


## JwtAuthFilter

When an `HTTP` request reaches the application, `Spring Security` passes it through a chain of filters. Each filter in the chain has the opportunity to inspect and/or modify the request and response. The `JwtAuthFilter` is responsible for validating the `token` that accompanies the request. Its role in the application's filter chain is crucial, as it determines if a request requires authentication or has the necessary permissions.

There are two workflows to follow: when the request contains a `token` and when it does not. These workflows are described below.

First, the following actions are always executed in both workflows:

### Common Actions in Both Workflows

#### Request Interception

The `JwtAuthFilter` intercepts each `HTTP` request before it reaches the `UsernamePasswordAuthenticationFilter`. It is configured to execute only once per request because `JwtAuthFilter` extends `OncePerRequestFilter`, a Spring class that ensures the filter is executed once per request.

![Filter Chain](https://github.com/user-attachments/assets/35d312ba-aaf2-41cb-965b-a2363fb7d49d)

**Header Verification Code**

![Captura de pantalla 2024-10-27 113157](https://github.com/user-attachments/assets/4596ac0c-bfc1-4037-aed8-48fb2d88a412)

1.  **Authorization Header Verification**:
    
    -   The condition `!StringUtils.hasText(authorizationHeader)` checks if the authorization header (`Authorization`) is present and contains text. `StringUtils.hasText` is a Spring utility method that returns `true` if the string is not null, is not empty, and contains at least one non-whitespace character.
2.  **Continuing the Filter Chain**:
    
    -   If the authorization header is absent or empty, the filter calls `filterChain.doFilter(request, response)` and immediately returns. This allows the request to continue through the filter chain without attempting to authenticate it with a JWT token.

By checking if the authorization header is present and continuing the filter chain if it is not, the `JwtAuthFilter` avoids trying to authenticate requests that do not have a JWT token. This is especially important for the `signUp` and `login` endpoints, where requests are not expected to include a JWT token.

### Request with Token

#### JWT Token Extraction and Validation:

1.  **Request Interception**:
    
    -   The `JwtAuthFilter` intercepts each HTTP request before it reaches the `UsernamePasswordAuthenticationFilter`.
2.  **JWT Token Extraction and Validation**:
    
    -   The filter checks if the authorization header (`Authorization`) starts with "Bearer ".
    -   If so, it extracts the JWT token from the header.
    -   It validates the JWT token using the `jwtTokenValidator` method from the `JwtService`.
3.  **User Authentication**:
    
    -   If the token is valid, the filter extracts the username from the token using the `getUserNameFromToken` method from the `JwtService`.
    -   It loads the user details (`UserDetails`) from the `UserDetailsService`.
    -   It creates a `UsernamePasswordAuthenticationToken` object with the user details and their authorities.
    -   It sets this `Authentication` object in the `SecurityContextHolder`.
4.  **Continuing the Filter Chain**:
    
    -   The filter calls `filterChain.doFilter(request, response)` to continue with the filter chain.

### Request without Token

1.  **Continuing the Filter Chain**:
    
    -   The `JwtAuthFilter` calls `filterChain.doFilter(request, response)` and returns, allowing the request to proceed through the filter chain.
2.  **Username and Password-Based Authentication**:
    
    -   The request reaches the `UsernamePasswordAuthenticationFilter`, which handles username and password-based authentication for the `login` endpoint.

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
