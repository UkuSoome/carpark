package com.swed.carpark.controller;


import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {
    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/floors")
    public ParkingLot saveFloor(@RequestBody ParkingLot floor) {return parkingLotService.saveFloor(floor) ;}
}
