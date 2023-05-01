package com.example.notificationgate.model.dto;


import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
public class Message {
    String consumer;
    String name;
    String description;
    LocalDateTime timestamp;
    String okUrl;
    String cancelUrl;

}
