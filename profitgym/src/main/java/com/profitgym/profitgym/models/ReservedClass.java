package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ReservedClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int AssignedClassID;
    private int CoachID;
    private int clientID;
    private String Attended;
    private String isActivated;

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public ReservedClass() {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAssignedClassID() {
        return this.AssignedClassID;
    }

    public void setAssignedClassID(int AssignedClassID) {
        this.AssignedClassID = AssignedClassID;
    }

    public int getCoachID() {
        return this.CoachID;
    }

    public void setCoachID(int CoachID) {
        this.CoachID = CoachID;
    }

    public String getAttended() {
        return this.Attended;
    }

    public void setAttended(String Attended) {
        this.Attended = Attended;
    }

    public String getIsActivated() {
        return this.isActivated;
    }

    public void setIsActivated(String isActivated) {
        this.isActivated = isActivated;
    }

}
