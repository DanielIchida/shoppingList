package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.TiendaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class NewTiendaTask implements ITask {

    private TiendaDialog dialog;

    private final static String SHOP = "tienda";

    public NewTiendaTask(TiendaDialog dialog){
        this.dialog = dialog;
    }



    protected void onPostExecute() {

        RecyclerView rv = (RecyclerView) dialog.getActivity().findViewById(R.id.rv_tiendaList);
        /*TiendaAdapter adapter = null;
        if(tienda != null){
            adapter = (TiendaAdapter) rv.getAdapter();
            adapter.getLista().add(tienda);
            adapter.notifyDataSetChanged();
        }*/

        dialog.dismiss();
    }


    public void run(final Object... params) {
        Query queryTienda = null;
        App app = (App) dialog.getActivity().getApplication();

        queryTienda = app.getDatabase().getReference().child(SHOP).orderByChild("nombre").equalTo((String) params[0]);
        queryTienda.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    DatabaseReference child = dataSnapshot.getRef().push();
                    String key = child.getKey();
                    TiendaDto tiendaDto = new TiendaDto();
                    tiendaDto.setNombre((String) params[0]);
                    tiendaDto.setUid(key);
                    child.setValue(tiendaDto);
                    onPostExecute();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
