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
            let page = document.querySelector("div#content-container");
            //let data = Object.keys(recipeData[0]); // Don't need headers?
            for(let recipe of recipeData) {
                page.appendChild(createRecipe(recipe));
            }

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
    recipeContainer.setAttribute("class", "row")
    recipeContainer.setAttribute("id", id)

    let col1 = document.createElement("div");
    col1.setAttribute("class", "col-1");
    let col10 = document.createElement("div");
    col10.setAttribute("class", "col-10");

    //--[ Put Title In Own Row ]---
    let recipeHeader = document.createElement("div");
    recipeHeader.setAttribute("class", "row")
    let recipeTitle = document.createElement("h3");
    recipeTitle.setAttribute("class", "recipe-title");
    recipeTitle.innerHTML = name;
    col10.appendChild(recipeTitle);
    recipeHeader.appendChild(col1);
    recipeHeader.appendChild(col10);
    recipeHeader.appendChild(col1);

    //---[ New Row for Two Tables ]
    let recipeBody = document.createElement("div");
    recipeBody.setAttribute("class", "row")
    let ingredientCol = document.createElement("div");
    ingredientCol.setAttribute("class", "col-6");
    let stepCol = document.createElement("div");
    stepCol.setAttribute("class", "col-6");

    // Send to be populated
    ingredientCol = createIngredients(ingredientCol, ingredients);
    stepCol = createSteps(stepCol, steps);

    // Add to body
    recipeBody.appendChild(ingredientCol);
    recipeBody.appendChild(stepCol);

    recipeContainer.appendChild(recipeHeader);
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