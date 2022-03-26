package com.company;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Silla {
    private boolean ocupado;
    private int idProducto = 0;


    public Silla(Component component) {
        this.idProducto++;
        this.ocupado = false;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    @Override
    public String toString() {
        return "" + idProducto;
    }
}