package com.swed.carpark.service;



import com.swed.carpark.entity.ParkingSpace;
import com.swed.carpark.repository.ParkingSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ParkingSpaceServiceImpl implements ParkingSpaceService {

    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;

    @Override
    public Integer saveSpace(ParkingSpace parkingSpace) {return parkingSpaceRepository.save(parkingSpace).getId();}


    @Override
    public List<ParkingSpace> getSpaces() {return  (List<ParkingSpace>) parkingSpaceRepository.findAll(); }

    @Override
    public String deleteSpaceByCarId(Integer carId) {
        try {
            ParkingSpace parkingSpace = parkingSpaceRepository.findOne(where(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("carId"), carId))).get();
            parkingSpaceRepository.deleteById(parkingSpace.getId());
            return "Parking space deleted successfully.";
        }
        catch (NoSuchElementException e) {
            return "Car with that id was not found in a parking space.";
        }
    }

}
