package com.swed.carpark.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeleteCarResponse {
    private DeleteCarStatus status;
    private String carId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long minutesParked;
    private BigDecimal priceForParking;
}
