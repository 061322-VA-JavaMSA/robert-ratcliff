let welcomeH1 = document.getElementById('welcome');

// Different welcome message based on logged in user retrieved from session storage
if(principal){
    welcomeH1.innerHTML = `Welcome back ${principal.username}!`
} else{
    welcomeH1.innerHTML = `Welcome to My Project1!`
}