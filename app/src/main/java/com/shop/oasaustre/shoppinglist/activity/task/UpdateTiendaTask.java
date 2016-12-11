package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;

/**
 * Created by oasaustre on 3/12/16.
 */

public class UpdateTiendaTask extends AsyncTask<Tienda, Void,Tienda> {

    private Activity activity;

    public UpdateTiendaTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Tienda doInBackground(Tienda... tiendas) {
        TiendaService tiendaService = null;

        try {
            tiendaService = new TiendaService((App) activity.getApplication());

            tiendaService.updateTienda(tiendas[0]);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido actualizar la tienda:"+ex);
        }

        return tiendas[0];
    }


    @Override
    protected void onPostExecute(Tienda tienda) {

        activity.finish();
    }
}
