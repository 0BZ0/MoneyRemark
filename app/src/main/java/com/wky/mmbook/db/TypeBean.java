package com.wky.mmbook.db;

public class TypeBean {
    int id;
    String typename;
    int imageId;
    int sImageId;
    int kind; //收入1 支出0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public TypeBean() {
    }

    public TypeBean(int id, String typename, int imageId, int sImageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.sImageId = sImageId;
        this.kind = kind;
    }
}
