package com.example.springsocial.service;


import com.example.springsocial.model.ParkingLot;
import com.example.springsocial.model.Specialisation;
import com.example.springsocial.model.Worker;
import com.example.springsocial.repository.ParkingLotRepository;
import com.example.springsocial.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService{

    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;


    @Override
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker addWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public Worker getWorkerById(Long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET Worker"));
    }

    @Override
    public Worker addSpecialisationToWorker(Long id, Specialisation specialisation) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET worker"));
        List<Specialisation> specs = worker.getSpecialisations();
        specs.add(specialisation);
        worker.setSpecialisations(specs);
        workerRepository.save(worker);
        return worker;
    }

    @Override
    public Worker addRating(Long id, Double rating) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to GET customer"));
        worker.setRating((worker.getRating() + rating)/2);
        return workerRepository.save(worker);
    }

    @Override
    public ParkingLot addWorkerToLot(Long workerId, Long lotId) {
        Worker workerToAdd = getWorkerById(workerId);
        ParkingLot parkingLot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to Find the parking lot"));

        List<Worker> workerList= parkingLot.getWorkerList();
        workerList.add(workerToAdd);
        parkingLot.setWorkerList(workerList);
        parkingLotRepository.save(parkingLot);
        return parkingLot;
    }

    @Override
    public void deleteWorkerById(Long workerId) {
         workerRepository.deleteById(workerId);
    }
}
