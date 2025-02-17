package com.example.bikash.SpringTest.Service;

import com.example.bikash.SpringTest.Model.Ticket;
import com.example.bikash.SpringTest.Model.Stock;
import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.StockRepository;
import com.example.bikash.SpringTest.Repos.TicketRepository;
import com.example.bikash.SpringTest.Repos.UserRepository;
import jakarta.persistence.LockTimeoutException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket bookTicket(Long userId, String eventName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ValidationException("User not found");
        }

        // in this method i have to use pessimist  locking  for concurrency

        try {


            Optional<Stock> stockOptional = stockRepository.findByEventNameForBooking(eventName);
            if (stockOptional.isEmpty()) {
                throw new ValidationException("Stock not found");
            }

            Stock stock = stockOptional.get();

            if (stock.getAvailableTickets() <= 0) {
                throw new ValidationException("Ticket number not available");
            }
            // Book the ticket
            Ticket ticket = new Ticket();
            ticket.setUser(userOptional.get());
            ticket.setEventName(eventName);
            ticket.setPrice(stock.getTicketPrice());
            ticket.setBookingDate(LocalDateTime.now());

            Ticket bookedTicket = ticketRepository.save(ticket);

            stock.setAvailableTickets(stock.getAvailableTickets() - 1);
            stockRepository.save(stock);

            return bookedTicket;
        } catch (LockTimeoutException e) {
            throw new ValidationException("Database is busy, please try again.");
        }
    }
}

