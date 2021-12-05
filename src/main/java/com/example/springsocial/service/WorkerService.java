package com.example.springsocial.service;


import com.example.springsocial.model.ParkingLot;
import com.example.springsocial.model.Specialisation;
import com.example.springsocial.model.Worker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkerService {
    List<Worker> getAllWorkers();
    Worker addWorker(Worker worker);
    Worker getWorkerById(Long id);
    Worker addSpecialisationToWorker(Long id, Specialisation specialisation);
    Worker addRating(Long id, Double rating);
    ParkingLot addWorkerToLot(Long workerId, Long lotId);
    void deleteWorkerById(Long workerId);
}
