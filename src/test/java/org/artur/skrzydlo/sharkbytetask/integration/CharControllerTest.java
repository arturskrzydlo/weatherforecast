package org.artur.skrzydlo.sharkbytetask.integration;

import org.artur.skrzydlo.sharkbytetask.WeatherForecastAPI;
import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CharControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherForecastAPI weatherForecastAPI;

    private final static int WEATHER_FREQUENCY_IN_HOURS=3;


    @Test
    public void getForecastFor5days() throws Exception {

        String city = "Washington";
        String countryCode="us";
        final int NUMBER_OF_DAYS=5;
        final int HOURS_DAY=24;

        final int numberOfWeatherForecasts = NUMBER_OF_DAYS * (HOURS_DAY / WEATHER_FREQUENCY_IN_HOURS);
        Mockito.when(weatherForecastAPI.get5daysWeatherForecastByCity(city,countryCode)).thenReturn(Collections.nCopies(
                numberOfWeatherForecasts,new WeatherForecastDTO()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/weather/"+city))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.*").value(hasSize(numberOfWeatherForecasts)))
                    .andReturn();

        Mockito.verify(weatherForecastAPI,Mockito.times(1)).get5daysWeatherForecastByCity(city,countryCode);
    }
}
