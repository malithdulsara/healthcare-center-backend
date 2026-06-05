package com.healthcare.center.service;

import com.healthcare.center.dto.AppointmentDTO;
import com.healthcare.center.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(AppointmentDTO dto);
    List<Appointment> getAppointmentsByPatient(Long patientId);
    Appointment changeStatus(Long appointmentId, String status);
    Appointment getAppointmentById(Long id);
}
