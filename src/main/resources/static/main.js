// Admin page header
const adminNavbar = document.getElementById('adminNavbar')
const activeAdminUrl = 'http://localhost:8080/api/getActiveUser'
let activeAdminId


fetch(activeAdminUrl)
    .then(res => res.json())
    .then(data => {
        adminNavbar.innerHTML = `${data.username} with roles: ${data.authorities}`
        activeAdminId = `${data.id}`
    })

// User page header
const userNavbar = document.getElementById('userNavbar')
const activeUserUrl = 'http://localhost:8080/api/getActiveUser'
fetch(activeUserUrl)
    .then(res => res.json())
    .then(data => {
        userNavbar.innerHTML = `${data.username} with roles: ${data.authorities}`
    })

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
                            <td>${user.username}</td>
                            <td>${user.authorities}</td>                            
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
                <td>${data.username}</td>
                <td>${data.authorities}</td>                  
            </tr>`
            userInfo.innerHTML = userInfoOutput
        })
}

// Creating new user
const createUserUrl = 'http://localhost:8080/api/users/create';
const allRolesUrl = 'http://localhost:8080/api/getAllRoles';
const selectRoleForm = document.getElementById('roles');


// Fetch all roles from API and populate dropdown options
fetch(allRolesUrl)
    .then(res => res.json())
    .then(data => {
        let options = '';
        for (const [id, name] of Object.entries(data)) {
            options += `<option value="${id}">${name}</option>`;
        }
        selectRoleForm.innerHTML = options;
    })
    .catch(err => console.error(err));

const createUserForm = document.getElementById('creating-user-form');

createUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const firstNameById = document.getElementById('first_name');
    const lastNameById = document.getElementById('last_name');
    const ageById = document.getElementById('age');
    const emailById = document.getElementById('email');
    const passwordById = document.getElementById('password');
    const roleById = document.getElementById('roles');

    // create array of roles
    const roles = [];
    for (let i = 0; i < roleById.options.length; i++) {
        if (roleById.options[i].selected) {
            roles.push({
                id: roleById.options[i].value,
                authority: roleById.options[i].text
            });
        }
    }

    // POST user data to API
    fetch(createUserUrl, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            firstName: firstNameById.value,
            lastName: lastNameById.value,
            age: ageById.value,
            username: emailById.value,
            password: passwordById.value,
            authorities: roles
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
        .catch(err => console.error(err));
});


// Filling modal forms for edit and delete operations
const editingUserForm = document.getElementById('users-table');

editingUserForm.addEventListener('click', (e) => {
    e.preventDefault()

    if (e.target.id === 'editButton') {
        fetch(`http://localhost:8080/api/getUser?id=${e.target.dataset.uid}`)
            .then(res => res.json())
            .then(data => {
                $('#idEdit').val(data.id)
                $('#firstNameEdit').val(data.firstName)
                $('#lastNameEdit').val(data.lastName)
                $('#ageEdit').val(data.age)
                $('#emailEdit').val(data.username)
                $('#passwordEdit').val('')

                // Fetch all roles from API and populate dropdown options
                fetch(allRolesUrl)
                    .then(res => res.json())
                    .then(rolesData => {
                        let options = '';
                        for (const [id, name] of Object.entries(rolesData)) {
                            const selected = data.authorities.some(role => role.id === id) ? 'selected' : '';
                            options += `<option value="${id}" ${selected}>${name}</option>`;
                        }
                        $('#rolesEdit').html(options);
                        $('#editModal').modal()
                    })
                    .catch(err => console.error(err));
            });
    } else if (e.target.id === 'deleteButton') {
        fetch(`http://localhost:8080/api/getUser?id=${e.target.dataset.uid}`)
            .then(res => res.json())
            .then(data => {
                $('#idDelete').val(data.id)
                $('#firstNameDelete').val(data.firstName)
                $('#lastNameDelete').val(data.lastName)
                $('#ageDelete').val(data.age)
                $('#emailDelete').val(data.username)
                $('#passwordDelete').val(data.password)
                $('#rolesDelete').val(data.roles)

                $('#deleteModal').modal()
            });
    }
})

/// Editing user
const editUserModalForm = document.getElementById('editModalForm')

editUserModalForm.addEventListener('submit', (e) => {
    e.preventDefault()

    const firstNameById = document.getElementById('firstNameEdit');
    const lastNameById = document.getElementById('lastNameEdit');
    const ageById = document.getElementById('ageEdit');
    const emailById = document.getElementById('emailEdit');
    const passwordById = document.getElementById('passwordEdit');
    const roleById = document.getElementById('rolesEdit');

    // create array of roles
    const roles = [];
    for (let i = 0; i < roleById.options.length; i++) {
        if (roleById.options[i].selected) {
            roles.push({
                id: roleById.options[i].value,
                authority: roleById.options[i].text
            });
        }
    }

    const requestBody = {
        id: document.getElementById('idEdit').value,
        firstName: firstNameById.value,
        lastName: lastNameById.value,
        age: ageById.value,
        username: emailById.value,
        password: passwordById.value,
        authorities: roles
    };

    console.log('Request body:', requestBody);

    fetch(usersUrl, {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
        .then(res => console.log(res))
        .then(() => {
            $('#editModal').modal('hide')
            usersTableOutput = ''
            fetch(usersUrl)
                .then(res => res.json())
                .then(data => listAllUsers(data))
        })

});


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