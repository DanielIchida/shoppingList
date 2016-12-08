package com.shop.oasaustre.shoppinglist.dto;

import com.shop.oasaustre.shoppinglist.db.entity.Lista;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ListaActivaDto {

    private Boolean ok;
    private Lista listaActiva;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Lista getListaActiva() {
        return listaActiva;
    }

    public void setListaActiva(Lista listaActiva) {
        this.listaActiva = listaActiva;
    }
}
