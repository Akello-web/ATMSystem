INSERT INTO t_permissions (role) VALUES ('ROLE_USER');
INSERT INTO t_permissions (role) VALUES ('ROLE_ADMIN');
--password is 1111
INSERT INTO t_users (account_number, pin, balance)
VALUES ('KZ01', '$2a$12$5KNr59rRnDO.EjUKdJZpzur89NIkFhO98TC5PBqkUbL10Q4Tqys8q', 1000.0);
--

-- Insert the relationship into the user_permissions join table
INSERT INTO user_permissions (user_id, permission_id)
VALUES (
           (SELECT id FROM t_users WHERE account_number = 'KZ01'),
           (SELECT id FROM t_permissions WHERE role = 'ROLE_ADMIN')
       );
