package com.example.bikash.SpringTest.Model;

import com.example.bikash.SpringTest.Auditors.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stock  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private int totalTickets;

    private int availableTickets;

    private double ticketPrice;

    @Version  // This enables Optimistic Locking
    private int version;
}
