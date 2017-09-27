package com.shop.oasaustre.shoppinglist.activity.task.firebase;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.ShareListDialog;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.adapter.CategoriaAdapter;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.User;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by oasaustre on 20/09/17.
 */

public class NewShareListTask implements ITask {

    private ShareListDialog dialog;
    private final static String SHARE = "compartida";
    private final static String USER = "usuario";

    public NewShareListTask(ShareListDialog dialog){
        this.dialog = dialog;
    }


    @Override
    public void run(Object... params) {
        DatabaseReference shareRef = null;

        final App app = (App) dialog.getActivity().getApplication();
        final ListaDto listaDto = app.getListaaFBActive();

        shareRef = app.getDatabase().getReference().child(SHARE).child(listaDto.getUid());

        Query queryUser = app.getDatabase().getReference().child(USER).orderByChild("email").equalTo((String) params[0]);

        queryUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                    Map<String,Object> userShareList = new HashMap<String,Object>();
                    User user = child.getValue(User.class);
                    userShareList.put(user.getUid(),false);
                    app.getDatabase().getReference().child(SHARE).child(listaDto.getUid()).updateChildren(userShareList);
                    dialog.dismiss();
                        //Meter en lista compartido
                }else{
                    dialog.dismiss();
                    //Firebase Invite
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    protected void onPostExecute(Categoria categoria) {

        RecyclerView rv = (RecyclerView) dialog.getActivity().findViewById(R.id.rv_categoriaList);

        if(categoria != null){
            ((CategoriaAdapter) rv.getAdapter()).getLista().add(categoria);
            ((CategoriaAdapter) rv.getAdapter()).notifyDataSetChanged();
        }

        dialog.dismiss();
    }
}
