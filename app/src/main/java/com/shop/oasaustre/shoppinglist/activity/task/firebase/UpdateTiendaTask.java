package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;
import com.shop.oasaustre.shoppinglist.db.service.TiendaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class UpdateTiendaTask implements ITask {

    private Activity activity;
    private final static String SHOP = "tienda";

    public UpdateTiendaTask(Activity activity){
        this.activity = activity;
    }


    protected void onPostExecute() {

        activity.finish();
    }

    @Override
    public void run(Object... params) {
        TiendaDto tiendaDto = (TiendaDto) params[0];
        App app = (App) activity.getApplication();
        DatabaseReference reference = app.getDatabase().getReference().child(SHOP).child(tiendaDto.getUid());
        reference.setValue(tiendaDto);
        onPostExecute();
    }
}
