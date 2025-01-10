-- Create the database
CREATE DATABASE IF NOT EXISTS student_management;

-- Use the database
USE student_management;

-- Create the StudentDetails table
CREATE TABLE StudentDetails (
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    batch VARCHAR(50),
    age INT,
    dateOfBirth DATE,
    courseList TEXT,
    PRIMARY KEY (email)
);
