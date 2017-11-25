package org.artur.skrzydlo.sharkbytetask.controllers;

import org.artur.skrzydlo.sharkbytetask.WeatherForecastAPI;
import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class ChartController {


    private WeatherForecastAPI weatherForecastAPI;

    @Autowired
    public void setWeatherForecastAPI(WeatherForecastAPI weatherForecastAPI) {
        this.weatherForecastAPI = weatherForecastAPI;
    }

    @GetMapping("/{cityName}")
    public List<WeatherForecastDTO> get5daysForecast(@PathVariable String cityName){
        CityWithCountryCode cityWithCountryCode =  CityWithCountryCode.valueOf(cityName.toUpperCase());
        return weatherForecastAPI.get5daysWeatherForecastByCity(cityName,cityWithCountryCode.getCountryCode());
    }
}
