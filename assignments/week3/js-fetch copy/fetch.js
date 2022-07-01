// document.getElementById('getData').onclick = getData;
document.getElementById('getData').addEventListener("click", getData);

/*
    - When button is clicked, send http request to API for a specific id

    - get the id value from input box
    - send request to PokeAPI
        - Method: GET
        - Headers: None
        - Body: None
        - url: https://pokeapi.co/api/v2/pokemon/ + id from input box
    - might have to convert JSON to JS object

    - populate the data in Section
*/
let baseApiURL = 'https://pokeapi.co/api/v2/pokemon';

async function getData() {
    console.log('Button was clicked!');
    let id = document.getElementById('dataInput').value;
    console.log(`id = ${id}`);

    let httpResponse = await fetch(`${baseApiURL}/${id}`);

    if(httpResponse.status >= 200 && httpResponse.status < 300){
    let data = await httpResponse.json();

    populateData(data);
    
    } else {
        console.log('Invalid request.');
    }
}

function populateData(response) {
    console.log(response);

    var table = document.createElement('table');
    var rows = 2;
    var columns = 3;

    for (var i = 0; i < rows; i++){
        let tr;
        tr = document.createElement('tr');
        for(var j = 0; j < columns; j++){
            var td;
            if (i === 0) {
                td = document.createElement('th');
                if(j == 0) td.innerHTML = 'name';
                else if (j == 1) td.innerHTML = 'move';
                else td.innerHTML = 'level_learned_at';
            }
            else{ 
            td = document.createElement('td');
            if(j == 0) td.innerHTML = response.name;
            else if (j == 1){
                //To get access to the move name attribute,
                //you have to get the element from the move array
                //to access that attribute.
                let arr = response.moves;
                let moveArr = arr[j - 1]; //minus 1 to get the first element
                let name = moveArr.move.name;
                td.innerHTML = name;
            }
            else{
                //To gain access to the level_learned_at attribute,
                //you have to go through 2 arrays.
                //The first one is the move array similiar to the condition above.
                //The second is the version_group_details array.
                let arr = response.moves;
                let moveArr = arr[j - 2]; //minus 2 to get first element
                let version_group_details = moveArr.version_group_details[0];
                let level_learned_at = version_group_details.level_learned_at;
                td.innerHTML = level_learned_at;
            }
        }
            tr.appendChild(td);
        }
        table.appendChild(tr);
    }
    data.appendChild(table);
}

