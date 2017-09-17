package com.shop.oasaustre.shoppinglist.dto.firebase;

/**
 * Created by oasaustre on 17/09/17.
 */

public class CategoriaDto {

    private String uid;

    private String nombre;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return nombre;
    }
}
