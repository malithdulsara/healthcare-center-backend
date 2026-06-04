package com.healthcare.center.dto;

import lombok.Data;

@Data
public class DoctorRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    private Long specializationId;
    private int maxPatientsPerDay;
}
