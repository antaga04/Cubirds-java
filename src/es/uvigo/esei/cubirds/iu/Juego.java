/*
 * Esta clase representa el funcionamiento general del juego
 */
package es.uvigo.esei.cubirds.iu;

import es.uvigo.esei.cubirds.core.Baraja;
import es.uvigo.esei.cubirds.core.Carta;
import es.uvigo.esei.cubirds.core.Jugador;
import es.uvigo.esei.cubirds.core.Jugador.*;
import es.uvigo.esei.cubirds.core.Mesa;
import es.uvigo.esei.cubirds.core.MontonDescartes;
import static es.uvigo.esei.cubirds.iu.ES.*;
import java.util.LinkedList;
import java.util.List;
import lista.Lista;

public class Juego {

    public static void inicio() {

        //INICIO DEL JUEGO
        System.out.println("##############CUBIRDS##############");

        //Se crea la baraja y se baraja
        Baraja b = new Baraja();
        b.barajar();

        //Se crea el montón de descartes
        MontonDescartes mB = new MontonDescartes();

        //Se crean los jugadores con cartas repartidas
        int numJugadores = 0;
        while (numJugadores < 2 || numJugadores > 5) {
            numJugadores = pideNumero("Elige el numero de jugadores: ");
        }

        Jugador[] jugadores = new Jugador[numJugadores];
        for (int i = 0; i < numJugadores; i++) {
            String name = pideCadena("Elige el nombre del jugador/a " + (i + 1) + ": ");
            Jugador player = new Jugador(b, name);
            jugadores[i] = player;
        }

        System.out.println("------------------------------------");
        System.out.println("EMPIEZA EL JUEGO");
        System.out.println("------------------------------------");

        //Se crea la mesa con las cartas iniciales
        Mesa m = new Mesa();
        m.colocarMesaInicial(b);

        // Empieza el juego
        int turno = 0;
        boolean fin = false;
        while (!fin) {
            mostrarTurno(m, jugadores, turno);

            String tipo = jugadores[turno].pedirCarta();
            int fila = jugadores[turno].pedirFila();
            int lado = jugadores[turno].pedirLado();

            jugadores[turno].colocarCartasMesa(m, tipo, fila, lado);
            boolean rodeadas = jugadores[turno].anhadirRodeadasMano(fila, lado, m);

            if (!m.rellenarFila(b)) {
                fin = true;
                comprobarFinal(b, jugadores);
                break;
            } else {
                m.rellenarFila(b);
            }

            System.out.println("------------------------------------");
            System.out.println("\n-ESTADO DE LA PARTIDA-");
            System.out.println(m);

            //Se comprueba si el jugador se ha quedado sin cartas
            boolean dadas;
            if (jugadores[turno].numeroCartasMano() == 0 && !jugadores[turno].darMano(b)) {
                fin = true;
                comprobarFinal(b, jugadores);
                break;
            }
            //Opcion de robar dos cartas
            if (!rodeadas) {
                if (!jugadores[turno].robarDosCartas(b)) {
                    fin = true;
                    comprobarFinal(b, jugadores);
                    break;
                }
            }
            System.out.println(jugadores[turno].mostrarMano());
            //Se pregunta si quiere colocar cartas en la zona de juego           
            bajarCartas(jugadores, turno, mB);

            if (continuar()) {
                if (turno == (numJugadores-1)) {
                    turno = -1;
                }
                System.out.println("\n-TURNO DE " + jugadores[turno+1].getNombrejugador() + "-");
                turno++;
            } else {
                fin = true;
                break;
            }
        }
        //Comprobar si hay un ganador
        if (jugadores[turno].diferentesEspeciesZonaJuego() == 7) {
            System.out.println("##############GANADOR##############\n"
                    + jugadores[turno].getNombrejugador()
                    + "\n##############GANADOR##############");
        }
    }

    public static void comprobarFinal(Baraja b, Jugador[] jugadores) {
        int maxEspecies = 0;
        Jugador ganador = null;
        for (Jugador player : jugadores) {
            if (player.diferentesEspeciesZonaJuego() > maxEspecies) {
                maxEspecies = player.diferentesEspeciesZonaJuego();
                ganador = player;
            }
        }
        System.out.println(ganador.getNombrejugador());
    }

    public static boolean continuar() {
        String msg = pideCadena("Pulsa una tecla para pasar al siguiente turno");
        if (msg.equalsIgnoreCase("killGame")) {
            return false;
        } else {
            return true;
        }
    }

    public static void mostrarTurno(Mesa m, Jugador[] jugadores, int turno) {
        System.out.println(m);
        System.out.println(jugadores[turno].mostrarMano());
        System.out.println(jugadores[turno].mostrarZonaJuego());
        System.out.println("------------------------------------");
    }

    public static void bajarCartas(Jugador[] jugadores, int turno, MontonDescartes mB) {
        jugadores[turno].colocarCartasZonaJuego(mB);
        System.out.println(jugadores[turno].mostrarZonaJuego());
        System.out.println("------------------------------------");
    }

}
