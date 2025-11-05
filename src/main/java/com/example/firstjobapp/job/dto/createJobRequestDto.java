package com.example.firstjobapp.job.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull; // IMPORT ADDED

public class createJobRequestDto {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    private String description;


    private String minSalary;
    private String maxSalary;

    @NotEmpty(message = "Location cannot be empty")
    private String location;


    @NotNull(message = "Company ID cannot be null")
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}