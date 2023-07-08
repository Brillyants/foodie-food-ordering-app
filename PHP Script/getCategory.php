<?php

require_once 'conn.php';

    if(isset($_POST['id_category'])){

        $id_category = $_POST['id_category'];

        $q = mysqli_query($con,"SELECT * FROM food WHERE id_category = '$id_category'");
        $response = array();

    } else {
        $response["success"] = -1;
        $response["message"] = "Data Empty";
        echo json_encode($response);
    }        

    if(mysqli_num_rows($q) > 0){
        $response["data"] = array();
        while($r = mysqli_fetch_array($q)){
            $meat = array();
            $meat["id_food"] = $r["id_food"];
            $meat["name"] = $r["name"];
            $meat["pic"] = $r["pic"];
            $meat["description"] = $r["description"];
            $meat["id_category"] = $r["id_category"];
            $meat["price"] = $r["price"];
            array_push($response["data"], $meat);
        }
        $response["success"] = 1;
        $response["message"] = "Data Cannot be read!";
        echo json_encode($response);
    }
    else{
        $response["success"] = 0;
        $response["message"] = "No Data";
        echo json_encode($response);
    }
?>