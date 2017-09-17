package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.firebase.ArticuloService;


/**
 * Created by oasaustre on 8/12/16.
 */

public class FindBarcodeTask implements ITask {

    private Activity activity;

    public FindBarcodeTask(Activity activity){
        this.activity = activity;
    }

    @Override
    public void run(Object... params) {
        ArticuloService articuloService = null;

        articuloService = new ArticuloService((App) activity.getApplication());

        articuloService.findArticuloByBarcode((String) params[0]);

        if(!articuloService.isExists()){
            View view = activity.findViewById(R.id.content_init);
            Snackbar.make(view, "Lo sentimos no se ha podido encontrar el producto. " +
                            "Por favor introduzcalo manualmente."
                    , Snackbar.LENGTH_SHORT).show();
        }
    }
}

