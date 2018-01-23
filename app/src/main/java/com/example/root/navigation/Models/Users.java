package com.example.root.navigation.Models;

import java.util.Date;

/**
 * Created by root on 21/01/18.
 */

public class Users {

    int id;
    String fullname;
    String email;
    Date fecha_nacimiento;
    String remember_token;
    Date created_at;
    Date updated_at;
    Date deleted_at;

    public Users(int id, String fullname, String email, Date fecha_nacimiento, String remember_token, Date created_at, Date updated_at, Date deleted_at) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.remember_token = remember_token;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }
}
