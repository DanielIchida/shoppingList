package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.shop.oasaustre.shoppinglist.adapter.helper.ListaCompraPanelHelper;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ArticleInShoppingListTask  extends AsyncTask<String,Void,ListaCompra> implements ITask{

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
        ListaCompraPanelHelper.refreshPanel(activity,listaCompra);


    }

    @Override
    public void run(Object ... params) {
        this.execute((String) params[0]);
    }
}
