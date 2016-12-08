package com.shop.oasaustre.shoppinglist.dto;

import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ArticuloBarcodeDto {

    private String barcode;
    private ListaCompra listaCompra;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ListaCompra getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ListaCompra listaCompra) {
        this.listaCompra = listaCompra;
    }
}
