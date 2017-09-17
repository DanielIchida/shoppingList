package com.shop.oasaustre.shoppinglist.adapter.item;

import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;

/**
 * Created by oasaustre on 7/12/16.
 */

public class ContentFBItem extends ListItem{

    private ListaCompraFBDto listaCompra;

    public ContentFBItem(ListaCompraFBDto listaCompra){
        this.listaCompra = listaCompra;
    }

    @Override
    public int getType() {
        return TYPE_CONTENT;
    }

    public ListaCompraFBDto getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ListaCompraFBDto listaCompra) {
        this.listaCompra = listaCompra;
    }
}
