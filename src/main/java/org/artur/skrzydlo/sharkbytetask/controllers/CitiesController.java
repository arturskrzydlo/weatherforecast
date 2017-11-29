package org.artur.skrzydlo.sharkbytetask.controllers;

import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CitiesController {

    @GetMapping("")
    public List<String> citiesNames() {

        return Arrays.stream(CityWithCountryCode.values())
                     .map(city -> city.toString()).collect(Collectors.toList());
    }

}
