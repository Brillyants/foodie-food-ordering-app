<?php

require_once 'conn.php';
        
    $q=mysqli_query($con,"SELECT * FROM food");
    $response = array();

    if(mysqli_num_rows($q)>0){
        $response["data"] = array();
        while($r=mysqli_fetch_array($q)){
            $food = array();
            $food["id_food"] = $r["id_food"];
            $food["name"] = $r["name"];
            $food["pic"] = $r["pic"];
            $food["description"] = $r["description"];
            $food["id_category"] = $r["id_category"];
            $food["price"] = $r["price"];
            array_push($response["data"], $food);
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