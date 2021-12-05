package com.example.springsocial.controller;


import com.example.springsocial.model.*;
import com.example.springsocial.repository.AdminRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.repository.WaitListRepository;
import com.example.springsocial.service.ParkingService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/parking") @Slf4j
public class ParkingController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ParkingService parkingService;

    @Autowired
    WaitListRepository waitListRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping @RequestMapping("/lot")
    public List<ParkingLot> getLots(){
        return parkingService.getAllLots();
    }

    @GetMapping("/waitlist")
    public Waitlist enterWaitList(
            @RequestParam("userId") Long userId,
            @RequestParam("lotId") Long lotId
    ){
        Waitlist wl = new Waitlist();
        wl.setLotId(lotId);
        waitListRepository.save(wl);
//        wl.setId(Long.valueOf(id));
        User usr = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET customer"));
        List<Waitlist> wls =  usr.getWaitlists();
        wls.add(wl);
        usr.setWaitlists(wls);
        userRepository.save(usr);
        return wl;
    }

    @GetMapping("/admin")
    public List<Admin> getAdmin(){
        return adminRepository.findAll();
    }

    @PostMapping("/admin")
    public Admin createAdmin(){
        Admin admin = new Admin();
        admin.setAmount(0l);
        return adminRepository.save(admin);
    }

    @GetMapping @RequestMapping("/slot")
    public List<ParkingSlot> getSlots(){
        return parkingService.getAllSlots();
    }

    @PostMapping @RequestMapping("/add/lot")
    public ParkingLot saveLot(@RequestBody ParkingLot parkingLot){
        return parkingService.addLot(parkingLot);
    }

    @PostMapping @RequestMapping("/add/slot")
    public ParkingSlot saveSlot(@RequestBody ParkingSlot parkingSlot){
        return parkingService.addSlot(parkingSlot);
    }

    @GetMapping @RequestMapping("/slot/{slotId}")
    public ParkingSlot slotById(@PathVariable Long slotId){
        return parkingService.getSlotById(slotId);
    }

    @GetMapping @RequestMapping("/lot/{lotId}")
    public ParkingLot lotById(@PathVariable Long lotId){
        return parkingService.getLotById(lotId);
    }

    @PutMapping @RequestMapping("/slot/{slotId}/lot/{lotId}")
    public ParkingLot allotSlotToLot(@PathVariable Long slotId, @PathVariable Long lotId){
        return parkingService.addSlotToLot(slotId, lotId);
    }

//    @GetMapping @RequestMapping("/isAvailable/{hour}/{lotId}")
    @DeleteMapping @RequestMapping("/delete/lot/{lotId}")
    public ParkingLot deleteLot(@PathVariable Long lotId){
        return parkingService.deleteLot(lotId);
    }
    @DeleteMapping @RequestMapping("/delete/slot/{slotId}")
    public ParkingSlot delteSlot(@PathVariable Long slotId){
        return parkingService.deleteSlot(slotId);
    }
    @GetMapping @RequestMapping("/isAvailable/{hour}/{lotId}")
    public String isAvail(@PathVariable(name = "hour") String hour, @PathVariable(name = "lotId") Long lotId){
        Boolean bool = parkingService.checkAvail(hour, lotId);
        if(bool){
            return "Available";
        }
        return "Not Available";
    }
    @GetMapping @RequestMapping("/bookSlot")
    public ParkingSlot bookSlotRandom(@RequestParam(name = "hour") String hour,
                                @RequestParam(name = "lotId") Long lotId,
                                @RequestParam(name = "customerId") Long customerId
                           ){
         return parkingService.bookSlotRandom(hour, lotId, customerId);
//        return hour + " " + lotId.toString() + " " + customerId.toString();
    }



    @PostMapping @RequestMapping("/bookSlotId")
    public Booking bookSlot(@RequestBody HoursDTO hours,
                            @RequestParam(name = "lotId") Long lotId,
                            @RequestParam(name = "slotId") Long slotId,
                            @RequestParam(name = "customerId") Long customerId,
                            @RequestParam(name = "cost") Integer cost,
                            @RequestParam(name = "workerId") String services
//                            @RequestBody String services
    ){
        log.info(hours.getHours());
        return parkingService.bookSlot(hours.getHours(), lotId, slotId,customerId, cost, services);
//        return hour + " " + lotId.toString() + " " + customerId.toString();
    }
//
//    @GetMapping("/getSlot")
//    public ParkingSlot getSlot(
//            @RequestParam(name = "lotId") Long lotId,
//            @RequestParam(name = "slotId") Long slotId,
//    )
}

@Data
class HoursDTO{
    private String hours;
}