package com.healthcare.center.service.impl;

import com.healthcare.center.dto.AppointmentDTO;
import com.healthcare.center.entity.Appointment;
import com.healthcare.center.entity.Schedule;
import com.healthcare.center.entity.User;
import com.healthcare.center.repository.AppointmentRepository;
import com.healthcare.center.repository.ScheduleRepository;
import com.healthcare.center.repository.UserRepository;
import com.healthcare.center.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Appointment bookAppointment(AppointmentDTO dto) {
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found!"));

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("The specified schedule could not be found!"));

        int existingCount = appointmentRepository.countByScheduleId(dto.getScheduleId());

        if (existingCount >= schedule.getDoctor().getMaxPatientsPerDay()) {
            throw new RuntimeException("Sorry! This doctor's clinic is fully booked for that day.");
        }

        int nextAppointmentNumber = existingCount + 1;

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setSchedule(schedule);
        appointment.setAppointmentNumber(nextAppointmentNumber);
        appointment.setStatus(Appointment.AppointmentStatus.PENDING);
        appointment.setPaymentStatus(Appointment.PaymentStatus.UNPAID);

        String uniqueBarcodeToken = "HC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase() + "-" + nextAppointmentNumber;
        appointment.setBarcodeData(uniqueBarcodeToken);

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Appointment changeStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found!"));

        try {
            appointment.setStatus(Appointment.AppointmentStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("The provided status is invalid!");
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found!"));
    }
}
