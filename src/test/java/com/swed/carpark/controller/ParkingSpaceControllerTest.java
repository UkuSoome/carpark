package com.swed.carpark.controller;

import com.swed.carpark.service.ParkingSpaceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingSpaceControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ParkingSpaceService parkingSpaceService;

    @Test
    public void getSpaces() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/spaces");

        mockMvc.perform((request)).andExpect(status().isOk());
    }
}
