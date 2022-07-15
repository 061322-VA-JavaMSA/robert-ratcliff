//stops unlogged users or admins from entering a request.
if(!principal){
    window.location.href='./index.html';
}
if(principal.userRole === 'ADMIN'){
    window.location.href='./reimburse.html';
}

let requestButton = document.getElementById('submitButton');
requestButton.addEventListener('click', request);

async function request(){

    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value;
    //let submission = new Date();
    let receipt = document.getElementById('receipt').value;
    let type = document.getElementById('type').value;
    let author = principal.id;
    let resolver = null;
    let status = 1;
    let resolved = null;

    let response = await fetch(`${apiUrl}/reimburse`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify({
            'amount': `${amount}`,
            'description': `${description}`,
            //'submitted': `${submission}`,
            'receipt': `${receipt}`,
            'typeId': `${type}`,
            'author': `${author}`,
            'resolver': `${resolver}`,
            'statusId': `${status}`,
            'resolved': `${resolved}`
        })
    });

    if(response.status == 201){
        alert("Success!");

        window.location.href="./reimburse.html";
    }else{
        console.log('Unable to submit request.');
    }
}