package com.profitgym.profitgym.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String FirstName;
    private String LastName;
    private int Age;
    private String Gender;
    private double Weight;
    private double Height;
    private String email;
    private String Password;
    private String PhoneNumber;
    private LocalDate createdAt;

    public Client() {
    }

    public Client(int ID, String FirstName, String LastName, int Age, String Gender, double Weight, double Height, String email, String Password, String PhoneNumber, LocalDate createdAt) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Age = Age;
        this.Gender = Gender;
        this.Weight = Weight;
        this.Height = Height;
        this.email = email;
        this.Password = Password;
        this.PhoneNumber = PhoneNumber;
        this.createdAt = createdAt;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public int getAge() {
        return this.Age;
    }

    public void setAge(int Age) {
        this.Age = Age;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public double getWeight() {
        return this.Weight;
    }

    public void setWeight(double Weight) {
        this.Weight = Weight;
    }

    public double getHeight() {
        return this.Height;
    }

    public void setHeight(double Height) {
        this.Height = Height;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public Client ID(int ID) {
        setID(ID);
        return this;
    }

    public Client FirstName(String FirstName) {
        setFirstName(FirstName);
        return this;
    }

    public Client LastName(String LastName) {
        setLastName(LastName);
        return this;
    }

    public Client Age(int Age) {
        setAge(Age);
        return this;
    }

    public Client Gender(String Gender) {
        setGender(Gender);
        return this;
    }

    public Client Weight(double Weight) {
        setWeight(Weight);
        return this;
    }

    public Client Height(double Height) {
        setHeight(Height);
        return this;
    }

    public Client email(String email) {
        setEmail(email);
        return this;
    }

    public Client Password(String Password) {
        setPassword(Password);
        return this;
    }

    public Client PhoneNumber(String PhoneNumber) {
        setPhoneNumber(PhoneNumber);
        return this;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return ID == client.ID && Objects.equals(FirstName, client.FirstName) && Objects.equals(LastName, client.LastName) && Age == client.Age && Objects.equals(Gender, client.Gender) && Weight == client.Weight && Height == client.Height && Objects.equals(email, client.email) && Objects.equals(Password, client.Password) && Objects.equals(PhoneNumber, client.PhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, FirstName, LastName, Age, Gender, Weight, Height, email, Password, PhoneNumber);
    }

    @Override
    public String toString() {
        return "{" +
            " ID='" + getID() + "'" +
            ", FirstName='" + getFirstName() + "'" +
            ", LastName='" + getLastName() + "'" +
            ", Age='" + getAge() + "'" +
            ", Gender='" + getGender() + "'" +
            ", Weight='" + getWeight() + "'" +
            ", Height='" + getHeight() + "'" +
            ", email='" + getEmail() + "'" +
            ", Password='" + getPassword() + "'" +
            ", PhoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }

    
}
