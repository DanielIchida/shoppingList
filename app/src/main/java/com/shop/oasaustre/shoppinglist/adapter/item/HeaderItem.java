package com.shop.oasaustre.shoppinglist.adapter.item;

/**
 * Created by oasaustre on 7/12/16.
 */

public class HeaderItem extends ListItem{

    private String header;

    public HeaderItem(String header){
        this.header = header;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
