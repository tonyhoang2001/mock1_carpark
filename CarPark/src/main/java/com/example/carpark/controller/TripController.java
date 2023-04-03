package com.example.carpark.controller;

import com.example.carpark.entity.Trip;
import com.example.carpark.service.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    @GetMapping()
    public List<Trip> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return tripService.getAll(page, size);
    }

    @GetMapping("/search")
    public List<Trip> searchByDestination(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                          @RequestParam(value = "destination", required = false, defaultValue = "") String destination) {
        return tripService.searchByDestination(destination, page, size);
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PostMapping()
    public Trip insertTrip(@RequestBody @Valid Trip trip) {
        return tripService.insertTrip(trip);
    }

    @PutMapping
    public Trip updateTrip(@RequestBody @Valid Trip trip) {
        return tripService.updateTrip(trip);
    }

    @DeleteMapping("/{id}")
    public void deleteTripById(@PathVariable Long id) {
        tripService.deleteTripById(id);
    }
}
