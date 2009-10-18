package com.tecacet.util.introspection;

public class TightBean {

    private String name;
    private int number;

    public TightBean(String name, int number) {
        super();
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

}
