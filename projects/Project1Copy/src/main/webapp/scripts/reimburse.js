if(!principal){
    window.location.href="./index.html";
}

if(principal.userRole == 'ADMIN'){
    getReimbursements();
}

//will only grab the user's submitted reimbursements.
if(principal.userRole !== 'ADMIN'){
    console.log(principal);
    getUserReimbursements();
}

async function getUserReimbursements(){
    //grabs this user's id, so we can view their submitted reimbursements.

    let response = await fetch(`${apiUrl}/reimburse/${principal.id}`,{
        credentials: 'include'
    });
    if(response.status == 200){
        let data = await response.json();

        populateTable(data);
    }
}

async function getReimbursements(){
    let response = await fetch(`${apiUrl}/reimburse`,{
        credentials: 'include'
    });
    if(response.status == 200){
        let data = await response.json();

        populateTable(data);
    }
}

function populateTable(data){
    let tableBody = document.getElementById('reimburse-tbody');

    data.forEach(reimburse => {
        let tr = document.createElement('tr');
        let tdId = document.createElement('td');
        let tdDescription = document.createElement('td');
        let tdDueDate = document.createElement('td');
        let tdStatus = document.createElement('td');

        tdId.innerHTML = reimburse.id;
        tdDescription.innerHTML = reimburse.description;
        tdDueDate.innerHTML = reimburse.dueDate;
        tdStatus.innerHTML = reimburse.status;

        tr.append(tdId);
        tr.append(tdDescription);
        tr.append(tdDueDate);
        tr.append(tdStatus);

        tableBody.append(tr);
    });
}