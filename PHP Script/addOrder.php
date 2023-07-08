<?php

require_once 'conn.php';

if($con) {
	$order_code = $_POST['order_code'];
	$id_user = $_POST['id_user'];
	$food_name = $_POST['food_name'];
	$number_order = $_POST['number_order'];
	$order_date = $_POST['order_date'];
	$total = $_POST['total'];
	$address = $_POST['address'];

	$insert = "INSERT INTO order_cust(order_code, id_user, food_name, number_order, order_date, total, address) VALUES('$order_code', '$id_user', '$food_name', '$number_order', '$order_date', '$total', '$address')";

	if($order_code != "" && $id_user != "" && $food_name != "" && $number_order != "" && $order_date != "" && $total != "" && $address != "") {
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