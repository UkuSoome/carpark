package com.swed.carpark.service;

import com.swed.carpark.constants.*;
import com.swed.carpark.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class CarServiceTest {

    @Autowired
    CarService carService;
    @Test
    public void saveCar() {
        Car car = new Car();
        car.setWeight(1000);
        car.setHeight(500);
        ParkCarResponse actualResponse = carService.saveCar(car);
        assert(actualResponse.getStatus().equals(ParkCarStatus.NOSUITABLESPACEFOUND));
    }
    @Test
    public void deleteCarById() {
        UUID uuid = UUID.randomUUID();
        DeleteCarResponse response = carService.deleteCarById(uuid);
        assert(response.getStatus().equals(DeleteCarStatus.NOSUCHCARFOUND));
    }
    @Test
    public void getCarList() {
        List<Car> carList = carService.getCarList();
        assert(carList.size()==0);
    }
    @Test
    public void findCarByUUID(){
        UUID uuid = UUID.randomUUID();
        FindCarResponse response = carService.findCarByUUID(uuid);
        assert(response.getCarFound().equals(FindCarStatus.CARNOTFOUND));
    }
}
