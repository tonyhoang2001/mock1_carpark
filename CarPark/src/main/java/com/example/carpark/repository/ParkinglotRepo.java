package com.example.carpark.repository;

import com.example.carpark.entity.Employee;
import com.example.carpark.entity.Parkinglot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ParkinglotRepo extends JpaRepository<Parkinglot, Long> {

    @Query(value = "select * from parkinglot", nativeQuery = true)
    Page<Parkinglot> getAll(Pageable pageable);

    @Query(value = "select * from parkinglot p where p.park_place like lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Parkinglot> searchByPlace(String place, Pageable pageable);

    @Query(value = "select * from parkinglot p where p.park_id = ?1",
            nativeQuery = true)
    Parkinglot getParkinglotById(Long id);

    @Query(value = "select * from parkinglot ORDER BY park_id DESC LIMIT 1",
            nativeQuery = true)
    Parkinglot getInsertedParkinglot();

    @Modifying
    @Query(value = "insert into parkinglot(park_area, park_name, park_place, park_price) values(?1,?2,?3,?4)",
            nativeQuery = true)
    void insertParkinglot(Long area, String name, String place, Long price);

    @Modifying
    @Query(value = "update parkinglot set park_area = ?1, park_name = ?2, park_place = ?3, park_price = ?4, park_status = ?5 where park_id = ?6", nativeQuery = true)
    void updateParkinglot(Long area, String name, String place, Long price, String status, Long id);

    @Modifying
    @Query(value = "delete from parkinglot where park_id = ?1", nativeQuery = true)
    void deleteParkinglotById(Long id);

}
