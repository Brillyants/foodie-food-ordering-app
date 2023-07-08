<?php

require_once 'conn.php';
        
    $q=mysqli_query($con,"SELECT * FROM admin_data");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $admin = array();
            $admin["id"] = $r["id"];
            $admin["name"] = $r["name"];
            $admin["phone"] = $r["phone"];
            $admin["email"] = $r["email"];
            $admin["username"] = $r["username"];
            array_push($response["data"], $admin);
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