<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    
    $email = $_POST['email'];
    $family_name = $_POST['family_name'];
    $first_name = $_POST['first_name'];
    $password = $_POST['password'];
    $age = $_POST['age'];
    $address = $_POST['address'];
    
    require_once('db_config.php');
    
    $sql = "INSERT INTO users (email, family_name, first_name, password, age, address) VALUES ('$email', '$family_name','$first_name','$password', '$age', '$address')";
    $run = "select from user where email = $email";
    if(mysqli_query($con, $sql)){
        $user_id = mysqli_insert_id($con);
		
		$session_token = bin2hex(random_bytes(16));
		
		$insert_sql = "INSERT INTO sessions (user_id, session_token) VALUES ($user_id, '$session_token')";
		
		$session_id = mysqli_insert_id($con);
        $user_info = array("first_name" => $first_name, "family_name" => $family_name,  "email" => $email, "password" => $password, "age" => $age, "address" => $address);
		if(mysqli_query($con, $insert_sql)){
			echo json_encode(array("status" => "success", "session_token" => $session_token, "session_id" => $session_id,"user_info" => $user_info));
		} else {
			echo json_encode(array("status" => "error", "message" => "Failed to create session"));
		}
	} else {
		echo json_encode(array("status" => "error", "message" => "Failed to create user"));
	}
    
    mysqli_close($con);
}

?>
         

