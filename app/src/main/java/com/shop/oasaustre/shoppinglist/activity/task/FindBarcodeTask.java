package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;

import com.shop.oasaustre.shoppinglist.adapter.helper.ListaCompraPanelHelper;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.service.ArticuloService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloBarcodeDto;


/**
 * Created by oasaustre on 8/12/16.
 */

public class FindBarcodeTask extends AsyncTask<String,Void,ArticuloBarcodeDto> {

    private Activity activity;

    public FindBarcodeTask(Activity activity){
        this.activity = activity;
    }
    @Override
    protected ArticuloBarcodeDto doInBackground(String... strings) {
        ArticuloService articuloService = null;
        Articulo articulo = null;
        ArticuloBarcodeDto articuloBarcodeDto = null;

        articuloService = new ArticuloService((App) activity.getApplication());

        articuloBarcodeDto = articuloService.findArticuloByBarcode(strings[0]);

        return articuloBarcodeDto;
    }

    @Override
    protected void onPostExecute(ArticuloBarcodeDto articuloBarcodeDto) {

        if(articuloBarcodeDto.getListaCompra() != null){
            ListaCompraPanelHelper.refreshPanel(activity,articuloBarcodeDto.getListaCompra());
        }else{

        }

    }
}

