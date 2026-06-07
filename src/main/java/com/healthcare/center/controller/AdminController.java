package com.healthcare.center.controller;

import com.healthcare.center.dto.DoctorRegistrationDTO;
import com.healthcare.center.dto.SpecializationDTO;
import com.healthcare.center.dto.StaffRegistrationDTO;
import com.healthcare.center.entity.Doctor;
import com.healthcare.center.entity.Specialization;
import com.healthcare.center.entity.User;
import com.healthcare.center.service.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private final AdminServiceImpl adminService;

    @PostMapping("/specialization")
    public ResponseEntity<Specialization> addSpecialization(@RequestBody SpecializationDTO dto) {
        return ResponseEntity.ok(adminService.addSpecialization(dto));
    }

    @GetMapping("/specializations")
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return ResponseEntity.ok(adminService.getAllSpecializations());
    }

    @PostMapping("/doctor")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorRegistrationDTO dto) {
        return ResponseEntity.ok(adminService.registerDoctor(dto));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }

    @DeleteMapping("/doctor/{id}")
    public ResponseEntity<String> removeDoctor(@PathVariable Long id) {
        adminService.removeDoctor(id);
        return ResponseEntity.ok("The doctor has been successfully removed from the system!");
    }

    @PostMapping("/staff")
    public ResponseEntity<User> registerStaff(@RequestBody StaffRegistrationDTO dto) {
        return ResponseEntity.ok(adminService.registerStaff(dto));
    }

    @GetMapping("/staff-members")
    public ResponseEntity<List<User>> getAllStaffMembers() {
        return ResponseEntity.ok(adminService.getAllStaffMembers());
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<String> removeStaff(@PathVariable Long id) {
        adminService.removeStaff(id);
        return ResponseEntity.ok("The staff member has been successfully removed from the system!");
    }

    @PutMapping("/doctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody DoctorRegistrationDTO dto) {
        return ResponseEntity.ok(adminService.updateDoctor(id, dto));
    }
}
