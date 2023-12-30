INSERT INTO users (username, password, enable, name, lastname, email) VALUES ('alexis', '$2a$10$7wlhy2GkRKLXBjt03QQt/OcsdODqClgK84wb7yLVbHdL7mwdC3QIK', 1, 'Alexis', 'Palma', 'maberin@hotmail.com');
INSERT INTO users (username, password, enable, name, lastname, email) VALUES ('andres', '$2a$10$NInrx5KD/nmtd/h4X5li5esnoCIvNVX.0.X8ugSaexVJF/DBUZY/q', 1, 'Andres', 'Guzman', 'andres@hotmail.com');


INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1,1);
INSERT INTO users_roles (user_id, role_id) VALUES (2,1);
INSERT INTO users_roles (user_id, role_id) VALUES (2,2);