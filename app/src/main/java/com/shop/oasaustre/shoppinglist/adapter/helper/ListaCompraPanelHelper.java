package com.shop.oasaustre.shoppinglist.adapter.helper;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ListaCompraPanelHelper {

    public static void refreshPanel(Activity activity, ListaCompra listaCompra){
        RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        ListaCompraAdapter adapter = (ListaCompraAdapter) rv.getAdapter();
        adapter.addItem(listaCompra);
        TextView txtTotalArticle = (TextView) activity.findViewById(R.id.txtTotalArticle);
        txtTotalArticle.setText(String.valueOf(adapter.getItemCount()));
        //TODO: Falta actualizar el total del precio
    }
}
