package com.swed.carpark.controller;


import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.service.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingSpaceController {
    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @GetMapping("/spaces")
    public List<ParkingSpace> fetchParkingSpaces() {return parkingSpaceService.getSpaces();}
}
