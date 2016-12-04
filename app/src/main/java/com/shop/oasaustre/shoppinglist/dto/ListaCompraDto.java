package com.shop.oasaustre.shoppinglist.dto;

import com.shop.oasaustre.shoppinglist.db.entity.Articulo;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;

import java.util.List;

/**
 * Created by oasaustre on 4/12/16.
 */

public class ListaCompraDto {

    private List<Articulo> allArticles;

    private List<ListaCompra> lstCompra;

    private Double sumTotalListaCompra;

    private Long totalUnidades;

    public List<Articulo> getAllArticles() {
        return allArticles;
    }

    public void setAllArticles(List<Articulo> allArticles) {
        this.allArticles = allArticles;
    }

    public List<ListaCompra> getLstCompra() {
        return lstCompra;
    }

    public void setLstCompra(List<ListaCompra> lstCompra) {
        this.lstCompra = lstCompra;
    }

    public Double getSumTotalListaCompra() {
        return sumTotalListaCompra;
    }

    public void setSumTotalListaCompra(Double sumTotalListaCompra) {
        this.sumTotalListaCompra = sumTotalListaCompra;
    }

    public Long getTotalUnidades() {
        return totalUnidades;
    }

    public void setTotalUnidades(Long totalUnidades) {
        this.totalUnidades = totalUnidades;
    }
}
