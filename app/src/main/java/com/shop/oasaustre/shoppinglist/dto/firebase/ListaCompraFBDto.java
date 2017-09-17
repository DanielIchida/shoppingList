package com.shop.oasaustre.shoppinglist.dto.firebase;

import java.util.Map;

/**
 * Created by oasaustre on 13/09/17.
 */

public class ListaCompraFBDto {

    private String uid;

    private Map<String,Object> articulo;

    private Map<String,Object> lista;

    private Map<String,Object> categoria;

    private Map<String,Object> tienda;


    private String idTienda;

    private Long unidades;

    private Double precio;

    private String notas;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, Object> getArticulo() {
        return articulo;
    }

    public void setArticulo(Map<String, Object> articulo) {
        this.articulo = articulo;
    }

    public Map<String, Object> getCategoria() {
        return categoria;
    }

    public void setCategoria(Map<String, Object> categoria) {
        this.categoria = categoria;
    }

    public String getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(String idTienda) {
        this.idTienda = idTienda;
    }

    public Long getUnidades() {
        return unidades;
    }

    public void setUnidades(Long unidades) {
        this.unidades = unidades;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Map<String, Object> getLista() {
        return lista;
    }

    public void setLista(Map<String, Object> lista) {
        this.lista = lista;
    }

    public Map<String, Object> getTienda() {
        return tienda;
    }

    public void setTienda(Map<String, Object> tienda) {
        this.tienda = tienda;
    }
}
