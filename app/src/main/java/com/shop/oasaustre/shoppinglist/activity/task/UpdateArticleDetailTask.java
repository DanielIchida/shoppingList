package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;

/**
 * Created by oasaustre on 4/12/16.
 */

public class UpdateArticleDetailTask extends AsyncTask<ListaCompra,Void,Void> {

    private Activity activity = null;

    public UpdateArticleDetailTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(ListaCompra... params) {
        ListaCompraService listaCompraService = null;
        ListaCompra listaCompra = null;

        listaCompraService = new ListaCompraService((App) activity.getApplication());


        listaCompraService.updateListaCompra(params[0]);

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Intent intent = new Intent();
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();

    }
}
