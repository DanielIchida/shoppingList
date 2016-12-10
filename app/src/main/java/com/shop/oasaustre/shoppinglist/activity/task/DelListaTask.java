package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteListaDialog;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelListaTask extends AsyncTask<Void,Void,Boolean> {

    private DeleteListaDialog dialog = null;
    private Lista lista = null;
    private Activity activity = null;
    private int delPosition;

    public DelListaTask(Activity activity, DeleteListaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }


    @Override
    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaList);
        ListaAdapter adapter = (ListaAdapter) recyclerView.getAdapter();
        lista = adapter.getLista().get(delPosition);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ListaService listaService = null;
        Boolean result;

        listaService = new ListaService((App) activity.getApplication());

        result = listaService.removeLista(lista);

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaList);
            ListaAdapter adapter = (ListaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }
}
