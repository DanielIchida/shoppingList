package com.shop.oasaustre.shoppinglist.adapter;

import android.app.Activity;
import android.content.Context;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.adapter.helper.ListaCompraAdapterHelper;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentItem;
import com.shop.oasaustre.shoppinglist.adapter.item.HeaderItem;
import com.shop.oasaustre.shoppinglist.adapter.item.ListItem;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class ListaCompraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflador;
    private List<ListItem> lista;
    private Context context;
    private View.OnClickListener onClickListener;
    private List<Long> selectedItem;

    public ListaCompraAdapter(Context context, List<ListaCompra> lista) {
        this.lista = ListaCompraAdapterHelper.performTransform(lista);
        this.context = context;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedItem = new ArrayList<Long>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == ListItem.TYPE_HEADER){
            itemView = inflador.inflate(R.layout.header_lista_compra, parent, false);
            viewHolder = new HeaderViewHolder(itemView);
        }else if(viewType == ListItem.TYPE_CONTENT){
            itemView = inflador.inflate(R.layout.element_lista_compra, parent, false);
            itemView.setOnClickListener(onClickListener);
            viewHolder = new ContentViewHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return lista.get(position).getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        int type = getItemViewType(i);

        if(type == ListItem.TYPE_HEADER){
            HeaderViewHolder headerViewHolder = null;
            HeaderItem headerItem = (HeaderItem) lista.get(i);

            headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.reset();

            headerViewHolder.getHeader().setText(headerItem.getHeader());
            if (i % 2 == 0) {
                headerViewHolder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.header_list));
            } else {
                headerViewHolder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.header_list));
            }

        }else if(type == ListItem.TYPE_CONTENT){
            ContentViewHolder contentViewHolder = null;
            ContentItem contentItem = (ContentItem) lista.get(i);
            ListaCompra listaCompra = contentItem.getListaCompra();
            Articulo articulo = listaCompra.getArticulo();

            contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.reset();

            contentViewHolder.getTitulo().setText(articulo.getNombre());
            contentViewHolder.getIdListaCompra().setText(listaCompra.getId().toString());

            if(listaCompra.getUnidades() != null){
                contentViewHolder.getCantidad().setText(" ("+listaCompra.getUnidades()+")");
            }else{
                contentViewHolder.getCantidad().setText(AppConstant.BLANK);
            }

            if(listaCompra.getPrecio() != null && listaCompra.getUnidades() != null){
                contentViewHolder.getPrecio().setText(Double.valueOf(listaCompra.getPrecio() * listaCompra.getUnidades()).toString() + AppConstant.EURO) ;
            }else if(listaCompra.getPrecio() != null){
                contentViewHolder.getPrecio().setText(listaCompra.getPrecio().toString() + AppConstant.EURO);
            }else{
                contentViewHolder.getCantidad().setText(AppConstant.BLANK);
            }

            if (i % 2 == 0) {
                contentViewHolder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_even));
            } else {
                contentViewHolder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_odd));
            }

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

    public void addItem(ListaCompra listaCompra) {
        setLista(ListaCompraAdapterHelper.addItem(getLista(),listaCompra));
        notifyDataSetChanged();
    }

    public void removeItems(List<Long> selectedItem){
        setLista(ListaCompraAdapterHelper.removeItem(getLista(),selectedItem));
        selectedItem.clear();
        notifyDataSetChanged();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView header;
        private RelativeLayout layout;

        HeaderViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_header_listaCompra);
            header = (TextView)itemView.findViewById(R.id.headerCategoria);

        }

        public void reset(){
            header.setText(AppConstant.BLANK);
        }

        public TextView getHeader() {
            return header;
        }

        public void setHeader(TextView header) {
            this.header = header;
        }

        public RelativeLayout getLayout() {
            return layout;
        }

        public void setLayout(RelativeLayout layout) {
            this.layout = layout;
        }
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private CheckBox chkSelect;
        private TextView titulo;
        private ImageView icono;
        private TextView precio;
        private TextView cantidad;
        private TextView idListaCompra;
        private RelativeLayout layout;

        ContentViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_listaCompra);
            chkSelect = (CheckBox) itemView.findViewById(R.id.chkArticulo);
            titulo = (TextView)itemView.findViewById(R.id.txtArticulo);
            icono = (ImageView)itemView.findViewById(R.id.imgEditar);
            precio = (TextView)itemView.findViewById(R.id.txtPrice);
            cantidad = (TextView)itemView.findViewById(R.id.txtUnit);
            idListaCompra = (TextView) itemView.findViewById(R.id.txtidListaCompra);


            chkSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((CheckBox) view).isChecked()){
                        selectedItem.add(new Long(idListaCompra.getText().toString()));
                    }else{
                        selectedItem.remove(new Long(idListaCompra.getText().toString()));
                    }
                    FloatingActionButton fab = (FloatingActionButton) ((Activity) context).findViewById(R.id.deleteArticleFloat);

                    if(selectedItem.size() > 0){

                        fab.setVisibility(View.VISIBLE);
                    }else{
                        fab.setVisibility(View.INVISIBLE);
                    }
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

        public void reset(){
            chkSelect.setChecked(false);
            titulo.setText(AppConstant.BLANK);
            precio.setText(AppConstant.BLANK);
            cantidad.setText(AppConstant.BLANK);
            idListaCompra.setText(AppConstant.BLANK);
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

        public TextView getIdListaCompra() {
            return idListaCompra;
        }

        public void setIdListaCompra(TextView idListaCompra) {
            this.idListaCompra = idListaCompra;
        }
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<ListItem> getLista() {
        return lista;
    }

    public void setLista(List<ListItem> lista) {
        this.lista = lista;
    }

    public List<Long> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<Long> selectedItem) {
        this.selectedItem = selectedItem;
    }
}
