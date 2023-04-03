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
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookingoffice")
public class BookingOffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long officeId;

    @NotNull(message = "End date cannot be empty!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endContractDeadline;

    @NotBlank(message = "Office name cannot be blank!")
    @Column(length = 50)
    private String officeName;

    @Column(length = 10)
    @NotBlank(message = "Phone number cannot be blank!")
    @Length(min = 10, max = 10, message = "Phone must be 10 number!")
    private String officePhone;

    @Column(length = 50)
    @NotBlank(message = "Place cannot be blank!")
    private String officePlace;

    @Column(length = 20)
    @NotNull(message = "Price cannot be blank!")
    @Min(value = 0, message = "Invalid price!")
    private Long officePrice;

    @NotNull(message = "Start date cannot be empty!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startContractDeadline;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

}
