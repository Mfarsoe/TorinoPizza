package com.example.torinopizza;

public class OrdreListObj {
    private int ID;
    private String Navn;
    private double Pris;
    private String status;

    public OrdreListObj(int ID, String navn, double pris, String status) {
        this.ID = ID;
        Navn = navn;
        Pris = pris;
        this.status = status;
    }

    //GETTERS
    public int getID() {
        return ID;
    }

    public String getNavn() {
        return Navn;
    }

    public double getPris() {
        return Pris;
    }

    public String getStatus() {
        return status;
    }
}
