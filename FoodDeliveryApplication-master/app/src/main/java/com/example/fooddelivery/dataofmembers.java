package com.example.fooddelivery;

public class dataofmembers {
    String dname,dph,demail,dpass;
    public dataofmembers(String dname, String dph,String demail,String dpass){
        this.dname=dname;
        this.dph=dph;
        this.demail=demail;
        this.dpass=dpass;
    }

    public String getDname() {
        return dname;
    }

    public String getDph() {
        return dph;
    }

    public String getDemail() {
        return demail;
    }

    public String getDpass() {
        return dpass;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setDph(String dph) {
        this.dph = dph;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public void setDpass(String dpass) {
        this.dpass = dpass;
    }
}
