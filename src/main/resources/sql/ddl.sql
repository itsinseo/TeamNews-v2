CREATE TABLE users (
                       user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       profile_name VARCHAR(255) NOT NULL,
                       introduction VARCHAR(255) NOT NULL
);
