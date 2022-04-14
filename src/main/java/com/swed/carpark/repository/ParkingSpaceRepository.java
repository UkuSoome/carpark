package com.swed.carpark.repository;


import com.swed.carpark.entity.ParkingSpace;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSpaceRepository extends CrudRepository<ParkingSpace, Integer>, JpaSpecificationExecutor<ParkingSpace> {
}