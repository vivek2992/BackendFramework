package com.ms.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.ms.framework.models.Booking;
import com.ms.framework.models.BookingDetails;
import org.springframework.stereotype.Component;

@Component
public class RestfulBookerUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Booking convertToBooking(String response) throws JsonProcessingException{
        return objectMapper.readValue(response, new TypeReference<>() {
        });
    }

    public BookingDetails convertToBookingDetails(String response) throws JsonProcessingException{
        return objectMapper.readValue(response, new TypeReference<>() {
        });
    }

    public BookingDetails updateBookingDetails(BookingDetails details, String json) throws JsonProcessingException {
        ObjectReader updater = objectMapper.readerForUpdating(details);
        return updater.readValue(json);
    }
}
