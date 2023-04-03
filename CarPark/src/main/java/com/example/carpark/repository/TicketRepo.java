package com.example.carpark.repository;

import com.example.carpark.entity.Employee;
import com.example.carpark.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Transactional
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    @Query(value = "select * from ticket", nativeQuery = true)
    Page<Ticket> getAll(Pageable pageable);

    @Query(value = "select * from ticket t where t.ticket_id = ?1",
            nativeQuery = true)
    Ticket getTicketById(Long id);

    @Query(value = "select * from ticket ORDER BY ticket_id DESC LIMIT 1",
            nativeQuery = true)
    Ticket getInsertedTicket();

    @Query(value = "select * from ticket t where t.car_license_plate like lower(concat('%', ?1,'%')",
            nativeQuery = true)
    Page<Ticket> getTicketByCar(String carPlate, Pageable pageable);

    @Query(value = "select * from ticket t where t.trip_id = ?1",
            nativeQuery = true)
    Page<Ticket> getTicketByTrip(Long tripID, Pageable pageable);

    @Query(value = "select * from ticket t where t.trip_id = ?1 and t.car_license_plate like lower(concat('%', ?2,'%')",
            nativeQuery = true)
    Page<Ticket> getTicketByTripAndCar(Long tripID, String carPlate, Pageable pageable);

    @Modifying
    @Query(value = "insert into ticket(booking_time, customer_name, car_license_plate, trip_id) values(?1,?2,?3,?4)",
            nativeQuery = true)
    void insertTicket(LocalTime bookingTime, String cusName, String carPlate, Long tripID);

    @Modifying
    @Query(value = "update ticket set booking_time = ?1, customer_name = ?2, car_license_plate = ?3, trip_id = ?4 where ticket_id = ?5",
            nativeQuery = true)
    void updateTicket(LocalTime bookingTime, String cusName, String carPlate, Long tripID, Long id);

    @Modifying
    @Query(value = "delete from ticket where ticket_id = ?1", nativeQuery = true)
    void deleteTicketById(Long id);

}
