package com.company;

import java.util.LinkedList;
import java.util.List;

public class Barberia {

    public static int sillas = 5;
    private boolean ocupado;

    public static List<Clientes> listaClientes = new LinkedList<Clientes>();

    public Barberia() {
        this.ocupado = false;
    }

    public void cortarCabello() {
        Clientes cliente;
        try {
            synchronized (listaClientes) {
                while (listaClientes.size() == 0) {

                    System.out.println("\nEl barbero esta en espera de un cliente... se encuentra dormido");

                    try {
                        listaClientes.wait();
                    } catch (InterruptedException error) {
                        error.printStackTrace();
                    }
                }

                cliente = (Clientes) ((LinkedList<?>) listaClientes).poll();
//                System.out.println("El barbero se ha despertado por que ha entrado un cliente.");
                GUI.popClient(GUI.clientsPanelGrid);
            }

            ocupado = true;
            System.out.println("El babero se encuentra cortando el cabello.");
            GUI.setBarber(GUI.chairPanelGrid, false);
            GUI.clientes.pop();
            Thread.sleep(6000);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Termino el corte de cabello");
        if (listaClientes.size() == 0) {
            System.out.println("El barbero se ha dormido.");
            GUI.setBarber(GUI.chairPanelGrid, true);
        }
        ocupado = false;
    }

    public void agregar(Clientes cliente) {

        System.out.println("Ha entrado un cliente nuevo");

        synchronized (listaClientes) {
            if (listaClientes.size() >= sillas) {
                System.out.println("No hay sillas disponibles... El cliente se ha ido.");
                return;
            } else if (!ocupado) {
                ((LinkedList<Clientes>) listaClientes).offer(cliente);
                listaClientes.notify();
            } else {
                ((LinkedList<Clientes>) listaClientes).offer(cliente);
                System.out.println("El barebero se encuentra ocupado... El cliente espera en una silla");
                if (listaClientes.size() == 1) listaClientes.notify();
            }
        }
    }
}