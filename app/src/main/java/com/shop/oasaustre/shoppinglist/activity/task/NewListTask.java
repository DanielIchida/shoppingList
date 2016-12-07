package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.activity.dialog.ListDialog;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class NewListTask extends AsyncTask<String, Void,Void> {

    private Activity activity;
    private String errors;
    private ListDialog listDialog;
    private boolean activo;

    public NewListTask(Activity activity,ListDialog listDialog,boolean activo){
        this.activity = listDialog.getActivity();
        this.listDialog = listDialog;
        this.activo = activo;
    }
    @Override
    protected Void doInBackground(String... strings) {

        DaoSession daoSession = null;
        ListaService listaService = null;
        Lista newList = null;

        try {
            listaService = new ListaService((App) activity.getApplication());

            newList = new Lista();
            newList.setActivo(1l);
            newList.setFecha(System.currentTimeMillis());
            newList.setNombre(strings[0]);

            if(activo){
                listaService.saveAndChangeLista(newList);
            }else{
                listaService.saveLista(newList);
            }

        }catch(Exception ex){

            Log.e(this.getClass().getName(),"No se ha podido insertar la nueva lista.");
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void v) {
        //TODO Falta contemplar todos los casos
        if(errors != null){

            Toast.makeText(activity,errors,Toast.LENGTH_LONG);
        }else{
            listDialog.dismiss();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }
    }
}
