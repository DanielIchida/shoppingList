package com.shop.oasaustre.shoppinglist.db.service;

import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.TiendaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class TiendaService {

    private App app;

    public TiendaService(App app) {
        this.app = app;
    }


    public List<Tienda> loadAll() {

        TiendaDao tiendaDao = null;
        List<Tienda> lstTienda = null;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            tiendaDao = daoSession.getTiendaDao();

            lstTienda = tiendaDao.loadAll();


            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido recuperar la lista de tiendas:"+ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return lstTienda;
    }

    public void saveTienda(Tienda tienda){
        TiendaDao tiendaDao = null;
        WhereCondition.StringCondition condition = null;
        Query query = null;
        List<Tienda> lstTienda = null;

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + tienda.getNombre() + "'))");

            tiendaDao = daoSession.getTiendaDao();

            query = tiendaDao.queryBuilder().where(condition).build();

            lstTienda = query.list();

            if(lstTienda != null && lstTienda.size() > 0){

            }else{
                tiendaDao.insert(tienda);
            }



            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido guardar la tienda "+ ex.toString());
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }


}


