package org.artur.skrzydlo.sharkbytetask.unit;

import org.artur.skrzydlo.sharkbytetask.dto.WeatherForecastDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class WeatherForecastsJSONTest {

    @Autowired
    private JacksonTester<WeatherForecastDTO> jacksonTester;

    private WeatherForecastDTO weatherForecastDTO;
    private String jsonWeatherForecast;

    @Before
    public void setup() {
        prepateTestWeatherForecast();
        prepareJSONWeatherForecast();
    }

    @Test
    public void testSerializationHasAllFields() throws IOException {

        assertThat(jacksonTester.write(weatherForecastDTO)).hasJsonPathNumberValue("$.temperature");
        assertThat(jacksonTester.write(weatherForecastDTO)).hasJsonPathNumberValue("$.humidity");
        assertThat(jacksonTester.write(weatherForecastDTO)).hasJsonPathNumberValue("$.pressure");
        assertThat(jacksonTester.write(weatherForecastDTO)).hasJsonPathStringValue("$.dt");

    }

    @Test
    public void testDateHasCorrectFormat() throws IOException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = weatherForecastDTO.getDate().format(dateTimeFormatter);
        assertThat(jacksonTester.write(weatherForecastDTO)).extractingJsonPathStringValue("$.dt")
                                                           .isEqualTo(dateFormatted);
    }

    @Test
    public void testDeserializedObjectHasAllFieldsFilled() throws IOException {
        assertThat(jacksonTester.parseObject(jsonWeatherForecast).getTemperature())
                .isEqualTo(weatherForecastDTO.getTemperature());
        assertThat(jacksonTester.parseObject(jsonWeatherForecast).getHumidity())
                .isEqualTo(weatherForecastDTO.getHumidity());
        assertThat(jacksonTester.parseObject(jsonWeatherForecast).getPressure())
                .isEqualTo(weatherForecastDTO.getPressure());
        assertThat(jacksonTester.parseObject(jsonWeatherForecast).getDate().toEpochSecond(ZoneOffset.UTC))
                .isEqualTo(weatherForecastDTO.getDate().toEpochSecond(ZoneOffset.UTC));
    }

    private void prepateTestWeatherForecast() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Integer humidity = 99;
        Double temperature = 36.6;
        Double pressure = 222.3;

        this.weatherForecastDTO = new WeatherForecastDTO();
        weatherForecastDTO.setHumidity(humidity);
        weatherForecastDTO.setTemperature(temperature);
        weatherForecastDTO.setPressure(pressure);
        weatherForecastDTO.setDate(localDateTime);
    }

    private void prepareJSONWeatherForecast() {

        this.jsonWeatherForecast =
                "{\"dt\":" + weatherForecastDTO.getDate().toEpochSecond(ZoneOffset.UTC) + ",\"temperature\":"
                        + weatherForecastDTO.getTemperature() + ",\"pressure\":" + weatherForecastDTO.getPressure()
                        + ",\"humidity\":" + weatherForecastDTO.getHumidity() + "}";
    }
}
