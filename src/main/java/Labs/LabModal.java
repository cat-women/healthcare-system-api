/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Labs;

/**
 *
 * @author Dell
 */
public class LabModal {
    private int id,doctor_id,patience_id,app_id;
    private String test_name,result,name,doctorName;

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public int getApp_id() {
        return app_id;
    }

    public int getId() {
        return id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getPatience_id() {
        return patience_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public String getResult() {
        return result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setPatience_id(int patience_id) {
        this.patience_id = patience_id;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
}
