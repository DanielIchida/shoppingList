package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.ListaActivaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaActivaTask extends AsyncTask<Lista, Void,ListaActivaDto> {

    private Activity activity;

    public ListaActivaTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected ListaActivaDto doInBackground(Lista ... listas) {

        ListaService listaService = null;

        listaService = new ListaService((App) activity.getApplication());

        return listaService.setListaActiva(listas[0]);

    }


    @Override
    protected void onPostExecute(ListaActivaDto result) {

        Intent intent = null;

        if(result.getOk()){
            ((App)activity.getApplication()).setListaActive(result.getListaActiva());
            intent = new Intent(activity, InitActivity.class);
            activity.startActivity(intent);
        }
    }
}
