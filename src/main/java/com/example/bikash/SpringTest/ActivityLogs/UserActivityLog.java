package com.example.bikash.SpringTest.ActivityLogs;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String username;
    private String eventType;
    private String description;
    private boolean status;
    private String apiUrl;
    private String httpMethod;
    private String ipAddress;
    private LocalDateTime timestamp;
}
