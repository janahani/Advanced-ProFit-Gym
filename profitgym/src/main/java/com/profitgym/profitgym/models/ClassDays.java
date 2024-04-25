package com.profitgym.profitgym.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClassDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int classID;
    private String Days;

    public ClassDays()
    {

    }
    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClassID() {
        return this.classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getDays() {
        return this.Days;
    }

    public void setDays(String Days) {
        this.Days = Days;
    }



    
}
