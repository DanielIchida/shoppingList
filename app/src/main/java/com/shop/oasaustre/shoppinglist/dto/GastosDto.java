package com.shop.oasaustre.shoppinglist.dto;

/**
 * Created by oasaustre on 11/12/16.
 */

public class GastosDto {

    private String month;
    private String year;
    private Double cantidad;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
}
