package com.weddingapp.wd17weddingplanner.services;

import com.weddingapp.wd17weddingplanner.model.Booking;
import com.weddingapp.wd17weddingplanner.model.Couple;
import com.weddingapp.wd17weddingplanner.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        booking.setStatus("WAITING FOR CONFIRMATION");
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByCouple(Couple couple) {
        return bookingRepository.findByCouple(couple);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBookingStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            return bookingRepository.save(booking);
        }
        return null;
    }
}
