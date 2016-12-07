package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ArticleInShoppingListTask  extends AsyncTask<String,Void,ListaCompra> {

    private Activity activity;

    public ArticleInShoppingListTask(Activity activity){
        this.activity = activity;
    }


    @Override
    protected ListaCompra doInBackground(String... params) {

        ListaCompraService listaCompraService = new ListaCompraService((App) activity.getApplication());


        return listaCompraService.saveListaCompra(params[0]);
    }

    @Override
    protected void onPostExecute(ListaCompra listaCompra) {
        RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        ListaCompraAdapter adapter = (ListaCompraAdapter) rv.getAdapter();
        adapter.addItem(listaCompra);
        TextView txtTotalArticle = (TextView) activity.findViewById(R.id.txtTotalArticle);
        txtTotalArticle.setText(String.valueOf(adapter.getItemCount()));
        //TODO: Falta actualizar el total del precio


    }
}
