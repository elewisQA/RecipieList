// Script Constants
const params = new URLSearchParams(window.location.search);
const url = "http://localhost:1337/recipe/read/" + params.getAll('id');

let id;
let ingredients;
let steps;
let name;

//---[ Script Body ]---
fetch(url)
.then(
    function(response) {
        //--[ Check Response ]--
        if (response.status !== 200) {
            console.log('Something went wrong.\nStatus Code: ' + response.status);
            return;
        }   

        //--[ Process Data ]--
        response.json().then(function(recipe) {
            // Set out values for easy acces
            id = recipe.id;
            ingredients = recipe.ingredients;
            steps = recipe.steps;
            name = recipe.name;

            console.log(recipe);

            // Build out the HTML
            buildTitle(id, name);
            buildIngredientsTable();
            buildStepsTable();

            // Add Listeners to 'Add' Buttons
            let addIngredient = document.querySelector("a#ingredient-add");
            addIngredient.onclick = function() {
                addIngredientRow();
            }
            let addStep = document.querySelector("a#step-add");
            addStep.onclick = function() {
                addStepRow();
            }
        });
    }
    //--[ Handle Errors ]--
).catch(function(err) {
    console.log('Fetch Error:-S', err);
});

//---[ HTML Build Functions ]---
function buildTitle(id, name) {
    let idField = document.querySelector("h2#id-field");
    idField.innerHTML = id;
    let nameField = document.querySelector("h1#title-field");
    nameField.innerHTML = name;
}

function buildIngredientsTable() {
    let tableBody = document.querySelector("tbody#ingredient-table-body");
    for (let i of ingredients) {
        // Add Ingredient Data
        let tr = document.createElement("tr");
        let id = document.createElement("td");
        id.setAttribute("class", "uid");
        id.innerHTML = i.id;
        tr.appendChild(id);
        let name = document.createElement("td");
        name.setAttribute("contenteditable", "true")
        name.innerHTML = i.name;
        tr.appendChild(name);
        let qty = document.createElement("td");
        qty.setAttribute("contenteditable", "true")
        qty.innerHTML =  i.quantity;
        tr.appendChild(qty);
        let unit = document.createElement("td");
        unit.setAttribute("contenteditable", "true")
        unit.innerHTML = i.unit;
        tr.appendChild(unit);

        // Add Save Button
        let save = document.createElement("a");
        save.setAttribute("class", "btn btn-warning");
        save.setAttribute("href", "#");
        save.innerHTML = "Update";
        updateIngredientListener(save, name, qty, unit, i.id);

        
        // Add Delete Button
        let del = document.createElement("a");
        del.setAttribute("class", "btn btn-danger");
        del.setAttribute("href", "#");
        del.innerHTML = "Delete";
        deleteListener(del, "ingredient", i.id);

        tr.appendChild(save);
        tr.appendChild(del);
        tableBody.appendChild(tr);

    }
}

function buildStepsTable() {
    let tableBody = document.querySelector("tbody#step-table-body");
    for (let s of steps) {

        // Add Ingredient Data
        let tr = document.createElement("tr");
        let id = document.createElement("td");
        id.setAttribute("class", "uid");
        id.innerHTML = s.id;
        tr.appendChild(id);
        let name = document.createElement("td");
        name.setAttribute("contenteditable", "true")
        name.innerHTML = s.name;
        tr.appendChild(name);
        let description = document.createElement("td");
        description.setAttribute("contenteditable", "true")
        description.innerHTML =  s.description;
        tr.appendChild(description);

        // Add Save Button
        let save = document.createElement("a");
        save.setAttribute("class", "btn btn-warning");
        save.setAttribute("href", "#");
        save.innerHTML = "Update";
        updateStepListener(save, name, description, s.id);

        // Add Delete Button
        let del = document.createElement("a");
        del.setAttribute("class", "btn btn-danger");
        del.setAttribute("href", "#");
        del.innerHTML = "Delete";
        deleteListener(del, "step", s.id);

        tr.appendChild(save);
        tr.appendChild(del);
        tableBody.appendChild(tr);

        
    }
}

function addIngredientRow() {
    let tableBody = document.querySelector("tbody#ingredient-table-body");
    let tr = document.createElement("tr");
    let id = document.createElement("td");
    id.setAttribute("class", "uid");
    id.innerHTML = "N";
    tr.appendChild(id);
    let name = document.createElement("td");
    name.setAttribute("contenteditable", "true")
    name.innerHTML = "Name";
    tr.appendChild(name);
    let qty = document.createElement("td");
    qty.setAttribute("contenteditable", "true")
    qty.innerHTML = "0";
    tr.appendChild(qty);
    let unit = document.createElement("td");
    unit.setAttribute("contenteditable", "true")
    unit.innerHTML = "x";
    tr.appendChild(unit);

    // Add 'Add' Button
    let add = document.createElement("a");
    add.setAttribute("class", "btn btn-warning");
    add.setAttribute("href", "#");
    add.innerHTML = "Add";
    addIngredientListener(add, name, qty, unit);

    // Add Delete Button
    let del = document.createElement("a");
    del.setAttribute("class", "btn btn-danger");
    del.setAttribute("href", "#");
    del.innerHTML = "Delete";
    del.onclick = function() {
        removeRow(tr);
    }

    tr.appendChild(add);
    tr.appendChild(del);
    tableBody.appendChild(tr);
}

function addStepRow() {
    let tableBody = document.querySelector("tbody#step-table-body");
    let tr = document.createElement("tr");
    let id = document.createElement("td");
    id.setAttribute("class", "uid");
    id.innerHTML = "N";
    tr.appendChild(id);
    let name = document.createElement("td");
    name.setAttribute("contenteditable", "true")
    name.innerHTML = "Name";
    tr.appendChild(name);
    let desc = document.createElement("td");
    desc.setAttribute("contenteditable", "true")
    desc.innerHTML = "description";
    tr.appendChild(desc);

    // Add 'Add' Button
    let add = document.createElement("a");
    add.setAttribute("class", "btn btn-warning");
    add.setAttribute("href", "#");
    add.innerHTML = "Add";
    addStepListener(add, name, desc);

    // Add Delete Button
    let del = document.createElement("a");
    del.setAttribute("class", "btn btn-danger");
    del.setAttribute("href", "#");
    del.innerHTML = "Delete";
    del.onclick = function() {
        removeRow(tr);
    }

    tr.appendChild(add);
    tr.appendChild(del);
    tableBody.appendChild(tr);
}

//---[ Button Listeners ]---
function updateIngredientListener(button, name, quantity, unit, id) {
    let updateURL = "http://localhost:1337/ingredient/update/" + id;
    button.onclick = function() {
        // Format Data from fields
        let dataToPost = {
            "id": id,
            "name": name.innerHTML,
            "unit": unit.innerHTML,
            "quantity": parseFloat(quantity.innerHTML)
        }
        
        // Send Update Request
        fetch(updateURL, {
            method: 'put',
            headers: {
                "Content-type": "application/json",
            },
            body:json = JSON.stringify(dataToPost)
        })
        .then(JSON)
        .then(function (data) {
            console.log('Request succeeded with JSON response: ', data);
        })
        .catch(function (err) {
            console.log("Request failed: ", err);
        })
        location.reload();
    }
}

function addIngredientListener(button, name, quantity, unit) {
    let createURL = "http://localhost:1337/ingredient/create";
    button.onclick = function() {
        // Format Data from fields
        let dataToPost = {
            "name": name.innerHTML,
            "unit": unit.innerHTML,
            "quantity": parseFloat(quantity.innerHTML)
        }

        // Send Create Request
        fetch(createURL, {
            method: 'post',
            headers: {
                "Content-type": "application/json",
            },
            body:json = JSON.stringify(dataToPost)
        })
        .then(JSON)
        .then(function (response) {
            //console.log('Request succeeded with JSON response: ', data);
            response.json().then(function(data) {
                ingredients.push(data)
                updateRecipe();
            })
        })
        .catch(function (err) {
            console.log("Request failed: ", err);
        })

        //TODO put returned data in Ingredients list
    }
}

function updateStepListener(button, name, description, id) {    
    let updateURL = "http://localhost:1337/step/update/" + id;
    button.onclick = function() {    
        // Format Data from fields
        let dataToPost = {
            "id": id,
            "name": name.innerHTML,
            "description": description.innerHTML
        }

        // SEnd Update Request
        console.log("URL: " + updateURL);
        fetch(updateURL, {
            method: 'put',
            headers: {
                "Content-type": "application/json"
            },
            body:json = JSON.stringify(dataToPost)
        })
        .then(JSON)
        .then(function (data) {
            console.log('Request succeeded with JSON response: ', data);
        })
        .catch(function (error) {
            console.log("Request failed: ", error);
        })
        location.reload();
    }
}


function addStepListener(button, name, description) {
    let createURL = "http://localhost:1337/step/create";
    button.onclick = function() {
        // Format Data from fields
        let dataToPost = {
            "name": name.innerHTML,
            "description": description.innerHTML,
        }

        // Send Create Request
        fetch(createURL, {
            method: 'post',
            headers: {
                "Content-type": "application/json",
            },
            body:json = JSON.stringify(dataToPost)
        })
        .then(JSON)
        .then(function (response) {
            //console.log('Request succeeded with JSON response: ', data);
            response.json().then(function(data) { 
                steps.push(data)
                updateRecipe();
            })
        })
        .catch(function (err) {
            console.log("Request failed: ", err);
        })

        //TODO put returned data in Ingredients list
    }
}

function removeRow(tr) {
    tr.parentNode.removeChild(tr);
}

function updateRecipe() {
    let updateURL = "http://localhost:1337/recipe/update/" + id;
    // Format Data 
    let dataToPost = {
        "id": id,
        "name": name,
        "ingredients": ingredients,
        "steps": steps
    }
    console.log(dataToPost);

    fetch(updateURL, {
        method: 'put',
        headers: {
            "Content-type": "application/json",
        },
        body:json = JSON.stringify(dataToPost)
    })
    .then(JSON)
    .then(function (data) {
        console.log('Request succeeded with JSON response: ', data);
    })
    .catch(function (error) {
        console.log("Request failed: ", error);
    })
    //location.reload();
}