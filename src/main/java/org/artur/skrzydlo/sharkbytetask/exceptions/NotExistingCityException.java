package org.artur.skrzydlo.sharkbytetask.exceptions;

import org.artur.skrzydlo.sharkbytetask.enums.CityWithCountryCode;

import java.text.MessageFormat;

public class NotExistingCityException extends Exception{

    private String cityChecked;
    private static final String MESSAGE="Can't get weather forecast for {0}. Choose one from these cities : "+ CityWithCountryCode.values();

    public NotExistingCityException(String cityChecked){
        this.cityChecked=cityChecked;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(MESSAGE,cityChecked);
    }

    public String getCityChecked() {
        return cityChecked;
    }
}
