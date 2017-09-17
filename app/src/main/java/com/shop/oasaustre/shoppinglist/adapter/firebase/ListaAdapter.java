package com.shop.oasaustre.shoppinglist.adapter.firebase;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import com.shop.oasaustre.shoppinglist.activity.ListaSaveActivity;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteListaDialog;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaDto;
import com.shop.oasaustre.shoppinglist.dto.firebase.TiendaDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> implements ValueEventListener {
    private LayoutInflater inflador;
    private List<ListaDto> lista;
    private Context context;
    private View.OnClickListener onClickListener;
    private SimpleDateFormat dateFormat;
    private Query reference;


    public ListaAdapter(Context context, Query reference) {
        this.context = context;
        this.reference = reference;
        dateFormat = new SimpleDateFormat(AppConstant.LIST_FORMAT_DATE);
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_lista, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        ListaDto currentLista = lista.get(i);
        Date listaDateCreated = null;

        holder.getIdLista().setText(currentLista.getUid());
        holder.getTituloLista().setText(currentLista.getNombre());

        listaDateCreated =  new Date(currentLista.getFecha());
        if(listaDateCreated != null){
            holder.getFechaCompra().setText(dateFormat.format(listaDateCreated));
        }

        if (i % 2 == 0) {
            holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_even));
        } else {
            holder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.line_divider_odd));
        }

    }

    @Override
    public int getItemCount() {
        int result = 0;
        if(lista != null){
            result = lista.size();
        }

        return result;
    }

    public void addItem(ListaDto newLista){
        if(lista == null) {
            lista = new ArrayList<ListaDto>();
        }
        lista.add(newLista);
        notifyDataSetChanged();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ListaDto listaDto = null;

        lista = new ArrayList<ListaDto>();

        for (DataSnapshot child : dataSnapshot.getChildren()) {
            listaDto = child.getValue(ListaDto.class);
            lista.add(listaDto);
        }

        notifyDataSetChanged();
    }


    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    public void activateListener(){
        lista = new ArrayList<ListaDto>();
        //FirebaseDatabase.getInstance().goOnline();
        reference.addValueEventListener(this);
    }
    public void deactivateListener(){
        reference.removeEventListener(this);
        //FirebaseDatabase.getInstance().goOffline();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloLista;
        private TextView idLista;
        private TextView fechaCompra;
        private ImageView iconoDel;
        private ImageView iconoEdit;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_list);
            tituloLista = (TextView)itemView.findViewById(R.id.txtLista);
            idLista = (TextView)itemView.findViewById(R.id.txtIdLista);
            fechaCompra = (TextView)itemView.findViewById(R.id.txtFechaLista);
            iconoDel = (ImageView)itemView.findViewById(R.id.imgDeleteLista);
            iconoEdit = (ImageView)itemView.findViewById(R.id.imgEditLista);


            iconoDel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(getItemCount() == 1){

                           Snackbar.make(v, "Esta lista no se puede eliminar porque es tu única lista de la compra",
                                Snackbar.LENGTH_LONG).show();

                    }else{
                        DeleteListaDialog dialog = new DeleteListaDialog();
                        dialog.setDelPosition(getAdapterPosition());
                        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"Eliminar Lista");

                    }
                }
            });

            iconoEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positionItemSelect = getAdapterPosition();
                    ListaDto lista = getLista().get(positionItemSelect);
                    Intent intent = new Intent(context, ListaSaveActivity.class);
                    intent.putExtra(AppConstant.ID_INTENT, lista.getUid());
                    intent.putExtra(AppConstant.TITLE_INTENT,lista.getNombre());
                    intent.putExtra(AppConstant.ACTIVE_INTENT, lista.getActivo());
                    intent.putExtra(AppConstant.FECHA_INTENT,lista.getFecha());
                    context.startActivity(intent);
                }
            });

        }

        public TextView getTituloLista() {
            return tituloLista;
        }

        public void setTituloLista(TextView tituloLista) {
            this.tituloLista = tituloLista;
        }

        public TextView getIdLista() {
            return idLista;
        }

        public void setIdLista(TextView idLista) {
            this.idLista = idLista;
        }

        public TextView getFechaCompra() {
            return fechaCompra;
        }

        public void setFechaCompra(TextView fechaCompra) {
            this.fechaCompra = fechaCompra;
        }

        public ImageView getIconoDel() {
            return iconoDel;
        }

        public void setIconoDel(ImageView iconoDel) {
            this.iconoDel = iconoDel;
        }

        public ImageView getIconoEdit() {
            return iconoEdit;
        }

        public void setIconoEdit(ImageView iconoEdit) {
            this.iconoEdit = iconoEdit;
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

    public List<ListaDto> getLista() {
        return lista;
    }

    public void setLista(List<ListaDto> lista) {
        this.lista = lista;
    }
}
