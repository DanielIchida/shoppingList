package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteListaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelListaTask implements ITask {

    private DeleteListaDialog dialog = null;
    private ListaDto lista = null;
    private Activity activity = null;
    private int delPosition;
    private final static String LIST = "lista";

    public DelListaTask(Activity activity, DeleteListaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }



    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaList);
        ListaAdapter adapter = (ListaAdapter) recyclerView.getAdapter();
        lista = adapter.getLista().get(delPosition);
    }


    protected Boolean doInBackground(Void... voids) {
        ListaService listaService = null;
        Boolean result = false;

        listaService = new ListaService((App) activity.getApplication());

        //result = listaService.removeLista(lista);

        return result;
    }


    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_listaList);
            ListaAdapter adapter = (ListaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }

    @Override
    public void run(Object... params) {

        App app = (App) activity.getApplication();

        onPreExecute();

        app.getDatabase().getReference().child(LIST).child(lista.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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
