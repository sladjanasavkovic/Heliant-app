-- Seeds for users table
INSERT INTO users (username, password, date_created, last_updated, role)
VALUES ('user1', '$2a$12$wwURM4C3SSEuagrfdarLNek2jUQMF7AMGo3Tlp1VWqN91YR2hQCbm', '2024-03-09 00:00:00', '2024-03-09 00:00:00', 'ADMIN'),
       ('user2', '$2a$12$1CNrbCAew1XJlZXEZAawVu.A7XpiwW7n.jJQW.7lMW8flX.xSRz/a', '2024-03-10 00:00:00', '2024-03-09 00:00:00', 'EMPLOYEE');
