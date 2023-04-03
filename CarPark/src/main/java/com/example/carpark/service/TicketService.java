package com.example.carpark.service;

import com.example.carpark.entity.Car;
import com.example.carpark.entity.Ticket;
import com.example.carpark.entity.Trip;
import com.example.carpark.repository.CarRepo;
import com.example.carpark.repository.TicketRepo;
import com.example.carpark.repository.TripRepo;
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
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private CarRepo carRepo;
    @Autowired
    private TripRepo tripRepo;


    public List<Ticket> getAll(Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Ticket> list = ticketRepo.getAll(pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no ticket!");
        }
        return list;
    }

    public Ticket getTicketById(Long id) {
        Ticket ticket = ticketRepo.getTicketById(id);
        if (ticket == null) {
            throw new NullPointerException("There's no ticket!");
        }
        return ticket;
    }

    public List<Ticket> getTicketsByCar(String license, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Ticket> list = ticketRepo.getTicketByCar(license, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no ticket!");
        }
        return list;
    }

    public List<Ticket> getTicketsByTrip(Long tripID, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Ticket> list = ticketRepo.getTicketByTrip(tripID, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no ticket!");
        }
        return list;
    }

    public List<Ticket> getTicketsByTripAndCar(Long tripID, String license, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Ticket> list = ticketRepo.getTicketByTripAndCar(tripID, license, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no ticket!");
        }
        return list;
    }

    public Ticket insertTicket(Ticket ticket, Long tripID, String carPlate) {

        List<Trip> trips = tripRepo.findAll();
        Long count = trips.stream().filter(t -> tripID == t.getTripId()).count();
        if (count == 0) {
            throw new RuntimeException("Not exist trip with ID: " + tripID);
        }

        List<Car> cars = carRepo.findAll();
        count = cars.stream().filter(c -> carPlate.equalsIgnoreCase(c.getLicensePlate())).count();
        if (count == 0) {
            throw new RuntimeException("Not exist car with license: " + carPlate);
        }

        List<Ticket> tickets = ticketRepo.findAll();
        count = tickets.stream().filter(t -> ticket.getCar().getLicensePlate().equalsIgnoreCase(t.getCar().getLicensePlate()) && ticket.getTrip().getTripId() == t.getTrip().getTripId()).count();
        if (count != 0) {
            throw new RuntimeException("There's already existed ticket with car license: " + carPlate + " and trip ID: " + tripID);
        }

        try {
            ticketRepo.insertTicket(ticket.getBookingTime(), ticket.getCustomerName(), carPlate, tripID);
            return ticketRepo.getInsertedTicket();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public Ticket updateTicket(Ticket ticket, Long tripID, String carPlate) {

        List<Trip> trips = tripRepo.findAll();
        Long count = trips.stream().filter(t -> tripID == t.getTripId()).count();
        if (count == 0) {
            throw new RuntimeException("Not exist trip with ID: " + tripID);
        }

        List<Car> cars = carRepo.findAll();
        count = cars.stream().filter(c -> carPlate.equalsIgnoreCase(c.getLicensePlate())).count();
        if (count == 0) {
            throw new RuntimeException("Not exist car with license: " + carPlate);
        }

        Optional<Ticket> t = ticketRepo.findById(ticket.getTicketId());
        if (t.isPresent()) {
            try {
                ticketRepo.updateTicket(ticket.getBookingTime(), ticket.getCustomerName(), carPlate, tripID, ticket.getTicketId());
                return ticketRepo.findById(ticket.getTicketId()).get();
            } catch (RuntimeException ex) {
                throw new RuntimeException("Updating fail!");
            }
        }
        throw new NullPointerException("Not exist ticket with ID: " + ticket.getTicketId() + " for updating!");
    }

    public void deleteTicketById(Long id) {
        Optional<Ticket> t = ticketRepo.findById(id);
        if (t.isPresent()) {
            try {
                ticketRepo.deleteTicketById(id);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist ticket with ID: " + id + " for deleting!");
    }

}
