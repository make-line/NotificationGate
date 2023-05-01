package com.example.notificationgate.model;


import com.example.notificationgate.model.enums.Stage;
import com.example.notificationgate.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_events")
@IdClass(EventUserId.class)
@Getter
@Setter
@ToString
public class EventUser {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    private Status status = Status.INITIAL;

    @ManyToOne
    private  NotifyStrategy notifyStrategy;

    private LocalDateTime nextNotify;

    private Stage nextStage;
}
