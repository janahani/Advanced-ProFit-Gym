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
    private int StartDate;
    private int EndDate;
    private int VisitsCount;
    private int InvitationsCount;
    private int InbodySessionsCount;
    private int PrivateTrainingSessionsCount;
    private int FreezeCount;
    private int Freezed;
    private String isActivated;

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

    public int getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(int StartDate) {
        this.StartDate = StartDate;
    }

    public int getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(int EndDate) {
        this.EndDate = EndDate;
    }

    public int getVisitsCount() {
        return this.VisitsCount;
    }

    public void setVisitsCount(int VisitsCount) {
        this.VisitsCount = VisitsCount;
    }

    public int getInvitationsCount() {
        return this.InvitationsCount;
    }

    public void setInvitationsCount(int InvitationsCount) {
        this.InvitationsCount = InvitationsCount;
    }

    public int getInbodySessionsCount() {
        return this.InbodySessionsCount;
    }

    public void setInbodySessionsCount(int InbodySessionsCount) {
        this.InbodySessionsCount = InbodySessionsCount;
    }

    public int getPrivateTrainingSessionsCount() {
        return this.PrivateTrainingSessionsCount;
    }

    public void setPrivateTrainingSessionsCount(int PrivateTrainingSessionsCount) {
        this.PrivateTrainingSessionsCount = PrivateTrainingSessionsCount;
    }

    public int getFreezeCount() {
        return this.FreezeCount;
    }

    public void setFreezeCount(int FreezeCount) {
        this.FreezeCount = FreezeCount;
    }

    public int getFreezed() {
        return this.Freezed;
    }

    public void setFreezed(int Freezed) {
        this.Freezed = Freezed;
    }

    public String getIsActivated() {
        return this.isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }
   
}
