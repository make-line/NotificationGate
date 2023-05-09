package com.example.notificationgate.model.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    String consumer;
    String name;
    String description;
    LocalDateTime timestamp;
    String okUrl;
    String cancelUrl;

}
