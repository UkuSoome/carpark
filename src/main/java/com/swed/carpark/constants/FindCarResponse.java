package com.swed.carpark.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class FindCarResponse {
    private FindCarStatus carFound;
    private UUID carid;
    private Integer floorId;
    private Integer spaceId;
    private double pricePerMinute;
}
