package com.swed.carpark.repository;

import com.swed.carpark.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CarRepository
        extends CrudRepository<Car, Integer> {
}
