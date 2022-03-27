package com.swed.carpark.controller;

import com.swed.carpark.service.CarService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CarController.class)
public class CarControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarService carService;

    @Test
    public void saveCar() throws Exception {

        JSONObject json = new JSONObject();
        json.put("weight", 1000);
        json.put("height", 500);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/cars")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void getCarList() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/cars");

        mockMvc.perform((request)).andExpect(status().isOk());
    }

    @Test
    public void deleteCarById() throws Exception {
        UUID uuid = UUID.randomUUID();
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/cars/" + uuid.toString());

        mockMvc.perform((request)).andExpect(status().isOk());
    }
}






















