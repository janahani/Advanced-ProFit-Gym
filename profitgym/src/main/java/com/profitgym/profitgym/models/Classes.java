package com.profitgym.profitgym.models;

import jakarta.persistence.Entity;

@Entity
public class Classes {
    private String imgPath;
    private String name;
    private String description;
    
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Getters and setters
}