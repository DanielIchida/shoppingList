package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;

/**
 * Created by oasaustre on 3/12/16.
 */

public class NewListTask extends AsyncTask<String, Void,Lista> implements ITask{

    private Activity activity;
    private ListDialog listDialog;
    private boolean activo;

    public NewListTask(Activity activity,ListDialog listDialog,boolean activo){
        this.activity = listDialog.getActivity();
        this.listDialog = listDialog;
        this.activo = activo;
    }
    @Override
    protected Lista doInBackground(String... strings) {

        DaoSession daoSession = null;
        ListaService listaService = null;
        Lista newList = null;

        listaService = new ListaService((App) activity.getApplication());

        newList = new Lista();
        newList.setActivo(AppConstant.LISTA_ACTIVA);
        newList.setFecha(System.currentTimeMillis());
        newList.setNombre(strings[0]);

        if(activo){
            listaService.saveAndChangeLista(newList);
        }else{
            listaService.saveLista(newList);
        }



        return newList;
    }


    @Override
    protected void onPostExecute(Lista lista) {
        if(activo){
            ((App)activity.getApplication()).setListaActive(lista);
            LoadArticlesTask task = new LoadArticlesTask(activity);
            task.execute();
        }else{
            RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_listaList);
            ListaAdapter listaAdapter = (ListaAdapter) rv.getAdapter();
            listaAdapter.addItem(lista);
        }

        listDialog.dismiss();
    }

    @Override
    public void run(Object... params) {
        this.execute((String) params[0]);
    }
}
