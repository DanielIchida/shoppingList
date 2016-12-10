package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteTiendaDialog;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelTiendaTask extends AsyncTask<Void,Void,Boolean> {

    private DeleteTiendaDialog dialog = null;
    private Tienda tienda = null;
    private Activity activity = null;
    private int delPosition;

    public DelTiendaTask(Activity activity, DeleteTiendaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }


    @Override
    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
        TiendaAdapter adapter = (TiendaAdapter) recyclerView.getAdapter();
        tienda = adapter.getLista().get(delPosition);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        TiendaService tiendaService = null;
        Boolean result;

        tiendaService = new TiendaService((App) activity.getApplication());

        result = tiendaService.removeTienda(tienda);

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
            TiendaAdapter adapter = (TiendaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }
}
