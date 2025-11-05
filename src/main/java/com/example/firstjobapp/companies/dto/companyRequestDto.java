package com.example.firstjobapp.companies.dto;

import jakarta.validation.constraints.NotEmpty;

public class companyRequestDto {

    @NotEmpty(message = "Company name cannot be empty")
    private String name;

    @NotEmpty(message = "Company description cannot be empty")
    private String description;


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
}