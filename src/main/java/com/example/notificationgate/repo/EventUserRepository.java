package com.example.notificationgate.repo;

import com.example.notificationgate.model.Event;
import com.example.notificationgate.model.EventUser;
import com.example.notificationgate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventUserRepository extends JpaRepository<EventUser, User> {
    EventUser getEventUserByUserAndEvent(User user, Event event);
}