package ui;

import java.util.Scanner;
import model.Controller;
/**
 * Clase GameUI
 * permite al jugador entre partida estandar o personalizada
 * ingresa coordenadas para disparar y muestra los tableros 
 */
public class GameUI {

    /**
     * main
     * metodo principal
     * Muestra el muenu, permite la configuracion de la partidda, controla los turnos
     * y determuna  el ganador de la partida
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Controller controller = new Controller(10, 10);

        System.out.println("Bienvenido a Batalla Naval");

        while (true) {
            controller.reiniciarJuego();

            System.out.println("#1 Partida estándar");
            System.out.println("#2 Partida personalizada");
            System.out.print("Elige el tipo de partida: ");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                controller.agregarBarcosPorDefectoJugador();
                controller.agregarBarcosAleatoriosMaquina(5);
            } else {
                System.out.print("¿Cuántos barcos querés? ");
                int cantidad = sc.nextInt();
                controller.agregarBarcosAleatoriosMaquina(cantidad);
                for (int i = 0; i < cantidad; i++) {
                    System.out.println("Barco " + (i + 1));
                    System.out.print("Fila (1-10): ");
                    int fila = sc.nextInt() - 1;
                    System.out.print("Columna (1-10): ");
                    int col = sc.nextInt() - 1;
                    System.out.print("Tamaño del barco: ");
                    int tam = sc.nextInt();
                    System.out.print("¿Horizontal? (true/false): ");
                    boolean hor = sc.nextBoolean();

                    boolean ok = controller.agregarBarcoJugador(fila, col, tam, hor);
                    if (!ok) {
                        System.out.println("NO se pudo agregar ese barco, intenta de nuevo");
                        i--;
                    } else {
                        mostrarTablero("Tu Tablero", controller, true, true);
                    }
                }
            }

            mostrarTablero("Tu Tablero", controller, true, true);
            System.out.println("Muy bien, AHORA VAMOS A JUGAR!!!");

            while (!controller.todosHundidosJugador() && !controller.todosHundidosMaquina()) {
                int f, c;
                while (true) {
                    System.out.print("Dime la coordenada X a atacar en el tablero rival (1-10):\n> ");
                    c = sc.nextInt() - 1;
                    System.out.print("Dime la coordenada Y a atacar en el tablero rival (1-10):\n> ");
                    f = sc.nextInt() - 1;

                    if (f < 0 || f >= 10 || c < 0 || c >= 10) {
                        System.out.println("Coordenadas inválidas, intenta de nuevo");
                    } else if (controller.yaFueDisparado(f, c)) {
                        System.out.println("Ya disparaste ahí, elegí otra coordenada.");
                    } else {
                        break;
                    }
                }

                System.out.println("Atacarás el punto exacto de fila " + (f + 1) + ", columna " + (c + 1) + ".");
                String resultado = controller.dispararAMaquina(f, c);
                System.out.println(resultado);
                mostrarTablero("Tablero Enemigo", controller, false, false);

                if (controller.todosHundidosMaquina()) {
                    System.out.println("GANASTEEEE :D todos los barcos enemigos están hundidos");
                    controller.sumarVictoriaJugador();
                    break;
                }

                System.out.println("Turno de la máquina...");
                String resultadoMaquina = controller.disparoMaquinaAJugador();
                System.out.println(resultadoMaquina);
                mostrarTablero("Tu Tablero", controller, true, true);

                if (controller.todosHundidosJugador()) {
                    System.out.println("Perdiste :( todos tus barcos fueron hundidos");
                    controller.sumarVictoriaMaquina();
                    break;
                }
            }

            System.out.println("PUNTUACION ACTUAL:");
            System.out.println("Jugador: " + controller.getVictoriasJugador());
            System.out.println("Máquina: " + controller.getVictoriasMaquina());

            System.out.print("¿Querés jugar otra vez? (si/no): ");
            String respuesta = sc.next();
            if (!respuesta.equalsIgnoreCase("si")) {
                System.out.println("Gracias por jugar ¡Hasta la próxima!");
                break;
            }
        }

        sc.close();
    }

    /**
     * mostrarTablero
     * muestra el tablero del juego en consola
     * @param titulo titulo del tablero
     * @param controller controlador del juego que tiene la logica
     * @param mostrarBarcos si es true, muestra las posiciones de los barcos
     * @param esJugador si es true, muestra el tablero del jugador, si es false muestra el tablero de la maquina
     */
    public static void mostrarTablero(String titulo, Controller controller, boolean mostrarBarcos, boolean esJugador) {
        System.out.println("\n" + titulo + ":");
        System.out.print("    "); 
        for (int col = 1; col <= controller.getColumnas(); col++) {
            if (col < 10) {
                System.out.print(" " + col + " ");
            } else {
                System.out.print(col + " ");
            } }
        System.out.println();
        for (int i = 0; i < controller.getFilas(); i++) {
            if (i + 1 < 10) {
                System.out.print(" " + (i + 1) + "  ");
            } else {
                System.out.print((i + 1) + "  ");
            }
            for (int j = 0; j < controller.getColumnas(); j++) {
                int valor;
    
                if (esJugador) {
                    valor = controller.getValorCeldaJugador(i, j);
                } else {
                    valor = controller.getValorCeldaMaquina(i, j);}
                String mostrar;
                if (!mostrarBarcos && valor == 1) {
                    mostrar = "0";
                } else {
                    mostrar = String.valueOf(valor);
                }
    
                System.out.print(" " + mostrar + " ");
            }
            System.out.println();
        }
    }
    
    
    
    
    
}
