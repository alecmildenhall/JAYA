# Fridge API

Fridge API is a service that currently allows users to maintain the contents of the
“fridges”, allowing this personal list of food items to be added to, deleted from, or updated 
in any way. Users can also keep a record a "core ingredients", that is food items of a specified quantity
that they want to always have in their fridge. A list of items in a specified user's fridge that differs from their desired core quanitites 
can also be generated. Users can be added to and deleted (including their fridge) at any time.

## Build

Clone this github repo into an accessible location in your file system and open up the JAYA folder in your favorite IDE (we reccomend IntelliJ). 

```bash
git clone https://github.com/ambermildenhall/JAYA.git
```

Build all files in the JAYA/Fridge. Using your preferred IDE, create a connection to a PostgreSQL data source. For the data source, provide
the same url as spring.datasource.url, username as spring.datasource.username=jaya_team
, and password as spring.datasource.password in application.properties (shown below). Apply this data source.

```bash
spring.datasource.url=jdbc:postgresql://35.226.5.196:5432/jaya
spring.datasource.username=jaya_team
spring.datasource.password=flower-chocolate-picnic-wall
```

## Run

Using your preferred IDE, run FridgeApplication.java. In your terminal enter the following command:

```bash
/Applications/Postgres.app/Contents/Versions/13/bin/psql -h 35.226.5.196 -U jaya_team jaya
```
From here, when prompted "Password for user jaya_team:", provide the same password as given in application.properties 
by spring.datasource.password

From your terminal, view the list of relations with the following command:

```bash
\d
```

To view the contents of the food table, enter the following command into your terminal:
```bash
SELECT * FROM food;
```
To view the contents of the users table, enter the following command into your terminal:
```bash
SELECT * FROM users;
```

## Test
Install the Postman app or access Postman through your web browser [here](https://www.postman.com/downloads/).

### getFridge
To get a specific user's fridge, create a GET request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/get-fridge
```
Add an number to represent the userId of the desired user in raw JSON format under the Body and send the request. An example is shown below:
```bash
10
```
This request will return a list of the specified user's items.

### getFridgeAll
To get a specific user's fridge, create a GET request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/get-all
```
This request will return a list of all user's fridges.

### missingCore
To get a list of foods that are below their core quantity amount of a specified user, create a GET request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/missing-core
```
Add an number to represent the userId of the desired user in raw JSON format under the Body and send the request. An example is shown below:
```bash
10
```
This request will return a list of items that are below their core quantity amount of the specified user's fridge.

### deleteItem
To delete a specific item of a specified user, create a DELETE request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/user/{userId}/food/{foodName}/delete
```
{userId} can be replaced with a specified user ID and {foodName} can be replaced with any food currently in the fridge of the specified user. An example is shown below:
```bash
localhost:8080/api/v1/fridge/user/10/food/{foodName}/carrot
```
This request will delete the specified item of the specified user.

### addUser
To add a user to the users table, create a POST request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/add-user
```
A User object that represents the desired user to be added must be given in raw JSON format under Body. An example is provided below:
```bash
{
    "userID": 12,
    "email": "alanturing@columbia.edu",
    "name": "Alan Turing"
}
```
This request will add a user to the users table.

### deleteUser
To delete a user from the users table and remove all their fridge items from food, create a DELETE request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/delete-user
```
Add an number to represent the userId of the desired user in raw JSON format under the Body and send the request. An example is shown below:
```bash
10
```

### updateFood
To update the quantity of a specific user's food, create a POST request in Postman with the following request url:
```bash
localhost:8080/api/v1/fridge/user/{userId}/food/{foodName}/update
```
{userId} can be replaced with a specified user ID and {foodName} can be replaced with any food currently in the fridge of the specified user. An example is shown below:
```bash
localhost:8080/api/v1/fridge/user/40/food/apple/update
```
A UpdateQuantity object that represents the desired quantity change must be given in raw JSON format under Body. An example is provided below:
```bash
{
    "deltaFoodQuantity": -8,
    "newCoreQuantity": 0
}
```

# API Documentation

## Get a specified user's fridge

### GET /api/v1/fridge/get-fridge
