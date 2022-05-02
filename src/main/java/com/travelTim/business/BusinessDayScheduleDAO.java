package com.travelTim.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessDayScheduleDAO extends JpaRepository<BusinessDaySchedule, Long> {

    Optional<BusinessDaySchedule> findBusinessDayScheduleByDayAndStartTimeAndEndTimeAndClosed(
            WeekDay day, String startTime, String endTime, Boolean closed
    );

    void deleteBusinessDayScheduleById(Long id);

}
