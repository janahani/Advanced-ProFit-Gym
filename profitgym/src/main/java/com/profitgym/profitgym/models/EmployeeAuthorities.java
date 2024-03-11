package com.profitgym.profitgym.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name = "employee_authorities")
public class EmployeeAuthorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name = "job_titleid")
    private int jobTitleID;

    @Column(name = "authorityid")
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
        return this.jobTitleID;
    }

    public void setJobTitleID(int JobTitleID) {
        this.jobTitleID = JobTitleID;
    }

    public int getAuthorityID() {
        return this.AuthorityID;
    }

    public void setAuthorityID(int AuthorityID) {
        this.AuthorityID = AuthorityID;
    }

}
