package com.example.bikash.SpringTest.Service;

import com.example.bikash.SpringTest.Model.Stock;
import com.example.bikash.SpringTest.Model.Ticket;
import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.StockRepository;
import com.example.bikash.SpringTest.Repos.TicketRepository;
import com.example.bikash.SpringTest.Repos.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketServiceForOptimisticLocaking {



    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public Ticket bookTicket(Long userId, String eventName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ValidationException("User not found");
        }

        for (int retry = 0; retry < 3; retry++) { // Retry in case of conflict
            try {
                Optional<Stock> stockOptional = stockRepository.findByEventName(eventName);
                if (stockOptional.isEmpty()) {
                    throw new ValidationException("Stock not found");
                }

                Stock stock = stockOptional.get();
                if (stock.getAvailableTickets() <= 0) {
                    throw new ValidationException("No available tickets.");
                }

                // Book the ticket
                Ticket ticket = new Ticket();
                ticket.setUser(userOptional.get());
                ticket.setEventName(eventName);
                ticket.setPrice(stock.getTicketPrice());
                ticket.setBookingDate(LocalDateTime.now());

                Ticket bookedTicket = ticketRepository.save(ticket);

                // Reduce available tickets and save
                stock.setAvailableTickets(stock.getAvailableTickets() - 1);
                stockRepository.save(stock);

                return bookedTicket;

            } catch (OptimisticLockingFailureException e) {
                if (retry == 2) {
                    throw new ValidationException("Booking failed due to high concurrency. Please try again.");
                }
            }
        }

        throw new ValidationException("Unexpected error occurred while booking.");
    }
}
