package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelCategoriaTask extends AsyncTask<Void,Void,Boolean> {

    private DeleteCategoriaDialog dialog = null;
    private Categoria categoria = null;
    private Activity activity = null;
    private int delPosition;

    public DelCategoriaTask(Activity activity, DeleteCategoriaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }


    @Override
    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
        CategoriaAdapter adapter = (CategoriaAdapter) recyclerView.getAdapter();
        categoria = adapter.getLista().get(delPosition);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        CategoriaService categoriaService = null;
        Boolean result;

        categoriaService = new CategoriaService((App) activity.getApplication());

        result = categoriaService.removeCategoria(categoria);

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
            CategoriaAdapter adapter = (CategoriaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }
}
