package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.activity.InitActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.ListaActivaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaActivaTask implements ITask {

    private Activity activity;

    private static final String SHARE = "compartida";

    public ListaActivaTask(Activity activity){
        this.activity = activity;
    }


    protected void onPostExecute(ListaDto listaDto) {
        Intent intent = null;

        ((App)activity.getApplication()).setListaFBActive(listaDto);
        intent = new Intent(activity, InitActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void run(Object... params) {
        final ListaDto listaDto = (ListaDto) params[0];
        final App app = (App) activity.getApplication();



        app.getDatabase().getReference().child(SHARE).orderByChild(app.getUser().getUid()).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    DataSnapshot child =  dataSnapshot.getChildren().iterator().next();
                    Map<String, Object> shareList = new HashMap<String, Object>();
                    Map<String, Object> shareUser = new HashMap<String, Object>();
                    shareUser.put(app.getUser().getUid(), false);
                    //shareList.put(child.getKey(), shareUser);
                    child.getRef().updateChildren(shareUser);

                    Map<String,Object> listaCompartida = new HashMap<String,Object>();
                    Map<String,Object> usuariosLista = new HashMap<String,Object>();

                    usuariosLista.put(app.getUser().getUid(),true);

                    listaCompartida.put(listaDto.getUid(),usuariosLista);
                    dataSnapshot.getRef().updateChildren(listaCompartida);

                    onPostExecute(listaDto);
                }



                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
