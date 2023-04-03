package com.example.carpark.repository;

import com.example.carpark.entity.BookingOffice;
import com.example.carpark.entity.BookingOffice;
import com.example.carpark.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Transactional
public interface BookingOfficeRepo extends JpaRepository<BookingOffice, Long> {

    @Query(value = "select * from bookingoffice", nativeQuery = true)
    Page<BookingOffice> getAll(Pageable pageable);

    @Query(value = "select * from bookingoffice b where b.office_id = ?1",
            nativeQuery = true)
    BookingOffice getBookingOfficeById(Long id);

    @Query(value = "select * from bookingoffice ORDER BY office_id DESC LIMIT 1",
            nativeQuery = true)
    BookingOffice getInsertedBookingOffice();

    @Query(value = "select * from bookingoffice b where b.office_id = ?1",
            nativeQuery = true)
    Page<BookingOffice> getBookingOfficeByTripID(Long tripID, Pageable pageable);

    @Query(value = "select * from bookingoffice b where b.office_name like lower(concat('%', ?1,'%')",
            nativeQuery = true)
    Page<BookingOffice> searchByName(String name, Pageable pageable);

    @Modifying
    @Query(value = "insert into bookingoffice(end_contract_deadline, office_name, office_phone, office_place, office_price, start_contract_deadline, trip_id) values(?1,?2,?3,?4,?5,?6,?7)",
            nativeQuery = true)
    void insertBookingOffice(LocalDate endDate, String name, String phone, String place, Long price, LocalDate startDate, Long tripID);

    @Modifying
    @Query(value = "update bookingoffice set end_contract_deadline = ?1, office_name = ?2, office_phone = ?3, office_place = ?4, office_price = ?5, start_contract_deadline = ?6, trip_id = ?7 " +
            " where office_id = ?8",
            nativeQuery = true)
    void updateBookingOffice(LocalDate endDate, String name, String phone, String place, Long price, LocalDate startDate, Long tripID, Long id);

    @Modifying
    @Query(value = "delete from bookingoffice where office_id = ?1", nativeQuery = true)
    void deleteBookingOfficeById(Long id);

}
