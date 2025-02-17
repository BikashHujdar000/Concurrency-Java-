package com.example.bikash.SpringTest.Service;

import com.example.bikash.SpringTest.Model.Ticket;
import com.example.bikash.SpringTest.Model.User;
import com.example.bikash.SpringTest.Repos.StockRepository;
import com.example.bikash.SpringTest.Repos.TicketRepository;
import com.example.bikash.SpringTest.Repos.UserRepository;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class TicketServiceAtomicSqlUpdateService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final StockRepository stockRepository;

    public TicketServiceAtomicSqlUpdateService(UserRepository userRepository, TicketRepository ticketRepository, StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.stockRepository = stockRepository;
    }

    public Ticket bookTicket(Long userId, String eventName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ValidationException("User not found");
        }
        // using the atomic csql logic to decrement hte ticket count
        // first decrement from the database then conform the ticket
        // **🔹 Atomic SQL update to decrement the ticket count**

        int updatedRows = stockRepository.decrementTicket(eventName);
        if (updatedRows == 0) {
            throw new ValidationException("No tickets available!");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(userOptional.get());
        ticket.setEventName(eventName);
        ticket.setBookingDate(LocalDateTime.now());

        Ticket bookedTicket = ticketRepository.save(ticket);
        return bookedTicket;

    }

}
