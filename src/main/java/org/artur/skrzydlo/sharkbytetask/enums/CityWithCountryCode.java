package org.artur.skrzydlo.sharkbytetask.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum CityWithCountryCode {

    LONDON("gb"),
    NEW_YORK("us"),
    WASHINGTON("us");

    private String countryCode;

    CityWithCountryCode(String countrCode) {
        this.countryCode = countrCode;
    }

    @Override public String toString() {

        return this.name().substring(0, 1).toUpperCase() + this.name().toLowerCase().substring(1).replace("_", " ");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public static String getListOfCities() {

        return Arrays.stream(values())
                     .map(cityWithCountryCode -> cityWithCountryCode.toString())
                     .collect(Collectors.joining(", "));
    }

    public static CityWithCountryCode getCityByName(String cityName) {

        Optional<CityWithCountryCode> result = Arrays.stream(values()).
                filter(city -> city.toString().toLowerCase().equals(cityName.toLowerCase()))
                                                     .findFirst();

        return result.orElseThrow(() -> new IllegalArgumentException(cityName + " is not valid city name"));
    }
}
