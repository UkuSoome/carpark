package com.swed.carpark.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ParkCarResponse {
    private ParkCarStatus status;
    private UUID carId;
}
