package com.shop.oasaustre.shoppinglist.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {
    private LayoutInflater inflador;
    private List<Lista> lista;
    private Context context;
    private View.OnClickListener onClickListener;
    long yourmilliseconds = System.currentTimeMillis();
    SimpleDateFormat dateFormat;


    public ListaAdapter(Context context, List<Lista> lista) {
        this.lista = lista;
        this.context = context;
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
        Lista currentLista = lista.get(i);
        Date listaDateCreated = null;

        holder.getIdLista().setText((currentLista.getId().toString()));
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
                    int pos = getAdapterPosition();
                    getLista().remove(pos);
                    notifyDataSetChanged();
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

    public List<Lista> getLista() {
        return lista;
    }

    public void setLista(List<Lista> lista) {
        this.lista = lista;
    }
}
