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
import com.shop.oasaustre.shoppinglist.activity.CategoriaSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;

import com.shop.oasaustre.shoppinglist.adapter.firebase.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class LoadCategoriesTask implements ITask {

    private Activity activity = null;
    private CategoriaAdapter adapter = null;
    private final static String CATEGORY = "categoria";

    public LoadCategoriesTask(Activity activity){
        this.activity = activity;
    }



    protected void onPostExecute(Query reference) {

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);

        adapter = new CategoriaAdapter(activity,reference);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
                CategoriaAdapter adapter = (CategoriaAdapter)((RecyclerView) view.getParent()).getAdapter();
                CategoriaDto categoria = adapter.getLista().get(positionItemSelect);

                editCategoria(categoria);

            }
        });

        RecyclerView rvCategoria = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
        rvCategoria.addItemDecoration(did);
        rvCategoria.setAdapter(adapter);
        adapter.activateListener();;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rvCategoria.setLayoutManager(layoutManager);

    }

    private void editCategoria(CategoriaDto categoria){
        Intent intent = null;
        intent = new Intent(activity, CategoriaSaveActivity.class);
        intent.putExtra(AppConstant.ID_INTENT,categoria.getUid());
        intent.putExtra(AppConstant.TITLE_INTENT,categoria.getNombre());
        activity.startActivity(intent);

    }

    @Override
    public void run(Object... params) {

        Query queryShopping = null;
        App app = (App) activity.getApplication();

        queryShopping = app.getDatabase().getReference().child(CATEGORY).orderByChild("nombre");
        onPostExecute(queryShopping);

    }
}
