package com.ms.framework.world;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RestfulBookerWorld {
    @Value("${healthCheck:}")
    private String healthCheck;
    @Value("${getBookingIds:}")
    private String getBookingIds;
    @Value("${createBooking:}")
    private String createBooking;
    @Value("${getBookingId:}")
    private String getBookingId;
    @Value("${auth:}")
    private String auth;
    @Value("${userid:}")
    private String userid;
    @Value("${password:}")
    private String password;
}
