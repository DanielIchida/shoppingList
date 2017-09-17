package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.Query;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.TiendaSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.TiendaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadTiendasTask implements ITask {

    private Activity activity = null;
    private TiendaAdapter adapter = null;
    private final static String SHOP = "tienda";

    public LoadTiendasTask(Activity activity){
        this.activity = activity;
    }


    protected void onPostExecute(Query reference) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new TiendaAdapter(activity,reference);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                TiendaAdapter adapter = (TiendaAdapter) ((RecyclerView) view.getParent()).getAdapter();
                TiendaDto tienda = adapter.getLista().get(positionItemSelect);

                editTienda(tienda);

            }
        });

        RecyclerView rvTienda = (RecyclerView) activity.findViewById(R.id.rv_tiendaList);
        rvTienda.addItemDecoration(did);
        rvTienda.setAdapter(adapter);
        adapter.activateListener();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvTienda.setLayoutManager(layoutManager);

    }

    private void editTienda(TiendaDto tienda){
        Intent intent = null;
        intent = new Intent(activity, TiendaSaveActivity.class);
        intent.putExtra(AppConstant.ID_INTENT,tienda.getUid());
        intent.putExtra(AppConstant.TITLE_INTENT,tienda.getNombre());
        activity.startActivity(intent);

    }

    @Override
    public void run(Object... params) {
        Query queryShop = null;
        App app = (App) activity.getApplication();

        queryShop = app.getDatabase().getReference().child(SHOP).orderByChild("nombre");
        onPostExecute(queryShop);
    }
}
