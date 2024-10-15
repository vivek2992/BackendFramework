package com.ms.framework.util;

import com.ms.framework.world.CommonWorld;
import com.ms.framework.world.ServiceWorld;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class ServiceUtil {
    @Autowired
    ServiceWorld sw;
    @Autowired
    CommonWorld cw;

    public Response get(String URL){
        return given().header("Content-type","application/json")
                .get(URL).then().extract().response();
    }

    public Response post(String url, String body){
        return given().header("Content-type","application/json")
                .body(body).post(url).then().extract().response();
    }

    public Response putWithAuth(String url, String body, String token){
        return given().header("Content-type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token="+token)
                .body(body).put(url).then().extract().response();
    }

    public Response patchWithAuth(String url, String body, String token){
        return given().header("Content-type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token="+token)
                .body(body).patch(url).then().extract().response();
    }

    public Response deleteWithAuth(String url, String token){
        return given().header("Content-type","application/json")
                .header("Accept","application/json")
                .header("Cookie","token="+token)
                .delete(url).then().extract().response();
    }
}
