package com.healthcare.center.dto;

import lombok.Data;

@Data
public class StaffRegistrationDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
