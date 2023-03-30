/*
 * Esta clase representa a cada jugador. Tendrá las siguientes funcionalidades
 * - Un constructor para crear el jugador // HECHO RAUL
 * - Añadir y eliminar cartas de la mano  // HECHO RAUL
 * - Colocar cartas en la mesa // HECHO RAUL
 * - Colocar cartas en su zona de juego // HECHO RAUL
 * - Número de cartas en la mano // HECHO RAUL
 * - Número de cartas en la zona de juego // HECHO RAUL
 * - Número de especies distintas en la zona de juego // HECHO RAUL
 * - Mostrar mano, zona de juego del jugador // HECHO RAUL
 */
package es.uvigo.esei.cubirds.core;

import es.uvigo.esei.cubirds.iu.ES;
import pila.*;
import lista.*;

public class Jugador {

    private String nombrejugador;
    private ZonaJuego zonajuego;
    private Mano mano;

    public Jugador(Baraja b, String nombrejugador) { // CREA UN JUGADOR VACÍO 
        this.nombrejugador = nombrejugador;
        this.zonajuego = new ZonaJuego(b); //el constructor directamente mete una carta en la zona de juego
        this.mano = new Mano(b); // inicializamos las mano y la rellenamos con las cartas de la baraja automaticamente
    }

    public ZonaJuego getZonajuego() {
        return zonajuego;
    }

    public Mano getMano() {
        return mano;
    }

    public void setMano(Mano mano) {
        this.mano = mano;
    }

    public String getNombrejugador() {
        return nombrejugador;
    }

    public void colocarCartasMesa(Mesa m, String opcionCarta, int opcionFila, int opcionLado) {
        // el usuario tiene que escribir el tipo de carta que quiere jugar y se gestionara en juego si es a la derecha o a la izquierda
        Pila<Carta> seleccionada;

        seleccionada = mano.devolverPilaSeleccionada(opcionCarta);
        if (opcionLado == 2) { // inserta a la derecha
            m.insertarDerecha(opcionFila, seleccionada);
        } else { // inserta a la izquierda
            m.insertarIzquierda(opcionFila, seleccionada);
        }

        mano.eliminarPila(seleccionada);
        //mano.anhadirCartasRodeadas(opcionFila, m); // te introduce en la mano las cartas de la mesa que dejes rodeadas
    }

    /**
     * el metodo añade a la mano las cartas rodeadas de mesa devolviendo true si
     * ha añadido y falso de lo contrario
     *
     * @param opcionFila
     * @param m
     * @return
     */
    public boolean anhadirRodeadasMano(int opcionFila, int opcionLado, Mesa m) {
        return mano.anhadirCartasRodeadas(opcionFila, opcionLado, m);
    }

    public String pedirCarta() {
        String opcionCarta;
        Lista<String> tiposPermitidos = mano.tiposMano();
        do {
            opcionCarta = ES.pideCadena("¿Qué carta(s) quiere jugar?: ");
        } while (tiposPermitidos.contiene(opcionCarta) == false);

        return opcionCarta;
    }

    public int pedirFila() {
        int opcionFila;
        do {
            opcionFila = ES.pideNumero("¿En que fila la quieres colocar? (1-4): ");
        } while ((opcionFila != 1) && (opcionFila != 2) && (opcionFila != 3) && (opcionFila != 4));

        return opcionFila - 1;
    }

    public int pedirLado() {
        int opcionLado;
        do {
            opcionLado = ES.pideNumero("¿Dónde las quiere colocar? (1.IZQUIERDA / 2.DERECHA): ");
        } while ((opcionLado != 1) && (opcionLado != 2));
        return opcionLado;
    }

    public void colocarCartasZonaJuego(MontonDescartes mont) {
        // con el metodo hay bandada minima y eliminar se tiene que enviar una carta a zona de juego y el resto a monton de descartes
        int opcion; // opcion guarda 1 o 2 segun el user quiera bajar la bandada o no
        Pila<Carta> pilaBandada = mano.hayBandadaPequena(); // pilaBandada guarda la pila que devuelve el metodo

        if (pilaBandada != null) { // el metodo devuelve null si no hay bandada pequeña
            do {
                opcion = ES.pideNumero("\n¿Quiere bajar la bandada " + pilaBandada.top().getNombre() + " a la zona de juego? (1.SI / 2.NO): ");
            } while ((opcion != 1) && (opcion != 2));

            if (opcion == 1) { // si el usuario decide bajar la bandada
                zonajuego.añadirCartaZonaJuego(pilaBandada.pop()); // se añade una carta de ese tipo a la zona de juego

                while (pilaBandada.tamaño() > 0) { // mientres la pila no se vacie del todo
                    mont.añadirCarta(mano.eliminarCarta(pilaBandada)); // el resto de cartas van al monton de descartes
                }
                mano.eliminarPila(pilaBandada); // elimina la pila de la lista
            }
        }
    }

    public void añadirCartasMano(Baraja b) {
        mano.añadirCarta(b);
    }

    public boolean robarDosCartas(Baraja b) {
        String opcion;

        do {
            opcion = ES.pideCadena("\n¿Quieres robar dos cartas de la baraja? (Si/No)");
        } while (!opcion.equalsIgnoreCase("SI") && !opcion.equalsIgnoreCase("NO"));

        if (opcion.equalsIgnoreCase("SI")) {
            if (b.getNumCartas() >= 2) {
                mano.añadirCarta(b);
                mano.añadirCarta(b);
                return true;
            }
        } else if (opcion.equalsIgnoreCase("NO")) {
            if (b.getNumCartas() >= 2) {
                return true;
            }
        }
        return false;
    }

    public int numeroCartasMano() { // devuelve cuantas cartas hay en la mano;
        return mano.numCartasMano();
    }

    public int numeroCartasZonaJuego() { // devuelve cuantas cartas hay en la zona de juego
        return zonajuego.numCartasZonaJuego();
    }

    public int diferentesEspeciesZonaJuego() { // devuelve el numero de especies diferentes en la zona de juego
        return zonajuego.numEspecies();
    }

    public String mostrarZonaJuego() {
        return "\nZona de Juego " + nombrejugador + ": " + "\n" + zonajuego.toStringZonaJuego();
    }

    public String mostrarMano() {
        return "\nMano " + nombrejugador + ": " + "\n" + mano.toStringMano();
    }

    public boolean darMano(Baraja b) {
        boolean creada = false;
        if (b.getNumCartas() >= 8) {
            System.out.println("\n...Repartiendo 8 cartas nuevas...");
            this.mano = new Mano(b);
            creada = true;
        }
        return creada;
    }

    /*
    * Esta clase representa la zona de juego de un jugador. Tendrá las siguientes funcionalidades
    * - Un constructor para crear la zona de juego // HECHO RAUL
    * - añadir cartas // HECHO RAUL
    * - Número de cartas // HECHO RAUL
    * - Número de especies distintas // HECHO RAUL
    * - mostrar zona de juego // HECHO RAUL
     */
    private class ZonaJuego {

        private Lista<Pila<Carta>> zonajuego; // la zona de juego tiene que ser una lista de pilas

        public ZonaJuego(Baraja b) {
            zonajuego = new ListaEnlazada<Pila<Carta>>();
            Pila<Carta> inicio = new EnlazadaPila<>();
            inicio.push(b.darCarta());
            zonajuego.insertarPrincipio(inicio);
        }

        public int numCartasZonaJuego() { // devuelve el numero de cartas que hay en la zona de juego
            int cont = 0; // cont devuelve el numero de cartas
            for (Pila<Carta> p : zonajuego) { // para cada pila de cartas perteneciente a zonajuego
                cont = cont + p.tamaño(); // al contador se le suma el tamaño de cada pila (numero de elemento de la pila)
            }
            return cont;
        }

        public void añadirCartaZonaJuego(Carta c) {
            boolean anhadida = false;
            for (Pila<Carta> p : zonajuego) {
                Carta comparada = p.top();
                if (c.getNombre().equals(comparada.getNombre())) { // si la carta es del mismo tipo de la pila
                    p.push(c);
                    anhadida = true;
                }
            }
            if (anhadida == false) {
                Pila<Carta> nueva = new EnlazadaPila<>();
                nueva.push(c);
                zonajuego.insertarFinal(nueva);
            }
        }

        public int numEspecies() {
            return zonajuego.tamaño(); // el numero de pilas que haya en la zona de juego es el numero de especies diferentes
        }

        public String toStringZonaJuego() {
            StringBuilder sb = new StringBuilder();

            for (Pila<Carta> p : zonajuego) {
                for (int i = 0; i < p.tamaño(); i++) {
                    sb.append(p.top().getNombre()).append(" ");
                }
            }
            return sb.toString();
        }
    }

    /*
    * Esta clase representa la mano de un jugador. Tendrá las siguientes funcionalidades
    * - Un constructor para crear la mano // HECHO RAUL
    * - añadir cartas // HECHO RAUL
    * - eliminar cartas // HECHO RAUL + eliminar pila de la lista HECHO RAUL
    * - número de cartas // HECHO RAUL
    * - comprobar si hay cartas suficientes para bandada pequeña // HECHO RAUL
    * - mostrar mano // HECHO RAUL
     */
    private class Mano {

        public static final int MAXCARTAS_MANO = 8;
        private Lista<Pila<Carta>> mano;
        int cartasIntroducidas = 0; // aqui se contabilizan las cartas que hay dentro de la mano

        /**
         * Crea una mano con 8 cartas ordenadas en pilas por tipo
         *
         * @param b
         */
        public Mano(Baraja b) {
            mano = new ListaEnlazada<Pila<Carta>>(); // mano representa la lista de pilas
            do {
                if (mano.esVacio() == true) { // si la mano está vacia crea una pila nueva y le inserta el primer tipo
                    Pila<Carta> primerTipo = new EnlazadaPila<>();
                    Carta c = b.darCarta();
                    primerTipo.push(c);
                    cartasIntroducidas++; // se incrementa el numero de cartas introducidas en una
                    mano.insertarPrincipio(primerTipo); // se añade la pila de cartas a la mano
                } else { // si la mano ya tiene algun elemento deberá introducir un elemento si es del mismo tipo o crear una pila si es diferente
                    añadirCarta(b);
                }
            } while (cartasIntroducidas < MAXCARTAS_MANO); // necesitamos exactamente 8
        }

        /**
         * Devuelve el numero de pilas de mano
         *
         * @return
         */
        public int tamaño() { // devuelve el numero de pilas que hay en la mano
            return mano.tamaño();
        }

        /**
         * Añade una carta de la baraja a la mano insertándola en la pila
         * correspondiente
         *
         * @param b
         */
        public void añadirCarta(Baraja b) { // desde una baraja 
            Carta k = b.darCarta();
            boolean anhadida = false;
            for (Pila<Carta> p : mano) { // para cada pila de cartas de mano
                Carta comparada = p.top(); // comparada es la carta de arriba de cada una de las pilas
                if (k.getNombre().equals(comparada.getNombre())) { // si la carta es del mismo tipo de la pila
                    p.push(k);
                    anhadida = true;
                    cartasIntroducidas++;
                }
            }
            if (anhadida == false) { // si la carta no es de un tipo que ya esté introducido
                Pila<Carta> nueva = new EnlazadaPila<>(); // se crea una nueva pila
                nueva.push(k); // se introduce la carta en la pila
                mano.insertarPrincipio(nueva); // se introduce la pila en la mano
                cartasIntroducidas++; // se incrementa el numero de cartas
            }
        }

        /**
         * Recibes las cartas rodeadas de mesa y las introduces ordenadas en la
         * pila correspondiente de mano
         *
         * @param fila
         * @param m
         */
        public boolean anhadirCartasRodeadas(int fila, int lado, Mesa m) {
            boolean hayRodeadas = false;
            for (Carta c : m.eliminarCartasRodeadas(fila, lado)) {
                boolean anhadida = false;
                for (Pila<Carta> p : mano) {

                    if (c.getNombre().equals(p.top().getNombre())) {
                        p.push(c);
                        anhadida = true;
                        hayRodeadas = true;
                        cartasIntroducidas++;
                    }
                }
                if (anhadida == false) {
                    Pila<Carta> nueva = new EnlazadaPila<>();
                    nueva.push(c);
                    hayRodeadas = true;
                    mano.insertarFinal(nueva);
                    cartasIntroducidas++;
                }
            }
            return hayRodeadas;
        }

        /**
         * Elimina una carta de una pila que se pasa como parametro y se usa
         * para vaciar pilas
         *
         * @param p
         * @return
         */
        public Carta eliminarCarta(Pila<Carta> p) { // lo que hace este metodo es eliminar una carta de una pila de cartas 
            cartasIntroducidas--;
            Carta c = p.pop();
            return c;

        }

        /**
         * Elimina una pila determinada de la mano
         *
         * @param p
         * @return
         */
        public boolean eliminarPila(Pila<Carta> p) { // se elimina una pila de la lista
            boolean eliminada = false;
            if (mano.contiene(p) && (p.esVacio() == true)) {
                eliminada = mano.suprimir(p);
            }
            return eliminada;
        }

        /**
         * Devuelve el numero de cartas que hay en la mano del jugador
         *
         * @return
         */
        public int numCartasMano() { // devuelve el numero de cartas que hay en la mano
            int cont = 0; // cont devuelve el numero de cartas
            for (Pila<Carta> p : mano) { // para cada pila de cartas
                cont = cont + p.tamaño(); // se suma el tamaño de cada pila al contador
            }
            return cont;
        }

        /**
         * Si hay un numero de cartas de un tipo mayor o igual al NumBddP
         * devuelve la pila de estos elementos
         *
         * @return
         */
        public Pila<Carta> hayBandadaPequena() { //Comprueba si hay una bandada pequeña en la mano
            for (Pila<Carta> p : mano) { // comprueba todas las pilas de la mano
                if (p.tamaño() >= p.top().getNumBddP()) { // si el tamaño de la pila coincide con el numero pequeño devuelve la pila
                    return p;
                }
            }
            return null; // si no devuelve null
        }

        /**
         * Se busca la pila del tipo de pajaros s y la retorna, restando del
         * numero de cartas de la mano el tamaño de la pila
         *
         * @param s
         * @return
         */
        public Pila<Carta> devolverPilaSeleccionada(String s) { // devuelve una pila de la mano que coincida con el tipo indicada por string
            Pila<Carta> seleccionada = new EnlazadaPila<>();
            for (Pila<Carta> p : mano) {
                if (p.top().getNombre().equals(s)) {
                    seleccionada = p;
                }
            }
            cartasIntroducidas = cartasIntroducidas - seleccionada.tamaño(); // siempre que se llame a este metodo es para introducir cartas en la mesa, por lo tanto se eliminan de la mano
            return seleccionada;
        }

        /**
         * Devuelve una lista con los tipos que hay en la mano
         *
         * @return
         */
        public Lista<String> tiposMano() { // este metodo devuelve una lista con los tipos que tiene la mano (para moverlo a mesa)
            Lista<String> tipos = new ListaEnlazada<>();
            for (Pila<Carta> p : mano) {
                tipos.insertarPrincipio(p.top().getNombre());
            }
            return tipos;
        }

        public String toStringMano() { // toString puede estar mal
            StringBuilder sb = new StringBuilder();
            int cont = 0;
            for (Pila<Carta> p : mano) {
                for (int i = 0; i < p.tamaño(); i++) {
                    if (cont == 4) {
                        sb.append("\n");
                        cont = 0;
                    }
                    sb.append(p.top().toString()).append(" ");
                    cont++;
                }
            }
            return sb.toString();
        }
    }
}
