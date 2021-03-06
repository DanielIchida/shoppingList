package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.ListaActivaTask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ListaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadListasTask implements ITask {

    private Activity activity = null;
    private ListaAdapter adapter = null;
    private final static String LIST = "lista";
    private final static String SHARE = "compartida";

    public LoadListasTask(Activity activity){
        this.activity = activity;
    }



    protected void onPostExecute(Query reference,List<String> lstKeyUserList) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new ListaAdapter(activity, reference,lstKeyUserList);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                ListaAdapter adapter = (ListaAdapter)((RecyclerView) view.getParent()).getAdapter();
                ListaDto lista = adapter.getLista().get(positionItemSelect);

                ITask task = TaskFactory.getInstance().createListaActivaTask(activity, (App) activity.getApplication());
                task.run(lista);
            }
        });

        RecyclerView rvLista = (RecyclerView) activity.findViewById(R.id.rv_listaList);
        rvLista.addItemDecoration(did);
        rvLista.setAdapter(adapter);
        adapter.activateListener();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvLista.setLayoutManager(layoutManager);

    }

    @Override
    public void run(Object... params) {

        Query queryShareUser = null;
        final App app = (App) activity.getApplication();

        queryShareUser = app.getDatabase().getReference().child(SHARE).orderByChild(app.getUser().getUid()).startAt(false).endAt(true);
        queryShareUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> lstKeyUserList = new ArrayList<String>();
                Query queryList = null;
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    lstKeyUserList.add(child.getKey());
                }

                queryList = app.getDatabase().getReference().child(LIST).orderByChild("fecha");
                onPostExecute(queryList,lstKeyUserList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
