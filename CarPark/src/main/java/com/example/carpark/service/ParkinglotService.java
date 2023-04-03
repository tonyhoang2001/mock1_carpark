package com.example.carpark.service;

import com.example.carpark.entity.Parkinglot;
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
public class ParkinglotService {
    @Autowired
    private ParkinglotRepo parkinglotRepo;

    public List<Parkinglot> getAll(Integer page, Integer size) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Parkinglot> list = parkinglotRepo.getAll(pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no parking lot!");
        }
        return list;
    }

    public List<Parkinglot> searchByPlace(Integer page, Integer size, String place) {
        if (page <= 0 || size <= 0) {
            throw new RuntimeException("Invalid page!");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        List<Parkinglot> list = parkinglotRepo.searchByPlace(place, pageable).getContent();
        if (list.isEmpty() || list == null) {
            throw new NoSuchElementException("There's no parking lot!");
        }
        return list;
    }

    public Parkinglot getParkinglotById(Long id) {
        Parkinglot parkinglot = parkinglotRepo.getParkinglotById(id);
        if (parkinglot == null) {
            throw new NullPointerException("There's no parking lot!");
        }
        return parkinglot;
    }

    public Parkinglot insertParkinglot(Parkinglot parkinglot) {
        try {
            parkinglotRepo.insertParkinglot(parkinglot.getParkArea(), parkinglot.getParkName(), parkinglot.getParkPlace(), parkinglot.getParkPrice());
            return parkinglotRepo.getInsertedParkinglot();
        } catch (RuntimeException e) {
            throw new RuntimeException("Inserting fail!");
        }
    }

    public Parkinglot updateParkinglot(Parkinglot parkinglot) {
        Optional<Parkinglot> p = parkinglotRepo.findById(parkinglot.getParkId());
        if (p.isPresent()) {
            try {
                parkinglotRepo.updateParkinglot(parkinglot.getParkArea(), parkinglot.getParkName(), parkinglot.getParkPlace(), parkinglot.getParkPrice(), parkinglot.getParkStatus(), parkinglot.getParkId());
                Optional<Parkinglot> rs = parkinglotRepo.findById(parkinglot.getParkId());
                return rs.get();
            } catch (RuntimeException ex) {
                throw new RuntimeException("Updating fail!");
            }
        }
        throw new NullPointerException("Not exist parking lot with ID: " + parkinglot.getParkId() + " for updating!");
    }

    public void deleteParkinglotById(Long id) {
        Optional<Parkinglot> p = parkinglotRepo.findById(id);
        if (p.isPresent()) {
            try {
                parkinglotRepo.deleteParkinglotById(id);
                return;
            } catch (RuntimeException ex) {
                throw new RuntimeException("Deleting fail!");
            }
        }
        throw new NullPointerException("Not exist parking lot with ID: " + id + " for deleting!");
    }

}
