package com.shop.oasaustre.shoppinglist.app;

/**
 * Created by oasaustre on 10/12/16.
 */

public class Settings {

    private String currency;
    private Boolean activeNotification;
    private String scheduledNotificacion;

    public Settings(){

    }

    public Settings(String currency,Boolean activeNotification,String scheduledNotificacion){
        this.currency = currency;
        this.activeNotification = activeNotification;
        this.scheduledNotificacion = scheduledNotificacion;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getActiveNotification() {
        return activeNotification;
    }

    public void setActiveNotification(Boolean activeNotification) {
        this.activeNotification = activeNotification;
    }

    public String getScheduledNotificacion() {
        return scheduledNotificacion;
    }

    public void setScheduledNotificacion(String scheduledNotificacion) {
        this.scheduledNotificacion = scheduledNotificacion;
    }
}
