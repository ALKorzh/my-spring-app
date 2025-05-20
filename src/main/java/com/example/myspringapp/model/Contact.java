package com.example.myspringapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "phonebook")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // Конструкторы
    public Contact() {}

    public Contact(String lastName, String phoneNumber) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Contact(Long id, String lastName, String phoneNumber) {
        this.id = id;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
