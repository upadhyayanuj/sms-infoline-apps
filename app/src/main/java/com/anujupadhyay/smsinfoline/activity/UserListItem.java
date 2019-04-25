package com.anujupadhyay.smsinfoline.activity;

public class UserListItem {
    String Name, Images, About;

    public UserListItem(String name, String images, String about) {
        Name = name;
        Images = images;
        About = about;
    }

    public String getName() {
        return Name;
    }

    public String getImages() {
        return Images;
    }

    public String getAbout() {
        return About;
    }
}
