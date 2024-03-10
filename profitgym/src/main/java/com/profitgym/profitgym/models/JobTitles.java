package com.profitgym.profitgym.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobTitles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    // @Column(name = "name")
    private String Name;

    public JobTitles()
    {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

}
