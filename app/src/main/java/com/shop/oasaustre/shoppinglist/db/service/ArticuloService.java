package com.shop.oasaustre.shoppinglist.db.service;

import android.util.Log;

import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.ArticuloDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.dto.ArticuloBarcodeDto;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ArticuloService {

    private App app;

    public ArticuloService(App app) {
        this.app = app;
    }

    public ArticuloBarcodeDto findArticuloByBarcode(String barcode){
        Articulo articulo = null;
        ArticuloBarcodeDto articuloBarcodeDto = null;
        WhereCondition.StringCondition condition = null;
        ArticuloDao articuloDao = null;
        Lista currentList = null;
        ListaCompra listaCompra = null;
        ListaCompraDao listaCompraDao = null;

        DaoSession daoSession = app.getDaoSession();

        articuloBarcodeDto = new ArticuloBarcodeDto();

        try{
            daoSession.getDatabase().beginTransaction();
            articuloDao = daoSession.getArticuloDao();

            condition = new WhereCondition.StringCondition(ArticuloDao.Properties.CodigoBarras.columnName+"='" + barcode + "'");

            Query<Articulo> query = articuloDao.queryBuilder().where(condition).build();

            List<Articulo> lstArticulo = query.list();

            if(lstArticulo != null && lstArticulo.size() > 0){
                articulo = lstArticulo.get(0);
            }

            if(articulo != null){
                currentList = app.getListaActive();

                listaCompra = new ListaCompra();
                listaCompra.setArticulo(articulo);
                listaCompra.setUnidades(1l);
                listaCompra.setLista(currentList);
                listaCompraDao = daoSession.getListaCompraDao();
                listaCompraDao.insert(listaCompra);

            }



            daoSession.getDatabase().setTransactionSuccessful();

            articuloBarcodeDto.setBarcode(barcode);
            articuloBarcodeDto.setListaCompra(listaCompra);

            daoSession.getDatabase().setTransactionSuccessful();

        }catch(Exception ex){
            Log.e(this.getClass().getName(),"No se ha podido localizar el artículo con código de barras: "+barcode+ "- "+ex);
        }finally {
            daoSession.getDatabase().endTransaction();
        }

        return articuloBarcodeDto;
    }
}
