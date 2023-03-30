/*
 * Esta clase representa la baraja de juego, necesitará implementar las siguientes funcionalidades
 *  - Un constructor con las cartas de la baraja
 *  - Barajar las cartas
 *  - Devolver el número de cartas de la baraja
 *  - Coger una carta de la baraja
 *  - Insertar una carta en la baraja
 */
package es.uvigo.esei.cubirds.core;

import cola.Cola;
import cola.EnlazadaCola;

public class Baraja {

    private Cola<Carta> baraja;

    public Baraja() {
        baraja = new EnlazadaCola<>();
        for (int i = 0; i < 20; i++) {
            baraja.insertar(new Carta("Curruca de canha", 6, 9));
        }
        for (int i = 0; i < 7; i++) {
            baraja.insertar(new Carta("Flamenco", 2, 3));
        }
        for (int i = 0; i < 20; i++) {
            baraja.insertar(new Carta("Petirrojo", 6, 9));
        }
        for (int i = 0; i < 10; i++) {
            baraja.insertar(new Carta("Tucan", 3, 4));
        }
        for (int i = 0; i < 13; i++) {
            baraja.insertar(new Carta("Pato", 4, 6));
        }
        for (int i = 0; i < 17; i++) {
            baraja.insertar(new Carta("Urraca", 5, 7));
        }
        for (int i = 0; i < 10; i++) {
            baraja.insertar(new Carta("Lechuza", 3, 4));
        }
        for (int i = 0; i < 13; i++) {
            baraja.insertar(new Carta("Guacamayo", 4, 6));
        }
    }
    
    public int getNumCartas(){
        return baraja.tamaño();
    }
    
    public void meter(Carta c){
        baraja.insertar(c);
    }
    
    public Carta darCarta(){ 
        if (baraja.esVacio()) {
            System.err.println("No hay cartas en la baraja.");
            return null;
        }else{
            return baraja.suprimir();            
        }  
    }
    
    public void barajar(){
        int numCartas = getNumCartas();
        Carta cartas[] = new Carta[numCartas];
        int aleatorio;
        int dentro = 0;
        do{
            aleatorio = (int) (Math.random() * numCartas);
            if(cartas[aleatorio]==null){
                cartas[aleatorio] = baraja.suprimir();
                dentro++;
            }
        }while(dentro<numCartas);
        for(int i = 0;i<numCartas;i++){
            baraja.insertar(cartas[i]);
        }
    }
    
    public String toString(){
        int numCartas = getNumCartas();
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < numCartas ; i++){
            Carta c = baraja.primero();
            sb.append(c.toString());
            sb.append(" ");
            baraja.suprimir();
            baraja.insertar(c);
        }
        return sb.toString();
    }
}
