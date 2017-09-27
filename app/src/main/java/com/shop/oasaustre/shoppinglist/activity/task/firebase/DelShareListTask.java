package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteShareListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.firebase.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.firebase.ShareListAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.db.service.CategoriaService;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.UserShareListDto;

/**
 * Created by oasaustre on 4/12/16.
 */

public class DelShareListTask implements ITask {

    private DeleteShareListDialog dialog = null;
    private UserShareListDto userShareListDto = null;
    private Activity activity = null;
    private int delPosition;
    private final static String SHARE = "compartida";

    public DelShareListTask(Activity activity, DeleteShareListDialog dialog, int delPosition){
        this.dialog = dialog;
        this.activity = activity;
        this.delPosition = delPosition;

    }



    protected void onPreExecute(){
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_shareList);
        ShareListAdapter adapter = (ShareListAdapter) recyclerView.getAdapter();
        userShareListDto = adapter.getListaUserShare().get(delPosition);
    }



    protected void onPostExecute(Boolean result) {

        if(result){
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.rv_shareList);
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

        app.getDatabase().getReference().child(SHARE).child(app.getListaaFBActive().getUid()).child(userShareListDto.getUidUser()).addListenerForSingleValueEvent(new ValueEventListener() {
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
