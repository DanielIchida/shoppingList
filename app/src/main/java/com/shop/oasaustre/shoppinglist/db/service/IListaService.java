package com.shop.oasaustre.shoppinglist.db.service;

import android.database.Cursor;
import android.util.Log;

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
 * Created by oasaustre on 10/09/17.
 */

public interface IListaService {

    List<Lista> loadAll();

    void saveLista(Lista lista);

    void saveAndChangeLista(Lista lista);


    ListaActivaDto setListaActiva(Lista lista);

    Boolean removeLista(Lista lista);

    void updateLista(Lista lista);

    List<GastosDto> calculateGastos();

    void getListaActive();
}
