package com.shop.oasaustre.shoppinglist.activity.task;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.TiendaDialog;
import com.shop.oasaustre.shoppinglist.adapter.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;

/**
 * Created by oasaustre on 3/12/16.
 */

    public class NewTiendaTask extends AsyncTask<String, Void,Tienda> implements ITask{

    private String errors;
    private TiendaDialog dialog;

    public NewTiendaTask(TiendaDialog dialog){
        this.dialog = dialog;
    }
    @Override
    protected Tienda doInBackground(String... params) {
        TiendaService tiendaService = null;
        Tienda tienda = null;

        try {
            tiendaService = new TiendaService((App) dialog.getActivity().getApplication());

            tienda = new Tienda();
            tienda.setNombre(params[0]);
            tiendaService.saveTienda(tienda);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido insertar la nueva tienda:"+ex);
        }

        return tienda;
    }


    @Override
    protected void onPostExecute(Tienda tienda) {

        RecyclerView rv = (RecyclerView) dialog.getActivity().findViewById(R.id.rv_tiendaList);
        TiendaAdapter adapter = null;
        if(tienda != null){
            adapter = (TiendaAdapter) rv.getAdapter();
            adapter.getLista().add(tienda);
            adapter.notifyDataSetChanged();
        }

        dialog.dismiss();
    }

    @Override
    public void run(Object... params) {
        this.execute((String) params[0]);
    }
}
