ALTER TABLE usuario
ADD COLUMN role VARCHAR(20) DEFAULT 'user' CHECK (role IN ('ADMIN', 'USER'));