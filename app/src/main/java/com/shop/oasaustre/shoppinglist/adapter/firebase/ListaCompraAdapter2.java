package com.shop.oasaustre.shoppinglist.adapter.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.ArticleSaveActivity;
import com.shop.oasaustre.shoppinglist.adapter.item.ContentItem;
import com.shop.oasaustre.shoppinglist.adapter.item.HeaderItem;
import com.shop.oasaustre.shoppinglist.adapter.item.ListItem;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.dto.firebase.ListaCompraFBDto;

import java.util.List;

/**
 * Created by oasaustre on 10/09/17.
 */

public class ListaCompraAdapter2 extends FirebaseRecyclerAdapter<ListaCompraFBDto,RecyclerView.ViewHolder>{

    private LayoutInflater inflador;
    private List<ListItem> lista;
    private Context context;
    private List<Long> selectedItem;
    private DatabaseReference listaCompraReference;
    private DatabaseReference articuloReference;
    private Context contexto;
    private HeaderViewHolder headerViewHolder = null;
    private ContentViewHolder contentViewHolder = null;
    private View.OnClickListener onClickListener;


    private App app;

    public ListaCompraAdapter2(Context contexto, DatabaseReference listaCompraReference) {
        super(ListaCompraFBDto.class, R.layout.element_lista_compra,
                RecyclerView.ViewHolder.class, listaCompraReference);
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaCompraReference = listaCompraReference;
        this.contexto = contexto;
    }


    // Creamos el ViewHolder con las vista de un elemento sin personalizar
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
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, final ListaCompraFBDto model, int position) {
        int type = getItemViewType(position);

        HeaderViewHolder headerViewHolder = null;
        ContentViewHolder contentViewHolder = null;
        //getRef(i).getKey()

        if(type == ListItem.TYPE_HEADER){
            HeaderItem headerItem = (HeaderItem) lista.get(position);

            headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.reset();

            headerViewHolder.getHeader().setText(headerItem.getHeader());
            headerViewHolder.getLayout().setBackground(ContextCompat.getDrawable(context,R.drawable.header_list));


        }else if(type == ListItem.TYPE_CONTENT){
            ContentItem contentItem = (ContentItem) lista.get(position);
            ListaCompra listaCompra = contentItem.getListaCompra();
            Articulo articulo = listaCompra.getArticulo();

            contentViewHolder = (ContentViewHolder) viewHolder;
            contentViewHolder.reset();


            contentViewHolder.getTitulo().setText(articulo.getNombre());
            contentViewHolder.getIdListaCompra().setText(listaCompra.getId().toString());

            if(listaCompra.getUnidades() != null){
                contentViewHolder.getCantidad().setText(" ("+listaCompra.getUnidades()+")");
            }else{
                contentViewHolder.getCantidad().setText(AppConstant.BLANK);
            }

            if(listaCompra.getPrecio() != null && listaCompra.getUnidades() != null){
                contentViewHolder.getPrecio().setText(Double.valueOf(listaCompra.getPrecio() * listaCompra.getUnidades()).toString() + app.getSettings().getCurrency()) ;
            }else if(listaCompra.getPrecio() != null){
                contentViewHolder.getPrecio().setText(listaCompra.getPrecio().toString() + app.getSettings().getCurrency());
            }else{
                contentViewHolder.getPrecio().setText(AppConstant.BLANK);
            }

            if (position % 2 == 0) {
                contentViewHolder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_even));
            } else {
                contentViewHolder.getLayout().setBackground(context.getResources().getDrawable(R.drawable.line_divider_odd));
            }

            contentViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(contexto,ArticleSaveActivity.class);
                    i.putExtra("idListaCompra",model.getUid());
                    ((Activity)contexto).startActivityForResult(i, AppConstant.RES_UPDATE_ARTICLE);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return lista.get(position).getType();
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
        private View mView;

        ContentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
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

        public View getmView() {
            return mView;
        }

        public void setmView(View mView) {
            this.mView = mView;
        }
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
