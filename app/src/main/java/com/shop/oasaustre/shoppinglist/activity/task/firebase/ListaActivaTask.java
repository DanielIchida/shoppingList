package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.ListaActivaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaActivaTask implements ITask {

    private Activity activity;

    public ListaActivaTask(Activity activity){
        this.activity = activity;
    }

    protected ListaActivaDto doInBackground(Lista ... listas) {

        ListaService listaService = null;

        listaService = new ListaService((App) activity.getApplication());

        return listaService.setListaActiva(listas[0]);

    }



    protected void onPostExecute(ListaActivaDto result) {

        Intent intent = null;

        if(result.getOk()){
            ((App)activity.getApplication()).setListaActive(result.getListaActiva());
            intent = new Intent(activity, InitActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void run(Object... params) {
        ListaDto listaDto = (ListaDto) params[0];
        App app = (App) activity.getApplication();
    }
}
