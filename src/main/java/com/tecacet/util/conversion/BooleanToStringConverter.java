package com.tecacet.util.conversion;

public class BooleanToStringConverter implements ToStringConverter<Boolean> {

    private String yesString = "Y";
    private String noString = "N";

    public BooleanToStringConverter() {

    }

    public BooleanToStringConverter(String yesString, String noString) {
        super();
        this.yesString = yesString;
        this.noString = noString;
    }

    @Override
    public String convertToString(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? yesString : noString;
    }

    public String getYesString() {
        return yesString;
    }

    public void setYesString(String yesString) {
        this.yesString = yesString;
    }

    public String getNoString() {
        return noString;
    }

    public void setNoString(String noString) {
        this.noString = noString;
    }

}
