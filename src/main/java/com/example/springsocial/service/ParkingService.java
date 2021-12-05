package com.example.springsocial.service;


import com.example.springsocial.model.Booking;
import com.example.springsocial.model.ParkingLot;
import com.example.springsocial.model.ParkingSlot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParkingService {
    List<ParkingLot> getAllLots();
    List<ParkingSlot> getAllSlots();

    ParkingLot addLot(ParkingLot parkingLot);
    ParkingSlot addSlot(ParkingSlot parkingSlot);

    ParkingLot getLotById(Long id);
    ParkingSlot getSlotById(Long id);

    ParkingLot addSlotToLot(Long slotId, Long lotId);
    ParkingLot deleteLot(Long lotId);
    ParkingSlot deleteSlot(Long slotId);
    Boolean checkAvail(String hour, Long lotId);

    ParkingSlot bookSlotRandom(String hour, Long lotId, Long userId);
    Booking bookSlot(String hours, Long lotId, Long slotId, Long userId, Integer cost, String services);
}
