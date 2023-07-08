<?php

require_once 'conn.php';

if($con) {
	$id_user = $_POST['id_user'];
	$food_name = $_POST['food_name'];
	$number_order = $_POST['number_order'];

	$insert = "INSERT INTO cart(id_user, food_name, number_order) VALUES('$id_user', '$food_name', '$number_order')";

	if($id_user != "" && $food_name != "" && $number_order != "") {
		$result = mysqli_query($con, $insert);
		$response = array();

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
} else {
	array_push($response, array(
		'status' => 'FAILED'
	));
}

echo json_encode(array("server_response" => $response));
mysqli_close($con);
	
?>