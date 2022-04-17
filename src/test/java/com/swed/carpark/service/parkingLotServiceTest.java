package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingLot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class parkingLotServiceTest {
    @Autowired
    ParkingLotService parkingLotService;

    @Test
    public void saveFloor() {
        ParkingLot testFloor = new ParkingLot(1, 1000, 500, new BigDecimal("0.5"), 5);
        ParkingLot floor = parkingLotService.saveFloor(testFloor);
        assertTrue(floor.getId()==1 && floor.getWeightLim() == 1000 && floor.getHeightLim() == 500);
    }
    @Test
    public void getBestSuitableFloor() {
        Integer weight = 351;
        Integer height = 50;
        ParkingLot floor = parkingLotService.getBestSuitableFloor(weight,height);
        assertTrue(weight <= floor.getWeightLim() && height <= floor.getHeightLim());
    }
    @Test
    public void findFloorById() {
        ParkingLot floor = parkingLotService.findFloorById(1);
        assertEquals(1, floor.getId());
    }
}
