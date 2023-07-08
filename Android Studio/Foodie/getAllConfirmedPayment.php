<?php

require_once 'conn.php';
        
    $q=mysqli_query($con,"SELECT * FROM payment WHERE is_paid = 1 AND is_delivered = 0");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $payment = array();
            $payment["id_user"] = $r["id_user"];
            $payment["order_code"] = $r["order_code"];
            $payment["payment_pic"] = $r["payment_pic"];
            array_push($response["data"], $payment);
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