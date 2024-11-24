CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS posts
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    author_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS likes
(
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    post_id BIGINT NOT NULL REFERENCES posts(id)
);

CREATE TABLE IF NOT EXISTS user_follow
(
    follower_id BIGINT NOT NULL REFERENCES users(id),
    followee_id BIGINT NOT NULL REFERENCES users(id),
    PRIMARY KEY (follower_id, followee_id)
);