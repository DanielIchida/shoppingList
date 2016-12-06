package com.shop.oasaustre.shoppinglist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class ListaCompraAdapter extends RecyclerView.Adapter<ListaCompraAdapter.ViewHolder> {
    private LayoutInflater inflador;
    private List<ListaCompra> lista;
    private Context context;
    private View.OnClickListener onClickListener;

    public ListaCompraAdapter(Context context, List<ListaCompra> lista) {
        this.lista = lista;
        this.context = context;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_lista_compra, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        ListaCompra listaCompra = lista.get(i);
        Articulo articulo = listaCompra.getArticulo();

        holder.getTitulo().setText(articulo.getNombre());

        if(listaCompra.getUnidades() != null){
           holder.getCantidad().setText("("+listaCompra.getUnidades()+")");
        }else{
            holder.getCantidad().setText(AppConstant.BLANK);
        }

        if(listaCompra.getPrecio() != null && listaCompra.getUnidades() != null){
            holder.getPrecio().setText(Double.valueOf(listaCompra.getPrecio() * listaCompra.getUnidades()).toString() + AppConstant.EURO) ;
        }else if(listaCompra.getPrecio() != null){
            holder.getPrecio().setText(listaCompra.getPrecio().toString() + AppConstant.EURO);
        }else{
            holder.getCantidad().setText(AppConstant.BLANK);
        }


        if (i % 2 == 0) {
            holder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_even));
        } else {
            holder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_odd));
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
        private CheckBox chkSelect;
        private TextView titulo;
        private ImageView icono;
        private TextView precio;
        private TextView cantidad;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_listaCompra);
            chkSelect = (CheckBox) itemView.findViewById(R.id.chkArticulo);
            titulo = (TextView)itemView.findViewById(R.id.txtArticulo);
            icono = (ImageView)itemView.findViewById(R.id.imgEditar);
            precio = (TextView)itemView.findViewById(R.id.txtPrice);
            cantidad = (TextView)itemView.findViewById(R.id.txtUnit);


           /* itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    g
                    Toast.makeText(itemView.getContext(), DataProvider.JAVA_BOOKS[pos], Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        public CheckBox getChkSelect() {
            return chkSelect;
        }

        public void setChkSelect(CheckBox chkSelect) {
            this.chkSelect = chkSelect;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public ImageView getIcono() {
            return icono;
        }

        public void setIcono(ImageView icono) {
            this.icono = icono;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public TextView getCantidad() {
            return cantidad;
        }

        public void setCantidad(TextView cantidad) {
            this.cantidad = cantidad;
        }
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<ListaCompra> getLista() {
        return lista;
    }

    public void setLista(List<ListaCompra> lista) {
        this.lista = lista;
    }
}
