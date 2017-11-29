package org.artur.skrzydlo.sharkbytetask.unit;

import org.artur.skrzydlo.sharkbytetask.controllers.ChartController;
import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;
import org.artur.skrzydlo.sharkbytetask.exceptions.RestResponseExceptionHandler;
import org.artur.skrzydlo.sharkbytetask.services.WeatherForecastAPI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = TestConfiguration.class)
@ActiveProfiles
public class CharControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ChartController chartController;

    @Mock
    private WeatherForecastAPI weatherForecastAPI;

    @Value("${weather.api.request.limit}")
    private int numberOfRequestPerMinute;

    private final static int WEATHER_FREQUENCY_IN_HOURS = 3;
    final int NUMBER_OF_DAYS = 5;
    final int HOURS_DAY = 24;

    final int numberOfWeatherForecasts = NUMBER_OF_DAYS * (HOURS_DAY / WEATHER_FREQUENCY_IN_HOURS);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(chartController)
                                 .setControllerAdvice(new RestResponseExceptionHandler())
                                 .build();
    }

    @Test
    public void getForecastFor5days() throws Exception {

        String city = "Washington";
        String countryCode = "us";

        Mockito.when(weatherForecastAPI.get5daysWeatherForecastByCity(city, countryCode))
               .thenReturn(Collections.nCopies(
                       numberOfWeatherForecasts, new WeatherForecastDTO()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/weather/" + city))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.*").value(hasSize(numberOfWeatherForecasts)))
                    .andReturn();

        Mockito.verify(weatherForecastAPI, Mockito.times(1)).get5daysWeatherForecastByCity(city, countryCode);
    }

    @Test
    public void returnNotFoundWhenNonExistingNameOfCityHasBeenSpecified() throws Exception {

        String city = "Katowice";

        this.mockMvc.perform(MockMvcRequestBuilders.get("/weather/" + city))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.status").isNotEmpty())
                    .andExpect(jsonPath("$.timestamp").isNotEmpty())
                    .andExpect(jsonPath("$.message").isNotEmpty())
                    .andExpect(jsonPath("$.subErrors").value(hasSize(1)))
                    .andExpect(jsonPath("$.subErrors[0].object").value(is("CITY")))
                    .andExpect(jsonPath("$.subErrors[0].rejectedValue").value(is(city)))
                    .andReturn();

        Mockito.verifyZeroInteractions(weatherForecastAPI);
    }

    @Test
    public void testRequestPerMinuteLimit() throws Exception {

        CityWithCountryCode cityWithCountryCode = CityWithCountryCode.WASHINGTON;

        Mockito.when(weatherForecastAPI
                .get5daysWeatherForecastByCity(cityWithCountryCode.toString(), cityWithCountryCode.getCountryCode()))
               .thenReturn(Collections.nCopies(
                       numberOfWeatherForecasts, new WeatherForecastDTO()));

        IntStream.range(0, numberOfRequestPerMinute + 1).forEach(i -> {
            try {

                if (i == numberOfRequestPerMinute + 1) {
                    mockMvc.perform(MockMvcRequestBuilders.get("/weather/" + cityWithCountryCode.toString()))
                           .andExpect(MockMvcResultMatchers.status().isTooManyRequests());
                } else {
                    mockMvc.perform(MockMvcRequestBuilders.get("/weather/" + cityWithCountryCode.toString()))
                           .andExpect(MockMvcResultMatchers.status().isOk());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
