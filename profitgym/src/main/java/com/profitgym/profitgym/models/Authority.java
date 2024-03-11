package com.profitgym.profitgym.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String Header;
    private String FriendlyName;
    private String LinkAddress;
    private String Icon;
    public Authority()
    {

    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHeader() {
        return this.Header;
    }

    public void setHeader(String Header) {
        this.Header = Header;
    }

    public String getFriendlyName() {
        return this.FriendlyName;
    }

    public void setFriendlyName(String FriendlyName) {
        this.FriendlyName = FriendlyName;
    }

    public String getLinkAddress() {
        return this.LinkAddress;
    }

    public void setLinkAddress(String LinkAddress) {
        this.LinkAddress = LinkAddress;
    }
    public String getIcon() {
        return this.Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }


}
