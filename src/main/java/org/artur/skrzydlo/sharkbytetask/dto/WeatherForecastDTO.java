package org.artur.skrzydlo.sharkbytetask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.artur.skrzydlo.sharkbytetask.utils.LocalDateTimeSerializer;
import org.artur.skrzydlo.sharkbytetask.utils.UnixTimestampDeserializer;

import java.time.LocalDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherForecastDTO {

    private Double temperature;
    private Double pressure;
    private Integer humidity;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    @JsonProperty("main")
    private void unpackNested(Map<String,String> main) {
        this.temperature = Double.valueOf(main.get("temp"));
        this.pressure =  Double.valueOf(main.get("pressure"));
        this.humidity =  Integer.valueOf(main.get("humidity"));
    }

}

