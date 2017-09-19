package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteTiendaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;

import com.shop.oasaustre.shoppinglist.db.service.TiendaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelTiendaTask implements ITask{

    private DeleteTiendaDialog dialog = null;
    private TiendaDto tienda = null;
    private Activity activity = null;
    private int delPosition;
    private final static String SHOP = "tienda";

    public DelTiendaTask(Activity activity, DeleteTiendaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;
    }



    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
        TiendaAdapter adapter = (TiendaAdapter) recyclerView.getAdapter();
        tienda = adapter.getLista().get(delPosition);
    }


    protected Boolean doInBackground(Void... voids) {
        TiendaService tiendaService = null;
        Boolean result = false;

        tiendaService = new TiendaService((App) activity.getApplication());

        //result = tiendaService.removeTienda(tienda);

        return result;
    }


    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
            TiendaAdapter adapter = (TiendaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }

    @Override
    public void run(Object... params) {
        App app = (App) activity.getApplication();

        onPreExecute();

        app.getDatabase().getReference().child(SHOP).child(tienda.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
