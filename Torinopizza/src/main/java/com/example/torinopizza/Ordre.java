package com.example.torinopizza;

public class Ordre {
    private int ordreID;
    private int kundeID;
    private String ordreDato;
    private double totalPris;
    private String leveringsStatus;


    //CONSTRUCTOR
    public Ordre(int ordreID, int kundeID, String ordreDato, double totalPris, String leveringsStatus) {
        this.ordreID = ordreID;
        this.kundeID = kundeID;
        this.ordreDato = ordreDato;
        this.totalPris = totalPris;
        this.leveringsStatus = leveringsStatus;
    }

    //SETTERS
    public void setOrdreID(int ordreID) {
        this.ordreID = ordreID;
    }

    public void setKundeID(int kundeID) {
        this.kundeID = kundeID;
    }

    public void setOrdreDato(String ordreDato) {
        this.ordreDato = ordreDato;
    }

    public void setTotalPris(double totalPris) {
        this.totalPris = totalPris;
    }

    public void setLeveringsStatus(String leveringsStatus) {
        this.leveringsStatus = leveringsStatus;
    }


    //GETTERS
    public int getOrdreID() {
        return ordreID;
    }

    public int getKundeID() {
        return kundeID;
    }

    public String getOrdreDato() {
        return ordreDato;
    }

    public double getTotalPris() {
        return totalPris;
    }

    public String getLeveringsStatus() {
        return leveringsStatus;
    }

}
