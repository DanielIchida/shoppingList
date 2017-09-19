package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.LoadArticlesTask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oasaustre on 3/12/16.
 */

public class NewListTask implements ITask {

    private Activity activity;
    private ListDialog listDialog;
    private boolean activo;
    private final static String LIST = "lista";
    private final static String SHARE = "compartida";

    public NewListTask(Activity activity, ListDialog listDialog, boolean activo){
        this.activity = listDialog.getActivity();
        this.listDialog = listDialog;
        this.activo = activo;
    }



    protected void onPostExecute() {
        if(activo){
            ITask task = TaskFactory.getInstance().createLoadArticlesTask(activity,
                    (App) activity.getApplication());
            task.run();

        }else{
/*            RecyclerView rv = (RecyclerView) activity.findViewById(R.id.rv_listaList);
            ListaAdapter listaAdapter = (ListaAdapter) rv.getAdapter();
            listaAdapter.addItem(lista);*/
        }

        listDialog.dismiss();
    }

    @Override
    public void run(final Object... params) {

        final App app = (App) activity.getApplication();

        Query queryList = app.getDatabase().getReference().child(LIST);
        queryList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    ListaDto listaDto = new ListaDto();
                    listaDto.setFecha(System.currentTimeMillis());
                    listaDto.setNombre((String) params[0]);
                    DatabaseReference listRef = dataSnapshot.getRef().push();
                    String key = listRef.getKey();
                    listaDto.setUid(key);
                    listRef.setValue(listaDto);



                    Map<String, Object> listaCompartida = new HashMap<String, Object>();
                    Map<String, Object> usuariosLista = new HashMap<String, Object>();

                    usuariosLista.put(app.getUser().getUid(), activo);

                    listaCompartida.put(key, usuariosLista);

                    DatabaseReference shareRef = app.getDatabase().getReference().child(SHARE);
                    //DatabaseReference shareChildRef = shareRef.push();
                    if (activo) {
                        Map<String,Object> shareUser = new HashMap<String,Object>();
                        shareUser.put(app.getUser().getUid(),true);
                        Query query = shareRef.orderByChild(app.getUser().getUid()).equalTo(true);
                        shareRef.updateChildren(listaCompartida);
                        //shareChildRef.setValue(listaCompartida);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String key = dataSnapshot.getChildren().iterator().next().getKey();
                                    Map<String, Object> shareList = new HashMap<String, Object>();
                                    Map<String, Object> shareUser = new HashMap<String, Object>();
                                    shareUser.put(app.getUser().getUid(), false);
                                    shareList.put(key, shareUser);
                                    dataSnapshot.getRef().updateChildren(shareList);
                                    onPostExecute();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else{
                        shareRef.updateChildren(listaCompartida);
                        onPostExecute();
                    }

                    ((App)activity.getApplication()).setListaFBActive(listaDto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
     }
}
