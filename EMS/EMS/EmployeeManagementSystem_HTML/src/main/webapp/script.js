let editing = false;

document.getElementById("employeeForm")
    .addEventListener("submit", function(event) {

    event.preventDefault();

    let id = document.getElementById("id").value;
    let name = document.getElementById("name").value.trim();
    let email = document.getElementById("email").value.trim();
    let department =
        document.getElementById("department").value.trim();
    let salary =
        document.getElementById("salary").value.trim();

    if (name === "" || email === "" ||
        department === "" || salary === "") {

        alert("Please fill all fields");
        return;
    }

    let action = editing ? "update" : "add";

    let data =
        "action=" + action +
        "&id=" + encodeURIComponent(id) +
        "&name=" + encodeURIComponent(name) +
        "&email=" + encodeURIComponent(email) +
        "&department=" + encodeURIComponent(department) +
        "&salary=" + encodeURIComponent(salary);

    fetch("/api/employees", {
        method: "POST",
        headers: {
            "Content-Type":
                "application/x-www-form-urlencoded"
        },
        body: data
    })
    .then(response => response.text())
    .then(message => {

        alert(message);

        cancelEdit();
        loadEmployees();
    })
    .catch(error => console.error(error));
});

function loadEmployees() {

    fetch("/api/employees?action=list")
        .then(response => response.json())
        .then(employees => {

            let table =
                document.getElementById("employeeTable");

            table.innerHTML = "";

            employees.forEach(employee => {

                table.innerHTML +=
                    "<tr>" +
                    "<td>" + employee.id + "</td>" +
                    "<td>" + employee.name + "</td>" +
                    "<td>" + employee.email + "</td>" +
                    "<td>" + employee.department + "</td>" +
                    "<td>" + employee.salary + "</td>" +
                    "<td>" +
                    "<button class='action-button' onclick='editEmployee(" +
                    employee.id + ")'>Edit</button>" +
                    "<button class='action-button' onclick='deleteEmployee(" +
                    employee.id + ")'>Delete</button>" +
                    "</td>" +
                    "</tr>";
            });
        })
        .catch(error => console.error(error));
}

function editEmployee(id) {

    fetch("/api/employees?action=get&id=" + id)
        .then(response => response.json())
        .then(employee => {

            document.getElementById("id").value =
                employee.id;

            document.getElementById("name").value =
                employee.name;

            document.getElementById("email").value =
                employee.email;

            document.getElementById("department").value =
                employee.department;

            document.getElementById("salary").value =
                employee.salary;

            editing = true;

            document.getElementById("formTitle")
                .innerText = "Update Employee";

            document.getElementById("submitButton")
                .innerText = "Update Employee";

            document.getElementById("cancelButton")
                .style.display = "inline-block";
        });
}

function deleteEmployee(id) {

    if (!confirm("Do you want to delete this employee?")) {
        return;
    }

    fetch("/api/employees?action=delete&id=" + id)
        .then(response => response.text())
        .then(message => {

            alert(message);
            loadEmployees();
        });
}

function searchEmployee() {

    let keyword =
        document.getElementById("search").value.trim();

    fetch("/api/employees?action=search&keyword=" +
          encodeURIComponent(keyword))
        .then(response => response.json())
        .then(employees => displayEmployees(employees));
}

function displayEmployees(employees) {

    let table =
        document.getElementById("employeeTable");

    table.innerHTML = "";

    employees.forEach(employee => {

        table.innerHTML +=
            "<tr>" +
            "<td>" + employee.id + "</td>" +
            "<td>" + employee.name + "</td>" +
            "<td>" + employee.email + "</td>" +
            "<td>" + employee.department + "</td>" +
            "<td>" + employee.salary + "</td>" +
            "<td>" +
            "<button class='action-button' onclick='editEmployee(" +
            employee.id + ")'>Edit</button>" +
            "<button class='action-button' onclick='deleteEmployee(" +
            employee.id + ")'>Delete</button>" +
            "</td>" +
            "</tr>";
    });
}

function cancelEdit() {

    document.getElementById("employeeForm").reset();

    document.getElementById("id").value = "";

    editing = false;

    document.getElementById("formTitle")
        .innerText = "Add Employee";

    document.getElementById("submitButton")
        .innerText = "Add Employee";

    document.getElementById("cancelButton")
        .style.display = "none";
}

loadEmployees();

function logout() {

    alert("Thank you for using Employee Management System!");

    document.querySelector(".container").innerHTML = `
        <div class="logout-message">
            <i class="fa-solid fa-circle-check"></i>
            <h1>Thank You!</h1>
            <h2>Successfully Logged Out</h2>
            <p>Thank you for using Employee Management System.</p>
        </div>
    `;
}
