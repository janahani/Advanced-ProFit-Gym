package com.profitgym.profitgym.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Memberships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int clientID;
    private int PackageID;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private int VisitsCount;
    private int InvitationsCount;
    private int InbodySessionsCount;
    private int PrivateTrainingSessionsCount;
    private int FreezeCount;
    private String Freezed;
    private String isActivated;



    public Memberships() {
    }

    public Memberships(int ID, int clientID, int PackageID, LocalDate StartDate, LocalDate EndDate, int VisitsCount, int InvitationsCount, int InbodySessionsCount, int PrivateTrainingSessionsCount, int FreezeCount, String Freezed, String isActivated) {
        this.ID = ID;
        this.clientID = clientID;
        this.PackageID = PackageID;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.VisitsCount = VisitsCount;
        this.InvitationsCount = InvitationsCount;
        this.InbodySessionsCount = InbodySessionsCount;
        this.PrivateTrainingSessionsCount = PrivateTrainingSessionsCount;
        this.FreezeCount = FreezeCount;
        this.Freezed = Freezed;
        this.isActivated = isActivated;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClientID() {
        return this.clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getPackageID() {
        return this.PackageID;
    }

    public void setPackageID(int PackageID) {
        this.PackageID = PackageID;
    }

    public LocalDate getStartDate() {
        return this.StartDate;
    }

    public void setStartDate(LocalDate StartDate) {
        this.StartDate = StartDate;
    }

    public LocalDate getEndDate() {
        return this.EndDate;
    }

    public void setEndDate(LocalDate EndDate) {
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

    public String getFreezed() {
        return this.Freezed;
    }

    public void setFreezed(String Freezed) {
        this.Freezed = Freezed;
    }

    public String getIsActivated() {
        return this.isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

    public Memberships ID(int ID) {
        setID(ID);
        return this;
    }

    public Memberships clientID(int clientID) {
        setClientID(clientID);
        return this;
    }

    public Memberships PackageID(int PackageID) {
        setPackageID(PackageID);
        return this;
    }

    public Memberships StartDate(LocalDate StartDate) {
        setStartDate(StartDate);
        return this;
    }

    public Memberships EndDate(LocalDate EndDate) {
        setEndDate(EndDate);
        return this;
    }

    public Memberships VisitsCount(int VisitsCount) {
        setVisitsCount(VisitsCount);
        return this;
    }

    public Memberships InvitationsCount(int InvitationsCount) {
        setInvitationsCount(InvitationsCount);
        return this;
    }

    public Memberships InbodySessionsCount(int InbodySessionsCount) {
        setInbodySessionsCount(InbodySessionsCount);
        return this;
    }

    public Memberships PrivateTrainingSessionsCount(int PrivateTrainingSessionsCount) {
        setPrivateTrainingSessionsCount(PrivateTrainingSessionsCount);
        return this;
    }

    public Memberships FreezeCount(int FreezeCount) {
        setFreezeCount(FreezeCount);
        return this;
    }

    public Memberships Freezed(String Freezed) {
        setFreezed(Freezed);
        return this;
    }

    public Memberships isActivated(String isActivated) {
        setIsActivated(isActivated);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Memberships)) {
            return false;
        }
        Memberships memberships = (Memberships) o;
        return ID == memberships.ID && clientID == memberships.clientID && PackageID == memberships.PackageID && Objects.equals(StartDate, memberships.StartDate) && Objects.equals(EndDate, memberships.EndDate) && VisitsCount == memberships.VisitsCount && InvitationsCount == memberships.InvitationsCount && InbodySessionsCount == memberships.InbodySessionsCount && PrivateTrainingSessionsCount == memberships.PrivateTrainingSessionsCount && FreezeCount == memberships.FreezeCount && Objects.equals(Freezed, memberships.Freezed) && Objects.equals(isActivated, memberships.isActivated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, clientID, PackageID, StartDate, EndDate, VisitsCount, InvitationsCount, InbodySessionsCount, PrivateTrainingSessionsCount, FreezeCount, Freezed, isActivated);
    }

    @Override
    public String toString() {
        return "{" +
            " ID='" + getID() + "'" +
            ", clientID='" + getClientID() + "'" +
            ", PackageID='" + getPackageID() + "'" +
            ", StartDate='" + getStartDate() + "'" +
            ", EndDate='" + getEndDate() + "'" +
            ", VisitsCount='" + getVisitsCount() + "'" +
            ", InvitationsCount='" + getInvitationsCount() + "'" +
            ", InbodySessionsCount='" + getInbodySessionsCount() + "'" +
            ", PrivateTrainingSessionsCount='" + getPrivateTrainingSessionsCount() + "'" +
            ", FreezeCount='" + getFreezeCount() + "'" +
            ", Freezed='" + getFreezed() + "'" +
            ", isActivated='" + getIsActivated() + "'" +
            "}";
    }
    
    
}
