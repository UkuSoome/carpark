package com.swed.carpark.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CarDto {
    @NotNull
    @Min(90)
    @Max(10000)
    private Integer weight;
    @NotNull
    @Min(10)
    @Max(5000)
    private Integer height;
}
