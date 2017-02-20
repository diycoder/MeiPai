package com.mumu.meipai.bean;

import java.io.Serializable;

/**
 * Created by MuMu on 2016/12/24/0024.
 */

public class HomeChannel implements Serializable {


    /**
     * id : 1
     * name : 热门
     * icon : http://mvimg10.meitudata.com/55ef949c90ad58565.png
     * icon_svg :
     * color : ff7e51
     * type : 1
     * has_content_rank : 0
     * has_banner : 0
     * index : 0
     */

    private int id;
    private String name;
    private String icon;
    private String icon_svg;
    private String color;
    private int type;
    private int has_content_rank;
    private int has_banner;
    private int index;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_svg() {
        return icon_svg;
    }

    public void setIcon_svg(String icon_svg) {
        this.icon_svg = icon_svg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHas_content_rank() {
        return has_content_rank;
    }

    public void setHas_content_rank(int has_content_rank) {
        this.has_content_rank = has_content_rank;
    }

    public int getHas_banner() {
        return has_banner;
    }

    public void setHas_banner(int has_banner) {
        this.has_banner = has_banner;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "HomeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", icon_svg='" + icon_svg + '\'' +
                ", color='" + color + '\'' +
                ", type=" + type +
                ", has_content_rank=" + has_content_rank +
                ", has_banner=" + has_banner +
                ", index=" + index +
                '}';
    }
}
