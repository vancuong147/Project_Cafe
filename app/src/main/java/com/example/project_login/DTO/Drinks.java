package com.example.project_login.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class Drinks implements Parcelable {
    String category;
    String Id;
    String name;
    String image;
    Integer price;

    public  Drinks(){ }
    public  Drinks(String category, String name, String image, Integer price){
        this.category = category;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public  Drinks( String name, String image, Integer price){
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    protected Drinks(Parcel in) {
        Id = in.readString();
        category = in.readString();
        name = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(category);
        dest.writeString(name);
        dest.writeString(image);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drinks> CREATOR = new Creator<Drinks>() {
        @Override
        public Drinks createFromParcel(Parcel in) {
            return new Drinks(in);
        }

        @Override
        public Drinks[] newArray(int size) {
            return new Drinks[size];
        }
    };
}