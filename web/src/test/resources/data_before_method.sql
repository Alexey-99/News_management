delete from news;
delete from authors;
delete from comments;
delete from news_tags;

delete from tags;

INSERT INTO authors (name)
VALUES ('Tom'),
       ('Mike');

INSERT INTO news (title, content, authors_id)
VALUES ('newsTitle', 'newsContent', 1);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

INSERT INTO users (id,email, login, password, roles_id)
VALUES (1, '', 'user', '$2a$10$sviC7sXLUk172jdQBa62PuTyRcjm8bfRcv7dp.w3q/SOHfAFAXxI.', 2),
       (2, '', 'user_2', '$2a$10$CLKtO8rvEJYg8/CFd3QQrOmcrgB3YmBT3BjtZvo4Wyh0N3aLYkzWe', 1);