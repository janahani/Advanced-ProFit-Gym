package com.profitgym.profitgym.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EmployeeAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private int JobTitleID;
    private int AuthorityID;

    public EmployeeAuthorities()
    {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getJobTitleID() {
        return this.JobTitleID;
    }

    public void setJobTitleID(int JobTitleID) {
        this.JobTitleID = JobTitleID;
    }

    public int getAuthorityID() {
        return this.AuthorityID;
    }

    public void setAuthorityID(int AuthorityID) {
        this.AuthorityID = AuthorityID;
    }

}
