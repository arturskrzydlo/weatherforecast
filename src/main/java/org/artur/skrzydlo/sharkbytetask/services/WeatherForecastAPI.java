package org.artur.skrzydlo.sharkbytetask.services;

import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;

import java.util.List;

public interface WeatherForecastAPI {

    List<WeatherForecastDTO> get5daysWeatherForecastByCity(String cityName, String countryCode);

}
