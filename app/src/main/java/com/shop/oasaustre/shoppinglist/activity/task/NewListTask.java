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

    public NewListTask(Activity activity,ListDialog listDialog){
        this.activity = listDialog.getActivity();
        this.listDialog = listDialog;
    }
    @Override
    protected Void doInBackground(String... strings) {

        DaoSession daoSession = null;

        try {

            daoSession = ((App) activity.getApplication()).getDaoSession();
            daoSession.getDatabase().beginTransaction();


            if (strings[0] != null && !strings[0].equals("")) {
                WhereCondition.StringCondition condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + strings[0] + "'))");
                ListaDao listaDao = daoSession.getListaDao();
                Query query = listaDao.queryBuilder().where(condition).build();
                List<Lista> lista = query.list();
                if (lista != null && lista.size() > 0) {
                    errors = "Ya existe una lista registrada con ese nombre.";
                } else {
                    Lista newList = new Lista();
                    newList.setActivo(1l);
                    newList.setFecha(System.currentTimeMillis());
                    newList.setNombre(strings[0]);
                    listaDao.insert(newList);
                }

            }

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){

            Log.e(this.getClass().getName(),"No se ha podido insertar la nueva lista.");
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return null;
    }


    @Override
    protected void onPostExecute(Void v) {
        if(errors != null){

            Toast.makeText(activity,errors,Toast.LENGTH_LONG);
        }else{
            listDialog.dismiss();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }
    }
}
