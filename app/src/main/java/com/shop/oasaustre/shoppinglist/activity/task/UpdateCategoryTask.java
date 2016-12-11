package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;

/**
 * Created by oasaustre on 3/12/16.
 */

    public class UpdateCategoryTask extends AsyncTask<Categoria, Void,Categoria> {

    private String errors;
    private Activity activity;

    public UpdateCategoryTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected Categoria doInBackground(Categoria... categorias) {
        CategoriaService categoriaService = null;

        try {
            categoriaService = new CategoriaService((App) activity.getApplication());

            categoriaService.updateCategoria(categorias[0]);

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido actualizar la categoría.");
        }

        return categorias[0];
    }


    @Override
    protected void onPostExecute(Categoria categoria) {

        activity.finish();
    }
}
