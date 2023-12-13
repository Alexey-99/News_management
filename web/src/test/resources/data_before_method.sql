delete from comments;
delete from news_tags;
delete from news;
delete from tags;
delete from authors;

INSERT INTO authors (name)
VALUES ('Tom'),
       ('Mike');

INSERT INTO tags (name)
VALUES ('tag_name');

INSERT INTO news (title, content, authors_id)
VALUES ('newsTitle', 'newsContent', 1);

INSERT INTO news_tags (tags_id, news_id)
VALUES (1, 1);

INSERT INTO comments (content, news_id)
VALUES ('comment_content', 1);


INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO users (id,email, login, password, roles_id)
VALUES (1, '', 'user', '$2a$10$sviC7sXLUk172jdQBa62PuTyRcjm8bfRcv7dp.w3q/SOHfAFAXxI.', 2),
       (2, '', 'user_2', '$2a$10$CLKtO8rvEJYg8/CFd3QQrOmcrgB3YmBT3BjtZvo4Wyh0N3aLYkzWe', 1);