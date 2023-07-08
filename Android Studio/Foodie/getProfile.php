<?php

require_once 'conn.php';

    if(isset($_POST['username'])){

        $username = $_POST['username'];

        $q = mysqli_query($con,"SELECT * FROM user_data WHERE username = '$username'");
        $response = array();

    } else {
        $response["success"] = -1;
        $response["message"] = "No Data";
        echo json_encode($response);
    }        

    if(mysqli_num_rows($q) > 0){
        $response["data"] = array();
        while($r = mysqli_fetch_array($q)){
            $profile = array();
            $profile["id"] = $r["id"];
            $profile["name"] = $r["name"];
            $profile["phone"] = $r["phone"];
            $profile["email"] = $r["email"];
            $profile["username"] = $r["username"];
            array_push($response["data"], $profile);
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