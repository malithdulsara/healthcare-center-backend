package com.healthcare.center.repository;

import com.healthcare.center.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByAvailableDate(LocalDate date);

    List<Schedule> findByDoctorId(Long doctorId);
}
