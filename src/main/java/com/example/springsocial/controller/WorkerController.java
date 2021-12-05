package com.example.springsocial.controller;


import com.example.springsocial.model.ParkingLot;
import com.example.springsocial.model.Specialisation;
import com.example.springsocial.model.Worker;
import com.example.springsocial.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public List<Worker> allWorkers(){
        return workerService.getAllWorkers();
    }

    @PostMapping("/add")
    public Worker addWorker(@RequestBody Worker worker){
        return workerService.addWorker(worker);
    }

    @GetMapping("/{id}")
    public Worker getById(@PathVariable Long id){
        return workerService.getWorkerById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id){
         workerService.deleteWorkerById(id);
         return "Worker Deleted!";
    }

    @PutMapping("/{id}/addSpecialisation")
    public Worker addWorkerToSpec(@PathVariable Long id,@RequestBody Specialisation specialisation){
        return workerService.addSpecialisationToWorker(id, specialisation);
    }

    @PutMapping("/{id}/rating/{rating}")
    public Worker addWorkerRating(@PathVariable Long id,@PathVariable Double rating){
        return workerService.addRating(id, rating);
    }

    @PutMapping("/{workerId}/addParking/{lotId}")
    public ParkingLot assignWorkerToLot(@PathVariable Long workerId, @PathVariable Long lotId){
        return workerService.addWorkerToLot(workerId, lotId);
    }
}
