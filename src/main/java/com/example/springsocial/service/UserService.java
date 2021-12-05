package com.example.springsocial.service;


import com.example.springsocial.model.Car;
import com.example.springsocial.model.ParkingSlot;
import com.example.springsocial.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    User getUserById(Long id);
    User addCarToUser(Long id, Car car);
    Boolean isSlotAvailable(Long parkingLotId, Integer hour);
    ParkingSlot bookSlot(Long parkingLotId, Integer hour);
    User addBalance(Long id, Integer amount);
}
