package org.artur.skrzydlo.sharkbytetask.enums;

public enum CityWithCountryCode {

    LONDON("us"),
    NEWYORK("us"),
    WASHINGTON("us");

    private String countryCode;

    CityWithCountryCode(String countrCode){
        this.countryCode = countrCode;
    }

    @Override public String toString() {
        return this.name().toLowerCase().substring(0,1).toUpperCase();
    }

    public String getCountryCode() {
        return countryCode;
    }
}
