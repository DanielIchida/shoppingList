package com.shop.oasaustre.shoppinglist.adapter;

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

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.dialog.DeleteCategoriaDialog;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private LayoutInflater inflador;
    private List<Categoria> lista;
    private Context context;
    private View.OnClickListener onClickListener;

    public CategoriaAdapter(Context context, List<Categoria> lista) {
        this.lista = lista;
        this.context = context;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_categoria, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Categoria categoria = lista.get(i);

        holder.getIdCategoria().setText((categoria.getId().toString()));
        holder.getTituloCategoria().setText(categoria.getNombre());

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
        private TextView tituloCategoria;
        private TextView idCategoria;
        private ImageView iconoDel;
        private ImageView iconoEdit;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_categoria);
            tituloCategoria = (TextView)itemView.findViewById(R.id.txtCategoria);
            idCategoria = (TextView)itemView.findViewById(R.id.txtIdCategoria);
            iconoDel = (ImageView)itemView.findViewById(R.id.imgDeleteCategoria);
            iconoEdit = (ImageView)itemView.findViewById(R.id.imgEditCategoria);


            iconoDel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DeleteCategoriaDialog dialog = new DeleteCategoriaDialog();
                    dialog.setDelPosition(getAdapterPosition());
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(),"Eliminar categoría");
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

        public TextView getTituloCategoria() {
            return tituloCategoria;
        }

        public void setTituloCategoria(TextView tituloCategoria) {
            this.tituloCategoria = tituloCategoria;
        }

        public TextView getIdCategoria() {
            return idCategoria;
        }

        public void setIdCategoria(TextView idCategoria) {
            this.idCategoria = idCategoria;
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

    public List<Categoria> getLista() {
        return lista;
    }

    public void setLista(List<Categoria> lista) {
        this.lista = lista;
    }
}
