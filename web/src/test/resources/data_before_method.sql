delete from comments;
delete from news_tags;
delete from news;
delete from tags;
delete from authors;
delete from users;
delete from roles;

INSERT INTO authors (name)
VALUES ('Tom'),
       ('Mike');

INSERT INTO tags (name)
VALUES ('tag_name'), ('tag_action');

INSERT INTO news (title, content, authors_id)
VALUES ('newsTitle', 'newsContent', 1),
       ('newsTitle_2', 'newsContent_2', 1);

INSERT INTO news_tags (tags_id, news_id)
VALUES (1, 1);

INSERT INTO comments (content, news_id)
VALUES ('comment_content', 1);


INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO users ( login, password, roles_id)
VALUES ( 'user', '$2a$10$sviC7sXLUk172jdQBa62PuTyRcjm8bfRcv7dp.w3q/SOHfAFAXxI.', 2),
       ( 'user_2', '$2a$10$CLKtO8rvEJYg8/CFd3QQrOmcrgB3YmBT3BjtZvo4Wyh0N3aLYkzWe', 1);