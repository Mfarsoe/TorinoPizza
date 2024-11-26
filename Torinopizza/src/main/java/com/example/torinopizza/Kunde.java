package com.example.torinopizza;

public class Kunde {
    private int ID;
    private String Navn;
    private String Addresse;
    private String Telefon;

    //CONSTRUCTOR
    public Kunde(int ID, String Navn, String Addresse, String Telefon) {
        this.ID = ID;
        this.Navn = Navn;
        this.Addresse = Addresse;
        this.Telefon = Telefon;
    }

    //GETTERS
    public int getID(){
        return ID;
    }

    public String getNavn() {
        return Navn;
    }

    public String getAddresse() {
        return Addresse;
    }

    public String getTelefon() {
        return Telefon;
    }
}
