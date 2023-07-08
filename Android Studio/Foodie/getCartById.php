<?php

require_once 'conn.php';

if(isset($_POST['id_user'])){

        $id_user = $_POST['id_user'];

	    $q=mysqli_query($con,"SELECT * FROM cart WHERE id_user = '$id_user'");
	    $response = array();

    } else {
        $response["success"] = -1;
        $response["message"] = "No Data";
        echo json_encode($response);
    }        

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $cart = array();
            $cart["id_cart"] = $r["id_cart"];
            $cart["id_user"] = $r["id_user"];
            $cart["food_name"] = $r["food_name"];
            $cart["number_order"] = $r["number_order"];
            array_push($response["data"], $cart);
        }
        $response["success"] = 1;
        $response["message"] = "Data can be read!";
        echo json_encode($response);
    }
    else{
        $response["success"] = 0;
        $response["message"] = "No Data";
        echo json_encode($response);
    }
?>