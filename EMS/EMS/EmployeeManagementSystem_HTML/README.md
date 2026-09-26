

This version uses:
- HTML + CSS + JavaScript
- Java built-in `com.sun.net.httpserver.HttpServer`
- JDBC
- MySQL



## Folder structure
```
EmployeeManagementSystem
├── src
│   └── main
│       ├── java
│       │   ├── MainServer.java
│       │   ├── Employee.java
│       │   ├── EmployeeDAO.java
│       │   └── DBConnection.java
│       └── webapp
│           ├── index.html
│           ├── style.css
│           └── script.js
├── lib
│   └── mysql-connector-j-9.6.0.jar
├── database.sql
├── RUN_WINDOWS.bat
└── README.md
```

## Requirements
1. JDK 17 (or newer)
2. MySQL Server
3. Your MySQL Connector/J JAR is already included.

## 1. Create the database
Open MySQL Workbench and run `database.sql`.

## 2. Set your MySQL password
Open:
`src/main/java/DBConnection.java`

Change:
`YOUR_MYSQL_PASSWORD`
to your actual MySQL root password.

If your MySQL username is not `root`, change `USER` too.

## 3. Run on Windows
Double-click `RUN_WINDOWS.bat`.

Or in VS Code terminal:
```
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d out src/main/java/*.java
java -cp "out;lib/mysql-connector-j-9.6.0.jar" MainServer
```

Then open:
`http://localhost:8080`

Do NOT use VS Code Live Server.

## 4. Stop
Press `Ctrl+C` in the terminal running Java.


//pain webpage without colors
<!-- <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management System</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

<div class="container">

    <h1>Employee Management System</h1>

    <div class="form-box">

        <h2 id="formTitle">Add Employee</h2>

        <form id="employeeForm">

            <input type="hidden" id="id">

            <label>Name</label>
            <input type="text" id="name">

            <label>Email</label>
            <input type="email" id="email">

            <label>Department</label>
            <input type="text" id="department">

            <label>Salary</label>
            <input type="number" id="salary">

            <button type="submit" id="submitButton">
                Add Employee
            </button>

            <button type="button"
                    id="cancelButton"
                    onclick="cancelEdit()"
                    class="secondary">
                Cancel
            </button>

        </form>

    </div>

    <div class="search-box">

        <input type="text"
               id="search"
               placeholder="Search by name, email or department">

        <button onclick="searchEmployee()">
            Search
        </button>

        <button onclick="loadEmployees()"
                class="secondary">
            Reset
        </button>

    </div>

    <table>

        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Department</th>
                <th>Salary</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody id="employeeTable">
        </tbody>

    </table>

</div>

<script src="script.js"></script>

</body>
</html> -->


/* * {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f4f4f4;
}

.container {
    width: 90%;
    max-width: 1100px;
    margin: 30px auto;
    background: white;
    padding: 25px;
}

h1 {
    text-align: center;
}

.form-box {
    width: 100%;
    margin-bottom: 25px;
}

label {
    display: block;
    margin-top: 10px;
    margin-bottom: 5px;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #cccccc;
}

button {
    padding: 10px 15px;
    border: none;
    margin-top: 15px;
    cursor: pointer;
    background: #333333;
    color: white;
}

.secondary {
    background: #777777;
}

.search-box {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
}

.search-box input {
    flex: 1;
}

.search-box button {
    margin-top: 0;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    border: 1px solid #cccccc;
    padding: 10px;
    text-align: center;
}

th {
    background: #eeeeee;
}

.action-button {
    margin: 2px;
    padding: 7px 10px;
}

#cancelButton {
    display: none;
} */