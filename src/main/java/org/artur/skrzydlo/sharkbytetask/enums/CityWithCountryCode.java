package org.artur.skrzydlo.sharkbytetask.enums;

public enum CityWithCountryCode {

    LONDON("us"),
    NEW_YORK("us"),
    WASHINGTON("us");

    private String countryCode;

    CityWithCountryCode(String countrCode){
        this.countryCode = countrCode;
    }

    @Override public String toString() {


        return this.name().substring(0,1).toUpperCase()+this.name().toLowerCase().substring(1).replace("_"," ");
    }

    public String getCountryCode() {
        return countryCode;
    }
}
