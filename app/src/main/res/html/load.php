<?php
require_once('db_config.php');
$sql = "select * from food";
$result = mysqli_query($con, $sql);
if($result->num_rows >0){
    $objects = array();
    while($row = $result->fetch_assoc()){
        $object = array(
            'name' => $row['name'],
            'description' => $row['description'],
            'image' => $row['image'],
            'price' => $row['price']   
        );
        array_push($objects, $object);
    }
    echo json_encode($objects);
}else{
    echo "No objects found";
}
$con->close();
?>
