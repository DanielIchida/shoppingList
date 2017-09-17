package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.service.ListaCompraService;
import com.shop.oasaustre.shoppinglist.dto.ListaCompraDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ArticuloDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class UpdateArticleDetailTask implements ITask{

    private Activity activity = null;
    private final static String SHOPPING_LIST = "lista_compra";
    private final static String ARTICLE ="articulo";

    public UpdateArticleDetailTask(Activity activity){
        this.activity = activity;
    }




    protected void onPostExecute() {
        Intent intent = new Intent();
        activity.setResult(activity.RESULT_OK, intent);
        activity.finish();

    }

    @Override
    public void run(Object... params) {
        ListaCompraFBDto listaCompraFBDto = (ListaCompraFBDto) params[0];
        App app = (App) activity.getApplication();

        ArticuloDto articuloDto = new ArticuloDto();
        articuloDto.setUid((String) listaCompraFBDto.getArticulo().get("idArticle"));
        articuloDto.setNombre((String) listaCompraFBDto.getArticulo().get("articleName"));

        DatabaseReference articleReference = app.getDatabase().getReference().child(ARTICLE).child(articuloDto.getUid());
        articleReference.setValue(articuloDto);

        DatabaseReference reference = app.getDatabase().getReference().child(SHOPPING_LIST).child(listaCompraFBDto.getUid());
        reference.setValue(listaCompraFBDto);
        onPostExecute();
    }
}
