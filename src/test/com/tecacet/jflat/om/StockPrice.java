package com.tecacet.jflat.om;

import java.util.Date;

public class StockPrice {
    private Date date;
    private double openPrice;
    private double closePrice;
    private int volume;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getAveragePrice() {
        return (openPrice + closePrice) / 2;
    }
}