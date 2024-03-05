package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Name;
    private String Email;
    private int PhoneNumber;
    private double Salary;
    private String Address;
    private int JobTitle;
    private String Password;



    
    public Employee(int iD, String name, String email, int phoneNumber, double salary, String address, Integer jobTitle,
            String password) {
        ID = iD;
        Name = name;
        Email = email;
        PhoneNumber = phoneNumber;
        Salary = salary;
        Address = address;
        JobTitle = jobTitle;
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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public Integer getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(Integer jobTitle) {
        JobTitle = jobTitle;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
