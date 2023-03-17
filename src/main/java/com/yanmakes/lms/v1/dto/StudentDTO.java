package com.yanmakes.lms.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanmakes.lms.v1.constant.Gender;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Email;

public class StudentDTO extends RepresentationModel<StudentDTO> {

    @JsonIgnore
    private Long id;
    private String firstName;

    private String lastName;

    private Gender gender;

    private String mobile;

    @Email(message = "Invalid email")
    private String email;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
