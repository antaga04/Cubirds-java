/*
 * Esta clase representa el montón de descartes. Funcionalidades a implementar:
 * - Constructor para crear un montón
 * - añadir carta
 */
package es.uvigo.esei.cubirds.core;

import pila.Pila;
import pila.EnlazadaPila;

public class MontonDescartes {

    private Pila<Carta> descartes;

    public MontonDescartes() {
        descartes = new EnlazadaPila<>();
    }

    public void añadirCarta(Carta c) {
        descartes.push(c);

    }
}
