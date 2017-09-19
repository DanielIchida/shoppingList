package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteArticlesDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelArticlesShoppingListTask implements ITask {

    private DeleteArticlesDialog dialog = null;
    private List<String> selectedItem = null;
    private Activity activity = null;
    private final static String SHOPPING_LIST = "lista_compra";

    public DelArticlesShoppingListTask(Activity activity, DeleteArticlesDialog dialog){
        this.dialog = dialog;
        this.activity = activity;
        this.selectedItem = new ArrayList<String>();
    }


    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
        ListaCompraAdapter adapter = (ListaCompraAdapter) recyclerView.getAdapter();
        selectedItem = adapter.getSelectedItem();
    }

    protected Boolean doInBackground(Void... voids) {
        ListaCompraService listaCompraService = null;
        Boolean result = false;

        listaCompraService = new ListaCompraService((App) activity.getApplication());

        //result = listaCompraService.deleteArticlesInListaCompra(selectedItem);

        return result;
    }

    protected void onPostExecute(Boolean result) {
        Articulo articulo = null;
        Tienda tienda = null;
        Categoria categoria = null;

       /* if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaCompraActual);
            ((ListaCompraAdapter)recyclerView.getAdapter()).removeItems(selectedItem);
            com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask task = new LoadArticlesTask(activity);
            task.execute();
        }*/
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.deleteArticleFloat);
        fab.setVisibility(View.INVISIBLE);
        this.dialog.dismiss();

    }

    @Override
    public void run(Object... params) {
        App app = (App) activity.getApplication();
        Map<String, Object> map = new HashMap<>();

        onPreExecute();

        DatabaseReference reference = app.getDatabase().getReference().child(SHOPPING_LIST);

        for(String item:selectedItem){
            reference.child(item).removeValue();

        }


        onPostExecute(true);




    }
}
