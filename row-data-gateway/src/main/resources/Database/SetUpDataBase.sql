-- Follow the order to avoid any set up errors:

-- 1. Create Database
CREATE DATABASE parrot;

-- 2. Create User
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin123';

-- 3. Grant Permissions to new user for the newly created Database
GRANT ALL PRIVILEGES ON parrot.* TO 'admin'@'localhost';
GRANT ALL PRIVILEGES ON * . * TO 'admin'@'localhost';
FLUSH PRIVILEGES;

-- Note: Log into using admin
-- mysql -u admin -p parrot

-- 4. Run the Create Statement for ParrotType.sql

-- 5. Run the Create Statement for OwnedParrot.sql (Run this last because it has a Foreign Key into the other tables)

-- 6. Run insert ParrotType.sql to load species of parrots


