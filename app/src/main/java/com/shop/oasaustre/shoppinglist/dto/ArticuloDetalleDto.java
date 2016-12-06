package com.shop.oasaustre.shoppinglist.dto;

import com.shop.oasaustre.shoppinglist.constant.AppConstant;
import com.shop.oasaustre.shoppinglist.db.entity.Categoria;
import com.shop.oasaustre.shoppinglist.db.entity.ListaCompra;
import com.shop.oasaustre.shoppinglist.db.entity.Tienda;

import java.util.List;

/**
 * Created by oasaustre on 6/12/16.
 */

public class ArticuloDetalleDto {

    private ListaCompra listaCompra;
    private List<Categoria> allCategorias;
    private List<Tienda> allTiendas;

    private Integer positionSelectCategoria;
    private Integer positionSelectTienda;

    public ArticuloDetalleDto(){
        positionSelectCategoria = AppConstant.POSITION_DEFAULT;
        positionSelectTienda = AppConstant.POSITION_DEFAULT;
    }

    public ListaCompra getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ListaCompra listaCompra) {
        this.listaCompra = listaCompra;
    }

    public List<Categoria> getAllCategorias() {
        return allCategorias;
    }

    public void setAllCategorias(List<Categoria> allCategorias) {
        this.allCategorias = allCategorias;
    }

    public List<Tienda> getAllTiendas() {
        return allTiendas;
    }

    public void setAllTiendas(List<Tienda> allTiendas) {
        this.allTiendas = allTiendas;
    }

    public Integer getPositionSelectCategoria() {
        return positionSelectCategoria;
    }

    public void setPositionSelectCategoria(Integer positionSelectCategoria) {
        this.positionSelectCategoria = positionSelectCategoria;
    }

    public Integer getPositionSelectTienda() {
        return positionSelectTienda;
    }

    public void setPositionSelectTienda(Integer positionSelectTienda) {
        this.positionSelectTienda = positionSelectTienda;
    }
}
