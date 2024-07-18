-- Create the t_users table
CREATE TABLE t_users (
                         id BIGSERIAL PRIMARY KEY,
                         account_number VARCHAR(255) NOT NULL,
                         pin VARCHAR(255) NOT NULL,
                         balance DOUBLE PRECISION NOT NULL
);

-- Create the t_permissions table
CREATE TABLE t_permissions (
                               id BIGSERIAL PRIMARY KEY,
                               role VARCHAR(255) NOT NULL
);

-- Create the join table for the many-to-many relationship between users and permissions
CREATE TABLE user_permissions (
                                  user_id BIGINT NOT NULL,
                                  permission_id BIGINT NOT NULL,
                                  CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES t_users(id) ON DELETE CASCADE,
                                  CONSTRAINT fk_permission FOREIGN KEY(permission_id) REFERENCES t_permissions(id) ON DELETE CASCADE,
                                  PRIMARY KEY(user_id, permission_id)
);

-- Create the t_transactions table
CREATE TABLE t_transactions (
                                id BIGSERIAL PRIMARY KEY,
                                type VARCHAR(255) NOT NULL,
                                amount DOUBLE PRECISION NOT NULL,
                                date TIMESTAMP NOT NULL,
                                user_id BIGINT,
                                CONSTRAINT fk_user_transaction FOREIGN KEY(user_id) REFERENCES t_users(id) ON DELETE CASCADE
);
