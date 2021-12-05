package com.example.springsocial.service;


import com.example.springsocial.model.*;
import com.example.springsocial.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService{

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    public List<ParkingLot> getAllLots() {
        return parkingLotRepository.findAll();
    }

    @Override
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public ParkingLot addLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public ParkingSlot addSlot(ParkingSlot parkingSlot) {
        return parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public ParkingLot getLotById(Long id) {
        return parkingLotRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
    }

    @Override
    public ParkingSlot getSlotById(Long id) {
        return parkingSlotRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
    }

    @Override
    public ParkingLot addSlotToLot(Long slotId, Long lotId) {
        ParkingLot parkingLot= parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));

        ParkingSlot parkingSlot= parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));

        List<ParkingSlot> ps = parkingLot.getParkingSlotList();
        for (ParkingSlot slot :
                ps) {
            if(slot.getId() == slotId){
                return parkingLot;
            }
        }
        ps.add(parkingSlot);
        parkingLot.setParkingSlotList(ps);
        parkingLotRepository.save(parkingLot);
        return parkingLot;
    }

    @Override
    public ParkingLot deleteLot(Long lotId) {
        ParkingLot pl =  parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
        parkingLotRepository.delete(pl);
        return pl;
    }

    @Override
    public ParkingSlot deleteSlot(Long slotId) {
        ParkingSlot pl =  parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find SLOT"));
        parkingSlotRepository.delete(pl);
        return pl;
    }

    @Override
    public Boolean checkAvail(String hour, Long lotId) {
        ParkingLot parkingLot= parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
        List<ParkingSlot> ps = parkingLot.getParkingSlotList(); // Search each slot for avail in that hour
        for (ParkingSlot slot : ps) {
            String availHours = slot.getHours();
            if(availHours.contains(hour)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ParkingSlot bookSlotRandom(String hour, Long lotId, Long customerId) {
        ParkingLot parkingLot= parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
        List<ParkingSlot> ps = parkingLot.getParkingSlotList(); // Search each slot for avail in that hour
        for (ParkingSlot slot : ps) {
            String availHours = slot.getHours();
            if(availHours.contains(hour)){
                // For a Given Day
                 slot.setHours(availHours.replace(hour, ""));
                 parkingSlotRepository.save(slot);
                 User user = userRepository.findById(customerId)
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find Customer"));
                 List<Booking> bookingList = user.getBookings();
                 // Create A NEW BOOKING
                    Booking booking = new Booking();

                String str = LocalDate.now().toString();
                int date = Integer.parseInt(str.substring(str.length()-2, str.length()));
                str = str.substring(0, str.length() - 2);
                int hours=(int)hour.charAt(0) - 49;
                int hoursReplaced=hours;
                int day=0;
                if(hours > 23){
                    hoursReplaced = hoursReplaced - 24;
                    day++;
                    if(hours>46){
                        hoursReplaced = hoursReplaced - 24;
                        day++;
                    }
                    if(hours>69){
                        System.out.println("Hours Not in bound");
                    }
                }
                date+=day;
                if(date < 10){
                    str= str + "0"+ Integer.toString(date);
                }
                else{
                    str= str + Integer.toString(date);
                }
                    booking.setDate(LocalDate.now());
                    booking.setHours(hour); // Convert to hour


                    booking.setSlotId(lotId);
                    booking.setLotId(slot.getId());
                    bookingRepository.save(booking);
                  // Booking Object Done
                 bookingList.add(booking);
//                customer.setParkingSlot(slots);
                userRepository.save(user);
                 return slot;
            }
        }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please check availability of slot before booking a slot");
    }

    @Override
    public Booking bookSlot(String hours, Long lotId, Long slotId, Long customerId, Integer cost, String services) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find LOT"));
        String availHours = slot.getHours();
        if(availHours.contains(hours)){
            // For a Given Day
            slot.setHours(availHours.replace(hours, ""));
            parkingSlotRepository.save(slot);
            User user = userRepository.findById(customerId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find Customer"));
            List<Booking> bookingList = user.getBookings();
            // Create A NEW BOOKING
            Booking booking = new Booking();
            user.setFastTag(user.getFastTag() - cost);
            String hoursReplacedString="";
            String str = LocalDate.now().toString();
            int date = Integer.parseInt(str.substring(str.length()-2, str.length()));
            str = str.substring(0, str.length() - 2);
            int hoursNew=(int)hours.charAt(0) - 49;
            int hoursReplaced=hoursNew;
            int day=0;
            if(hoursNew > 23){
                hoursReplaced = hoursReplaced - 24;
                day++;
                if(hoursNew>46){
                    hoursReplaced = hoursReplaced - 24;
                    day++;
                }
                if(hoursNew>69){
                    System.out.println("Hours Not in bound");
                }
            }

            date+=day;
            if(date < 10){
                str= str + "0"+ Integer.toString(date);
            }
            else{
                str= str + Integer.toString(date);
            }
//            str= str + Integer.toString(date);

            booking.setDate(LocalDate.parse(str));
            booking.setHours(hours); // Convert to hour
            booking.setDateOfBooking(LocalDate.now());

            booking.setSlotId(lotId);
            booking.setLotId(slot.getId());
            booking.setCost(cost);
            booking.setServices(services);
            List<Admin> admin = adminRepository.findAll();
            admin.get(0).setAmount(admin.get(0).getAmount() + cost);
            bookingRepository.save(booking);
            // Booking Object Done
            bookingList.add(booking);
//                customer.setParkingSlot(slots);
            userRepository.save(user);
            return booking;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Please check availability of slot before booking a slot");
    }
}
