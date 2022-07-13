if(!principal){
    window.location.href="./index.html";
}

if(principal.userRole == 'ADMIN'){
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
    }
}

async function getReimbursements(){
    let response = await fetch(`${apiUrl}/reimburse`,{
        credentials: 'include'
    });
    if(response.status == 200){
        let data = await response.json();
        console.log(data);

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

        tdId.innerHTML = reimburse.id;
        tdDescription.innerHTML = reimburse.description;
        tdDueDate.innerHTML = reimburse.dueDate;
        tdStatus.innerHTML = reimburse.status;
        tdAmount.innerHTML = reimburse.amount;
        tdReceipt.innerHTML = reimburse.receipt;
        tdType.innerHTML = reimburse.type;


        tr.append(tdId);
        tr.append(tdDescription);
        tr.append(tdDueDate);
        tr.append(tdStatus);
        tr.append(tdAmount);
        tr.append(tdReceipt);
        tr.append(tdType);

        tableBody.append(tr);
    });
}

async function request(){
    window.location.href="request.html";
}