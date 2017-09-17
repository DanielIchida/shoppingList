package com.shop.oasaustre.shoppinglist.activity.task;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;

/**
 * Created by oasaustre on 3/12/16.
 */

    public class NewCategoryTask extends AsyncTask<String, Void,Categoria> implements ITask {

    private String errors;
    private CategoriaDialog dialog;

    public NewCategoryTask(CategoriaDialog dialog){
        this.dialog = dialog;
    }
    @Override
    protected Categoria doInBackground(String... params) {
        CategoriaService categoriaService = null;
        Categoria categoria = null;

        try {
            categoriaService = new CategoriaService((App) dialog.getActivity().getApplication());

            categoria = new Categoria();
            categoria.setNombre(params[0]);
            categoriaService.saveCategoria(categoria);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido insertar la nueva categoría.");
        }

        return categoria;
    }


    @Override
    protected void onPostExecute(Categoria categoria) {

        RecyclerView rv = (RecyclerView) dialog.getActivity().findViewById(R.id.rv_categoriaList);

        if(categoria != null){
            ((CategoriaAdapter) rv.getAdapter()).getLista().add(categoria);
            ((CategoriaAdapter) rv.getAdapter()).notifyDataSetChanged();
        }

        dialog.dismiss();
    }

    @Override
    public void run(Object... params) {
        this.execute((String) params[0]);
    }
}
