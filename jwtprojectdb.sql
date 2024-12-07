CREATE SCHEMA crudjwtdb;

USE crudjwtdb;

CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE
);

INSERT INTO roles VALUES(1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

