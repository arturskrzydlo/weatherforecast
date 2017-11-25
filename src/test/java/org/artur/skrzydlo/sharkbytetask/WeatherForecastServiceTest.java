package org.artur.skrzydlo.sharkbytetask;

import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherForecastServiceTest {

    private static final String RESOURCE_URL="http://api.openweathermap.org/data/2.5/forecast?";

    @Value("${weather.api.appid}")
    private static String apiKey;

    @InjectMocks
    private WeatherForecastService weatherForecastService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIfWeatherForecastIsAvailable() {

        String jsonResponse = "        {\n"
                + "            \"list\": [\n"
                + "            {\n"
                + "                    \"dt\": 1511600400,\n"
                + "                    \"main\": {\n"
                + "                        \"temp\": 281.39,\n"
                + "                        \"pressure\": 986.66,\n"
                + "                        \"humidity\": 65\n"
                + "                    },\n"
                + "                    \"dt_txt\": \"2017-11-25 09:00:00\"\n"
                + "            },\n"
                + "            {\n"
                + "                    \"dt\": 1511611200,\n"
                + "                    \"main\": {\n"
                + "                        \"temp\": 281.17,\n"
                + "                        \"pressure\": 987.56,\n"
                + "                        \"humidity\": 66\n"
                + "                    }\n"
                + "                    \"dt_txt\": \"2017-11-25 12:00:00\"\n"
                + "            }\n"
                + "                    ]\n"
                + "        }";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(RESOURCE_URL+"q=London,us&appid="+apiKey,String.class)).thenReturn(responseEntity);

        List<WeatherForecastDTO> weatherForecast = weatherForecastService.get5daysWeatherForecastByCity("London", "us");

        Mockito.verify(restTemplate,Mockito.times(1)).getForEntity(RESOURCE_URL+"q=London,us&appid="+apiKey,String.class);
        Assertions.assertThat(weatherForecast).isNotNull().isNotEmpty();

    }

    @Test(expected = RestClientException.class)
    public void testIfWeatherForecastIsNotAvailable() throws RestClientException{


        Mockito.when(restTemplate.getForEntity(RESOURCE_URL+"q=London,us&appid="+apiKey,String.class)).thenThrow(new RestClientException("test message"));

        List<WeatherForecastDTO> weatherForecast = weatherForecastService.get5daysWeatherForecastByCity("London", "us");

        Mockito.verifyZeroInteractions(restTemplate);
        Assertions.assertThat(weatherForecast).isNull();
    }

}
