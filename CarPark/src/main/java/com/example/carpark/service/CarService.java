package com.example.carpark.service;

import com.example.carpark.entity.Car;
import com.example.carpark.entity.Parkinglot;
import com.example.carpark.repository.CarRepo;
import com.example.carpark.repository.ParkinglotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CarService {
    @Autowired
    private CarRepo carRepo;

    @Autowired
    private ParkinglotRepo parkinglotRepo;

    public List<Car> getAll(Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Car> list = carRepo.getAll(pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no car!");
        }
        return list;
    }

    public List<Car> searchByLicense(Integer page, Integer size, String license) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Car> list = carRepo.searchByLicense(license, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no car!");
        }
        return list;
    }

    public Car getCarById(String plate) {
        Car car = carRepo.getCarByLicensePlate(plate);
        if (car == null) {
            throw new NullPointerException("There's no car!");
        }
        return car;
    }

    public List<Car> getCarByParkID(Long parkID, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Car> car = carRepo.getCarsByParkID(parkID, pageable).getContent();
        if (car == null || car.isEmpty()) {
            throw new NullPointerException("There's no car!");
        }
        return car;
    }

    public Car insertCar(Car car, Long parkID) {
        List<Car> carList = carRepo.findAll();
        Long count = carList.stream().filter(c -> car.getLicensePlate().equals(c.getLicensePlate())).count();

        if (count != 0) {
            throw new RuntimeException("There's existed car with license: " + car.getLicensePlate());
        }

        List<Parkinglot> parkinglots = parkinglotRepo.findAll();
        count = parkinglots.stream().filter(p -> parkID == p.getParkId()).count();

        if (count == 0) {
            throw new RuntimeException("Not exists parking lot with ID: " + parkID);
        }

        try {
            carRepo.insertCar(car.getLicensePlate(), car.getCarColor(), car.getCarType(), car.getCompany(), parkID);
            return carRepo.findById(car.getLicensePlate()).get();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public Car updateCar(Car car, Long parkID) {

        List<Parkinglot> parkinglots = parkinglotRepo.findAll();
        Long count = parkinglots.stream().filter(p -> parkID == p.getParkId()).count();

        if (count == 0) {
            throw new RuntimeException("Not exists parking lot with ID: " + parkID);
        }

        Optional<Car> c = carRepo.findById(car.getLicensePlate());

        if (c.isPresent()) {
            try {
                carRepo.updateCar(car.getLicensePlate(), car.getCarColor(), car.getCarType(), car.getCompany(), parkID, car.getLicensePlate());
                return carRepo.findById(car.getLicensePlate()).get();
            } catch (RuntimeException ex) {
                throw new RuntimeException("Updating fail!");
            }
        }
        throw new NullPointerException("Not exist car with license: " + car.getLicensePlate() + " for updating!");
    }

    public void deleteCarById(String plate) {
        Optional<Car> p = carRepo.findById(plate);
        if (p.isPresent()) {
            try {
                carRepo.deleteCarById(plate);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist car with license: " + plate + " for deleting!");
    }

}
