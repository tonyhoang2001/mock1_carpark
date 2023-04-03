package com.example.carpark.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long tripId;

    @Column(length = 11)
    @NotBlank(message = "Type cannot be blank!")
    private String carType;

    @NotNull(message = "DOB cannot be empty!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;

    @NotNull(message = "Time cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "HH-mm-ss")
    private LocalTime departureTime;

    @Column(length = 50)
    @NotBlank(message = "Address cannot be blank!")
    private String destination;

    @Column(length = 11)
    @NotBlank(message = "Address cannot be blank!")
    private String driver;

    @Column(length = 11)
    @NotNull(message = "Address cannot be blank!")
    @Min(value = 1,message = "Invalid ticket number!")
    private int maximumOnlineTicketNumber;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "trip")
    private List<Ticket> tickets;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "trip")
    private List<BookingOffice> bookingOffices;

}
