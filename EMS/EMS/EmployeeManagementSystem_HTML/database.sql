CREATE DATABASE employee_db;
USE employee_db;

CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    salary DOUBLE NOT NULL
);

INSERT INTO employees(name, email, department, salary)
VALUES
('Ravi Kumar', 'ravi@gmail.com', 'IT', 45000),
('Anitha Reddy', 'anitha@gmail.com', 'HR', 40000);
