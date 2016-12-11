package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadListasTask extends AsyncTask<Void,Void,List<Lista>> {

    private Activity activity = null;
    private ListaAdapter adapter = null;

    public LoadListasTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected List<Lista> doInBackground(Void... voids) {
        ListaService listaService = null;
        List<Lista> lstLista = null;


        try {
            listaService = new ListaService((App) activity.getApplication());

            lstLista = listaService.loadAll();

        }catch(Exception ex){
                Log.e(this.getClass().getName(),"Error al obtener la listas:"+ex);
            }

        return lstLista;
    }

    @Override
    protected void onPostExecute(List<Lista> lstLista) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new ListaAdapter(activity, lstLista);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                ListaAdapter adapter = (ListaAdapter)((RecyclerView) view.getParent()).getAdapter();
                Lista lista = adapter.getLista().get(positionItemSelect);

                ListaActivaTask listaActivaTask = new ListaActivaTask(activity);
                listaActivaTask.execute(lista);

            }
        });

        RecyclerView rvLista = (RecyclerView) activity.findViewById(R.id.rv_listaList);
        rvLista.addItemDecoration(did);
        rvLista.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvLista.setLayoutManager(layoutManager);

    }
}
