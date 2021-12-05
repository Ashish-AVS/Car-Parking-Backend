package com.example.springsocial.service;

import com.example.springsocial.model.Car;
import com.example.springsocial.model.ParkingSlot;
import com.example.springsocial.model.User;


import com.example.springsocial.repository.ParkingLotRepository;
import com.example.springsocial.repository.ParkingSlotRepository;
import com.example.springsocial.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    PasswordEncoder passwordEncoder;


     public void generateOneTimePassword(User user)
            throws UnsupportedEncodingException, MessagingException {
        String OTP = RandomString.make(8);
        String encodedOTP = passwordEncoder.encode(OTP);

        user.setOneTimePassword(encodedOTP);
        user.setOtpRequestedTime(new Date());

        userRepository.save(user);

        sendOTPEmail(user, OTP);
    }

    public void sendOTPEmail(User user, String OTP)
            throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(user.getEmail());

        String subject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";

        String content = "<p>Hello " + user.getName() + "</p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public void clearOTP(User user) {
        user.setOneTimePassword(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET customer"));
    }

    @Override
    public User addCarToUser(Long id, Car car) {
        User customer = userRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET customer"));
        List<Car> cars = customer.getCars();
//        Long carIndex = cars.size()==0 ? 1: cars.get(cars.size() - 1).getId();
//        car.setId(carIndex);
        cars.add(car);
        customer.setCars(cars);
        userRepository.save(customer);
        return customer;
    }

    @Override
    public Boolean isSlotAvailable(Long parkingLotId, Integer hour) {
        List<ParkingSlot> parkingSlotList = parkingLotRepository.getById(parkingLotId).getParkingSlotList();
        for (ParkingSlot ps :
                parkingSlotList) {
            if (ps.getHours().contains(hour.toString() + ",")){
                // That Hour is present in the String
                String str = ps.getHours();
                str.replace((hour.toString() + ","), "");
                ps.setHours(str);
                // Book Ticket
                return true;
            }
        }
        return false;
    }

    @Override
    public ParkingSlot bookSlot(Long parkingLotId, Integer hour) {
        return null;
    }
    public User addBalance(Long id, Integer amount){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET customer"));
        user.setFastTag(user.getFastTag() + amount);
        userRepository.save(user);
        return user;
    }
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
}
