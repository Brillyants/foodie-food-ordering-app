<?php

require_once 'conn.php';

    if(isset($_POST['order_code'])){
        $order_code=$_POST['order_code'];

        $q=mysqli_query($con,"DELETE FROM payment WHERE order_code ='$order_code'");
        $response=array();

        if($q){
            $response["success"]=1;
            $response["message"]="Data deleted";
            echo json_encode($response);
        }else{
            $response["success"]=0;
            $response["message"]="Data failed to delete!";
            echo json_encode($response);
        }
    }else{
        $response["success"]=-1;
        $response["message"]="No Data";
        echo json_encode($response);
    }
?>