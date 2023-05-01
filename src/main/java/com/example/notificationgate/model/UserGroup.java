package com.example.notificationgate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

    @ManyToMany(mappedBy = "groups")
    private Set<Event> events;

    @ManyToMany
    private Set<User> subs;
    private String name;
    private String definition;
    private Boolean isAdminAddsOnly;
    @ManyToOne
    private User admin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_group")
    private UserGroup parentUserGroup;
}
