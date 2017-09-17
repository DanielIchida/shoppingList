package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;

import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.helper.ListaCompraPanelHelper;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.firebase.ListaCompraService;


/**
 * Created by oasaustre on 3/12/16.
 */

public class ArticleInShoppingListTask implements ITask {

    private Activity activity;

    public ArticleInShoppingListTask(Activity activity){
        this.activity = activity;
    }

    @Override
    public void run(Object ... params) {
        ListaCompraService listaCompraService = null;

        listaCompraService = new ListaCompraService((App) activity.getApplication());
        listaCompraService.saveListaCompra((String) params[0]);

        //ListaCompraPanelHelper.refreshPanel(activity,listaCompra);
    }
}
