package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.CategoriaSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ShareListAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.User;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadShareListTask implements ITask {

    private Activity activity = null;
    private ShareListAdapter adapter = null;
    private final static String SHARE = "compartida";

    public LoadShareListTask(Activity activity){
        this.activity = activity;
    }



    protected void onPostExecute(Query reference) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new ShareListAdapter(((App)activity.getApplication()).getUser(),activity,reference);

        RecyclerView rvShareList = (RecyclerView) activity.findViewById(R.id.rv_shareList);
        rvShareList.addItemDecoration(did);
        rvShareList.setAdapter(adapter);
        adapter.activateListener();;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvShareList.setLayoutManager(layoutManager);

    }


    @Override
    public void run(Object... params) {

        DatabaseReference shareRef = null;
        App app = (App) activity.getApplication();

        User user = app.getUser();

        ListaDto listaDto = app.getListaaFBActive();

        shareRef = app.getDatabase().getReference().child(SHARE).child(listaDto.getUid());
        onPostExecute(shareRef);

    }
}
