package com.example.torinopizza;

public class Pizza {
    private int PizzaID;
    private String Navn;
    private String Beskrivelse;
    private double pris;


    //CONSTRUCTOR
    public Pizza(int pizzaID, String navn, String beskrivelse, double pris) {
        PizzaID = pizzaID;
        Navn = navn;
        Beskrivelse = beskrivelse;
        this.pris = pris;
    }

    //SETTERS
    public void setPizzaID(int pizzaID) {
        PizzaID = pizzaID;
    }

    public void setNavn(String navn) {
        Navn = navn;
    }

    public void setBeskrivelse(String beskrivelse) {
        Beskrivelse = beskrivelse;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    //GETTERS
    public double getPris() {
        return pris;
    }

    public String getBeskrivelse() {
        return Beskrivelse;
    }

    public String getNavn() {
        return Navn;
    }

    public int getPizzaID() {
        return PizzaID;
    }
}
