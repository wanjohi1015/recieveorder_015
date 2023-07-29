package com.blueprint.blueprintdeliv;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Titlemodel {

    @ServerTimestamp
    Date timestamp;


    String   Phonenumber;
    int totalprice,items;
    List<model> titleitem;




    public Titlemodel() {
    }

    public Titlemodel( String Phonenumber, Date timestamp, int totalprice,int items, List<model> titleitem) {

        this.Phonenumber = Phonenumber;
        this.timestamp = timestamp;
        this.totalprice = totalprice;
        this.titleitem = titleitem;
        this.items = items;

    }





    public String getPhoneNumber() {
        return Phonenumber;
    }

    public void setPhoneNumber(String Phonenumber) {
        this.Phonenumber = Phonenumber;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }
    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public List<model> getTitleItem(){
        return titleitem;
    }
    public void setTitleItem(List<model> titleitem){
        this.titleitem = titleitem;
    }


}



