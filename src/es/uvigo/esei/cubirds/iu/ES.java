/*
 * Clase de Entrada de datos
 */
package es.uvigo.esei.cubirds.iu;

import java.util.Scanner;


public class ES {

    public static Scanner leer = new Scanner(System.in, "ISO-8859-2");

    public static String pideCadena(String mensaje) {
        // Poner el mensaje
        System.out.println(mensaje);

        // Pedir
        return leer.nextLine();
    }
    
    public static int pideNumero(String msg) {
        boolean esValido = false;
        int toret = 0;
        Scanner teclado = new Scanner(System.in);

        do {
            System.out.print(msg);

            try {
                toret = Integer.parseInt(teclado.nextLine());
                esValido = true;
            } catch (NumberFormatException exc) {
                System.err.println("La cadena introducida no se puede "
                        + "convertir a número entero. Por favor, "
                        + "introdúcela de nuevo.");
            }
        } while (!esValido);

        return toret;
    }
    
}
