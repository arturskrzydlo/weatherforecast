package org.artur.skrzydlo.sharkbytetask.services;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherForecastService implements WeatherForecastAPI {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.appid}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiURL;

    @Autowired
    private Configuration jsonPathconfiguration;


    @Override public List<WeatherForecastDTO> get5daysWeatherForecastByCity(String cityName, String countryCode) {

        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(apiURL + "?q="+cityName+","+countryCode+"&appid=" + apiKey, String.class);
        return parseResponseToWeatherForecast(responseEntity.getBody());
    }

    private List<WeatherForecastDTO> parseResponseToWeatherForecast(String response) {

        List<WeatherForecastDTO> weatherForecastDTOS;
        TypeRef<List<WeatherForecastDTO>> typeRef = new TypeRef<List<WeatherForecastDTO>>() {};
        weatherForecastDTOS = JsonPath.using(jsonPathconfiguration).parse(response).read("$.list[*].main",typeRef);

        return weatherForecastDTOS;

    }
}
