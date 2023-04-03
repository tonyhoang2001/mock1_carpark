package com.example.carpark.controller;

import com.example.carpark.entity.Ticket;
import com.example.carpark.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping()
    public List<Ticket> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "1000") Integer size) {
        return ticketService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/findByCar")
    public List<Ticket> getTicketByCar(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                       @RequestParam(value = "license", required = false, defaultValue = "") String license) {
        return ticketService.getTicketsByCar(license, page, size);
    }

    @GetMapping("/findByTrip")
    public List<Ticket> getTicketByTrip(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                        @RequestParam(value = "tripID", required = false, defaultValue = "1") Long tripID) {
        return ticketService.getTicketsByTrip(tripID, page, size);
    }

    @GetMapping("/find")
    public List<Ticket> getTicketByTripAndCar(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                              @RequestParam(name = "tripID", required = false, defaultValue = "1") Long tripID,
                                              @RequestParam(name = "license", required = false, defaultValue = "") String license) {
        return ticketService.getTicketsByTripAndCar(tripID, license, page, size);
    }

    @PostMapping()
    public Ticket insertTicket(@RequestBody Ticket ticket,
                               @RequestParam(name = "tripID") Long tripID,
                               @RequestParam(name = "carPlate") String carPlate) {
        return ticketService.insertTicket(ticket, tripID, carPlate);
    }

    @PutMapping
    public Ticket updateTicket(@RequestBody Ticket ticket,
                               @RequestParam(name = "tripID") Long tripID,
                               @RequestParam(name = "carPlate") String carPlate) {
        return ticketService.updateTicket(ticket, tripID, carPlate);
    }

    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable Long id) {
        ticketService.deleteTicketById(id);
    }
}
