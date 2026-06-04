package com.healthcare.center.repository;

import com.healthcare.center.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);

    int countByScheduleId(Long scheduleId);
}
