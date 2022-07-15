if(!principal){
    window.location.href="./index.html";
}

if(principal.userRole === 'ADMIN'){
    getReimbursements();
}

//will only grab the user's submitted reimbursements.
if(principal.userRole !== 'ADMIN'){
    getUserReimbursements();
}

let requestButton = document.getElementById('submitButton');
requestButton.addEventListener('click', request);

async function getUserReimbursements(){
    //grabs this user's id, so we can view their submitted reimbursements.

    let response = await fetch(`${apiUrl}/reimburse/${principal.id}`,{
        credentials: 'include'
    });
    if(response.status == 200){
        let data = await response.json();

        populateTable(data);
        //<button class-"btn btn-primary" onclick="acceptReimbursement(`${reimbursement.id})">Accept</button>';
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
        let tdAmount = document.createElement('td');
        let tdReceipt = document.createElement('td');
        let tdType = document.createElement('td');
        let tdAuthor = document.createElement('td');

        tdId.innerHTML = reimburse.id;
        tdDescription.innerHTML = reimburse.description;
        tdDueDate.innerHTML = new Date(reimburse.dueDate).toLocaleDateString("en-US");
        tdStatus.innerHTML = reimburse.status;
        tdAmount.innerHTML = reimburse.amount;
        tdReceipt.innerHTML = reimburse.receipt;
        tdType.innerHTML = reimburse.type;
        tdAuthor.innerHTML = reimburse.author;


        tr.append(tdId);
        tr.append(tdDescription);
        tr.append(tdDueDate);
        tr.append(tdStatus);
        tr.append(tdAmount);
        tr.append(tdReceipt);
        tr.append(tdType);
        tr.append(tdAuthor);

        if(principal.userRole === 'ADMIN'){
            let tdResolver = document.createElement('td');
            tdResolver.innerHTML = reimburse.resolver;
            tr.append(tdResolver);
            let tdAccept = document.createElement('td');
            tdAccept.innerHTML = `<button class="btn btn-primary" onclick="acceptReimbursement(${reimburse.id})">Accept</button>`; //acceptReimbursement(${reimburse.id})
            tr.append(tdAccept);
            let tdDeny = document.createElement('td');
            tdDeny.innerHTML = `<button class="btn btn-primary" onclick="denyReimbursement(${reimburse.id})">Deny</button>`; //acceptReimbursement(${reimburse.id})
            tr.append(tdDeny);
        }

        tableBody.append(tr);
    });
}

function acceptReimbursement(id){
    let confirmA = confirm("Are Are you sure you want to accept this request? sure?");
    if(confirmA){
        fetch(`${apiUrl}/reimburse/${id}`, {
            method: 'PUT',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/json; charset=utf-8',
            }
        }) .then(response => {
            if(response.status == 200){
                alert('Request has been accepted');
            }else{
                alert("Unable to accept");
            }
        })
    }
}

function denyReimbursement(id){
    let confirmA = confirm("Are you sure you want to deny this request?");
    if(confirmA){
        fetch(`${apiUrl}/deny/${id}`, {
            method: 'PUT',
            credentials: 'include',
            headers:{
                'Content-Type': 'application/json; charset=utf-8',
            }
        }) .then(response => {
            if(response.status == 200){
                alert('Request has been denied');
            }else{
                alert("Unable to deny");
            }
        })
    }
}

async function request(){
    if(!principal || principal.userRole === 'ADMIN'){

    }else{
        window.location.href="request.html";
    }
}
