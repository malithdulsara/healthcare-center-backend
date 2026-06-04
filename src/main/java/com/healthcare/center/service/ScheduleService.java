package com.healthcare.center.service;

import com.healthcare.center.dto.ScheduleDTO;
import com.healthcare.center.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(ScheduleDTO dto);
    List<Schedule> getSchedulesByDoctor(Long doctorId);
    List<Schedule> getSchedulesByDate(LocalDate date);
    List<Schedule> getAllSchedules();
    void deleteSchedule(Long scheduleId);
}
