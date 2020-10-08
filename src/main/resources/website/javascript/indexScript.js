// Populate the Index (Home) Page with data from the Spring-Backend using Fetch
fetch('http://localhost:1337/recipe/read')
.then(
    function(response) {
        //---[ Check Response ]---
        if (response.status !== 200) {
            console.log('Something went wrong.\nStatus Code: ' + response.status);
            return;
        }

        //---[ Process Data ]---
        response.json().then(function(recipeData) {
            let page = document.querySelector("div#super-container");
            //let data = Object.keys(recipeData[0]); // Don't need headers?
            for(let recipe of recipeData) {
                page.appendChild(createRecipe(recipe));
            }
            let br = document.createElement("br");
            page.appendChild(br);
            page.appendChild(br);
        });
    }
    //---[ Handle Errors ]---
).catch(function(err) {
    console.log('Fetch Error:-S',err);
});

function createRecipe(recipe) {
    //--[ Extract Data ]---
    let id = recipe.id;
    let name = recipe.name;
    let ingredients = recipe.ingredients
    let steps = recipe.steps;

    //--[ Build Recipe Container ]---
    let recipeContainer = document.createElement("div")
    recipeContainer.setAttribute("class", "recipe-container")
    recipeContainer.setAttribute("id", id)

    //--[ Put Title In Own Row ]---
    let recipeHeader = document.createElement("div");
    recipeHeader.setAttribute("class", "row")
    // Header Content
    let recipeTitle = document.createElement("h3");
    recipeTitle.setAttribute("class", "recipe-title");
    recipeTitle.setAttribute("id", "recipeTitle-" + id)
    recipeTitle.innerHTML = name;

    // Add Buttons
    let editButton = document.createElement("a");
    editButton.setAttribute("class", "btn btn-warning");
    editButton.setAttribute("id", "editRecipe-" + id);
    editButton.setAttribute("href", "recipeUpdate.html?id=" + id);
    editButton.innerHTML = "Edit";
    let delButton  = document.createElement("a");
    delButton.setAttribute("class", "btn btn-danger");
    delButton.setAttribute("id", "delRecipe-" + id);
    delButton.setAttribute("href", "#");
    deleteListener(delButton, 'recipe', id)
    delButton.innerHTML = "Delete";

    // Add Spacing & Content
    let spaceCol = document.createElement("div");
    spaceCol.setAttribute("class", "col-2");
    let titleCol = document.createElement("div");
    titleCol.setAttribute("class", "col-6");
    titleCol.appendChild(recipeTitle);
    let editCol = document.createElement("div");
    editCol.setAttribute("class", "col-2");
    editCol.appendChild(editButton);
    let delCol = document.createElement("div");
    delCol.setAttribute("class", "col-2");
    delCol.appendChild(delButton);

    // Add to header
    recipeHeader.appendChild(spaceCol);
    recipeHeader.appendChild(titleCol);
    recipeHeader.appendChild(editCol);
    recipeHeader.appendChild(delCol);

    // Add header to container
    recipeContainer.appendChild(recipeHeader);

    //---[ New Row for Two Tables ]
    let recipeBody = document.createElement("div");
    recipeBody.setAttribute("class", "row")
    let ingredientCol = document.createElement("div");
    ingredientCol.setAttribute("class", "col-6");
    let stepCol = document.createElement("div");
    stepCol.setAttribute("class", "col-6");

    // Send to be populated & Add to body 
    recipeBody.appendChild(createIngredients(ingredientCol, ingredients));
    recipeBody.appendChild(createSteps(stepCol, steps));

    // Add body to container
    recipeContainer.appendChild(recipeBody);

    return recipeContainer;
}

function createIngredients(ingredientCol, ingredients) {
    // Add Title
    let title = document.createElement("h4");
    title.innerHTML = "Ingredients:";
    ingredientCol.appendChild(title);

    // Build Table - Head
    let table = document.createElement("table");
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();
    let th = document.createElement("th");
    th.innerHTML = "Ingredient:";
    row.appendChild(th);
    th.innerHTML = "Qty:";
    row.appendChild(th);
    th.innerHTML = "Unit:";
    row.appendChild(th);
    // Append Two blank columns for buttons
    th.innerHTML = "";
    row.appendChild(th);
    row.appendChild(th);
    tableHead.appendChild(row);

    let tableBody = table.createTBody();
    
    // Build Table - Contents
    for(let i of ingredients) {
        // Set-up Row
        let row = tableBody.insertRow();
        let name = document.createElement("td");
        let qty = document.createElement("td");
        let unit = document.createElement("td");

        // Populate with data
        name.innerHTML = i.name;
        qty.innerHTML = i.quantity;
        unit.innerHTML = i.unit;

        // Add to the table row
        row.appendChild(name);
        row.appendChild(qty);
        row.appendChild(unit);
        tableBody.appendChild(row);
    }

    table.appendChild(tableHead);
    table.appendChild(tableBody);
    ingredientCol.appendChild(table);

    return ingredientCol;
}

function createSteps(stepCol, steps) {
    // Add Title
    let title = document.createElement("h4");
    title.innerHTML = "Method:";
    stepCol.appendChild(title);

    // Build Table - Head
    let table = document.createElement("table");
    let tableHead = table.createTHead();
    let row = tableHead.insertRow();
    let th = document.createElement("th");
    th.innerHTML = "Step:";
    row.appendChild(th);
    th.innerHTML = "Description:";
    row.appendChild(th);
    // Append Two blank columns for buttons
    th.innerHTML = "";
    row.appendChild(th);
    row.appendChild(th);
    tableHead.appendChild(row);

    let tableBody = table.createTBody();
    
    // Build Table - Contents
    for(let i of steps) {
        // Set-up Row
        let row = tableBody.insertRow();
        let name = document.createElement("td");
        let desc = document.createElement("td");

        // Populate with data
        name.innerHTML = i.name;
        desc.innerHTML = i.description;

        // Add to the table row
        row.appendChild(name);
        row.appendChild(desc);
        tableBody.appendChild(row);
    }

    table.appendChild(tableHead);
    table.appendChild(tableBody);
    stepCol.appendChild(table);

    return stepCol
}

