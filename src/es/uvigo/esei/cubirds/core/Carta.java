/*
 * Esta clase representa cada carta del juego, con todos sus atributos, constructor y métodos observadores
 */

package es.uvigo.esei.cubirds.core;


public class Carta {
    private String nombre; // TIPO DE CARTA
    private int numBddP,numBddG; //NUM BANDADA GRANDE Y NUM BANDADA PEQUEÑO
    

    public Carta(String nombre, int numBddP, int numBddG) {
        this.nombre = nombre;
        this.numBddP = numBddP;
        this.numBddG = numBddG;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumBddP() {
        return numBddP;
    }

    public int getNumBddG() {
        return numBddG;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(nombre)
                .append(numBddP)
                .append("/").append(numBddG)
                .append("]");
        return sb.toString();
    }
}
