-- Insert sample users
INSERT INTO users (username) VALUES ('john_doe'), ('jane_doe'), ('test_user');

-- Insert sample posts
INSERT INTO posts (title, body, author_id)
VALUES
    ('First Post', 'This is the body of the first post', 1),
    ('Second Post', 'This is the body of the second post', 2);

-- Insert likes
INSERT INTO likes (user_id, post_id)
VALUES (1, 2), (2, 1);

-- Insert followers
INSERT INTO user_follow (follower_id, followee_id)
VALUES (1, 2), (2, 1);