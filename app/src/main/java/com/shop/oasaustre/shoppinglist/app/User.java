package com.shop.oasaustre.shoppinglist.app;

import android.net.Uri;

/**
 * Created by oasaustre on 3/09/17.
 */

public class User {

    private String uid;
    private String name;
    private String email;
    private String provider;
    private Uri image;

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

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
