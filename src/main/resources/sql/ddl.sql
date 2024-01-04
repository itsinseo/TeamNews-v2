CREATE TABLE users
(
    user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    profile_name VARCHAR(255) NOT NULL,
    introduction VARCHAR(255) NOT NULL
);

CREATE TABLE posts
(
    post_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255),
    content     VARCHAR(500) NOT NULL,
    user_id     BIGINT,
    created_at  TIMESTAMP    NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    comment_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    body        VARCHAR(255),
    post_id     BIGINT,
    user_id     BIGINT,
    created_at  TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts (post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE likes
(
    like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES posts (post_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);