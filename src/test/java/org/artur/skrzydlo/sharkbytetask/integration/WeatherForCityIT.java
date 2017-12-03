package org.artur.skrzydlo.sharkbytetask.integration;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WeatherForCityIT {

    private final static int WEATHER_FREQUENCY_IN_HOURS = 3;
    final int NUMBER_OF_DAYS = 5;

    final int numberOfWeatherForecasts = Long
            .valueOf(TimeUnit.DAYS.toHours(NUMBER_OF_DAYS) / WEATHER_FREQUENCY_IN_HOURS).intValue();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Configuration configuration;

    @Test
    public void getWeatherForExistingCity() throws Exception {

        String existingCity = "London";

        mockMvc.perform(get("/weather/" + existingCity))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.*").value(hasSize(numberOfWeatherForecasts)));
    }

    @Test
    public void getWeatherForNonExistingCity() throws Exception {

        String notExistingCity = "DummyCity";

        mockMvc.perform(get("/weather/" + notExistingCity))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.status").value(equalTo(HttpStatus.NOT_FOUND.name())))
               .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    public void receivedWeatherHasAllRequiredInformations() throws Exception {

        String existingCity = "Washington";

        mockMvc.perform(get("/weather/" + existingCity))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("$.*.temperature").isNotEmpty())
               .andExpect(jsonPath("$.*.pressure").isNotEmpty())
               .andExpect(jsonPath("$.*.humidity").isNotEmpty())
               .andExpect(jsonPath("$.*.dt").isNotEmpty());

    }

    @Test
    public void receivedWeatherHasApropriateTypes() throws Exception {

        String existingCity = "Washington";

        MvcResult result = mockMvc.perform(get("/weather/" + existingCity))
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andExpect(
                                          MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                  .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        TypeRef<List<Object>> typeRef = new TypeRef<List<Object>>() {
        };

        List<Object> humidities = JsonPath.using(configuration).parse(responseContent).read("$.*.humidity", typeRef);
        List<Object> temperatures = JsonPath.using(configuration).parse(responseContent)
                                            .read("$.*.temperature", typeRef);
        List<Object> pressures = JsonPath.using(configuration).parse(responseContent).read("$.*.pressure", typeRef);
        List<Object> dateTimes = JsonPath.using(configuration).parse(responseContent).read("$.*.dt", typeRef);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(humidities.stream().allMatch(humidity -> humidity instanceof Integer)).isTrue();
        softAssertions.assertThat(temperatures.stream().allMatch(temperature -> temperature instanceof Double))
                      .isTrue();
        softAssertions.assertThat(pressures.stream().allMatch(pressure -> pressure instanceof Double)).isTrue();
        softAssertions.assertThat(dateTimes.stream().allMatch(dt -> dt instanceof String)).isTrue();

        softAssertions.assertAll();

    }

    @Test
    public void receivedWeatherWithAllRequiredContent() throws Exception {

        String existingCity = "Washington";

        MvcResult result = mockMvc.perform(get("/weather/" + existingCity))
                                  .andExpect(MockMvcResultMatchers.status().isOk())
                                  .andExpect(
                                          MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                  .andExpect(jsonPath("$.*.temperature").value(hasSize(numberOfWeatherForecasts)))
                                  .andExpect(jsonPath("$.*.pressure").value(hasSize(numberOfWeatherForecasts)))
                                  .andExpect(jsonPath("$.*.humidity").value(hasSize(numberOfWeatherForecasts)))
                                  .andExpect(jsonPath("$.*.dt").value(hasSize(numberOfWeatherForecasts)))
                                  .andReturn();

    }

}
