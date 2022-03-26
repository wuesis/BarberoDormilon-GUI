package com.company;


public class Clientes implements Runnable {
    int idCliente;

    Barberia barberia;

    public Clientes(Barberia barberia ) {
        this.barberia = barberia;
    }

    @Override
    public void run() {
        irACortarCabello();
    }

    private synchronized void irACortarCabello() {
        barberia.agregar(this);
    }
}