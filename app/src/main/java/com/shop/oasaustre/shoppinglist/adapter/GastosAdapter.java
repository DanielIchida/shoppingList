package com.shop.oasaustre.shoppinglist.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.dto.GastosDto;

import java.util.List;

/**
 * Created by AsaustreGarO on 24/11/2016.
 */

public class GastosAdapter extends RecyclerView.Adapter<GastosAdapter.ViewHolder> {
    private LayoutInflater inflador;
    private List<GastosDto> lista;
    private Context context;
    private View.OnClickListener onClickListener;
    private App app;

    public GastosAdapter(Context context, List<GastosDto> lista, App app) {
        this.lista = lista;
        this.context = context;
        this.app = app;
          inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.element_gastos, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        GastosDto gastosDto = lista.get(i);

        holder.getTituloPeriodo().setText(gastosDto.getMonth()+"/"+gastosDto.getYear());
        holder.getTituloImporte().setText(gastosDto.getCantidad().toString()+app.getSettings().getCurrency());

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
        private TextView tituloImporte;
        private TextView tituloPeriodo;
        private RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.rl_element_gastos);
            tituloImporte = (TextView)itemView.findViewById(R.id.txtImporte);
            tituloPeriodo = (TextView)itemView.findViewById(R.id.txtPeriodo);


        }

        public TextView getTituloImporte() {
            return tituloImporte;
        }

        public void setTituloImporte(TextView tituloImporte) {
            this.tituloImporte = tituloImporte;
        }

        public TextView getTituloPeriodo() {
            return tituloPeriodo;
        }

        public void setTituloPeriodo(TextView tituloPeriodo) {
            this.tituloPeriodo = tituloPeriodo;
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

    public List<GastosDto> getLista() {
        return lista;
    }

    public void setLista(List<GastosDto> lista) {
        this.lista = lista;
    }
}
