package com.example.carpark.controller;

import com.example.carpark.entity.Parkinglot;
import com.example.carpark.service.ParkinglotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park")
public class ParkinglotController {
    @Autowired
    private ParkinglotService parkinglotService;

    @GetMapping()
    public List<Parkinglot> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return parkinglotService.getAll(page, size);
    }

    @GetMapping("/search")
    public List<Parkinglot> searchByPlace(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                          @RequestParam(value = "place", required = false, defaultValue = "") String place) {
        return parkinglotService.searchByPlace(page, size, place);
    }

    @GetMapping("/{id}")
    public Parkinglot getParkinglotById(@PathVariable Long id) {
        return parkinglotService.getParkinglotById(id);
    }

    @PostMapping()
    public Parkinglot insertParkinglot(@RequestBody @Valid Parkinglot parkinglot) {
        return parkinglotService.insertParkinglot(parkinglot);
    }

    @PutMapping
    public Parkinglot updateParkinglot(@RequestBody @Valid Parkinglot parkinglot) {
        return parkinglotService.updateParkinglot(parkinglot);
    }

    @DeleteMapping("/{id}")
    public void deleteParkinglotById(@PathVariable Long id) {
        parkinglotService.deleteParkinglotById(id);
    }
}
