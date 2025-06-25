-- Create the database
CREATE DATABASE IF NOT EXISTS student_management;

-- Use the database
USE student_management;

-- Create the Students table
CREATE TABLE Students (
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    batch VARCHAR(50),
    age INT,
    dateOfBirth DATE,
    courseList TEXT,
    PRIMARY KEY (email)
);

-- Create the Courses table
CREATE TABLE Courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(10) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    description TEXT,
    credits INT NOT NULL
);

-- Create the Instructors table
CREATE TABLE Instructors (
    instructor_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(10),
    department VARCHAR(50),
    joining_date DATE
);

