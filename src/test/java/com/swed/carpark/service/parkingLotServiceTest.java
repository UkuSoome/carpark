package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingLot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class parkingLotServiceTest {
    @Autowired
    ParkingLotService parkingLotService;

    @Test
    public void saveFloor() {
        ParkingLot testFloor = new ParkingLot(1, 1000, 500, new BigDecimal(0.5));
        ParkingLot floor = parkingLotService.saveFloor(testFloor);
        assert(floor.getId()==1);
    }
    @Test
    public void getBestSuitableFloor() {
        Integer weight = 1000;
        Integer height = 500;
        ParkingLot floor = parkingLotService.getBestSuitableFloor(weight,height);
        assert(floor.getWeightLim()== weight.intValue());
        assert(floor.getHeightLim()== height.intValue());
    }
    @Test
    public void findFloorById() {
        Optional<ParkingLot> floor = parkingLotService.findFloorById(1);
        assert(floor.get().getId()==1);
    }
}
