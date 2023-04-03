package com.example.carpark.repository;

import com.example.carpark.entity.Car;
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
public interface CarRepo extends JpaRepository<Car, String> {

    @Query(value = "select * from car", nativeQuery = true)
    Page<Car> getAll(Pageable pageable);

    @Query(value = "select * from car c where c.license_plate like lower(concat('%', ?1,'%'))", nativeQuery = true)
    Page<Car> searchByLicense(String license, Pageable pageable);

    @Query(value = "select * from car c where c.license_plate = ?1",
            nativeQuery = true)
    Car getCarByLicensePlate(String plate);

    @Query(value = "select * from car c where c.parkinglot_park_id = ?1",
            nativeQuery = true)
    Page<Car> getCarsByParkID(Long parkID, Pageable pageable);

    @Modifying
    @Query(value = "insert into car(license_plate, car_color, car_type, company, park_id) values(?1,?2,?3,?4,?5)",
            nativeQuery = true)
    void insertCar(String plate, String color, String type, String company, Long parkID);

    @Modifying
    @Query(value = "update car set license_plate = ?1, car_color = ?2, car_type = ?3, company = ?4, park_id = ?5 where license_plate = ?6", nativeQuery = true)
    void updateCar(String plate1,String color, String type, String company,Long parkID ,String plate2);

    @Modifying
    @Query(value = "delete from car where license_plate = ?1", nativeQuery = true)
    void deleteCarById(String plate);

}
