package com.swed.carpark.service;



import com.swed.carpark.entity.ParkingLot;
import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public ParkingSpace saveSpace(ParkingSpace parkingSpace) {return parkingSpaceRepository.save(parkingSpace);}


    @Override
    public List<ParkingSpace> getSpaces() {return  (List<ParkingSpace>) parkingSpaceRepository.findAll(); }

    @Override
    public void deleteSpaceById(Integer spaceId) { parkingSpaceRepository.deleteById(spaceId); }

}
