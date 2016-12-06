package com.shop.oasaustre.shoppinglist.db.service;

import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.CategoriaDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class CategoriaService {

    private App app;

    public CategoriaService(App app) {
        this.app = app;
    }


    public List<Categoria> loadAll() {

        CategoriaDao categoriaDao = null;
        List<Categoria> lstCategoria = null;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            categoriaDao = daoSession.getCategoriaDao();

            lstCategoria = categoriaDao.loadAll();


            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido recuperar la lista de categorias:"+ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return lstCategoria;
    }

    public void saveCategoria(Categoria categoria){
        CategoriaDao categoriaDao = null;
        WhereCondition.StringCondition condition = null;
        Query query = null;
        List<Categoria> lstCategoria = null;

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + categoria.getNombre() + "'))");

            categoriaDao = daoSession.getCategoriaDao();

            query = categoriaDao.queryBuilder().where(condition).build();

            lstCategoria = query.list();

            if(lstCategoria != null && lstCategoria.size() > 0){

            }else{
                categoriaDao.insert(categoria);
            }



            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido guardar la categoría "+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }


}


