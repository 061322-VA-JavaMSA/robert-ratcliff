let apiUrl = 'http://localhost:8080/project1';

// When logged in, retrieves the JSON string representing the logged in user from Session Storage
let principalString = sessionStorage.getItem('principal');
let principal = null;

let nav_right = document.getElementById("nav-right");
let nav_left = document.getElementById("nav-left");

// If principalString is a truthy value(non null), therefore exists
if (principalString) {
    // converts the JSON string back to a JS object and assigns it to principal
    principal = JSON.parse(principalString);

    if (principal.userRole === 'ADMIN') {
        createNavElement('Users', nav_left, './users.html', null);
    }

    createNavElement('Reimbursements', nav_left, './reimburse.html', null);
    createNavElement('Profile', nav_right, './profile.html', null);

    createNavElement('Logout', nav_right, null, logout);
} else {
    createNavElement('Login', nav_right, './login.html', null);

}

async function logout() {

    // Sends a DELETE request to API to invalidate session
    let response = await fetch(`${apiUrl}/auth`, {
        method: 'DELETE',
        credentials: 'include'
    });

    if (response.status == 200) {
        // clears user object JSON string in session storage
        sessionStorage.clear();
        // clears principal variable representing logged in user
        principal = null;
        window.location.href="./index.html";
    } else {
        console.log('Unable to logout.')
    }
}

// Reusable function to create nav element in navbar
function createNavElement(innerHTML, parentElement, link, callback) {
    let li = document.createElement('li');
    // bootstrap class for styling
    li.setAttribute('class', 'nav-item');

    let a = document.createElement('a');
    // bootstrap class for styling
    a.setAttribute('class', 'nav-link');

    // if a link to a different page has been assigned, assign that link
    if (link) {
        a.setAttribute('href', link);
    } else {
        a.setAttribute('href', '#');
    }

    // if a callback function was passed in, assigns that function to the new element
    if (callback) {
        a.addEventListener('click', callback);
    }

    // Assigns nav-bar item name
    a.innerHTML = innerHTML;

    li.appendChild(a);

    parentElement.appendChild(li);
}