package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.GastosAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.GastosDto;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class GastosTask extends AsyncTask<Void,Void,List<GastosDto>> {

    private Activity activity = null;
    private GastosAdapter adapter = null;

    public GastosTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected List<GastosDto> doInBackground(Void... voids) {
        ListaService listaService = null;
        List<GastosDto> lstGastos = null;


        try {
            listaService = new ListaService((App) activity.getApplication());

            lstGastos = listaService.calculateGastos();

        }catch(Exception ex){
                Log.e(this.getClass().getName(),"Error al obtener la listas:"+ex);
            }

        return lstGastos;
    }

    @Override
    protected void onPostExecute(List<GastosDto> lstGastos) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new GastosAdapter(activity, lstGastos,(App) activity.getApplication());

        RecyclerView rvLista = (RecyclerView) activity.findViewById(R.id.rv_gastosList);
        rvLista.addItemDecoration(did);
        rvLista.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvLista.setLayoutManager(layoutManager);

    }
}
