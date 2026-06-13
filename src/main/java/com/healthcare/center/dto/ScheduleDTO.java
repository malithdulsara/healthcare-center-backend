package com.healthcare.center.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDTO {
    private Long doctorId;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomNumber;
}
