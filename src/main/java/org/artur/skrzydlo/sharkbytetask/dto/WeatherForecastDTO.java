package org.artur.skrzydlo.sharkbytetask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherForecastDTO {

    @JsonProperty("temp")
    private Double temperature;
    private Double pressure;
    private Double humidity;
























}
