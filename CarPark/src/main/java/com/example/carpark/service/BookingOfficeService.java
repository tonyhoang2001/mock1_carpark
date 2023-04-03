package com.example.carpark.service;

import com.example.carpark.entity.BookingOffice;
import com.example.carpark.entity.Trip;
import com.example.carpark.repository.BookingOfficeRepo;
import com.example.carpark.repository.TripRepo;
import com.example.carpark.validation.ValidateInput;
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
public class BookingOfficeService {
    @Autowired
    private BookingOfficeRepo bookingOfficeRepo;

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private ValidateInput validateInput;

    public List<BookingOffice> getAll(Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<BookingOffice> list = bookingOfficeRepo.getAll(pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no booking office!");
        }
        return list;
    }

    public BookingOffice getBookingOfficeById(Long id) {
        BookingOffice bookingOffice = bookingOfficeRepo.getBookingOfficeById(id);
        if (bookingOffice == null) {
            throw new NullPointerException("There's no booking office!");
        }
        return bookingOffice;
    }

    public List<BookingOffice> getBookingOfficeByTripID(Long tripID, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<BookingOffice> bookingOffices = bookingOfficeRepo.getBookingOfficeByTripID(tripID, pageable).getContent();
        if (bookingOffices == null || bookingOffices.isEmpty()) {
            throw new NullPointerException("There's no booking office!");
        }
        return bookingOffices;
    }

    public List<BookingOffice> searchByName(String name, Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<BookingOffice> bookingOffices = bookingOfficeRepo.searchByName(name, pageable).getContent();
        if (bookingOffices == null || bookingOffices.isEmpty()) {
            throw new NullPointerException("There's no booking office!");
        }
        return bookingOffices;
    }

    public BookingOffice insertBookingOffice(BookingOffice bookingOffice, Long tripID) {

        validateInput.validatePhone(bookingOffice.getOfficePhone());

        if (bookingOffice.getEndContractDeadline().isBefore(bookingOffice.getStartContractDeadline())) {
            throw new RuntimeException("End date must be after start date.");
        }

        List<Trip> trips = tripRepo.findAll();
        Long count = trips.stream().filter(t -> tripID == t.getTripId()).count();
        if (count == 0) {
            throw new RuntimeException("Not exist trip with ID: " + tripID);
        }

        try {
            bookingOfficeRepo.insertBookingOffice(bookingOffice.getEndContractDeadline(), bookingOffice.getOfficeName(), bookingOffice.getOfficePhone(), bookingOffice.getOfficePlace(),
                    bookingOffice.getOfficePrice(), bookingOffice.getStartContractDeadline(), tripID);
            return bookingOfficeRepo.getInsertedBookingOffice();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public BookingOffice updateBookingOffice(BookingOffice bookingOffice, Long tripID) {

        validateInput.validatePhone(bookingOffice.getOfficePhone());

        if (bookingOffice.getEndContractDeadline().isBefore(bookingOffice.getStartContractDeadline())) {
            throw new RuntimeException("End date must be after start date.");
        }

        List<Trip> trips = tripRepo.findAll();
        Long count = trips.stream().filter(t -> tripID == t.getTripId()).count();
        if (count == 0) {
            throw new RuntimeException("Not exist trip with ID: " + tripID);
        }

        Optional<BookingOffice> b = bookingOfficeRepo.findById(bookingOffice.getOfficeId());
        if (b.isPresent()) {
            try {
                bookingOfficeRepo.updateBookingOffice(bookingOffice.getEndContractDeadline(), bookingOffice.getOfficeName(), bookingOffice.getOfficePhone(), bookingOffice.getOfficePlace(),
                        bookingOffice.getOfficePrice(), bookingOffice.getStartContractDeadline(), tripID, bookingOffice.getOfficeId());
                return bookingOfficeRepo.findById(bookingOffice.getOfficeId()).get();
            } catch (RuntimeException ex) {
                throw new RuntimeException("Updating fail!");
            }
        }
        throw new NullPointerException("Not exist bookingOffice with ID: " + bookingOffice.getOfficeId() + " for updating!");
    }

    public void deleteBookingOfficeById(Long id) {
        Optional<BookingOffice> b = bookingOfficeRepo.findById(id);
        if (b.isPresent()) {
            try {
                bookingOfficeRepo.deleteBookingOfficeById(id);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist bookingOffice with ID: " + id + " for deleting!");
    }

}
