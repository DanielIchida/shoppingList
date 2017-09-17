package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.service.ListaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class UpdateListaTask implements ITask {

    private Activity activity;

    private final static String LIST = "lista";

    public UpdateListaTask(Activity activity){
        this.activity = activity;
    }


    protected void onPostExecute() {
        activity.finish();
    }

    @Override
    public void run(Object... params) {
        ListaDto listaDto = (ListaDto) params[0];
        App app = (App) activity.getApplication();
        DatabaseReference reference = app.getDatabase().getReference().child(LIST).child(listaDto.getUid());
        reference.setValue(listaDto);
        onPostExecute();
    }
}
