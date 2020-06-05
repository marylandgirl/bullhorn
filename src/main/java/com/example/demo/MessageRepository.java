package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface MessageRepository extends CrudRepository<Message,Long> {
    Set<Message> findAllByUser(User user);
}
