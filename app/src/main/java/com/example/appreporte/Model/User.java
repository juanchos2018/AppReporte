package com.example.appreporte.Model;

public class User {

    Integer id;
    String name;
    String number_document;
    String last_name;
    String email;
    String phone;
    String location;
    String lat;
    String lon;
    String photo;
    Integer state;
    String password;

    public User(String name, String last_name, String email, String phone, String location, String lat, String lon, String photo, Integer state,String number_document,String password) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.photo = photo;
        this.state = state;
        this.number_document=number_document;
        this.password=password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNumber_document() {
        return number_document;
    }

    public void setNumber_document(String number_document) {
        this.number_document = number_document;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }



}
