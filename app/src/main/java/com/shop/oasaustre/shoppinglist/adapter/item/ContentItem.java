package com.shop.oasaustre.shoppinglist.adapter.item;

import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

/**
 * Created by oasaustre on 7/12/16.
 */

public class ContentItem extends ListItem{

    private ListaCompra listaCompra;

    public ContentItem(ListaCompra listaCompra){
        this.listaCompra = listaCompra;
    }

    @Override
    public int getType() {
        return TYPE_CONTENT;
    }

    public ListaCompra getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ListaCompra listaCompra) {
        this.listaCompra = listaCompra;
    }
}
