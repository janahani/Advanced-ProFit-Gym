package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Memberships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int ClientID;
    private int PackageID;
    private int startDate;
    private int endDate;
    private int numOfVisits;
    private int numOfInvitations;
    private int numOfInbodySessions;
    private int numOfPrivateTrainingSessions;
    private int numOfFreeze;
    private int freezed;
    private boolean isActivated;

    public Memberships() {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClientID() {
        return this.ClientID;
    }

    public void setClientID(int ClientID) {
        this.ClientID = ClientID;
    }

    public int getPackageID() {
        return this.PackageID;
    }

    public void setPackageID(int PackageID) {
        this.PackageID = PackageID;
    }

    public int getstartDate() {
        return this.startDate;
    }

    public void setstartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getendDate() {
        return this.endDate;
    }

    public void setendDate(int endDate) {
        this.endDate = endDate;
    }

    public int getnumOfVisits() {
        return numOfVisits;
    }

    public void setnumOfVisits(int numOfVisits) {
        this.numOfVisits = numOfVisits;
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

    public int getnumOfFreeze() {
        return numOfFreeze;
    }

    public void setnumOfFreeze(int numOfFreeze) {
        this.numOfFreeze = numOfFreeze;
    }

    public int getfreezed() {
        return freezed;
    }

    public void setfreezed(int freezed) {
        this.freezed = freezed;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setVisitsLimited(boolean isActivated) {
        this.isActivated = isActivated;
    }

}
