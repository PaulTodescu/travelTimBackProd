package com.travelTim.business;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "day_schedule")
public class BusinessDaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeekDay day;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Column(nullable = false)
    private Boolean closed;

    @ManyToMany(mappedBy = "schedule")
    @JsonIgnore
    private Set<BusinessEntity> businesses = new HashSet<>();

    public BusinessDaySchedule() {
    }

    public BusinessDaySchedule(Long id, WeekDay day, String startTime, String endTime,
                               Boolean closed) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.closed = closed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Set<BusinessEntity> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(Set<BusinessEntity> businesses) {
        this.businesses = businesses;
    }
}
