package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.Admin;
import com.example.springsocial.model.Car;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.AdminRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/addCustomer")
    public User placeCustomer(@RequestBody User request){
//        return customerRepository.save(request.getCustomer());
//        return customerRepository.save(request);
        return userService.addUser(request);
    }
    @GetMapping("/findAllOrders")
    public List<User> findAllOrders(){
        return userService.getAllUsers();
    }

    @GetMapping("/addbalance")
    public User addBalance(@RequestParam("amount") Integer amount, @RequestParam("id") Long id){
        return userService.addBalance(id, amount);
    }

    @PutMapping("/{customerId}/addCar")
    public User addCarToCustomer(@RequestBody Car car, @PathVariable Long customerId){
        return userService.addCarToUser(customerId, car);
    }

    @PostMapping("/slotAvailable")
    public Boolean checkSlot(Long parkingLotId, Integer hour){
        return userService.isSlotAvailable(parkingLotId, hour);
    }


}
