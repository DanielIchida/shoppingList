package com.shop.oasaustre.shoppinglist.adapter.helper;

import android.app.Activity;

import com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ListaCompraPanelHelper {

    public static void refreshPanel(Activity activity, ListaCompra listaCompra){
        LoadArticlesTask task = new LoadArticlesTask(activity);
        task.execute();
    }
}
