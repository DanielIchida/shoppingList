package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

public class UpdateCategoryTask implements ITask {

    private String errors;
    private Activity activity;
    private final static String CATEGORY = "categoria";

    public UpdateCategoryTask(Activity activity) {
        this.activity = activity;
    }


    protected void onPostExecute() {

        activity.finish();
    }

    @Override
    public void run(Object... params) {
        CategoriaDto categoriaDto = (CategoriaDto) params[0];
        App app = (App) activity.getApplication();
        DatabaseReference reference = app.getDatabase().getReference().child(CATEGORY).child(categoriaDto.getUid());
        reference.setValue(categoriaDto);
        onPostExecute();
    }
}
