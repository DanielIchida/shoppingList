package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;

/**
 * Created by oasaustre on 3/12/16.
 */

public class UpdateListaTask extends AsyncTask<Lista, Void,Lista> implements ITask{

    private Activity activity;

    public UpdateListaTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected Lista doInBackground(Lista... listas) {
        ListaService listaService = null;

        try {
            listaService = new ListaService((App) activity.getApplication());

            listaService.updateLista(listas[0]);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido actualizar la tienda:"+ex);
        }

        return listas[0];
    }


    @Override
    protected void onPostExecute(Lista lista) {

        activity.finish();
    }

    @Override
    public void run(Object... params) {
        this.execute((Lista) params[0]);
    }
}
