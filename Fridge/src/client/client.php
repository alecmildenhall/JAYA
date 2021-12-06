<!DOCTYPE html>
<html>
<head>
<title>Table with database</title>
<style>
table {
border-collapse: collapse;
width: 100%;
color: #588c7e;
font-family: monospace;
font-size: 25px;
text-align: left;
}
th {
background-color: #588c7e;
color: white;
}
tr:nth-child(even) {background-color: #f2f2f2}
</style>
</head>
<body>
<table>
<tr>
<th>userId</th>
<th>foodName</th>
<th>foodQuantity</th>
<th>coreQuantity</th>
</tr>
<?php
$conn = mysqli_connect("35.226.5.196", "jaya_team", "flower-chocolate-picnic-wall", "jaya");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql = "SELECT food.user_id, food.food_name, food.food_quantity, food.core_quantity FROM food";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
// output data of each row
while($row = $result->fetch_assoc()) {
    echo "<tr><td>" . $row["userId"]. "</td><td>" . $row["foodName"] . "</td><td>" . $row["foodQuantity"] . "</td><td>"
    . $row["coreQuantity"]. "</td></tr>";
    }
    echo "</table>";
} 
else { 
    echo "0 results"; 
}
$conn->close();
?>
</table>
</body>
</html>