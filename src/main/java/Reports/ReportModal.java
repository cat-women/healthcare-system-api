/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import java.util.Date;


/**
 *
 * @author Dell
 */
public class ReportModal extends Labs.LabModal{
    
    private String medicine,symptoms;
    private Date nextDate;

    public String getMedicine() {
        return medicine;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }


    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }
    
    
    
    
}
