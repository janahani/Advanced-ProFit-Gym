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
    private int ClientID;
    private String Attended;
    private String isActivated;

    ReservedClass()
    {

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

    public int getClientID() {
        return this.ClientID;
    }

    public void setClientID(int ClientID) {
        this.ClientID = ClientID;
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
