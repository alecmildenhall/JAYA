var baseurl = "http://localhost:8080/api/v1/fridge"
const foodNames = [];

function collapseLoginForm(){
    document.getElementById("emailForm").style.display = "none";
    login();
}

function showLoginForm(){
    document.getElementById("emailForm").style.display = "block";
}

function collapseAddFoodForm(){
    document.getElementById("addFoodForm").style.display = "none";
    addFood();
}

function collapseEditFoodForm(button){
    document.getElementById("editFoodForm").style.display = "none";
    editFood(button);
}

function collapseAndCallNewUserForm(){
    if(document.getElementById("newUserForm") !== null){
        document.getElementById("newUserForm").style.display = "none";
        var name = document.getElementById("newUserName").value;
        var email = document.getElementById("newUserEmail").value;
        if(name != "" && email != ""){
            newUser();
        }
        else{
            alert("Please enter a name and email!");
            const button = document.createElement("newUserButton");
            var newUserButton = `<button id = "newUser" class = "button-8" onclick="newUserForm();">Create New User</button>`;
            button.innerHTML = newUserButton;
            var userForm = document.getElementById("newUserForm");
            userForm.parentNode.replaceChild(button, userForm);
        }
    }
}

function collapseNewUserForm(){
    if(document.getElementById("newUserForm") !== null){
        document.getElementById("newUserForm").style.display = "none";
    }
}

function login(){
var email = document.getElementById("email").value;
var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseurl + "/get-user/" + email, true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var user = JSON.parse(xmlhttp.responseText);
            if (user.userId === -1){
                showLoginForm();
                alert("User does not exist. Try a different email or Create new user.")
            }
            else{
                document.getElementById("userName").innerHTML = "Logged in as: " + user.name;
                document.getElementById("userId").innerHTML = user.userId;
                document.getElementById("userId").style.display = "none";
                document.getElementById("userEmail").innerHTML = user.email;
                document.getElementById("userEmail").style.display = "none";
                getFridge(user.userId);
                if(document.getElementById("newUser") !== null){
                    document.getElementById("newUser").style.display = "none";
                }
                document.getElementById("deleteAccount").innerHTML = `<button id = "deleteAccount" class="button-45" onclick="deleteUser();">Delete Account</button>`;
                document.getElementById("logout").innerHTML = `<button id = "logout" class="button-8" onclick="logout();">Logout</button>`;
                document.getElementById("getRecipe").innerHTML = `<button id = "recipeButton" class="button-8" onclick="getRecipes();">Get Recipes</button>`;
                collapseNewUserForm();
            }
        }
    };
    xmlhttp.send();
}

function newUserForm(){
    const user = document.createElement("edit");
    user.id = "newUserForm";
    var newUserForm = `<form onsubmit="return false;">
    <label for=newUserName>Name</label>
    <br><input type=text id=newUserName><br>
    <label for=newUserEmail>Email</label>
    <br><input type=text id=newUserEmail><br>
    <input type="submit" class="button-8" onclick="collapseAndCallNewUserForm(this);" />
    </form>`;
    user.innerHTML = newUserForm;
    var button = document.getElementById("newUser");
    button.parentNode.replaceChild(user, button);
}

function newUser(){
    var name = document.getElementById("newUserName").value;
    var email = document.getElementById("newUserEmail").value;
    var xmlhttp = new XMLHttpRequest();
    const json = {
        "name": name,
        "email": email
    };
    xmlhttp.open("POST", baseurl + "/add-user", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var user = JSON.parse(xmlhttp.responseText);
            if (user.userId === -1){
                alert("Account already exists with that email. Login.");
                const button = document.createElement("newUserButton");
                var newUserButton = `<button id = "newUser" class = "button-8" onclick="newUserForm();">Create New User</button>`;
                button.innerHTML = newUserButton;
                var userForm = document.getElementById("newUserForm");
                userForm.parentNode.replaceChild(button, userForm);
            }
            else{
                alert("New user created! Login with email.");
            }
        }
    };
    xmlhttp.send(JSON.stringify(json));
}

function getFridge(userId){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseurl + "/get-fridge/" + userId, true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var food = JSON.parse(xmlhttp.responseText);
            var tbltop = "<thead><tr><th>Food Name</th><th>Food Quantity</th><th>Core Quantity</th><th>Edit</th><th>Delete</th></tr><thead/>";
            
            var main = "<tbody>";
            for (i = 0; i < food.length; i++){
                main += "<tr><td>" + food[i].foodName +"</td><td>" + food[i].foodQuantity 
                +"</td><td>" + food[i].coreQuantity 
                + `</td><td><button class="tableButton" id = "editButton" onclick = editFoodForm(this)>Edit</button>`
                + `</td><td><button class="tableButton" id = "deleteButton" onclick = deleteFood(this)>Delete</button>`;
                foodNames[i] = food[i].foodName
            }
            main+= "</tbody>"
            var tbl = tbltop + main;
            document.getElementById("fridgeTable").innerHTML = tbl;
            sortTable();
            var addFoodButton = "<button class = \"button-37\" onclick = addFoodForm()>Add Food Item</button>";
            document.getElementById("addFood").innerHTML = addFoodButton;
            var missingCoreButton = "<button class = \"button-37\" onclick = missingCore()>What am I missing?</button>";
            document.getElementById("missingCore").innerHTML = missingCoreButton;
        }
    };
    xmlhttp.send();
}

function sortTable() {
    var filterTable, rows, sorted, i, x, y, sortFlag;
    filterTable = document.getElementById("fridgeTable");
    sorted = true;
    while (sorted) {
       sorted = false;
       rows = filterTable.rows;
       for (i = 1; i < rows.length - 1; i++) {
            sortFlag = false;
            x = rows[i].getElementsByTagName("TD")[0];
            y = rows[i + 1].getElementsByTagName("TD")[0];
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                sortFlag = true;
                break;
            }
        }
        if (sortFlag) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            sorted = true;
        }
    }
}

function addFoodForm(){
    var addFoodForm = 
    `<form onsubmit="return false;">
        <label for=foodName>What food would you like to add?:</label>
        <br><input type=text id=foodName><br>
        <label for=foodQuantity>How many do you want to add to your fridge?:</label>
        <br><input type=number id=foodQuantity><br>
        <label for=coreQuantity>How many would you like in your fridge at all times?:</label>
        <br><input type=number id=coreQuantity><br>
        <input type="submit" onclick="collapseAddFoodForm();" />
    </form>`
    document.getElementById("addFoodForm").innerHTML = addFoodForm;
    document.getElementById("addFoodForm").style.display = 'block';
}

function addFood(){
    var foodName = document.getElementById("foodName").value;
    var foodQuantity = document.getElementById("foodQuantity").value;
    var coreQuantity = document.getElementById("coreQuantity").value;
    var userId = document.getElementById("userId").innerHTML;
    var xmlhttp = new XMLHttpRequest();
    const json = {
        "deltaFoodQuantity": foodQuantity,
        "newCoreQuantity": coreQuantity
    };
    xmlhttp.open("POST", baseurl + "/user/" + userId + "/food/" + foodName + "/update", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(json));
    alert("Food item added!");
    getFridge(userId);
    if (foodQuantity == ""){
        foodQuantity = 0;
    }
    const food = {
        "foodName": foodName,
        "foodQuantity": foodQuantity,
        "coreQuantity": coreQuantity
    };
    coreAlert(food);
    document.getElementById("missingList").style.display = "none";
}

function deleteFood(button){
    var row = button.parentNode.parentNode.rowIndex;
    var table = document.getElementById("fridgeTable");
    var foodName = table.rows[row].cells[0].innerHTML;
    var userId = document.getElementById("userId").innerHTML;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("DELETE", baseurl + "/user/" + userId + "/food/" + foodName + "/delete", true);
    xmlhttp.send();
    alert("Food item deleted!");
    alert("Food item deleted!");
    getFridge(userId);
}

function deleteUser(){
    var userId = document.getElementById("userId").innerHTML;
    var xmlhttp = new XMLHttpRequest();
    if (confirm('Are you sure you want to delete your account?')) {
        xmlhttp.open("DELETE", baseurl + "/delete-user/" + userId, true);
        xmlhttp.send();
        window.location.reload();
    }
}

function editFoodForm(button){
    const edit = document.createElement("edit");
    edit.id = "editFoodForm";
    var editFoodForm = `<form onsubmit="return false;">
    <label for=foodQuan>How much would you like to +/-?</label>
    <br><input type=number id=foodQuan><br>
    <label for=coreQuan>How many would you like in your fridge at all times?</label>
    <br><input type=number id=coreQuan><br>
    <input type="submit" onclick="collapseEditFoodForm(this);" />
    </form>`;
    edit.innerHTML = editFoodForm;
    button.parentNode.replaceChild(edit, button);
    
}

function editFood(button){
    var row = button.parentNode.parentNode.parentNode.parentNode.rowIndex;
    var table = document.getElementById("fridgeTable");
    var foodName = table.rows[row].cells[0].innerHTML;
    var foodQuantity = document.getElementById("foodQuan").value;
    var foodQForCore = table.rows[row].cells[1].innerHTML;
    var coreQuantity = document.getElementById("coreQuan").value;
    var userId = document.getElementById("userId").innerHTML;
    var xmlhttp = new XMLHttpRequest();
    const json = {
        "deltaFoodQuantity": foodQuantity,
        "newCoreQuantity": coreQuantity
    };
    xmlhttp.open("POST", baseurl + "/user/" + userId + "/food/" + foodName + "/update", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.send(JSON.stringify(json));
    alert("Food item updated!");
    getFridge(userId);
    const food = {
        "foodName": foodName,
        "foodQuantity": foodQForCore,
        "coreQuantity": coreQuantity
    };
    document.getElementById("missingList").style.display = "none";
    coreAlert(food);
}

function coreAlert(food){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", baseurl + "/has-core", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var hasCoreFood = JSON.parse(xmlhttp.responseText);
            if(hasCoreFood == false){
                alert("You are under your core value!");
            }
        }
    };
    xmlhttp.send(JSON.stringify(food));
}

function missingCore(){
    document.getElementById("missingList").style.display = 'block';
    var xmlhttp = new XMLHttpRequest();
    var userId = document.getElementById("userId").innerHTML;
    xmlhttp.open("GET", baseurl + "/missing-core/" + userId, true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var food = JSON.parse(xmlhttp.responseText);

            var list = "<p>You do not have enough of the following items: "
            for (i = 0; i < food.length; i++){
                list += "<p>" + (i+1) + ". " + food[i].foodName + "</p>";
            }
            document.getElementById("missingList").innerHTML = list;
        }
    };
    xmlhttp.send();
}

function logout(){
    window.location.reload();
}

function getRecipes(){
    var xmlhttp = new XMLHttpRequest();
    var ingredients = foodNames[0];
    for(i = 1; i < foodNames.length; i++){
        ingredients += "," + foodNames[i];
    }
    alert(ingredients);
    // xmlhttp.open("GET", baseurl + "/get-recipe/ingredients/" + ingredients, true);
    xmlhttp.open("GET", baseurl + "/get-recipe/ingredients/lemon,sugar", true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            alert("Got it ");
            alert(xmlhttp.responseText);
            var recipes = JSON.parse(xmlhttp.responseText);
            for (var i = 0; i < 2; i++) {
                var ingr = recipes.usedIngredients[i];
                alert(ingr.name);
            }
            // document.getElementById("recipes").innerHTML = "<h1>" + recipes.title +"</h1>";
            }
    };
    xmlhttp.send();
}