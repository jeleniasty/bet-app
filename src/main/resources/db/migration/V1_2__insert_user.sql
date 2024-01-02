INSERT INTO betapp.role (name)
VALUES ('ADMIN'),
       ('PLAYER');

INSERT INTO betapp.betapp_user (username, password, email, points, created_at)
VALUES ('betapp_admin', '$2a$12$cBACjOpRdwz5ynH8JigVh.GlcxBKL88fKZkLBUmcO1kNYhFjE1hU2', 'betapp.user1@gmail.com',0.00, now());

INSERT INTO betapp.user_role (role, betapp_user)
VALUES (1, 1);