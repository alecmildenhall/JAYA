var baseurl = "http://localhost:8080/api/v1/fridge"

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

function login(){
var email = document.getElementById("email").value;
var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseurl + "/get-user/" + email, true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var user = JSON.parse(xmlhttp.responseText);
            if (user.userId === -1){
                showLoginForm();
                alert("User does not exist. Try a different email.")
            }
            else{
                document.getElementById("userName").innerHTML = "Logged in as: " + user.name;
                document.getElementById("userId").innerHTML = user.userId;
                document.getElementById("userId").style.display = "none";
                document.getElementById("userEmail").innerHTML = user.email;
                document.getElementById("userEmail").style.display = "none";
                getFridge(user.userId);
            }
        }
        else{
            System.out.println("Error")
        }
    };
    xmlhttp.send();
}

function getFridge(userId){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseurl + "/get-fridge/" + userId, true);
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var food = JSON.parse(xmlhttp.responseText);
            var tbltop = "<tr><th>Food Name</th><th>Food Quantity</th><th>Core Quantity</th></tr>";
            
            var main = "";
            for (i = 0; i < food.length; i++){
                main += "<tr><td>" + food[i].foodName +"</td><td>" + food[i].foodQuantity 
                +"</td><td>" + food[i].coreQuantity 
                + `</td><td><button id = "deleteButton" onclick = deleteFood(this)>Delete</button>`
                + `</td><td><button id = "editButton" onclick = editFoodForm(this)>Edit</button>`;
            }
            var tbl = tbltop + main;
            document.getElementById("fridgeTable").innerHTML = tbl;
            var addFoodButton = "<button onclick = addFoodForm()>Add Food Item</button>";
            document.getElementById("addFood").innerHTML = addFoodButton;
            var missingCoreButton = "<button onclick = missingCore()>What am I missing?</button>";
            document.getElementById("missingCore").innerHTML = missingCoreButton;
        }
        else{
            System.out.println("Error")
        }
    };
    xmlhttp.send();
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
        "foodQuantity": foodQuantity,
        "coreQuantity": coreQuantity
    };
    coreAlert(food);
    document.getElementById("missingList").style.display = "none";
}



function coreAlert(food){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", baseurl + "/has-core", true);
    xmlhttp.setRequestHeader('Content-Type', 'application/json');
    xmlhttp.onreadystatechange = function() {
        if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
            var bool = JSON.parse(xmlhttp.responseText);
            if(bool == false){
                alert("You are under your core value!")
            }
        }
        else{
            System.out.println("Error")
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

            var list = ""
            for (i = 0; i < food.length; i++){
                list += "<p>" + food[i].foodName + "</p>";
            }
            document.getElementById("missingList").innerHTML = list;
        }
        else{
            System.out.println("Error")
        }
    };
    xmlhttp.send();
}