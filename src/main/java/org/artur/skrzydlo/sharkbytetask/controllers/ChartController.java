package org.artur.skrzydlo.sharkbytetask.controllers;

import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;
import org.artur.skrzydlo.sharkbytetask.exceptions.NotExistingCityException;
import org.artur.skrzydlo.sharkbytetask.services.WeatherForecastAPI;
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
    public List<WeatherForecastDTO> get5daysForecast(@PathVariable String cityName) throws NotExistingCityException {

        CityWithCountryCode cityWithCountryCode;

        try {
            cityWithCountryCode = CityWithCountryCode.getCityByName(cityName);
        } catch (IllegalArgumentException exc) {
            throw new NotExistingCityException(cityName);
        }
        return weatherForecastAPI
                .get5daysWeatherForecastByCity(cityWithCountryCode.toString(), cityWithCountryCode.getCountryCode());
    }

}
