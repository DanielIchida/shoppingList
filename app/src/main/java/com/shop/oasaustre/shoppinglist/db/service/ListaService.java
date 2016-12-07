package com.shop.oasaustre.shoppinglist.db.service;

import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;
import com.shop.oasaustre.shoppinglist.db.dao.TiendaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaService {

    private App app;

    public ListaService(App app) {
        this.app = app;
    }


    public List<Lista> loadAll() {

        ListaDao listaDao = null;
        List<Lista> lstLista = null;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            listaDao = daoSession.getListaDao();

            lstLista = listaDao.queryBuilder().
                    orderAsc(ListaDao.Properties.Nombre).
                    list();


            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido recuperar la lista:"+ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return lstLista;
    }

    public void saveLista(Lista lista){
        ListaDao listaDao = null;
        WhereCondition.StringCondition condition = null;
        Query query = null;
        List<Lista> lstLista = null;

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + lista.getNombre() + "'))");

            listaDao = daoSession.getListaDao();

            query = listaDao.queryBuilder().where(condition).build();

            lstLista = query.list();

            if(lstLista != null && lstLista.size() > 0){

            }else{
                listaDao.insert(lista);
            }



            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido guardar la lista:"+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }

    public void saveAndChangeLista(Lista lista) {

        ListaDao listaDao = null;
        WhereCondition.StringCondition condition = null;
        Query query = null;
        List<Lista> lstLista = null;

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + lista.getNombre() + "'))");

            listaDao = daoSession.getListaDao();

            query = listaDao.queryBuilder().where(condition).build();

            lstLista = query.list();

            if(lstLista != null && lstLista.size() > 0){

            }else{
                listaDao.insert(lista);
                daoSession.getDatabase().rawQuery("UPDATE LISTA SET ACTIVO = 0 " +
                        "WHERE ID <> ?",new String[]{lista.getId().toString()});
            }



            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido guardar la lista:"+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }


}


