CREATE DATABASE IF NOT EXISTS goals_db;

USE goals_db;

CREATE TABLE IF NOT EXISTS goals (
                                     id BIGINT NOT NULL AUTO_INCREMENT,
                                     user_id BIGINT NOT NULL,
                                     title VARCHAR(50) NOT NULL,
    description VARCHAR(150),
    target_date DATE,
    progress BIGINT NOT NULL DEFAULT 0,
    category VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
    );