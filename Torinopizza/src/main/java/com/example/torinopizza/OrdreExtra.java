package com.example.torinopizza;

public class OrdreExtra {
    private int ordreExtraID;
    private int ordreDetaljeID;
    private int inventarID;
    private int antal;
    private double extrapis;

    //CONSTRUCTOR

    public OrdreExtra(int ordreExtraID, int ordreDetaljeID, int inventarID, int antal, double extrapis) {
        this.ordreExtraID = ordreExtraID;
        this.ordreDetaljeID = ordreDetaljeID;
        this.inventarID = inventarID;
        this.antal = antal;
        this.extrapis = extrapis;
    }

    public int getOrdreExtraID() {
        return ordreExtraID;
    }

    public void setOrdreExtraID(int ordreExtraID) {
        this.ordreExtraID = ordreExtraID;
    }

    public int getOrdreDetaljeID() {
        return ordreDetaljeID;
    }

    public void setOrdreDetaljeID(int ordreDetaljeID) {
        this.ordreDetaljeID = ordreDetaljeID;
    }

    public int getInventarID() {
        return inventarID;
    }

    public void setInventarID(int inventarID) {
        this.inventarID = inventarID;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public double getExtrapis() {
        return extrapis;
    }

    public void setExtrapis(double extrapis) {
        this.extrapis = extrapis;
    }
}
