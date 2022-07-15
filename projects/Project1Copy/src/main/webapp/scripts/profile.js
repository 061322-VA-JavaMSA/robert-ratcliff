if(!principal){
    window.location.href="./index.html"
}else{
    getUser();
}

async function getUser(){

    let response = await fetch(`${apiUrl}/users/${principal.id}`, {
        credentials: 'include'
    });

    if(response.status == 200){
        let data = await response.json();

        populateTable(data);
    } else{
        console.log('Unable to retrieve users.')
    }
}

function populateTable(data){
    let tableBody = document.getElementById('users-tbody');

        let tr = document.createElement('tr');
        let tdUsername = document.createElement('td');
        let tdFirstName = document.createElement('td');
        let tdLastName = document.createElement('td');
        let tdEmail = document.createElement('td');
        let tdRole = document.createElement('td');

        tdUsername.innerHTML = data.username;
        tdFirstName.innerHTML = data.firstName;
        tdLastName.innerHTML = data.lastName;
        tdEmail.innerHTML = data.email;
        tdRole.innerHTML = data.userRole;

        tr.append(tdUsername);
        tr.append(tdFirstName);
        tr.append(tdLastName);
        tr.append(tdEmail);
        tr.append(tdRole);

        tableBody.append(tr);
}