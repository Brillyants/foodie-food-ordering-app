<?php

require_once 'conn.php';

    if(isset($_POST['order_code'])){

        $order_code = $_POST['order_code'];

        $q = mysqli_query($con,"SELECT DISTINCT order_date, total, address FROM order_cust WHERE order_code = '$order_code'");
        $response = array();

    } else {
        $response["success"] = -1;
        $response["message"] = "No Data";
        echo json_encode($response);
    }        

    if(mysqli_num_rows($q) > 0){
        $response["data"] = array();
        while($r = mysqli_fetch_array($q)){
            $order = array();
            $order["order_date"] = $r["order_date"];
            $order["total"] = $r["total"];
            $order["address"] = $r["address"];
            array_push($response["data"], $order);
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