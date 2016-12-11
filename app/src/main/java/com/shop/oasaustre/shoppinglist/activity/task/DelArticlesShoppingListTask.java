package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteArticlesDialog;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelArticlesShoppingListTask extends AsyncTask<Void,Void,Boolean> {

    private DeleteArticlesDialog dialog = null;
    private List<Long> selectedItem = null;
    private Activity activity = null;

    public DelArticlesShoppingListTask(Activity activity,DeleteArticlesDialog dialog){
        this.dialog = dialog;
        this.activity = activity;
        this.selectedItem = new ArrayList<Long>();
    }


    @Override
    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        ListaCompraAdapter adapter = (ListaCompraAdapter) recyclerView.getAdapter();
        selectedItem = adapter.getSelectedItem();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ListaCompraService listaCompraService = null;
        Boolean result;

        listaCompraService = new ListaCompraService((App) activity.getApplication());

        result = listaCompraService.deleteArticlesInListaCompra(selectedItem);

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Articulo articulo = null;
        Tienda tienda = null;
        Categoria categoria = null;

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
            ((ListaCompraAdapter)recyclerView.getAdapter()).removeItems(selectedItem);
            LoadArticlesTask task = new LoadArticlesTask(activity);
            task.execute();
        }
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.deleteArticleFloat);
        fab.setVisibility(View.INVISIBLE);
        this.dialog.dismiss();

    }
}
