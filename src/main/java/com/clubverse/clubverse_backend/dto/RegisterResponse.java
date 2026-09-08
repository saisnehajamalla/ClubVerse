package com.clubverse.clubverse_backend.dto;

public class RegisterResponse {

    private Long id;
    private String name;
    private String email;
    private String rollNumber;
    private String batch;
    private String department;
    private String role;

    public RegisterResponse() {
    }

    public RegisterResponse(Long id, String name, String email,
                            String rollNumber, String batch,
                            String department, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rollNumber = rollNumber;
        this.batch = batch;
        this.department = department;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getBatch() {
        return batch;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }
}