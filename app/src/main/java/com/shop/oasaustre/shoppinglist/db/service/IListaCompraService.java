package com.shop.oasaustre.shoppinglist.db.service;

import android.database.Cursor;
import android.util.Log;

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
import java.util.List;

/**
 * Created by oasaustre on 10/09/17.
 */

public interface IListaCompraService {

    ListaCompra saveListaCompra(String nombreArticulo);
    ListaCompra findById(Long id);
    void updateListaCompra(ListaCompra listaCompra);
    ListaCompraDto loadListaCompraActive(Long idLista);
    ArticuloDetalleDto getArticleDetail(Long idListaCompra);
    boolean deleteArticlesInListaCompra(List<Long> selectedItems);
    ListaCompra saveListaCompra(Articulo articulo);
}
