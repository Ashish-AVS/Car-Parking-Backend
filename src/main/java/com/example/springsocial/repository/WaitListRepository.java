package com.example.springsocial.repository;

import com.example.springsocial.model.Waitlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitListRepository extends JpaRepository<Waitlist, Long> {
}
