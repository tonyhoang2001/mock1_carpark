package com.example.carpark.repository;

import com.example.carpark.entity.Employee;
import com.example.carpark.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Transactional
public interface TripRepo extends JpaRepository<Trip, Long> {

    @Query(value = "select * from trip", nativeQuery = true)
    Page<Trip> getAll(Pageable pageable);

    @Query(value = "select * from trip t where t.destination like lower(concat('%', ?1,'%')", nativeQuery = true)
    Page<Trip> searchByDestination(String destination, Pageable pageable);

    @Query(value = "select * from trip t where t.trip_id = ?1",
            nativeQuery = true)
    Trip getTripById(Long id);

    @Query(value = "select * from trip ORDER BY trip_id DESC LIMIT 1",
            nativeQuery = true)
    Trip getInsertedTrip();

    @Modifying
    @Query(value = "insert into trip(car_type, departure_date, departure_time, destination, driver, maximum_online_ticket_number) values(?1,?2,?3,?4,?5,?6)",
            nativeQuery = true)
    void insertTrip(String carType, LocalDate departureDate, LocalTime departureTime, String destination, String driver, int maxTicketNum);

    @Modifying
    @Query(value = "update trip set car_type = ?1, departure_date = ?2, departure_time = ?3, destination = ?4, driver = ?5, maximum_online_ticket_number = ?6 where trip_id = ?7",
            nativeQuery = true)
    void updateTrip(String carType, LocalDate departureDate, LocalTime departureTime, String destination, String driver, int maxTicketNum, Long id);

    @Modifying
    @Query(value = "delete from trip where trip_id = ?1", nativeQuery = true)
    void deleteTripById(Long id);

}
