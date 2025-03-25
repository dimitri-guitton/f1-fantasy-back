ALTER TABLE users
    ADD token VARCHAR(255);

ALTER TABLE users
    ADD CONSTRAINT uc_users_token UNIQUE (token);