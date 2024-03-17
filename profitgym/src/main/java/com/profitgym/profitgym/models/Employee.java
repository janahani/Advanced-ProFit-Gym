package com.profitgym.profitgym.models;

import java.io.Serializable;
import java.util.Objects;

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
    private int JobTitle;
    private String Password;

    public Employee(int iD, String name, String Email, int phoneNumber, double salary, String address, Integer jobTitle,
            String password) {
        ID = iD;
        Name = name;
        email = Email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return ID == employee.ID && PhoneNumber == employee.PhoneNumber && Double.compare(employee.Salary, Salary) == 0
                && Objects.equals(Name, employee.Name) && Objects.equals(email, employee.email)
                && Objects.equals(Address, employee.Address) && Objects.equals(JobTitle, employee.JobTitle)
                && Objects.equals(Password, employee.Password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Name, email, PhoneNumber, Salary, Address, JobTitle, Password);
    }

    @Override
    public String toString() {
        return "{" + " ID='" + getId() + "'" + ", Name='" + getName() + "'" + ", Email='" + getEmail() + "'"
                + ", PhoneNumber='" + getPhoneNumber() + "'" + ", Salary='" + getSalary() + "'" + ", Address='"
                + getAddress() + "'" + ", JobTitle='" + getJobTitle() + "'" + ", Password='" + getPassword() + "'"
                + "}";
    }

}
