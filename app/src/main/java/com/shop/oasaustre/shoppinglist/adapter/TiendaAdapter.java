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
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.ViewHolder> {
    private LayoutInflater inflador;
    private List<Tienda> lista;
    private Context context;
    private View.OnClickListener onClickListener;

    public TiendaAdapter(Context context, List<Tienda> lista) {
        this.lista = lista;
        this.context = context;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_tienda, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Tienda tienda = lista.get(i);

        holder.getIdTienda().setText((tienda.getId().toString()));
        holder.getTituloTienda().setText(tienda.getNombre());

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
        private TextView tituloTienda;
        private TextView idTienda;
        private ImageView iconoDel;
        private ImageView iconoEdit;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_tienda);
            tituloTienda = (TextView)itemView.findViewById(R.id.txtTienda);
            idTienda = (TextView)itemView.findViewById(R.id.txtIdTienda);
            iconoDel = (ImageView)itemView.findViewById(R.id.imgDeleteTienda);
            iconoEdit = (ImageView)itemView.findViewById(R.id.imgEditTienda);


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

        public TextView getTituloTienda() {
            return tituloTienda;
        }

        public void setTituloTienda(TextView tituloTienda) {
            this.tituloTienda = tituloTienda;
        }

        public TextView getIdTienda() {
            return idTienda;
        }

        public void setIdTienda(TextView idTienda) {
            this.idTienda = idTienda;
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

    public List<Tienda> getLista() {
        return lista;
    }

    public void setLista(List<Tienda> lista) {
        this.lista = lista;
    }
}
