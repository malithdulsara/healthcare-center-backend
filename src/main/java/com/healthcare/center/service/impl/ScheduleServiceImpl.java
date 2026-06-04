package com.healthcare.center.service.impl;

import com.healthcare.center.dto.ScheduleDTO;
import com.healthcare.center.entity.Doctor;
import com.healthcare.center.entity.Schedule;
import com.healthcare.center.repository.DoctorRepository;
import com.healthcare.center.repository.ScheduleRepository;
import com.healthcare.center.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public Schedule createSchedule(ScheduleDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("The doctor could not be found!"));

        if (dto.getAvailableDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Schedules cannot be created for past dates!");
        }

        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new RuntimeException("The start time must be earlier than the end time!");
        }

        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setAvailableDate(dto.getAvailableDate());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setCurrentLiveNumber(0);

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getSchedulesByDoctor(Long doctorId) {
        return scheduleRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Schedule> getSchedulesByDate(LocalDate date) {
        return scheduleRepository.findByAvailableDate(date);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new RuntimeException("The schedule could not be found!");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}

