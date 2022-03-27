package com.swed.carpark.controller;

import com.swed.carpark.service.ParkingLotService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ParkingLotService parkingLotService;

    @Test
    public void saveFloor() throws Exception {
        JSONObject json = new JSONObject();
        json.put("weightlim", 1000);
        json.put("heightlim", 500);
        json.put("pricemultiplier", 3.3);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/floors")
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform((request)).andExpect(status().isOk());
    }
}
