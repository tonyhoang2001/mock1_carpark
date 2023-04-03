package com.example.carpark.controller;

import com.example.carpark.entity.BookingOffice;
import com.example.carpark.service.BookingOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingOfficeController {
    @Autowired
    private BookingOfficeService bookingOfficeService;

    @GetMapping()
    public List<BookingOffice> getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return bookingOfficeService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public BookingOffice getBookingOfficeById(@PathVariable Long id) {
        return bookingOfficeService.getBookingOfficeById(id);
    }

    @GetMapping("/findByTrip")
    public List<BookingOffice> getBookingOfficeByTrip(@RequestParam(value = "tripID", required = false, defaultValue = "1") Long tripID,
                                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return bookingOfficeService.getBookingOfficeByTripID(tripID, page, size);
    }

    @GetMapping("/search")
    public List<BookingOffice> searchByName(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return bookingOfficeService.searchByName(name, page, size);
    }

    @PostMapping("/{tripID}")
    public BookingOffice insertBookingOffice(@RequestBody @Valid BookingOffice bookingOffice,
                                             @PathVariable Long tripID) {
        return bookingOfficeService.insertBookingOffice(bookingOffice, tripID);
    }

    @PutMapping("/{tripID}")
    public BookingOffice updateBookingOffice(@RequestBody @Valid BookingOffice bookingOffice,
                                             @PathVariable Long tripID) {
        return bookingOfficeService.updateBookingOffice(bookingOffice, tripID);
    }

    @DeleteMapping("/{id}")
    public void deleteBookingOfficeById(@PathVariable Long id) {
        bookingOfficeService.deleteBookingOfficeById(id);
    }
}
