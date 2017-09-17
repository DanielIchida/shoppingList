package com.shop.oasaustre.shoppinglist.db.service.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.dao.DaoSession;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.IListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloDetalleDto;
import com.shop.oasaustre.shoppinglist.dto.ListaCompraDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ArticuloDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oasaustre on 3/12/16.
 */

public class ListaCompraService implements IListaCompraService{

    private App app;
    private static final String ARTICLE = "articulo";
    private static final String SHOPPING_LIST = "lista_compra";
    private static final Long INITIAL_UNIT = 1l;
    private static final Double INITIAL_PRICE = 0d;

    public ListaCompraService(App app){
        this.app = app;
    }


    public ListaCompra saveListaCompra(final String nombreArticulo){

        DaoSession daoSession = app.getDaoSession();

        Query queryArticle = app.getDatabase().getReference().child(ARTICLE).orderByChild("nombre").equalTo(nombreArticulo);
        final DatabaseReference articleReference = app.getDatabase().getReference().child(ARTICLE);
        final DatabaseReference listaCompraReference = app.getDatabase().getReference().child(SHOPPING_LIST);

        queryArticle.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArticuloDto articuloDto = null;
                if(!dataSnapshot.exists()){
                    articuloDto = new ArticuloDto();
                    articuloDto.setNombre(nombreArticulo);
                    DatabaseReference childReference = articleReference.push();
                    articuloDto.setUid(childReference.getKey());
                    childReference.setValue(articuloDto);

                }else{
                    DataSnapshot childSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = childSnapshot.getKey();
                    articuloDto = childSnapshot.getValue(ArticuloDto.class);
                }

                createListaCompra(listaCompraReference,articuloDto);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return null;
    }


    private void createListaCompra(final DatabaseReference listaCompraReference,ArticuloDto articuloDto){
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

    public ListaCompra findById(Long id){
        ListaCompra listaCompra = null;


        return listaCompra;
    }

    public void updateListaCompra(ListaCompra listaCompra){

    }

    public ListaCompraDto loadListaCompraActive(Long idLista){
        return null;
    }

    public ArticuloDetalleDto getArticleDetail(Long idListaCompra){

        ArticuloDetalleDto articuloDetalleDto = null;

        return articuloDetalleDto;

    }


    public boolean deleteArticlesInListaCompra(List<Long> selectedItems){
        boolean result = true;

        return result;
    }


    public ListaCompra saveListaCompra(Articulo articulo){
        ListaCompra listaCompra = null;

        return listaCompra;
    }



    /*private void copyListaCompra(ListaCompra source, ListaCompra target){

        target.setPrecio(source.getPrecio());
        target.setUnidades(source.getUnidades());
        target.
    }*/
}


