package com.shop.oasaustre.shoppinglist.db.entity;

import java.util.Map;

/**
 * Created by oasaustre on 10/09/17.
 */

public class Usuario {

    private String uid;
    private String name;
    private String email;
    private String provider;
    private Map<String,Object> compartida;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Map<String, Object> getCompartida() {
        return compartida;
    }

    public void setCompartida(Map<String, Object> compartida) {
        this.compartida = compartida;
    }
}
