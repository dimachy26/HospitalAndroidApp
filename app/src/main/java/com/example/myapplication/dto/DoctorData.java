package com.example.myapplication.dto;

import java.time.LocalDate;

public class DoctorData {
    private Long doctorId;
    private String firstName;
    private String secondName;
    private String lastName;
    private String healthPosition;
    private int seniority;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHealthPosition() {
        return healthPosition;
    }

    public void setHealthPosition(String healthPosition) {
        this.healthPosition = healthPosition;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }
}
