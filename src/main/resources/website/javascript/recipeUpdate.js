// Script Constants
const params = new URLSearchParams(window.location.search);
const url = "http://localhost:1337/recipe/read/" + params.getAll('id');

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
            let id = recipe.id;
            let ingredients = recipe.ingredients;
            let steps = recipe.steps;
            let name = recipe.name;

            console.log(name);
            console.log(ingredients);
            console.log(steps);

            // Build out the HTML
            buildTitle(id, name);
            buildIngredientsTable(ingredients);
            buildStepsTable(steps);
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

function buildIngredientsTable(ingredients) {
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
        tr.appendChild(save);
        tableBody.appendChild(tr);
    }
}

function buildStepsTable(steps) {
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
        tr.appendChild(save);
        tableBody.appendChild(tr);
    }
}

function addNewEntry() {

}

function updateIngredientListener(button, name, quantity, unit, id, type) {
    // Format Data from fields
    let dataToPost = {
        "id": id,
        "name": name.innerHTML,
        "unit": unit.innerHTML,
        "quantity": quantity.innerHTML
    }

    // Send Update Request
    button.onclick = function() {
        let url = "http://localhost:1337/" + type + "/update/" + id;
        fetch(url, {
            method: 'put'
        })
    }
}
