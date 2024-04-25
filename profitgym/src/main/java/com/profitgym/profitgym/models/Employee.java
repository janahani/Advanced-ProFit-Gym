package com.profitgym.profitgym.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Name;
    private String email;
    private int PhoneNumber;
    private double Salary;
    private String Address;
    private int jobTitle;
    private String Password;

    public Employee(int iD, String name, String Email, int phoneNumber, double salary, String address, Integer jobTitle,
            String password) {
        ID = iD;
        Name = name;
        email = Email;
        PhoneNumber = phoneNumber;
        Salary = salary;
        Address = address;
        this.jobTitle = jobTitle;
        Password = password;
    }

    public Employee() {
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String Email) {
        email = Email;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(int jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
