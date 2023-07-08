<?php

require_once 'conn.php';

    $food_name = $_POST['food_name'];
        
    $q=mysqli_query($con,"SELECT pic FROM food WHERE name = '$food_name'");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $order_cust = array();
            $order_cust["pic"] = $r["pic"];
            array_push($response["data"], $order_cust);
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