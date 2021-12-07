var baseurl = "http://localhost:8080/api/v1/fridge"

            function login(){
            var email = document.getElementById("email").value;
            var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("GET", baseurl + "/get-user/" + email, true);
                xmlhttp.onreadystatechange = function() {
                    if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
                        var user = JSON.parse(xmlhttp.responseText);
                        document.getElementById("userName").innerHTML = user.name;
                        document.getElementById("userId").innerHTML = user.userId;
                        document.getElementById("userEmail").innerHTML = user.email;
                        getFridgeAll(user.userId);
                    }
                    else{
                        System.out.println("Error")
                    }
                };
                xmlhttp.send();
            }

            function getFridgeAll(userId){
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("GET", baseurl + "/get-fridge/" + userId, true);
                xmlhttp.onreadystatechange = function() {
                    if(xmlhttp.readyState === 4 && xmlhttp.status === 200){
                        var food = JSON.parse(xmlhttp.responseText);
                        var tbltop = "<tr><th>Food Name</th><th>Food Quantity</th><th>Core Quantity</th></tr>";
                        
                        var main = "";
                        for (i = 0; i < food.length; i++){
                            main += "<tr><td>" + food[i].foodName +"</td><td>" + food[i].foodQuantity +"</td><td>" + food[i].coreQuantity;
                        }
                        var tbl = tbltop + main;
                        document.getElementById("fridgeTable").innerHTML = tbl;
                        var addFoodButton = "<button onclick = addFoodForm()>Add Food Item</button>";
                        document.getElementById("addFood").innerHTML = addFoodButton;

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
                    <input type="submit" onclick="addFood();" />
                </form>`
                document.getElementById("addFoodForm").innerHTML = addFoodForm;
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
                var table = document.getElementById("fridgeTable");
                table.refresh();
                getFridgeAll(userId);

            }