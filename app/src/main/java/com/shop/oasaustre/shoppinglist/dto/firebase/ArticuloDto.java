package com.shop.oasaustre.shoppinglist.dto.firebase;

/**
 * Created by oasaustre on 13/09/17.
 */

public class ArticuloDto {

    private String uid;

    private String nombre;

    private String codigoBarras;

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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }


    @Override
    public String toString(){
        return nombre;
    }
}
