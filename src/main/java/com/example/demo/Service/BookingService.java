package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.user;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.UserRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            UserRepository userRepository) {

        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking saveBooking(Booking booking) {
        return createBooking(booking);
    }

    public Booking createBooking(Booking booking) {

        Long userId = booking.getUserId();

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID is missing. Please login again."
            );
        }

        user loggedInUser = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found: " + userId
                        )
                );

        booking.setUser(loggedInUser);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByUser(Long userId) {

        if (userId == null) {
            return List.of();
        }

        return bookingRepository.findByUser_Id(userId);
    }

    public Booking getBookingById(Long id) {

        Optional<Booking> booking =
                bookingRepository.findById(id);

        return booking.orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}