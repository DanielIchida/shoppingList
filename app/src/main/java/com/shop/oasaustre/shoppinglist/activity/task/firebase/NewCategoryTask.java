package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.CategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;

/**
 * Created by oasaustre on 3/12/16.
 */

    public class NewCategoryTask implements ITask {

    private String errors;
    private CategoriaDialog dialog;
    private final static String CATEGORY = "categoria";

    public NewCategoryTask(CategoriaDialog dialog){
        this.dialog = dialog;
    }


    protected void onPostExecute() {

        RecyclerView rv = (RecyclerView) dialog.getActivity().findViewById(R.id.rv_categoriaList);
        //((CategoriaAdapter) rv.getAdapter()).notifyDataSetChanged();

       /* if(categoria != null){
            ((CategoriaAdapter) rv.getAdapter()).getLista().add(categoria);
            ((CategoriaAdapter) rv.getAdapter()).notifyDataSetChanged();
        }*/

        dialog.dismiss();
    }

    @Override
    public void run(final Object... params) {
        Query queryCategory = null;
        App app = (App) dialog.getActivity().getApplication();

        queryCategory = app.getDatabase().getReference().child(CATEGORY).orderByChild("nombre").equalTo((String) params[0]);
        queryCategory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    DatabaseReference child = dataSnapshot.getRef().push();
                    String key = child.getKey();
                    CategoriaDto categoriaDto = new CategoriaDto();
                    categoriaDto.setNombre((String) params[0]);
                    categoriaDto.setUid(key);
                    child.setValue(categoriaDto);
                    onPostExecute();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
