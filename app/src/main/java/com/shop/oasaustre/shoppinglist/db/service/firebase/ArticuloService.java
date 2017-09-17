package com.shop.oasaustre.shoppinglist.db.service.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.ArticuloDao;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.dao.ListaCompraDao;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.IArticuloService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloBarcodeDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ArticuloDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;


import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oasaustre on 8/12/16.
 */

public class ArticuloService implements IArticuloService{

    private static final String ARTICLE = "articulo";
    private static final Long INITIAL_UNIT = 1l;
    private static final Double INITIAL_PRICE = 0d;
    private static final String SHOPPING_LIST = "lista_compra";
    private boolean exists = false;
    private App app;


    public ArticuloService(App app) {
        this.app = app;
    }

    public ArticuloBarcodeDto findArticuloByBarcode(String barcode){

        Query queryBarcode = app.getDatabase().getReference().child(ARTICLE).orderByChild("codigoBarras").equalTo(barcode);
        final DatabaseReference listaCompraReference = app.getDatabase().getReference().child(SHOPPING_LIST);
        exists = false;
        queryBarcode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    ArticuloDto articuloDto = dataSnapshot.getValue(ArticuloDto.class);
                    createListaCompra(listaCompraReference,articuloDto);
                    exists = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return null;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    private void createListaCompra(final DatabaseReference listaCompraReference, ArticuloDto articuloDto){
        ListaCompraFBDto listaCompraFBDto = new ListaCompraFBDto();
        Map<String,Object> categoria = new HashMap<String,Object>();
        Map<String,Object> lista = new HashMap<String,Object>();
        Map<String,Object> articulo = new HashMap<String,Object>();
        DatabaseReference reference = null;

        reference = listaCompraReference.push();

        listaCompraFBDto.setPrecio(INITIAL_PRICE);
        listaCompraFBDto.setUnidades(INITIAL_UNIT);
        listaCompraFBDto.setUid(reference.getKey());


        articulo.put("idArticle",articuloDto.getUid());
        articulo.put("articleName",articuloDto.getNombre());

        listaCompraFBDto.setArticulo(articulo);

        ListaDto listaDto = app.getListaaFBActive();

        lista.put("idLista",listaDto.getUid());
        lista.put("listaName",listaDto.getNombre());

        listaCompraFBDto.setLista(lista);

        reference.setValue(listaCompraFBDto);

    }
}
