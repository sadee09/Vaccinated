package com.samrina.storage;

public class Member {
    private String Vaccine;
    private String pregvaccine;


    public Member(){}

    public String getVaccine() {
        return Vaccine;
    }

    public void setVaccine(String vaccine) {
        Vaccine = vaccine;
    }

    public String getPregvaccine() {
        return pregvaccine;
    }

    public void setPregvaccine(String pregvaccine) {
        this.pregvaccine = pregvaccine;
    }
}
