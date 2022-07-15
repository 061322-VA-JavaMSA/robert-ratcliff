// Checks if a user is already logged in, if yes redirect to homepage
if(principal){
    window.location.href="../index.html";
}

let signUpButton = document.getElementById('submitButton');
signUpButton.addEventListener('click', signup);

async function signup(){
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    let firstName = document.getElementById('first-name').value;
    let lastName = document.getElementById('last-name').value;
    let email = document.getElementById('email').value;


    let response = await fetch(`${apiUrl}/users`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify({
            'username': `${username}`,
            'password': `${password}`,
            'firstName': `${firstName}`,
            'lastName': `${lastName}`,
            'email': `${email}`,
            'role': BASIC_USER
        })
    });

    if(response.status == 201){
        alert("Success!");

        window.location.href="../reimburse.html";
    }else{
        console.log('Unable to make new user.');
    }
}