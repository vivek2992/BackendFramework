package com.ms.framework.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Booking {
    public int bookingid;
    public BookingDetails booking;
}
