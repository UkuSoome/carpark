package com.swed.carpark.service;

import com.swed.carpark.entity.ParkingSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParkingSpaceServiceTest {

    @Autowired
    ParkingSpaceService parkingSpaceService;

    private ParkingSpace parkingSpace;
    private ParkingSpace saveResponse;

    @BeforeEach
    void setup() {
        parkingSpace = new ParkingSpace(1,2, UUID.randomUUID().toString());
        saveResponse = parkingSpaceService.saveSpace(parkingSpace);
    }

    @Test
    public void saveSpace() {
        assertTrue( saveResponse.getFloorId().equals(parkingSpace.getFloorId()) &&
                             saveResponse.getSpaceId().equals(parkingSpace.getSpaceId()) &&
                             saveResponse.getCarId().equals(parkingSpace.getCarId()));
    }

    @Test
    public void deleteSpaceByCarId() {
        ParkingSpace deleteResponse = parkingSpaceService.deleteSpaceByCarId(parkingSpace.getCarId());
        assertTrue(deleteResponse.getFloorId().equals(parkingSpace.getFloorId()) &&
                deleteResponse.getSpaceId().equals(parkingSpace.getSpaceId()) &&
                deleteResponse.getCarId().equals(parkingSpace.getCarId()));
    }

    @Test
    public void getSpaces() { //its a weird function to test, the contents are not really important, just that it would return a list with at least 1 component
        // since we run setup() function to save a car before this test.
        List<ParkingSpace> spaceList = parkingSpaceService.getSpaces();
        assertTrue(spaceList.size()>0);
    }

    @Test
    public void findSpaceByCarId() {
        ParkingSpace findResponse = parkingSpaceService.findSpaceByCarId(saveResponse.getCarId());
        assertTrue(findResponse.getFloorId().equals(parkingSpace.getFloorId()) &&
                findResponse.getSpaceId().equals(parkingSpace.getSpaceId()) &&
                findResponse.getCarId().equals(parkingSpace.getCarId()));
    }
}
