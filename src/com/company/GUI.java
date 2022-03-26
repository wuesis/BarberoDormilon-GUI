package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class GUI extends JFrame {

    public static final Icon ICON_CLIENTWAIT = new ImageIcon("src/Imagenes/Esperando.png");
    public static final Icon ICON_SLEEP = new ImageIcon("src/Imagenes/Dormido.gif");
    public static final Icon ICON_MURLOC = new ImageIcon("src/Imagenes/Murloc.gif");
    public static final Icon ICON_BARBER = new ImageIcon("src/Imagenes/Barbero.png");
    public static final Icon ICON_SILLA = new ImageIcon("src/Imagenes/sillaEspera.png");


    static LinkedList<JLabel> jClient = new LinkedList<JLabel>();
    static JLabel jbarber = new JLabel();
    static LinkedList<JButton> buttonsControl = new LinkedList<JButton>();
    static LinkedList<Thread> clientes = new LinkedList<Thread>();

    static int contadorClientes = 0, contadorSillas = 0, contadorSillasTotales = 0;

    private static Barberia barberia = new Barberia();

    public static Barbero barbero;


    public static JPanel clientsPanelGrid = new JPanel();
    public static JPanel chairPanelGrid = new JPanel();
    public static JPanel exitPanelGrid = new JPanel();

    public GUI() {

        super("Productor consumidor");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720);
        setBounds(600, 450, 1080, 720);
        setVisible(true);
        setLayout(new BorderLayout());

        //Button panel
        JPanel buttonsPanel = new JPanel();
        JPanel centerButtonPanel = new JPanel();

        //Inventory panel
        JPanel clientsPanel = new JPanel();
        //Product panel
        JPanel chairPanel = new JPanel();
        //Consumidor panel
        JPanel exitPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());

        clientsPanelGrid.setLayout(new GridLayout(5, 2, 5, 5));
        chairPanelGrid.setLayout(new GridLayout(1, 1, 5, 5));
        exitPanelGrid.setLayout(new GridLayout(5, 2, 5, 5));


        ponerBoton(centerButtonPanel, "++Cliente", new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                pushClient(clientsPanelGrid);
                Clientes cliente = new Clientes(barberia);
                Thread hiloCliente = new Thread(cliente);
                hiloCliente.start();
                clientes.add(hiloCliente);
                contadorSillasTotales++;
                repaint();

            }
        });

        ponerBoton(centerButtonPanel, "++Silla", new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                pushITEM(jClient, clientsPanelGrid, ICON_SILLA);
                contadorSillas++;
                Barberia.sillas++;
                buttonsControl.get(2).setEnabled(true);
                repaint();
            }
        });


        ponerBoton(centerButtonPanel, "--Silla", new ActionListener() {
            public void actionPerformed(ActionEvent evento) {

                if (clientes.size() > contadorSillas - 1) {
                    System.out.println("no puedes quitar sillas ocupadas " + clientes.size());
                    return;
                }


                popItem(jClient, clientsPanelGrid);
                Barberia.sillas--;
                contadorSillas--;
                if (contadorSillas == 0)
                    buttonsControl.get(2).setEnabled(false);
                repaint();

            }
        });

        ponerBoton(centerButtonPanel, "Abrir barberia", new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                buttonsControl.get(3).setVisible(false);
                buttonsControl.get(3).setEnabled(false);
                barbero = new Barbero(barberia);
                barbero.start();
                jbarber.setIcon(ICON_SLEEP);
                chairPanelGrid.add(jbarber);
                buttonsControl.get(0).setEnabled(true);
                buttonsControl.get(1).setEnabled(true);
                buttonsControl.get(2).setEnabled(true);
                repaint();
            }
        });


        for (int i = 0; i < barberia.sillas; i++) {
            pushITEM(jClient, clientsPanelGrid, ICON_SILLA);
            contadorSillas++;
            contadorSillasTotales++;
        }

        jbarber.setIcon(ICON_SLEEP);
        chairPanelGrid.add(jbarber);


        //Set all the button panels
        buttonsPanel.add(centerButtonPanel, BorderLayout.CENTER);


        //Add all the grids
        clientsPanel.add(clientsPanelGrid);
        chairPanel.add(chairPanelGrid);
        exitPanel.add(exitPanelGrid);

        //Add all the main panels
        add(buttonsPanel, BorderLayout.NORTH);
        add(clientsPanel, BorderLayout.WEST);
        add(chairPanel, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.EAST);
        buttonsPanel.getAlignmentY();
        chairPanelGrid.getAlignmentY();


    }


    public void ponerBoton(Container container, String name, ActionListener listener) {
        JButton boton = new JButton(name);
        buttonsControl.add(boton);
        container.add(boton);
        boton.addActionListener(listener);
    }

    public static void pushITEM(LinkedList<JLabel> type, Container container, Icon icon) {
        JLabel label = new JLabel();
        type.add(label);
        label.setIcon(icon);
        container.add(label);
        container.revalidate();
    }

    public void popItem(LinkedList<JLabel> type, Container container) {
        JLabel targetLabel = type.get(type.size() - 1);
        container.remove(targetLabel);
        type.remove(targetLabel);
    }

    public static void pushClient(Container container) {
        for (var item : jClient) {
            if (item.getIcon() == ICON_SILLA) {
                item.setIcon(ICON_CLIENTWAIT);
                break;
            }
        }
        container.revalidate();
    }

    public static void popClient(Container container) {

        for (int i = jClient.size() - 1; i >= 0; i--) {


            if (jClient.get(i).getIcon() == ICON_CLIENTWAIT) {
                jClient.get(i).setIcon(ICON_SILLA);
                break;
            }
            container.revalidate();
        }
    }

        public static void setBarber (Container container,boolean pc){
            if (pc)
                jbarber.setIcon(ICON_SLEEP);
            else
                jbarber.setIcon(ICON_BARBER);

            container.revalidate();
        }


    }
