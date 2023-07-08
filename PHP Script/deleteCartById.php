<?php

require_once 'conn.php';

    if(isset($_POST['id_user'])){
        $id_user=$_POST['id_user'];

        $q=mysqli_query($con,"DELETE FROM cart WHERE id_user ='$id_user'");
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