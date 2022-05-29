package com.fhbielefeld.wholetsthedogoutpraesi;

import java.io.Serializable;

public class ItemsModel implements Serializable {

    private String name;
    private final String desc;
    private final int image;

    public ItemsModel(String name, String desc, int image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public int getImage() {
        return image;
    }

}
