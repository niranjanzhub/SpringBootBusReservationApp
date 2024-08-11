package org.nirz.reservationApp.Dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BusResponse {
    private String name;
    private String busNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfDeparture;
    
    private String from;
    private String to;
    private int numberOfSeats;
    private int adminId;
}
