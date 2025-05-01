ALTER TABLE users
ADD COLUMN role VARCHAR(20) CHECK (role IN ('admin', 'user'));
