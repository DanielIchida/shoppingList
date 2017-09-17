package com.shop.oasaustre.shoppinglist.activity.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.helper.ListaCompraPanelHelper;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.service.ArticuloService;
import com.shop.oasaustre.shoppinglist.dto.ArticuloBarcodeDto;


/**
 * Created by oasaustre on 8/12/16.
 */

public class FindBarcodeTask extends AsyncTask<String,Void,ArticuloBarcodeDto> implements ITask{

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
            View view = activity.findViewById(R.id.content_init);
            Snackbar.make(view, "Lo sentimos no se ha podido encontrar el producto. " +
                    "Por favor introduzcalo manualmente."
                    , Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void run(Object... params) {
        this.execute((String) params[0]);
    }
}

