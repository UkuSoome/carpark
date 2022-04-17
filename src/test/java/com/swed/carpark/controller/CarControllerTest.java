package com.swed.carpark.controller;

import com.swed.carpark.service.CarService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Test
    public void saveCar() throws Exception {
        JSONObject json = new JSONObject();
        json.put("weight", 351);
        json.put("height", 50);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cars/save")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void getCarList() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/cars/findall");

        mockMvc.perform((request)).andExpect(status().isOk());
    }

    @Test
    public void deleteCarById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/cars/delete/" + UUID.randomUUID());

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void findCarByUUID() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/cars/find/" + UUID.randomUUID());

        mockMvc.perform(request).andExpect(status().isOk());
    }
}






















