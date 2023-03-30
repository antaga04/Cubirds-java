/*
 * Esta clase representa la mesa común a todos los jugadores. Funcionalidades a implementar:
 * - Constructor que cree la mesa HECHO
 * - Colocar la mesa inicial cumpliendo las restricciones HECHO
 * - Rellenar fila de la mesa cumpliendo las restricciones  (CUANDO HAY MENOS DE 3 CARTAS INSERTAR CARTA CON RESTRICCIONES) POR HACER
 * - Insertar cartas 
 * - Eliminar cartas rodeadas 
 * - Mostrar mesa HECHO
 */
package es.uvigo.esei.cubirds.core;

import es.uvigo.esei.cubirds.iu.ES;  //HAY QUE CAMBIAR LAS LISTAS DE JAVA A AEDI 1 JAR PORQUE NECESITAMOS INSERTAR PRINCIPIO E INSERTAR FINAL
import lista.*;
import pila.*;

public class Mesa {

    private final static int MAX_ROWS = 4;
    private ListaEnlazada<Carta>[] table;

    public Mesa() {
        table = new ListaEnlazada[MAX_ROWS];
        for (int i = 0; i < MAX_ROWS; i++) {
            table[i] = new ListaEnlazada<>();
        }
    }

    public ListaEnlazada<Carta>[] getTable() {
        return table;
    }

    public void colocarMesaInicial(Baraja b) { // HAY QUE COMPROBAR QUE LA CARTA NO SEA NULA ANTES DE LA LINEA 36
        for (int i = 0; i < MAX_ROWS; i++) {
            while (table[i].tamaño() < 3) {
                Carta x = b.darCarta();
                if (esta(x, table[i])) { // SI LA CARTA ESTÁ REPETIDA SE VUELVE A METER EN LA BARAJA 
                    b.meter(x);
                } else {
                    table[i].insertarPrincipio(x); // SI LA CARTA NO ESTÁ REPETIDA SE METE EN LA LISTA DE MESA
                }
            }
        }
    }

    public boolean esta(Carta c, ListaEnlazada<Carta> l) {
        for (Carta carta : l) {
            if (carta.getNombre().equals(c.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public boolean rellenarFila(Baraja b) { // si se ha rellenado la fila devuelve true y sino false ( no quedan cartas en la baraja)
        boolean rellenada = true; // rellenada es si se ha rellenado la fila o no con exito
        for (int i = 0; i < table.length; i++) {
            while ((b.getNumCartas() > 0) && (hayQueRellenar(i) != -1)) {
                table[i].insertarFinal(b.darCarta());
            }
        }

        if (b.getNumCartas() == 0) {
            rellenada = false;
        }

        return rellenada;
    }

    public int hayQueRellenar(int f) {
        String n = table[f].recuperar().getNombre();
        for (Carta c : table[f]) {
            if (n.equals(c.getNombre()) == false) { // si no son iguales
                return -1; // retornamos - 1
            }
        }
        return f; // sino retornamos la fila
    }

    public void insertarDerecha(int fila, Pila<Carta> p) { // HECHO RAUL
        while (p.tamaño() > 0) {
            Carta c = p.pop();
            table[fila].insertarFinal(c);
        }
    }

    public void insertarIzquierda(int fila, Pila<Carta> p) { // HECHO RAUL
        while (p.tamaño() > 0) {
            Carta c = p.pop();
            table[fila].insertarPrincipio(c);
        }
    }

    public Lista<Carta> eliminarCartasRodeadas2(int fila) { // devuelve una lista de cartas que quedan rodeadas por dos tipos iguales
        Lista<Carta> rodeadas = new ListaEnlazada<>();
        String tipo = table[fila].recuperar().getNombre();

        Carta ultima = null;
        for (Carta c : table[fila]) {
            ultima = c; // deja ultima en el ultimo elmento de la fila
        }

        if ((ultima != null) && (tipo.equals(ultima.getNombre()) == true)) {
            for (Carta c : table[fila]) {
                if (c.getNombre().equals(tipo) == false) {
                    rodeadas.insertarPrincipio(c);
                    table[fila].suprimir(c);
                }
            }
        }
        return rodeadas;
    }

    public Lista<Carta> eliminarCartasRodeadas(int fila, int lado) { // devuelve una lista de cartas que quedan rodeadas por dos tipos iguales
        Lista<Carta> rodeadas = new ListaEnlazada<>();

        Carta cartaIntroducida;
        if (lado == 1) {
            cartaIntroducida = table[fila].recuperar();
        } else {
            cartaIntroducida = null;
            for (Carta c : table[fila]) {
                cartaIntroducida = c; // deja ultima en el ultimo elmento de la fila
            }
        }

        boolean esta = false;
        boolean distinta = false;
        int cont = 0;
        for (Carta c : table[fila]) {
            cont++;
            if (c.getNombre() != cartaIntroducida.getNombre()) {
                distinta = true;
            }
            if (distinta && c.getNombre() == cartaIntroducida.getNombre()) {
                esta = true;
            }
        }

        if (lado == 1 && esta) {
            String primero = table[fila].recuperar().getNombre();
            boolean diferente = false;

            for (Carta c : table[fila]) {
                if (c.getNombre() != primero) {
                    diferente = true;
                }
                if (c.getNombre() == primero && diferente) {
                    break;
                }
                if (c.getNombre() != primero && diferente) {
                    rodeadas.insertarPrincipio(c);
                    table[fila].suprimir(c);
                }
            }
        } else if (lado == 2 && esta) {
            Carta ultima = null;
            for (Carta c : table[fila]) {
                ultima = c; // deja ultima en el ultimo elmento de la fila
            }
            boolean checkPoint = false;
            for (Carta c : table[fila]) {
                if (c.getNombre() == ultima.getNombre()) {
                    checkPoint = true;
                }
                if (c.getNombre() != ultima.getNombre() && checkPoint == true) {
                    rodeadas.insertarPrincipio(c);
                    table[fila].suprimir(c);
                }
            }
        }

        return rodeadas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMESA:");
        for (int i = 0; i < MAX_ROWS; i++) {
            sb.append("\n");
            for (Carta c : table[i]) {
                sb.append(c);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

}
