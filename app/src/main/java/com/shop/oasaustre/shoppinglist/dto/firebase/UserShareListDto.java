package com.shop.oasaustre.shoppinglist.dto.firebase;

/**
 * Created by oasaustre on 20/09/17.
 */

public class UserShareListDto {

    private String uidUser;


    private boolean isListActive;

    public String getUidUser() {
        return uidUser;
    }

    public void setUidUser(String uidUser) {
        this.uidUser = uidUser;
    }

    public boolean isListActive() {
        return isListActive;
    }

    public void setListActive(boolean listActive) {
        isListActive = listActive;
    }
}
