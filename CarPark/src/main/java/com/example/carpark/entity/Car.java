package com.example.carpark.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "car")
public class Car {
    @Id
    @Column(length = 50)
    @NotBlank(message = "License cannot be blank!")
    private String licensePlate;

    @Column(length = 11)
    @NotBlank(message = "Color cannot be blank!")
    private String carColor;

    @Column(length = 50)
    @NotBlank(message = "Type cannot be blank!")
    private String carType;

    @Column(length = 50)
    @NotBlank(message = "Company cannot be blank!")
    private String company;

    @ManyToOne
    @JoinColumn(name = "park_id")
    @JsonIgnore
    private Parkinglot parkinglot;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "car")
    @JsonIgnore
    private List<Ticket> tickets;

}
