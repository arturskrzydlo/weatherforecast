package org.artur.skrzydlo.sharkbytetask;

import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherForecastService implements WeatherForecastAPI
{
    @Override public List<WeatherForecastDTO> get5daysWeatherForecastByCity(String cityName, String countryCode) {
        return null;
    }
}
