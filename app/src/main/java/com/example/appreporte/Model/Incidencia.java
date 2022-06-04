package com.example.appreporte.Model;

public class Incidencia {

    String id;
    String fecha;
    String type_incidence_id;
    String title;
    String description;
    String location;
    String photo;
    Integer usuarioId;
    String status;
    Double lat;
    Double lon;
    String organization_id;

    public  Incidencia(){

    }


    public Incidencia(String id, String fecha, String type_incidence_id, String title, String description, String location, String photo, Integer usuarioId, String status, Double lat, Double lon,String organization_id) {
        this.id = id;
        this.fecha = fecha;
        this.type_incidence_id = type_incidence_id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.photo = photo;
        this.usuarioId = usuarioId;
        this.status = status;
        this.lat = lat;
        this.lon = lon;
        this.organization_id=organization_id;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getType_incidence_id() {
        return type_incidence_id;
    }

    public void setType_incidence_id(String type_incidence_id) {
        this.type_incidence_id = type_incidence_id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }




}
