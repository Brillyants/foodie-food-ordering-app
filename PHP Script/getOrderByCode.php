<?php

require_once 'conn.php';

    $order_code = $_POST['order_code'];
        
    $q=mysqli_query($con,"SELECT * FROM order_cust WHERE order_code = '$order_code'");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $order_cust = array();
            $order_cust["order_code"] = $r["order_code"];
            $order_cust["id_user"] = $r["id_user"];
            $order_cust["food_name"] = $r["food_name"];
            $order_cust["number_order"] = $r["number_order"];
            $order_cust["order_date"] = $r["order_date"];
            $order_cust["total"] = $r["total"];
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