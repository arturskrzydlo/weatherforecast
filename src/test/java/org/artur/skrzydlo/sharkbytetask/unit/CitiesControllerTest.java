package org.artur.skrzydlo.sharkbytetask.unit;

import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;
import org.artur.skrzydlo.sharkbytetask.services.WeatherForecastAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CitiesControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherForecastAPI weatherForecastAPI;

    @Test
    public void getCitiesReturnAllCitiesNames() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cities"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.*").value(hasSize(CityWithCountryCode.values().length)))
                    .andReturn();

    }


}
