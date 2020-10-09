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

            let recipeSave = document.querySelector("a#save-button");
            recipeSave.onclick = function() {
                console.log("click")
                let nameField = document.querySelector("input#title-field");
                if (nameField.value !== "") {
                    name = nameField.value;
                }
                updateRecipe();
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
    let nameField = document.querySelector("input#title-field");
    nameField.setAttribute("placeholder", name);
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
        let nameField = document.createElement("input");
        nameField.setAttribute("class", "ingredient-name-field");
        nameField.setAttribute("id", "ingredient-name-" + i.id)
        nameField.setAttribute("placeholder", i.name);
        nameField.setAttribute("type", "text")
        name.appendChild(nameField);
        tr.appendChild(name);
        let qty = document.createElement("td");
        let qtyField = document.createElement("input");
        qtyField.setAttribute("class", "qty-field");
        qtyField.setAttribute("id", "qty-" + i.id);
        qtyField.setAttribute("placeholder", i.quantity);
        qtyField.setAttribute("type", "number")
        qty.appendChild(qtyField);
        tr.appendChild(qty);
        let unit = document.createElement("td");
        let unitField = document.createElement("input");
        unitField.setAttribute("class", "unit-field");
        unitField.setAttribute("id", "id-" + i.id);
        unitField.setAttribute("placeholder", i.unit);
        unitField.setAttribute("type", "text");
        unit.appendChild(unitField);
        tr.appendChild(unit);

        // Add Save Button
        let save = document.createElement("a");
        save.setAttribute("class", "btn btn-warning");
        save.setAttribute("id", "isave-" + i.id);
        save.setAttribute("href", "#");
        save.innerHTML = "Update";
        updateIngredientListener(save, nameField, qtyField, unitField, i.id);

        
        // Add Delete Button
        let del = document.createElement("a");
        del.setAttribute("class", "btn btn-danger");
        del.setAttribute("id", "idel-" + i.id);
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
        let nameField = document.createElement("input");
        nameField.setAttribute("id", "step-name-" + s.id);
        nameField.setAttribute("placeholder", s.name);
        nameField.setAttribute("type", "text");
        name.appendChild(nameField);
        tr.appendChild(name);
        let description = document.createElement("td");
        let descriptionField = document.createElement("input");
        descriptionField.setAttribute("id", "description-" + s.id);
        descriptionField.setAttribute("placeholder", s.description);
        descriptionField.setAttribute("type", "text");
        description.appendChild(descriptionField);
        tr.appendChild(description);

        // Add Save Button
        let save = document.createElement("a");
        save.setAttribute("class", "btn btn-warning");
        save.setAttribute("id", "ssave-" + s.id);
        save.setAttribute("href", "#");
        save.innerHTML = "Update";
        updateStepListener(save, nameField, descriptionField, s.id);

        // Add Delete Button
        let del = document.createElement("a");
        del.setAttribute("class", "btn btn-danger");
        del.setAttribute("id", "sdel-" + s.id);
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
    let nameField = document.createElement("input");
    nameField.setAttribute("class", "add-ingredient-name-field");
    nameField.setAttribute("type", "text");
    nameField.setAttribute("placeholder", "name");
    name.appendChild(nameField);
    tr.appendChild(name);
    let qty = document.createElement("td");
    let qtyField = document.createElement("input");
    qtyField.setAttribute("class", "add-qty-field");
    qtyField.setAttribute("type", "number");
    qtyField.setAttribute("placeholder", "amount");
    qty.appendChild(qtyField);
    tr.appendChild(qty);
    let unit = document.createElement("td");
    let unitField = document.createElement("input");
    unitField.setAttribute("class", "add-unit-field");
    unitField.setAttribute("type", "text");
    unitField.setAttribute("placeholder", "unit");
    unit.appendChild(unitField);
    tr.appendChild(unit);

    // Add 'Add' Button
    let add = document.createElement("a");
    add.setAttribute("class", "btn btn-warning");
    add.setAttribute("id", "add-add");
    add.setAttribute("href", "#");
    add.innerHTML = "Add";
    addIngredientListener(add, nameField, qtyField, unitField);

    // Add Delete Button
    let del = document.createElement("a");
    del.setAttribute("class", "btn btn-danger");
    del.setAttribute("id", "add-del");
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
    let nameField = document.createElement("input");
    nameField.setAttribute("class", "add-step-name-field");
    nameField.setAttribute("placeholder", "name");
    name.appendChild(nameField);
    tr.appendChild(name);
    let desc = document.createElement("td");
    let descField = document.createElement("input");
    descField.setAttribute("class", "add-desc-field");
    descField.setAttribute("placeholder", "description");
    desc.appendChild(descField);
    tr.appendChild(desc);

    // Add 'Add' Button
    let add = document.createElement("a");
    add.setAttribute("class", "btn btn-warning");
    add.setAttribute("href", "#");
    add.innerHTML = "Add";
    addStepListener(add, nameField, descField);

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
function updateIngredientListener(button, nameField, quantityField, unitField, id) {
    let updateURL = "http://localhost:1337/ingredient/update/" + id;
    button.onclick = function() {
        // Set values with placeholder data grabbed from original fetch
        let name = nameField.getAttribute("placeholder");
        let qty = quantityField.getAttribute("placeholder");
        let unit = unitField.getAttribute("placeholder");

        // Check if user has entered values, if so - replace
        if (nameField.value !== "") { 
            name = nameField.value;
        }
        if (quantityField.value !== "") {
            qty = quantityField.value;
        }
        if (unitField.value !== "") {
            unit = unitField.value;
        }

        // Format into object
        let dataToPost = {
            "id": id,
            "name": name,
            "unit": unit,
            "quantity": parseFloat(qty)
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

function updateStepListener(button, nameField, descriptionField, id) {    
    let updateURL = "http://localhost:1337/step/update/" + id;
    button.onclick = function() {    
        // Set values with placeholder data grabbed from original fetch
        let name = nameField.getAttribute("placeholder");
        let desc = descriptionField.getAttribute("placeholder");
        
        // Check if user has entered values, if so - replace
        if (nameField.value !== "") { 
            name = nameField.value;
        }
        if (descriptionField.value !== "") {
            desc = quantityField.value;
        }
        let dataToPost = {
            "id": id,
            "name": name,
            "description": desc
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
    location.reload();
}