package com.shop.oasaustre.shoppinglist.db.service;

import android.database.Cursor;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.dao.ListaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.dto.GastosDto;
import com.shop.oasaustre.shoppinglist.dto.ListaActivaDto;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
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
                    orderDesc(ListaDao.Properties.Fecha).
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


    public ListaActivaDto setListaActiva(Lista lista) {

        ListaDao listaDao = null;
        List<Lista> lstLista = null;
        ListaActivaDto result = new ListaActivaDto();

        result.setOk(Boolean.TRUE);

        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            listaDao = daoSession.getListaDao();

            lista.setActivo(AppConstant.LISTA_ACTIVA);
            listaDao.update(lista);

            daoSession.getDatabase().rawQuery("UPDATE LISTA SET ACTIVO = 0 " +
                    "WHERE ID <> ?",new String[]{lista.getId().toString()}).moveToFirst();


            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido establecer la lista activa:"+ex);
            result.setOk(Boolean.FALSE);
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        result.setListaActiva(lista);

        return result;
    }


    public Boolean removeLista(Lista lista) {

        ListaDao listaDao = null;
        ListaCompraDao listaCompraDao = null;
        List<Lista> lstLista = null;
        Boolean result = Boolean.TRUE;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();


            listaDao = daoSession.getListaDao();



            long total = listaDao.queryBuilder().count();

            if(total == 1l){

            }else if(total > 1l){
                daoSession.getDatabase().execSQL("DELETE FROM LISTA_COMPRA WHERE IDLISTA = ?"
                        ,new String[]{lista.getId().toString()});

                daoSession.getDatabase().execSQL("DELETE FROM LISTA WHERE ID = ?",
                        new String[]{lista.getId().toString()});

                //listaDao.delete(lista);

                if(lista.getActivo() == 1l){
                    daoSession.getDatabase().execSQL("UPDATE LISTA SET ACTIVO = 1 WHERE ACTIVO = 0 " +
                            "AND FECHA = (SELECT MAX(FECHA) FROM LISTA L2 WHERE L2.ID <> ? " +
                            "AND L2.ACTIVO = 0)",new String[]{lista.getId().toString()});

                    List<Lista> listaActiva = listaDao.queryBuilder().where
                            (new WhereCondition.StringCondition("ACTIVO = 1")).list();
                    if(listaActiva != null){
                        app.setListaActive(listaActiva.get(0));
                    }

                }


            }


            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido eliminar la lista "+lista.getNombre()+" :"+ex);
            result = Boolean.FALSE;
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return result;
    }


    public void updateLista(Lista lista){
        ListaDao listaDao = null;


        DaoSession daoSession = app.getDaoSession();

        try {
            daoSession.getDatabase().beginTransaction();

            listaDao = daoSession.getListaDao();

            listaDao.update(lista);

            if(lista.getActivo() == 1l){
                app.setListaActive(lista);
            }

            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido actualizar la lista:"+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }
    }


    public List<GastosDto> calculateGastos(){
        GastosDto gastosDto = null;
        DaoSession daoSession = app.getDaoSession();
        List<GastosDto> lstGastos = new ArrayList<GastosDto>();

        try {
            daoSession.getDatabase().beginTransaction();
            String sql = "SELECT SUM(PRECIO*UNIDADES),strftime('%m',L.FECHA/1000,'unixepoch'),strftime('%Y',L.FECHA/1000,'unixepoch') "+
            "FROM LISTA L, LISTA_COMPRA LC "+
            "WHERE L.ID = LC.IDLISTA AND DATE(L.FECHA/1000,'unixepoch') BETWEEN DATE('now','start of month','-6 months') AND DATE('now','-1 days') "+
            "GROUP BY strftime('%m',L.FECHA/1000,'unixepoch'),strftime('%Y',L.FECHA/1000,'unixepoch') " +
                    "ORDER BY strftime('%Y',L.FECHA/1000,'unixepoch') DESC,strftime('%m',L.FECHA/1000,'unixepoch') DESC";



            Cursor cursor = daoSession.getDatabase().rawQuery(sql,null);
            while(cursor.moveToNext()){
                gastosDto = new GastosDto();
                gastosDto.setCantidad(cursor.getDouble(0));
                gastosDto.setMonth(cursor.getString(1));
                gastosDto.setYear(cursor.getString(2));

                lstGastos.add(gastosDto);

            }

            if(cursor != null){
                cursor.close();
            }

            daoSession.getDatabase().setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "No se ha podido calcular los gastos:"+ ex);
        } finally {
            daoSession.getDatabase().endTransaction();
        }

        return lstGastos;
    }


}


