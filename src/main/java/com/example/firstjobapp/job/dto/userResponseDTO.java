package com.example.firstjobapp.job.dto;


public class userResponseDTO {
    private Long id;
    private String title;
    private String maxSalary;
    private String location;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMaxSalary() { return maxSalary; }
    public void setMaxSalary(String maxSalary) { this.maxSalary = maxSalary; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}