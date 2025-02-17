package com.example.bikash.SpringTest.ActivityLogs;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;

@Service
public class UserActivityLogService{

    private  final UserActivityLogRepository userActivityLogRepository;

    public UserActivityLogService(UserActivityLogRepository userActivityLogRepository) {
        this.userActivityLogRepository = userActivityLogRepository;
    }


    public  void logActivity(Long userId, String username, String eventType, String description, boolean status, String apiUrl, String httpMethod, String ipAddress)
    {
        new UserActivityLog();
        UserActivityLog userActivityLog = UserActivityLog.builder()
                .userId(userId)
                .username(username)
                .eventType(eventType)
                .description(description)
                .status(status)
                .apiUrl(apiUrl)
                .httpMethod(httpMethod)
                .ipAddress(ipAddress)
                .timestamp(LocalDateTime.now())
                .build();
        userActivityLogRepository.save(userActivityLog);

    }
}

