package com.group.wallet.model;

import com.group.core.model.BaseEntity2;

public class ModularDesign extends BaseEntity2{

    private String app;

    private String position;

    private String name;

    private String description;

    private String image;

    private String routeType;

    private String routePage;

    private String richText;

    private Integer sortOrder;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType == null ? null : routeType.trim();
    }

    public String getRoutePage() {
        return routePage;
    }

    public void setRoutePage(String routePage) {
        this.routePage = routePage == null ? null : routePage.trim();
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText == null ? null : richText.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}