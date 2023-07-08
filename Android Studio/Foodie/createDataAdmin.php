<?php

require_once 'conn.php';

if($con) {
	$name = $_POST['name'];
	$phone = $_POST['phone'];
	$email = $_POST['email'];
	$username = $_POST['username'];
	$password = $_POST['password'];

	$check_username = mysqli_query($con, "SELECT username FROM admin_data where username = '$username' ");
	$response = array();

	if(mysqli_num_rows($check_username) > 0){
		array_push($response, array(
			'status' => 'Username Already Exists'
		));
	}
	else{
		$insert = "INSERT INTO admin_data(name, phone, email, username, password) VALUES('$name', '$phone', '$email', '$username', '$password')";

		if($name != "" && $phone != "" && $email != "" && $username != "" && $password != "") {
			$result = mysqli_query($con, $insert);

			if($result) {
				array_push($response, array(
					'status' => 'OK'
				));
			} else {
				array_push($response, array(
					'status' => 'FAILED'
				));
			}
		} else {
			array_push($response, array(
				'status' => 'FAILED'
			));
		}
	}
} else {
	array_push($response, array(
		'status' => 'FAILED'
	));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);

?>