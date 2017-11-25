package org.artur.skrzydlo.sharkbytetask.dto;

import lombok.Data;

@Data
public class WeatherForecastDTO {

    private CityDTO cityDTO;
    private Double temperature;
    private Double pressure;
    private Double humidity;
}
