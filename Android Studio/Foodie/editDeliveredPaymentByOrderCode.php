<?php

require_once 'conn.php';

    if(isset($_POST['order_code'])){

        $order_code = $_POST['order_code'];

        $q = mysqli_query($con,"UPDATE payment SET is_delivered = 1 WHERE order_code = '$order_code'");
        $response = array();   

        if($q){
            $response["success"]=1;
            $response["message"]="Data Updated!";
            echo json_encode($response);
        }else{
            $response["success"]=0;
            $response["message"]="Data Failed to Update!";
            echo json_encode($response);
        }
    }else{
        $response["success"]=-1;
        $response["message"]="Data Empty!";
        echo json_encode($response);
    }
?>