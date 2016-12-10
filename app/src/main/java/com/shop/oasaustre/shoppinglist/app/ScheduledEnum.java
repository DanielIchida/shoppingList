package com.shop.oasaustre.shoppinglist.app;

/**
 * Created by oasaustre on 10/12/16.
 */

public enum ScheduledEnum {
    DIARIA ("0", 1l),
    SEMANAL ("1", 7l),
    QUINCENAL("2", 15l),
    MENSUAL ("3", 30l);


    private String ident;
    private Long value;

    ScheduledEnum(String ident,Long value){
        this.ident = ident;
        this.value = value;
    }


    public String getIdent() {
        return ident;
    }

    public Long getValue() {
        return value;
    }

    public static ScheduledEnum getType(String ident){
        ScheduledEnum scheduledEnum = null;
        if(ident.equals(DIARIA.getIdent())){
            scheduledEnum=DIARIA;

        }else if(ident.equals(SEMANAL.getIdent())){
            scheduledEnum = SEMANAL;

        }else if(ident.equals(QUINCENAL.getIdent())){
            scheduledEnum = QUINCENAL;

        }else if(ident.equals(MENSUAL.getIdent())){
            scheduledEnum = MENSUAL;
        }

        return scheduledEnum;

    }
}
