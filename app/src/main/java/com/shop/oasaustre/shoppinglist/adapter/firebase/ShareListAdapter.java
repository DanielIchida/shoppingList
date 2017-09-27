package com.shop.oasaustre.shoppinglist.adapter.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteShareListDialog;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.User;
import com.shop.oasaustre.shoppinglist.db.entity.Usuario;
import com.shop.oasaustre.shoppinglist.dto.firebase.CategoriaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.UserShareListDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.ViewHolder> implements ValueEventListener {
    private LayoutInflater inflador;
    private Context context;
    private View.OnClickListener onClickListener;
    private List<UserShareListDto> listaUserShare;
    private Query reference;
    private User user;
    private App app;
    private static final String USER = "usuario";

    public ShareListAdapter(User user, Context context, Query reference) {
        this.user = user;
        this.reference = reference;
        this.context = context;
        app = (App) ((Activity) context).getApplication();

        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_share_list, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {
        final UserShareListDto userShareListDto = listaUserShare.get(i);

        app.getDatabase().getReference().child(USER).child(userShareListDto.getUidUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Usuario userDto = dataSnapshot.getValue(Usuario.class);

                    holder.getIdUserShareList().setText(userShareListDto.getUidUser());
                    holder.getTituloUserShareList().setText(userDto.getEmail());

                    if (i % 2 == 0) {
                        holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_even));
                    } else {
                        holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_odd));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        int result = 0;
        if(listaUserShare != null){
            result = listaUserShare.size();
        }

        return result;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        UserShareListDto userShareListDto = null;

        listaUserShare = new ArrayList<UserShareListDto>();

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            if (!user.getUid().equalsIgnoreCase(child.getKey())) {
                userShareListDto = new UserShareListDto();
                userShareListDto.setUidUser(child.getKey());
                userShareListDto.setListActive(child.getValue(Boolean.class));
                listaUserShare.add(userShareListDto);
            }

        }

        notifyDataSetChanged();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void activateListener(){
        listaUserShare = new ArrayList<UserShareListDto>();
        //FirebaseDatabase.getInstance().goOnline();
        reference.addValueEventListener(this);
    }
    public void deactivateListener(){
        reference.removeEventListener(this);
        //FirebaseDatabase.getInstance().goOffline();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloUserShareList;
        private TextView idUserShareList;
        private ImageView iconoDel;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_sharelist);
            tituloUserShareList = (TextView)itemView.findViewById(R.id.txtShareList);
            idUserShareList = (TextView)itemView.findViewById(R.id.txtIdShareList);
            iconoDel = (ImageView)itemView.findViewById(R.id.imgDeleteShareList);


            iconoDel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DeleteShareListDialog dialog = new DeleteShareListDialog();
                    dialog.setDelPosition(getAdapterPosition());
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"Eliminar Usuario Lista Compartida");
                }
            });

           /* itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    g
                    Toast.makeText(itemView.getContext(), DataProvider.JAVA_BOOKS[pos], Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        public TextView getTituloUserShareList() {
            return tituloUserShareList;
        }

        public void setTituloUserShareList(TextView tituloUserShareList) {
            this.tituloUserShareList = tituloUserShareList;
        }

        public TextView getIdUserShareList() {
            return idUserShareList;
        }

        public void setIdUserShareList(TextView idUserShareList) {
            this.idUserShareList = idUserShareList;
        }

        public ImageView getIconoDel() {
            return iconoDel;
        }

        public void setIconoDel(ImageView iconoDel) {
            this.iconoDel = iconoDel;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<UserShareListDto> getListaUserShare() {
        return listaUserShare;
    }

    public void setListaUserShare(List<UserShareListDto> listaUserShare) {
        this.listaUserShare = listaUserShare;
    }
}
