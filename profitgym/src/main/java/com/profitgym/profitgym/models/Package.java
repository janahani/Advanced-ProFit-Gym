package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Title;
    private int NumOfMonths;
    private int VisitsLimit;
    private int FreezeLimit;
    private int NumOfInvitations;
    private int NumOfInbodySessions;
    private int NumOfPrivateTrainingSessions;
    private double Price;
    private String isActivated;

    public Package(){

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public int getNumOfMonths() {
        return this.NumOfMonths;
    }

    public void setNumOfMonths(int NumOfMonths) {
        this.NumOfMonths = NumOfMonths;
    }

    public int getVisitsLimit() {
        return this.VisitsLimit;
    }

    public void setVisitsLimit(int VisitsLimit) {
        this.VisitsLimit = VisitsLimit;
    }

    public int getFreezeLimit() {
        return this.FreezeLimit;
    }

    public void setFreezeLimit(int FreezeLimit) {
        this.FreezeLimit = FreezeLimit;
    }

    public int getNumOfInvitations() {
        return this.NumOfInvitations;
    }

    public void setNumOfInvitations(int NumOfInvitations) {
        this.NumOfInvitations = NumOfInvitations;
    }

    public int getNumOfInbodySessions() {
        return this.NumOfInbodySessions;
    }

    public void setNumOfInbodySessions(int NumOfInbodySessions) {
        this.NumOfInbodySessions = NumOfInbodySessions;
    }

    public int getNumOfPrivateTrainingSessions() {
        return this.NumOfPrivateTrainingSessions;
    }

    public void setNumOfPrivateTrainingSessions(int NumOfPrivateTrainingSessions) {
        this.NumOfPrivateTrainingSessions = NumOfPrivateTrainingSessions;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getIsActivated() {
        return this.isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

}
