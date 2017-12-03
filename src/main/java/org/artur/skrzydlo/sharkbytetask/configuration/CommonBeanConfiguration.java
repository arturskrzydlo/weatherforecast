package org.artur.skrzydlo.sharkbytetask.configuration;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;

@Configuration
public class CommonBeanConfiguration {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public com.jayway.jsonpath.Configuration configurationA() {

        return com.jayway.jsonpath.Configuration.builder()
                                                .jsonProvider(new JacksonJsonProvider())
                                                .mappingProvider(new JacksonMappingProvider())
                                                .options(EnumSet.noneOf(Option.class))
                                                .build();
    }

}
