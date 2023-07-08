<?php

require_once 'conn.php';

	if($con) {
	if (isset($_POST['id_user']) && isset($_POST['order_code']) && isset($_POST['payment_pic'])){

		   $id_user = $_POST['id_user'];
		   $order_code = $_POST['order_code'];
		   $payment_pic = $_POST['payment_pic'];

		   $target_dir = "uploads//transfer";
		   if(!file_exists($target_dir)){
			   mkdir($target_dir, 0777,true);
		   }

		   $target_dir = $target_dir."/".$order_code.".png";

		   if(file_put_contents($target_dir,base64_decode($payment_pic))){
		   	$sql=mysqli_query($con,"INSERT INTO payment(id_user, order_code, payment_pic) VALUES('$id_user', '$order_code', 'http://10.0.2.2/foodie/$target_dir')");

			if($sql){
				$response["success"]=1;
			   	$response["message"]="Data has been added";
			   	echo json_encode($response);
			} else {
				$response["success"]= -2;
			   	$response["message"]="Data not added";
			   	echo json_encode($response);
			}
		   } else {
			$response["success"]=0;
			$response["message"]="Data not added";
			echo json_encode($response);
		   }
		} else {
			$response["success"]=-1;
			$response["message"]="Data empty";
			echo json_encode($response);
		}
	} else {
		array_push($response, array(
			'status' => 'FAILED'
		));
	}

?>