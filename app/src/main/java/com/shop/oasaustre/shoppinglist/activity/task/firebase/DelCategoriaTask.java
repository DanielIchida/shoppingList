package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelCategoriaTask implements ITask {

    private DeleteCategoriaDialog dialog = null;
    private CategoriaDto categoria = null;
    private Activity activity = null;
    private int delPosition;
    private final static String CATEGORY = "categoria";

    public DelCategoriaTask(Activity activity, DeleteCategoriaDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }



    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
        CategoriaAdapter adapter = (CategoriaAdapter) recyclerView.getAdapter();
        categoria = adapter.getLista().get(delPosition);
    }


    protected Boolean doInBackground(Void... voids) {
        CategoriaService categoriaService = null;
        Boolean result = false;

        categoriaService = new CategoriaService((App) activity.getApplication());

        //result = categoriaService.removeCategoria(categoria);

        return result;
    }


    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_categoriaList);
            CategoriaAdapter adapter = (CategoriaAdapter) recyclerView.getAdapter();
            adapter.getLista().remove(delPosition);
            adapter.notifyDataSetChanged();

        }
        this.dialog.dismiss();

    }

    @Override
    public void run(Object... params) {
        App app = (App) activity.getApplication();

        onPreExecute();

        app.getDatabase().getReference().child(CATEGORY).child(categoria.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
