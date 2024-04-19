package com.example.cms.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
