<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
   $email = $_POST['email'];
   $password = $_POST['password'];
  
   // Connect to database
   require_once('db_config.php');
   
   // Execute SQL query
   $sql = "SELECT * FROM users WHERE email='$email' AND password='$password'";
   $result = mysqli_query($con, $sql);
   
   // Check if query was successful
   if (mysqli_num_rows($result) > 0) {
        // Get user information
        $row = mysqli_fetch_assoc($result);
		
        $user_info = array(
			    'family_name' => $row['family_name'],
			    'first_name' => $row['first_name'],
			    'address' => $row['address'],
			    'email' => $row['email'],
			    'age' => $row['age']
	    	);
		
        $user_id = $row['id'];
        
        // Generate a random session token
        $session_token = bin2hex(random_bytes(16));
        
        // Insert session token into sessions table
        $insert_sql = "INSERT INTO sessions (user_id, session_token) VALUES ($user_id, '$session_token')";
        mysqli_query($con, $insert_sql);
        
        // Get the ID of the new session
        $session_id = mysqli_insert_id($con);
        
		if(mysqli_query($con, $insert_sql)){        
		// Return user info, session token and session ID
        echo json_encode(array("status" => "success","user_info" => $user_info, "session_token"=> $session_token, "session_id" => $session_id));
		} else {
			echo json_encode(array("status" => "error", "message" => "Failed to create session"));
		}

    } else {
        echo json_encode(array("status" => "error", "message" => "Invalid email or password"));
    }
    
    // Close database connection
    mysqli_close($con);
}
?>

