<?php

require_once 'conn.php';

    if(isset($_POST['username']) && isset($_POST['name']) && isset($_POST['phone']) && isset($_POST['email'])){

        $username = $_POST['username'];
        $name = $_POST['name'];
        $phone = $_POST['phone'];
        $email = $_POST['email'];

        $q = mysqli_query($con,"UPDATE admin_data SET name = '$name', phone = '$phone', 
            email = '$email' WHERE username = '$username'");
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