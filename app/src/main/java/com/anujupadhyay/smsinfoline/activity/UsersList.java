package com.anujupadhyay.smsinfoline.activity;

public class UsersList {
    public String name, images, about;

    public UsersList() {
    }

    public UsersList(String name, String images, String about) {
        this.name = name;
        this.images = images;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}