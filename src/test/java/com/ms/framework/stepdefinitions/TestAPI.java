package com.ms.framework.stepdefinitions;


import com.ms.framework.util.ServiceUtil;
import com.ms.framework.world.ServiceWorld;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class TestAPI implements En {

    @Autowired
    ServiceUtil su;
    @Autowired
    ServiceWorld sw;

    @Value("${baseurl}")
    String baseURL;
    Response response;

    public TestAPI(){
        When("^User hits the API$",()-> {
            response=su.get(baseURL+sw.getEndpoint());
        });

        Then("^Verify that API gives 200 response code$",()->{
            System.out.println(response.getBody().asPrettyString());
            Assert.assertEquals(200, response.getStatusCode());
        });
    }
}
