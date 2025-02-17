package com.example.bikash.SpringTest.Controller;

import com.example.bikash.SpringTest.Model.Ticket;
import com.example.bikash.SpringTest.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestParam Long userId, @RequestParam String eventName) {


          Ticket ticket =   ticketService.bookTicket(userId, eventName);
          return  new ResponseEntity<>(ticket, HttpStatus.OK);
    }
}
