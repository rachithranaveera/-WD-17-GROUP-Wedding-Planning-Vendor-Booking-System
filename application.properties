package com.weddingapp.wd17weddingplanner.repository;

import com.weddingapp.wd17weddingplanner.model.Booking;
import com.weddingapp.wd17weddingplanner.model.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCouple(Couple couple);
    List<Booking> findByStatus(String status);
}
