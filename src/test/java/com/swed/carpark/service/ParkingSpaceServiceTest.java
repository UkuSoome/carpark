package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingSpace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingSpaceServiceTest {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    @Test
    public void deleteSpaceByCarId() {
        UUID uuid = UUID.randomUUID();
        boolean test = parkingSpaceService.deleteSpaceByCarId(uuid);
        assert(!test);
    }

    @Test
    public void saveSpace() {
        Integer resp = parkingSpaceService.saveSpace(new ParkingSpace());
        assert(resp==1);
    }

    @Test
    public void getSpaces() {
        List<ParkingSpace> spaceList = parkingSpaceService.getSpaces();
        assert(spaceList.size()==1);
    }

    @Test
    public void findSpaceByCarId() {
        UUID uuid = UUID.randomUUID();
        ParkingSpace parkingSpace = parkingSpaceService.findSpaceByCarId(uuid);
        assert(parkingSpace.getId()==null);
    }
}
