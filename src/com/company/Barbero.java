package com.company;


import java.awt.*;

public class Barbero extends Thread {
    Barberia barberia;


    public Barbero(Barberia barberia) {
        this.barberia = barberia;
    }


    @Override
    public void run() {
        while (true) {
            barberia.cortarCabello();
        }
    }
}