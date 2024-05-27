package com.profitgym.membershipsmicroservices.membershipsmicroservices.models;
import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ScheduledUnfreeze implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int membershipID;
    private LocalDate FreezeStartDate;
    private LocalDate FreezeEndDate;
    private int FreezeCount;

    public ScheduledUnfreeze()
    {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMembershipID() {
        return this.membershipID;
    }

    public void setMembershipID(int MembershipID) {
        this.membershipID = MembershipID;
    }

    public LocalDate getFreezeStartDate() {
        return this.FreezeStartDate;
    }

    public void setFreezeStartDate(LocalDate FreezeStartDate) {
        this.FreezeStartDate = FreezeStartDate;
    }

    public LocalDate getFreezeEndDate() {
        return this.FreezeEndDate;
    }

    public void setFreezeEndDate(LocalDate FreezeEndDate) {
        this.FreezeEndDate = FreezeEndDate;
    }

    public int getFreezeCount() {
        return this.FreezeCount;
    }

    public void setFreezeCount(int FreezeCount) {
        this.FreezeCount = FreezeCount;
    }

}
