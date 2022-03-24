/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Doctor;

/**
 *
 * @author Dell
 */
public class DoctorModule {

    private int id, nmc_no, charge;
    private String name, citizenship, address, specialization, qualification, email, contact;
    private Boolean isAvailable;

    public int getId() {
        return id;
    }

    public int getNmc_no() {
        return nmc_no;
    }

    public int getCharge() {
        return charge;
    }

    public String getName() {
        return name;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getAddress() {
        return address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNmc_no(int nmc_no) {
        this.nmc_no = nmc_no;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
