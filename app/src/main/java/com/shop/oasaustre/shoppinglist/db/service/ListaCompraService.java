package com.shop.oasaustre.shoppinglist.db.service;

import android.database.Cursor;
import android.util.Log;

import com.shop.oasaustre.shoppinglist.adapter.ListaCompraAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.dao.ArticuloDao;
import com.shop.oasaustre.shoppinglist.db.dao.CategoriaDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.dao.TiendaDao;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.dto.ArticuloDetalleDto;
import com.shop.oasaustre.shoppinglist.dto.ListaCompraDto;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaCompraService {

    private App app;
    public ListaCompraService(App app){
        this.app = app;
    }


    public ListaCompra saveListaCompra(String nombreArticulo){
        Articulo articulo       = null;
        ArticuloDao articuloDao = null;
        WhereCondition.StringCondition condition = null;
        Lista currentList = null;
        ListaCompra listaCompra = null;
        ListaCompraDao listaCompraDao = null;

        DaoSession daoSession = app.getDaoSession();

        try{
        daoSession.getDatabase().beginTransaction();


        if (nombreArticulo != null && !nombreArticulo.equals("")) {

            condition = new WhereCondition.StringCondition("upper(nombre) = trim(upper('" + nombreArticulo + "'))");
            articuloDao = daoSession.getArticuloDao();
            Query query = articuloDao.queryBuilder().where(condition).build();
            List<Articulo> listaArticulo = query.list();
            if (listaArticulo != null && listaArticulo.size() > 0) {
                articulo = listaArticulo.get(0);
            } else {
                articulo = new Articulo();
                articulo.setNombre(nombreArticulo);
                articuloDao.insert(articulo);

            }

            currentList = app.getListaActive();

            listaCompra = new ListaCompra();
            listaCompra.setArticulo(articulo);
            listaCompra.setLista(currentList);
            listaCompraDao = daoSession.getListaCompraDao();
            listaCompraDao.insert(listaCompra);

        }

        daoSession.getDatabase().setTransactionSuccessful();

    }catch(Exception ex){
        Log.e(this.getClass().getName(),"No se ha podido insertar un artículo en la lista de la compra");
    }finally {
        daoSession.getDatabase().endTransaction();
    }

        return listaCompra;
    }

    public ListaCompra findById(Long id){
        ListaCompra listaCompra = null;

        DaoSession daoSession = app.getDaoSession();

        try{
            daoSession.getDatabase().beginTransaction();

            if (id != null) {
                listaCompra = daoSession.getListaCompraDao().load(id);

            }

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido recuperar la lista de la compra con id "+id);
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return listaCompra;
    }

    public void updateListaCompra(ListaCompra listaCompra){

        ListaCompra listaCompraBD = null;
        DaoSession daoSession = app.getDaoSession();

        try{
            daoSession.getDatabase().beginTransaction();

            if (listaCompra != null) {
                //listaCompraBD = daoSession.getListaCompraDao().load(listaCompra.getId());
                daoSession.getListaCompraDao().update(listaCompra);

            }

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido recuperar la lista de la compra con id "+listaCompra.getId());
        }finally {
            daoSession.getDatabase().endTransaction();
        }
    }

    public ListaCompraDto loadListaCompraActive(Long idLista){
        Double result = null;
        Cursor cursor = null;
        List<Articulo> allArticles = null;
        Long totalUnidades = 0l;
        ListaCompraDto listaCompraDto = new ListaCompraDto();

        DaoSession daoSession = app.getDaoSession();

        try{

            daoSession.getDatabase().beginTransaction();

            allArticles = daoSession.getArticuloDao().loadAll();

            List<ListaCompra> lstCompra = daoSession.getListaCompraDao()._queryLista_ListaCompra(idLista);



            cursor = daoSession.getDatabase().rawQuery("SELECT SUM(PRECIO*UNIDADES) " +
                    "FROM LISTA_COMPRA WHERE IDLISTA = ?;",new String[]{idLista.toString()});
            if(cursor.moveToFirst()){
                result = cursor.getDouble(0);
            }

            listaCompraDto.setAllArticles(allArticles);
            listaCompraDto.setLstCompra(lstCompra);
            listaCompraDto.setSumTotalListaCompra(result);
            if(lstCompra != null){
                totalUnidades = new Long(lstCompra.size());
            }
            listaCompraDto.setTotalUnidades(totalUnidades);

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido cargar la lista de la compra activa "+idLista);
        }finally {
            if(cursor != null){
                cursor.close();
            }
            daoSession.getDatabase().endTransaction();
        }

        return listaCompraDto;
    }

    public ArticuloDetalleDto getArticleDetail(Long idListaCompra){

        ArticuloDetalleDto articuloDetalleDto = null;
        ListaCompraDao listaCompraDao = null;
        CategoriaDao categoriaDao = null;
        TiendaDao tiendaDao = null;
        ListaCompra listaCompra = null;
        List<Categoria> allCategoria = null;
        List<Tienda> allTienda = null;
        List<Categoria> lstCategoria = null;
        List<Tienda> lstTienda = null;
        Categoria categoriaDefault = null;
        Tienda tiendaDefault = null;


        DaoSession daoSession = app.getDaoSession();

        articuloDetalleDto = new ArticuloDetalleDto();

        categoriaDefault = new Categoria();
        categoriaDefault.setId(AppConstant.ID_DEFAULT);
        categoriaDefault.setNombre(AppConstant.TITLE_CATEGORY_DEFAULT);

        tiendaDefault = new Tienda();
        tiendaDefault.setId(AppConstant.ID_DEFAULT);
        tiendaDefault.setNombre(AppConstant.TITLE_TIENDA_DEFAULT);


        allCategoria = new ArrayList<Categoria>();
        allCategoria.add(categoriaDefault);

        allTienda = new ArrayList<Tienda>();
        allTienda.add(tiendaDefault);

        try{
            daoSession.getDatabase().beginTransaction();

            if (idListaCompra != null) {
                listaCompraDao = daoSession.getListaCompraDao();
                categoriaDao = daoSession.getCategoriaDao();
                tiendaDao = daoSession.getTiendaDao();

                listaCompra = listaCompraDao.load(idListaCompra);

                if(listaCompra.getCategoria() != null){

                }

                lstCategoria = categoriaDao.loadAll();
                lstTienda = tiendaDao.loadAll();

                if(lstCategoria != null && lstCategoria.size() > 0){
                    allCategoria.addAll(lstCategoria);
                }

                if(lstTienda != null && lstTienda.size() > 0){
                    allTienda.addAll(lstTienda);
                }

            }

            articuloDetalleDto.setListaCompra(listaCompra);
            articuloDetalleDto.setAllCategorias(allCategoria);
            articuloDetalleDto.setAllTiendas(allTienda);

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido recuperar la lista de la compra con id "+listaCompra.getId());
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return articuloDetalleDto;

    }


    public boolean deleteArticlesInListaCompra(List<Long> selectedItems){
        boolean result = true;
        DaoSession daoSession = app.getDaoSession();

        try{
            daoSession.getDatabase().beginTransaction();

            daoSession.getListaCompraDao().deleteByKeyInTx(selectedItems);

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido borrar los artículos de la lista de la compra "+selectedItems+ "- "+ex);
            result = false;
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return result;
    }

    /*private void copyListaCompra(ListaCompra source, ListaCompra target){

        target.setPrecio(source.getPrecio());
        target.setUnidades(source.getUnidades());
        target.
    }*/
}


