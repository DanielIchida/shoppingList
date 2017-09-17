package com.shop.oasaustre.shoppinglist.db.service.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.CategoriaDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.ICategoriaService;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class CategoriaService implements ICategoriaService{

    private final static String CATEGORY = "categoria";

    private App app;

    public CategoriaService(App app) {
        this.app = app;
    }


    public List<Categoria> loadAll() {

        return null;
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

    public Boolean removeCategoria(Categoria categoria) {

        CategoriaDao categoriaDao = null;
        Boolean result = Boolean.TRUE;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();


            daoSession.getDatabase().execSQL("UPDATE LISTA_COMPRA SET IDCATEGORIA = NULL WHERE IDCATEGORIA = ?  "
                    ,new String[]{categoria.getId().toString()});


            categoriaDao = daoSession.getCategoriaDao();

            categoriaDao.delete(categoria);

            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido eliminar la categoría:"+ex);
            result = Boolean.TRUE;
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return result;
    }


    public void updateCategoria(Categoria categoria){
        CategoriaDao categoriaDao = null;
        WhereCondition.StringCondition condition = null;
        Query query = null;
        List<Categoria> lstCategoria = null;

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            categoriaDao = daoSession.getCategoriaDao();

            categoriaDao.update(categoria);

            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido guardar la categoría "+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }


}


