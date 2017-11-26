package org.artur.skrzydlo.sharkbytetask.config;

import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

/**
 * Created by artur.skrzydlo on 2017-05-13.
 */
@Configuration
@EnableAutoConfiguration
public class TestConfiguration {



    @Bean
    public com.jayway.jsonpath.Configuration configuration(){

        return com.jayway.jsonpath.Configuration.builder()
                                                .jsonProvider(new JacksonJsonProvider())
                                                .mappingProvider(new JacksonMappingProvider())
                                                .options(EnumSet.noneOf(Option.class))
                                                .build();
    }


}
