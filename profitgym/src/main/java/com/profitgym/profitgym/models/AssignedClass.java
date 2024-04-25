package com.profitgym.profitgym.models;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AssignedClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int ClassID;
    private int CoachID;
    private LocalDate Date;
    private LocalTime StartTime;
    private LocalTime EndTime;
    private String isFree;
    private double Price;
    private int NumOfAttendants;
    private int AvailablePlaces;

    public AssignedClass()
    {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClassID() {
        return this.ClassID;
    }

    public void setClassID(int ClassID) {
        this.ClassID = ClassID;
    }

    public int getCoachID() {
        return this.CoachID;
    }

    public void setCoachID(int CoachID) {
        this.CoachID = CoachID;
    }

    public LocalDate getDate() {
        return this.Date;
    }

    public void setDate(LocalDate Date) {
        this.Date = Date;
    }

    public LocalTime getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(LocalTime StartTime) {
        this.StartTime = StartTime;
    }

    public LocalTime getEndTime() {
        return this.EndTime;
    }

    public void setEndTime(LocalTime EndTime) {
        this.EndTime = EndTime;
    }

    public String getIsFree() {
        return this.isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getNumOfAttendants() {
        return this.NumOfAttendants;
    }

    public void setNumOfAttendants(int NumOfAttendants) {
        this.NumOfAttendants = NumOfAttendants;
    }

    public int getAvailablePlaces() {
        return this.AvailablePlaces;
    }

    public void setAvailablePlaces(int AvailablePlaces) {
        this.AvailablePlaces = AvailablePlaces;
    }




}
