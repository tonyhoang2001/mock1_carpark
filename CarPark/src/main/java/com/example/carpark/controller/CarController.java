package com.example.carpark.controller;

import com.example.carpark.entity.Car;
import com.example.carpark.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping()
    public List<Car> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return carService.getAll(page, size);
    }

    @GetMapping("/search")
    public List<Car> searchByLicense(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                     @RequestParam(value = "license", required = false, defaultValue = "") String license) {
        return carService.searchByLicense(page, size, license);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable String plate) {
        return carService.getCarById(plate);
    }

    @GetMapping("/findByPark")
    public List<Car> getCarByParkID(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                    @RequestParam(value = "parkID", required = false, defaultValue = "1") Long parkID) {
        return carService.getCarByParkID(parkID, page, size);
    }

    @PostMapping("/{parkID}")
    public Car insertParkinglot(@RequestBody @Valid Car car,
                                @PathVariable Long parkID) {
        return carService.insertCar(car, parkID);
    }

    @PutMapping("/{parkID}")
    public Car updateCar(@RequestBody @Valid Car car,
                         @PathVariable Long parkID) {
        return carService.updateCar(car, parkID);
    }

    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable String plate) {
        carService.deleteCarById(plate);
    }
}
