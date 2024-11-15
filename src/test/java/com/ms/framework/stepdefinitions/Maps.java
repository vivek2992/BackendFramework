package com.ms.framework.stepdefinitions;

import com.ms.framework.util.ServiceUtil;
import com.ms.framework.world.MapsWorld;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Maps implements En {

    @Autowired
    ServiceUtil su;
    @Autowired
    MapsWorld mw;

    @Value("${placesbaseurl}")
    String baseURL;

    Response response;
    String latitude, longitude;

    public Maps(){
        When("^I hit the search places api with \"([^\"]*)\"$",(String testDataId)->{
            JSONObject payload = new JSONObject();
            payload.put("textQuery",testDataId);
            Map<String,String> headers = new HashMap<>();
            headers.put("Content-Type","application/json");
            headers.put("X-Goog-Api-Key",mw.getMapskey());
            headers.put("X-Goog-FieldMask","places.formattedAddress,places.location.latitude,places.location.longitude");
            headers.put("Accept","application/json");
            response = su.postWithKey(baseURL+ mw.getTextsearch(),payload.toString(),headers);
            Assert.assertEquals(200,response.getStatusCode());
        });

        Then("^I should get coordinates of the location$",()->{
            JSONArray places = new JSONObject(response.getBody().asString()).getJSONArray("places");
            latitude=places.getJSONObject(0).getJSONObject("location").getString("latitude");
            longitude=places.getJSONObject(0).getJSONObject("location").getString("longitude");
            System.out.println(latitude+"|"+longitude);
        });
    }
}
