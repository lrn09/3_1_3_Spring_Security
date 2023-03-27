// Admin page header
const adminNavbar = document.getElementById('adminNavbar')
const activeAdminUrl = 'http://localhost:8080/admin'
let activeAdminId

fetch(activeAdminUrl)
    .then(response => response.text())
    .then(data => {
        const admin = JSON.parse(data);
        adminNavbar.innerHTML = `User name: ${admin.username}, roles: ${admin.roles}`
    });

// User page header
const userNavbar = document.getElementById('userNavbar')
const activeUserUrl = 'http://localhost:8080/user'
fetch(activeAdminUrl)
    .then(response => response.text())
    .then(data => {
        const user = JSON.parse(data);
        adminNavbar.innerHTML = `User name: ${user.username}, roles: ${user.roles}`
    });

showUserPage()

// Listing all users function
let usersTableOutput = ''
const listAllUsers = (users) => {
    users.forEach(user => {
        usersTableOutput += `
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles}</td>                            
                            <td><button type="button" class="btn btn-primary" data-toggle="modal" 
                                        data-target="#editModal" id="editButton" data-uid=${user.id}>Edit</button></td>
                            <td><button type="button" class="btn btn-danger" data-toggle="modal" 
                                        data-target="#deleteModal" id="deleteButton" data-uid=${user.id}>Delete</button></td>
                        </tr>`
    })
    usersTable.innerHTML = usersTableOutput
}

// Listing all users on admin page
const usersTable = document.getElementById("users-table")
const usersUrl = 'http://localhost:8080/api/users'
fetch(usersUrl)
    .then(res => res.json())
    .then(data => listAllUsers(data))

// Showing user page
function showUserPage() {
    const userInfo = document.getElementById('user-info')
    let userInfoOutput
    fetch(activeUserUrl)
        .then(res => res.json())
        .then(data => {
            userInfoOutput = `
            <tr>
                <td>${data.id}</td>
                <td>${data.firstName}</td>
                <td>${data.lastName}</td>
                <td>${data.age}</td>
                <td>${data.email}</td>
                <td>${data.roles}</td>                  
            </tr>`
            userInfo.innerHTML = userInfoOutput
        })
}

// Creating new user
const selectRoleForm = document.getElementById('role')
const allRolesUrl = 'http://localhost:8080/api/getAllRoles'
fetch(allRolesUrl)
    .then(res => res.json())
    .then(data => {
        let tempResult = ''
        data.forEach(selectedRole => {
            tempResult += `<option>${selectedRole}</option>`
        })
        selectRoleForm.innerHTML = tempResult
    })

const createUserForm = document.getElementById('creating-user-form')

const firstNameById = document.getElementById('first_name')
const lastNameById = document.getElementById('last_name')
const ageById = document.getElementById('age')
const emailById = document.getElementById('email')
const passwordById = document.getElementById('password')
const roleById = document.getElementById('role')

createUserForm.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(usersUrl, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            firstName: firstNameById.value,
            lastName: lastNameById.value,
            age: ageById.value,
            email: emailById.value,
            password: passwordById.value,
            roles: [...roleById].filter(el => el.selected == true).map(el => el.text)
        })

    })
        .then(res => res.json())
        .then(data => {
            const dataArr = []
            dataArr.push(data)
            listAllUsers(dataArr)
            createUserForm.reset()
            $('[href="#users_table"]').tab('show');
        })
})

// Filling modal forms for edit and delete operations

const editingUserForm = document.getElementById('users-table')

editingUserForm.addEventListener('click', (e) => {
    e.preventDefault()

    if (e.target.id == 'editButton') {
        fetch(`http://localhost:8080/api/getUser?id=${e.target.dataset.uid}`)
            .then(res => res.json())
            .then(data => {
                $('#idEdit').val(data.id)
                $('#firstNameEdit').val(data.firstName)
                $('#lastNameEdit').val(data.lastName)
                $('#ageEdit').val(data.age)
                $('#emailEdit').val(data.email)
                $('#passwordEdit').val('')
                $('#rolesEdit').val(data.roles)

                $('#editModal').modal()
            });
    } else if (e.target.id == 'deleteButton') {
        fetch(`http://localhost:8080/api/getUser?id=${e.target.dataset.uid}`)
            .then(res => res.json())
            .then(data => {
                $('#idDelete').val(data.id)
                $('#firstNameDelete').val(data.firstName)
                $('#lastNameDelete').val(data.lastName)
                $('#ageDelete').val(data.age)
                $('#emailDelete').val(data.email)
                $('#passwordDelete').val(data.password)
                $('#rolesDelete').val(data.roles)

                $('#deleteModal').modal()
            });
    }


})

// Editing user
const editUserModalForm = document.getElementById('editModalForm')

editUserModalForm.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(usersUrl, {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('idEdit').value,
            firstName: document.getElementById('firstNameEdit').value,
            lastName: document.getElementById('lastNameEdit').value,
            age: document.getElementById('ageEdit').value,
            email: document.getElementById('emailEdit').value,
            password: document.getElementById('passwordEdit').value,
            roles: [...document.getElementById('rolesEdit')].filter(el => el.selected == true).map(el => el.text)
        })
    })
        .then(res => res.json())
        .then(() => {
            $('#editModal').modal('hide')
            usersTableOutput = ''
            fetch(usersUrl)
                .then(res => res.json())
                .then(data => listAllUsers(data))
            if (activeAdminId == document.getElementById('idEdit').value) {
                fetch(activeAdminUrl)
                    .then(res => res.json())
                    .then(data => {
                        adminNavbar.innerHTML = `${data.email} with roles: ${data.roles}`
                    })
                showUserPage()
            }
        })

})

// Deleting user
const deleteUserModalForm = document.getElementById('deleteModalForm')

deleteUserModalForm.addEventListener('submit', (e) => {
    e.preventDefault()
    const uid = document.getElementById('idDelete').value
    fetch(`${usersUrl}/${uid}`, {
        method: 'DELETE'
    })
        .then(res => console.log(res))
        .then(() => {
            $('#deleteModal').modal('hide')
            usersTableOutput = ''
            fetch(usersUrl)
                .then(res => res.json())
                .then(data => listAllUsers(data))
        })
})
