package com.example.carpark.service;

import com.example.carpark.entity.Trip;
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
public class TripService {
    @Autowired
    private TripRepo tripRepo;

    public List<Trip> getAll(Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Trip> list = tripRepo.getAll(pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no trip!");
        }
        return list;
    }

    public List<Trip> searchByDestination(String destination, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Trip> list = tripRepo.searchByDestination(destination, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no trip!");
        }
        return list;
    }

    public Trip getTripById(Long id) {
        Trip trip = tripRepo.getTripById(id);
        if (trip == null) {
            throw new NullPointerException("There's no trip!");
        }
        return trip;
    }

    public Trip insertTrip(Trip trip) {
        try {
            tripRepo.insertTrip(trip.getCarType(), trip.getDepartureDate(), trip.getDepartureTime(), trip.getDestination(), trip.getDriver(), trip.getMaximumOnlineTicketNumber());
            return tripRepo.getInsertedTrip();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public Trip updateTrip(Trip trip) {
        Optional<Trip> t = tripRepo.findById(trip.getTripId());
        if (t.isPresent()) {
            try {
                tripRepo.updateTrip(trip.getCarType(), trip.getDepartureDate(), trip.getDepartureTime(), trip.getDestination(), trip.getDriver(), trip.getMaximumOnlineTicketNumber(), trip.getTripId());
                return tripRepo.findById(trip.getTripId()).get();
            } catch (RuntimeException ex) {
                throw new RuntimeException("Updating fail!");
            }
        }
        throw new NullPointerException("Not exist trip with ID: " + trip.getTripId() + " for updating!");
    }

    public void deleteTripById(Long id) {
        Optional<Trip> t = tripRepo.findById(id);
        if (t.isPresent()) {
            try {
                tripRepo.deleteTripById(id);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist trip with ID: " + id + " for deleting!");
    }

}
