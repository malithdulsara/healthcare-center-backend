package com.healthcare.center.service;

import com.healthcare.center.dto.DoctorRegistrationDTO;
import com.healthcare.center.dto.SpecializationDTO;
import com.healthcare.center.dto.StaffRegistrationDTO;
import com.healthcare.center.entity.Doctor;
import com.healthcare.center.entity.Specialization;
import com.healthcare.center.entity.User;

import java.util.List;

public interface AdminService {
    Specialization addSpecialization(SpecializationDTO dto);
    List<Specialization> getAllSpecializations();
    Doctor registerDoctor(DoctorRegistrationDTO dto);
    List<Doctor> getAllDoctors();
    void removeDoctor(Long doctorId);
    User registerStaff(StaffRegistrationDTO dto);
    List<User> getAllStaffMembers();
    void removeStaff(Long staffId);
}
