package com.ms.framework.stepdefinitions;

import com.ms.framework.models.Booking;
import com.ms.framework.models.BookingDetails;
import com.ms.framework.util.CommonUtils;
import com.ms.framework.util.RestfulBookerUtils;
import com.ms.framework.util.ServiceUtil;
import com.ms.framework.world.RestfulBookerWorld;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RestfulBooker implements En {

    @Autowired
    ServiceUtil su;
    @Autowired
    RestfulBookerWorld rbf;
    @Autowired
    RestfulBookerUtils rbu;
    @Autowired
    CommonUtils cu;

    @Value("${restfulbookerbaseurl}")
    String baseURL;
    @Value("${restfulBookerTestDataFolder}")
    private String testDataFolder;
    Response response;
    JSONArray responseJSONArray;
    Booking booking;
    BookingDetails createBookingDetails,updateRequest;
    String token;
    private String testDataFile = "RestfulBooker.json";
    List<Integer> bookingIds = new ArrayList<>();
    public RestfulBooker(){
        When("^I hit the health check api$",()->{
           response = su.get(baseURL+rbf.getHealthCheck());
        });

        Then("^I should get 201 Response$",()->{
            Assert.assertEquals(201,response.getStatusCode());
        });

        Given("^I have the list of existing Bookings$",()->{
            response = su.get(baseURL+rbf.getGetBookingIds());
            Assert.assertEquals(200,response.getStatusCode());
            responseJSONArray = new JSONArray(response.body().asString());
            for(int i=0;i<responseJSONArray.length();i++){
                bookingIds.add(responseJSONArray.getJSONObject(i).getInt("bookingid"));
            }
        });

        When("^I Create a new booking using \"([^\"]*)\"$",(String testDataId)->{
            String payload = cu.readJSONData(testDataFolder+testDataFile,testDataId);
            createBookingDetails = rbu.convertToBookingDetails(payload);
            response = su.post(baseURL+rbf.getCreateBooking(),payload);
            Assert.assertEquals(200,response.getStatusCode());
            booking = rbu.convertToBooking(response.body().asString());
            Assert.assertEquals(createBookingDetails,booking.getBooking());
        });

        Then("^Verify that its not in the existing Bookings$",()->{
            Assert.assertFalse(bookingIds.contains(booking.getBookingid()));
        });

        Then("^Verify that its added to the list of Bookings$",()->{
            bookingIds.clear();
            response = su.get(baseURL+rbf.getGetBookingIds());
            Assert.assertEquals(200,response.getStatusCode());
            responseJSONArray = new JSONArray(response.body().asString());
            for(int i=0;i<responseJSONArray.length();i++){
                bookingIds.add(responseJSONArray.getJSONObject(i).getInt("bookingid"));
            }
            Assert.assertTrue(bookingIds.contains(booking.getBookingid()));
        });

        Then("^Verify that the booking has the correct details$",()->{
            response = su.get(baseURL+rbf.getGetBookingId()+booking.getBookingid());
            BookingDetails getBookingDetails = rbu.convertToBookingDetails(response.getBody().asString());
            Assert.assertEquals(createBookingDetails,getBookingDetails);
            System.out.println(booking.getBookingid());
        });

        Given("^I Create Token to be used for PUT and DELETE$",()->{
            JSONObject payload = new JSONObject();
            payload.put("username",rbf.getUserid());
            payload.put("password",rbf.getPassword());
            response = su.post(baseURL+rbf.getAuth(),payload.toString());
            token = new JSONObject(response.body().asString()).getString("token");
        });

        When("^I Update the Booking with \"([^\"]*)\"$",(String testDataId)->{
            String payload = cu.readJSONData(testDataFolder+testDataFile,testDataId);
            updateRequest = rbu.convertToBookingDetails(payload);
            response = su.putWithAuth(baseURL+rbf.getGetBookingId()+booking.getBookingid(),payload,token);
            Assert.assertEquals(200,response.getStatusCode());
            BookingDetails updateResponse = rbu.convertToBookingDetails(response.body().asString());
            Assert.assertEquals(updateRequest,updateResponse);
        });

        Then("^Verify that the booking has been update with the correct details$",()->{
            response = su.get(baseURL+rbf.getGetBookingId()+booking.getBookingid());
            BookingDetails getBookingDetails = rbu.convertToBookingDetails(response.getBody().asString());
            Assert.assertEquals(updateRequest,getBookingDetails);
        });

        When("^I Partially Update the Booking with \"([^\"]*)\"$",(String testDataId)->{
            String payload = cu.readJSONData(testDataFolder+testDataFile,testDataId);
            response = su.patchWithAuth(baseURL+rbf.getGetBookingId()+booking.getBookingid(),payload,token);
            Assert.assertEquals(200,response.getStatusCode());
            BookingDetails updateResponse = rbu.convertToBookingDetails(response.body().asString());
            updateRequest = rbu.updateBookingDetails(updateRequest,payload);
            Assert.assertEquals(updateRequest,updateResponse);
        });

        When("^I Delete the booking$",()->{
            response = su.deleteWithAuth(baseURL+rbf.getGetBookingId()+booking.getBookingid(),token);
            Assert.assertEquals(201,response.getStatusCode());
            Assert.assertEquals("Created",response.body().asString());
        });

        Then("^I Verify that the booking has been deleted successfully$",()->{
            response = su.get(baseURL+rbf.getGetBookingId()+booking.getBookingid());
            Assert.assertEquals(404,response.getStatusCode());
            Assert.assertEquals("Not Found",response.body().asString());
            response = su.get(baseURL+rbf.getGetBookingIds());
            Assert.assertEquals(200,response.getStatusCode());
            responseJSONArray = new JSONArray(response.body().asString());
            bookingIds.clear();
            for(int i=0;i<responseJSONArray.length();i++){
                bookingIds.add(responseJSONArray.getJSONObject(i).getInt("bookingid"));
            }
            Assert.assertFalse(bookingIds.contains(booking.getBookingid()));
        });

    }
}
