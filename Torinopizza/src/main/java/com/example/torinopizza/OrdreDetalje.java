package com.example.torinopizza;

public class OrdreDetalje {
    private int ordreDetaljeID;
    private int ordreID;
    private int pizzaID;
    private int antal;
    private double Subtotal;

    //CONSTRUCTOR
    public OrdreDetalje(int ordreDetaljeID, int ordreID, int pizzaID, int antal, double subtotal) {
        this.ordreDetaljeID = ordreDetaljeID;
        this.ordreID = ordreID;
        this.pizzaID = pizzaID;
        this.antal = antal;
        Subtotal = subtotal;
    }

    //GETTER
    public int getOrdreDetaljeID() {
        return ordreDetaljeID;
    }

    public int getOrdreID() {
        return ordreID;
    }

    public int getPizzaID() {
        return pizzaID;
    }

    public int getAntal() {
        return antal;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    //SETTER
    public void setOrdreDetaljeID(int ordreDetaljeID) {
        this.ordreDetaljeID = ordreDetaljeID;
    }

    public void setOrdreID(int ordreID) {
        this.ordreID = ordreID;
    }

    public void setPizzaID(int pizzaID) {
        this.pizzaID = pizzaID;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public void setSubtotal(double subtotal) {
        Subtotal = subtotal;
    }
}
