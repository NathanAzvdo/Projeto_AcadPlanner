CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO usuario (nome, email, senha, role)
VALUES ('Admin', 'admin@admin.com',
        crypt('admin123', gen_salt('bf', 10)),
        'ADMIN');