package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;

@Entity
public class Memberships {

    private String title;
    private boolean isVisitsLimited;
    private int numOfInvitations;
    private int numOfInbodySessions;
    private int numOfPrivateTrainingSessions;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean isVisitsLimited() {
        return isVisitsLimited;
    }
    public void setVisitsLimited(boolean isVisitsLimited) {
        this.isVisitsLimited = isVisitsLimited;
    }
    public int getNumOfInvitations() {
        return numOfInvitations;
    }
    public void setNumOfInvitations(int numOfInvitations) {
        this.numOfInvitations = numOfInvitations;
    }
    public int getNumOfInbodySessions() {
        return numOfInbodySessions;
    }
    public void setNumOfInbodySessions(int numOfInbodySessions) {
        this.numOfInbodySessions = numOfInbodySessions;
    }
    public int getNumOfPrivateTrainingSessions() {
        return numOfPrivateTrainingSessions;
    }
    public void setNumOfPrivateTrainingSessions(int numOfPrivateTrainingSessions) {
        this.numOfPrivateTrainingSessions = numOfPrivateTrainingSessions;
    }

    
}

