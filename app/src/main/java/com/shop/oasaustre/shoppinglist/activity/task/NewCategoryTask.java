package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

    public class NewCategoryTask extends AsyncTask<String, Void,Categoria> {

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
}
