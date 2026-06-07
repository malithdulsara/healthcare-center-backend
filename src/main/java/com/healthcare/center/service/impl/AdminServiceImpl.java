package com.healthcare.center.service.impl;

import com.healthcare.center.dto.DoctorRegistrationDTO;
import com.healthcare.center.dto.SpecializationDTO;
import com.healthcare.center.dto.StaffRegistrationDTO;
import com.healthcare.center.entity.Doctor;
import com.healthcare.center.entity.Specialization;
import com.healthcare.center.entity.User;
import com.healthcare.center.repository.DoctorRepository;
import com.healthcare.center.repository.SpecializationRepository;
import com.healthcare.center.repository.UserRepository;
import com.healthcare.center.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public Specialization addSpecialization(SpecializationDTO dto) {
        if (specializationRepository.findByName(dto.getName()).isPresent()) {
            throw new RuntimeException("This specialization already exists in the system!");
        }
        Specialization spec = new Specialization();
        spec.setName(dto.getName());
        return specializationRepository.save(spec);
    }

    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Transactional
    public Doctor registerDoctor(DoctorRegistrationDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("This email address is already in use!");
        }

        Specialization spec = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("The provided Specialization ID is invalid!"));

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(User.Role.DOCTOR);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setSpecialization(spec);
        if(dto.getMaxPatientsPerDay() > 0) {
            doctor.setMaxPatientsPerDay(dto.getMaxPatientsPerDay());
        }

        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


    public void removeDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));

        userRepository.deleteById(doctor.getUser().getId());
    }

    public User registerStaff(StaffRegistrationDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("This email address is already in use!");
        }

        User staff = new User();
        staff.setName(dto.getName());
        staff.setEmail(dto.getEmail());
        staff.setPhoneNumber(dto.getPhoneNumber());
        staff.setRole(User.Role.STAFF);
        staff.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(staff);
    }

    public List<User> getAllStaffMembers() {
        return userRepository.findByRole(User.Role.STAFF);
    }

    public void removeStaff(Long staffId) {
        User user = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff member not found!"));

        if (user.getRole() != User.Role.STAFF) {
            throw new RuntimeException("The person you are trying to delete is not a staff member!");
        }

        userRepository.deleteById(staffId);
    }

    @Transactional
    public Doctor updateDoctor(Long doctorId, DoctorRegistrationDTO dto) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));

        User user = doctor.getUser();

        if (!user.getEmail().equals(dto.getEmail()) && userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("This email address is already in use!");
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);

        Specialization spec = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("The provided Specialization ID is invalid!"));

        doctor.setSpecialization(spec);
        doctor.setMaxPatientsPerDay(dto.getMaxPatientsPerDay());

        return doctorRepository.save(doctor);
    }

}