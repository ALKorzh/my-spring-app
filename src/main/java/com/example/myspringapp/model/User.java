package com.example.myspringapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 40, message = "Username must be between 4 and 40 characters")
    @Column(nullable = false, unique = true, length = 40)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 64, message = "Password must be at least 6 characters")
    @Column(nullable = false, length = 64)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "confirmation_token", length = 255)
    private String confirmationToken;

    @Column(nullable = false)
    private Boolean confirmed = false;

    @Lob
    private byte[] photo;

    // Конструктор по умолчанию
    public User() {}

    // Геттеры и сеттеры

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
