/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Appointment;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class Appointment {

    int id, patience_id, doctor_id, token;
    String name, contact, email, department, date, patient_type, last_viste_date,doctor_name;
    boolean isChecked;
    
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setPatient_type(String patient_type) {
        this.patient_type = patient_type;
    }

    public void setLast_viste_date(String last_viste_date) {
        this.last_viste_date = last_viste_date;
    }

    public String getPatient_type() {
        return patient_type;
    }

    public String getLast_viste_date() {
        return last_viste_date;
    }

    public int getId() {
        return id;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getPatience_id() {
        return patience_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatience_id(int patience_id) {
        this.patience_id = patience_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
