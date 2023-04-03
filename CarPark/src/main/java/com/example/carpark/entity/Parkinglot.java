package com.example.carpark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "parkinglot")
public class Parkinglot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long parkId;

    @NotBlank(message = "Area cannot be blank!")
    @Column(length = 20)
    private Long parkArea;

    @NotBlank(message = "Name cannot be blank!")
    @Column(length = 50)
    private String parkName;

    @NotBlank(message = "Place cannot be blank!")
    @Column(length = 11)
    private String parkPlace;

    @NotNull(message = "Price cannot be blank!")
    @Column(length = 20)
    @Min(value = 0, message = "Invalid price!")
    private Long parkPrice;

    @Column(length = 50)
    private String parkStatus;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "parkinglot")
    private List<Car> carList;

}
