package com.swed.carpark.constants;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FindCarResponse {
    private FindCarStatus carFound;
    private String carid;
    private Integer floorId;
    private Integer spaceId;
    private BigDecimal pricePerMinute;
}
