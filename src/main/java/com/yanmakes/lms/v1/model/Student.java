package com.yanmakes.lms.v1.model;

import com.yanmakes.lms.v1.constant.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@Table(name = "student")
public class Student extends RepresentationModel<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
//    @Min(value = 3, message = "First name should contain at least 3 character")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    @Email(message = "Invalid email")
    private String email;

    @Column(name = "password", nullable = false)
//    @JsonIgnore
    private String password;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
