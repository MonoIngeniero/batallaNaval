package model;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clase Controller 
 * Controla la logica del juego
 * Se encarga de los tableros, los barcos, los disparos y
 * verificar las condiciones para ganar
 */
public class Controller {
   
    private Board tableroJugador;
    private Board tableroMaquina;

    private ArrayList<Ship> barcosJugador;
    private ArrayList<Ship> barcosMaquina;

    private HashSet<Coordenada> disparosHechosPorMaquina;
    private int victoriasJugador = 0;
    private int victoriasMaquina = 0;


    /**
     * Controller
     * Se crea un controlador para el juego con tableros de tamaño dado
     * @param filas numero de filas del tablero(10)
     * @param columnas numero de columnas del tablero(10)
     */

    public Controller(int filas, int columnas) {
        tableroJugador = new Board(filas, columnas);
        tableroMaquina = new Board(filas, columnas);
        barcosJugador = new ArrayList<>();
        barcosMaquina = new ArrayList<>();
        disparosHechosPorMaquina = new HashSet<>();
    }


    /**
     *disparoMaquinaAJugador
     *Asegura de no repetir disparos anteriores
     * @return resultado del disparo(0,2,3)
     */ 
    public String  disparoMaquinaAJugador() {
        Coordenada coord;
        do {
            int fila = (int) (Math.random() * tableroJugador.getFilas());
            int columna = (int) (Math.random() * tableroJugador.getColumnas());
            coord = new Coordenada(fila, columna);
        } while (disparosHechosPorMaquina.contains(coord));

        disparosHechosPorMaquina.add(coord);
        return dispararAJugador(coord.getFila(), coord.getColumna());
    
    }

    /**
     * dispararAJugador
     * Realiza un disparo de la maquina hacia el jugador
     * @param fila fila a la que se va  atacar
     * @param columna columna a la que se va a atacar
     * @return resultado del disparo
     */
    public String dispararAJugador(int fila, int columna) {
        return disparar(tableroJugador, barcosJugador, fila, columna);
    }

    /**
     * dispararAMaquina
     * Realiza un disparo del jugador hacia la maquina
     * @param fila fila a la que se va a atacar
     * @param columna columna a la que se va a atacar
     * @return resultado del disparo
     */
    public String dispararAMaquina(int fila, int columna) {
        return disparar(tableroMaquina, barcosMaquina, fila, columna);
    }
    
    /**
     * agregarBarcoJugador
     * Agrega un barco al tablero del jugador
     * @param fila fila inicial del barco
     * @param columna columna inicial del barco
     * @param tamaño tamaño del barco
     * @param horizontal direccion del barco
     * @return true si se agregó, false si hay superposicion o fuera del limite
     */
    public boolean agregarBarcoJugador(int fila, int columna, int tamaño, boolean horizontal) {
        Ship barco = new Ship(fila, columna, tamaño, horizontal);
        return agregarBarco(tableroJugador, barco, barcosJugador);
    }
    
    /**
     * agregarBarcoMaquina
     * Agrega los barcos al tablero de la maquina
     * @param fila fila inicial del barco
     * @param columna columna inicial del barco
     * @param tamaño tamaño del barco
     * @param horizontal direccion del barco
     * @return true si se agregó correctamente
     */
    public boolean agregarBarcoMaquina(int fila, int columna, int tamaño, boolean horizontal) {
        Ship barco = new Ship(fila, columna, tamaño, horizontal);
        return agregarBarco(tableroMaquina, barco, barcosMaquina);
    }
    
    /**
     * agregarBarco
     * Agrega un barco al tablero del jugador o de la maquina
     * Verifica que no se salga de los limites, ni que haya superposicion
     * @param board tablero donde se colocara el barco
     * @param barco barco a agregar
     * @param listaBarcos barcos del jugador o maquina
     * @return true si se colocó, false si no se pudo
     */
    private boolean agregarBarco(Board board, Ship barco, ArrayList<Ship> listaBarcos) {
        for (int i = 0; i < barco.getTamaño(); i++) {
            int f;
            int c;

            if (barco.isEsHorizontal()) {
                f = barco.getFila();
                c = barco.getColumna() + i;
            } else {
                f = barco.getFila() + i;
                c = barco.getColumna();
            }

            if (f < 0 || f >= board.getFilas() || c < 0 || c >= board.getColumnas()) return false;
            if (board.getCell(f, c) != 0) return false;
        }

        for (int i = 0; i < barco.getTamaño(); i++) {
            int f;
            int c;

            if (barco.isEsHorizontal()) {
                f = barco.getFila();
                c = barco.getColumna() + i;
            } else {
                f = barco.getFila() + i;
                c = barco.getColumna();
            }

            board.setCell(f, c, 1);
        }

        listaBarcos.add(barco);
        return true;
    }
    
    /**
     * yaFueDisparado
     * Verifica si ya fue disparado en el tablero
     * @param fila fila objetivo
     * @param columna columan objetivo
     * @return true si ya fue disparada, false si aun no
     */
    public boolean yaFueDisparado(int fila, int columna) {
        int valor = tableroMaquina.getCell(fila, columna);
        return valor == 2 || valor == 3;
    }
    
    /**
     * disparar
     * Realiza un disparo en el tablero indicado y actualiza el estado
     * @param board el tablero don de se realiza el disparo
     * @param barcos barcos que estan en el tablero
     * @param fila fila en la que se dispara
     * @param columna columna en la que se dispara
     * @return mensaje: indicando el estado del disparo
     */
    private String disparar(Board board, ArrayList<Ship> barcos, int fila, int columna) {
        for (Ship barco : barcos) {
            if (barco.occupies(fila, columna)) {
                barco.registrarToques();
                board.setCell(fila, columna, 2); 

                if (barco.hundido()) {
                    for (int i = 0; i < barco.getTamaño(); i++) {
                        int f;
                        int c;
                        if (barco.isEsHorizontal()) {
                            f = barco.getFila();
                            c = barco.getColumna() + i;
                        } else {
                            f = barco.getFila() + i;
                            c = barco.getColumna();
                        }
                        board.setCell(f, c, 3); 
                    }

                    return "\n BARCO HUNDIDOOO";
                } else {
                    
                    return "\n El tiro ha sido acertado";
                }
            }
        }board.setCell(fila, columna, 0);
        return "El tiro ha sido fallado :c ";
    }

    /**
     * todosHundidosJugador
     * Verifica si todos los barcos del jugador fueron hundidos
     * @return true si todos fueron hundidos
     */
    public boolean todosHundidosJugador() {
        for (Ship barco : barcosJugador) {
            if (!barco.hundido()) return false;
        }
        return true;
    }

    /**
     * todosHundidosMaquina
     * verifica si todos los barcos de la maquina fueron hundidos
     * @return true si todos fueron hundidos
     */
    public boolean todosHundidosMaquina() {
        for (Ship barco : barcosMaquina) {
            if (!barco.hundido()) return false;
        }
        return true;
    }

    /**
     * agregarBarcosPorDefectoJugador
     * Agrega los barcos por defecto del jugador(partida estanadar)
     */
    public void agregarBarcosPorDefectoJugador() {
        agregarBarcoJugador(0, 0, 5, true);   
        agregarBarcoJugador(2, 1, 4, false);   
        agregarBarcoJugador(5, 0, 3, true);  
        agregarBarcoJugador(7, 0, 3, true); 
        agregarBarcoJugador(9, 5, 2, false);   
    }
    
    /**
     * agregarBarcoMaquina
     *Intenta agregar un barco al tablero de la maquina
     * @param barco el barco que se va a colocar
     * @return true si fue agregado, false si no se pudo colocar
     */
    private boolean agregarBarcoMaquina(Ship barco) {
        return agregarBarco(tableroMaquina, barco, barcosMaquina);
    }
    
    /**
     * agregarBarcosAleatoriosMaquina
     *Agrega la cantidad de barcos que se van a usar en la partida de forma aleatoria
     * @param cantidad numero de barcos a agregar
     */
    public void agregarBarcosAleatoriosMaquina(int cantidad) {
        int intentos = 0;
    
        while (barcosMaquina.size() < cantidad && intentos < cantidad * 10) {
            int tamaño = (int) (Math.random() * 4) + 2;
            int fila = (int) (Math.random() * tableroMaquina.getFilas());
            int columna = (int) (Math.random() * tableroMaquina.getColumnas());
            boolean horizontal = Math.random() < 0.5;
    
            Ship barco = new Ship(fila, columna, tamaño, horizontal);
            boolean agregado = agregarBarcoMaquina(barco);
    
            if (!agregado) {
                intentos++;
            }
        }
    }

    /**
    * inicializarTableros
    * Reinicia los tableros del jugador y la máquina a su estado inicial (vacíos).
    */
    private void inicializarTableros() {
        int filas = tableroJugador.getFilas();
        int columnas = tableroJugador.getColumnas();
        tableroJugador = new Board(filas, columnas);
        tableroMaquina = new Board(filas, columnas);
        disparosHechosPorMaquina.clear();
    }

    public void reiniciarJuego() {
        this.inicializarTableros(); 
        this.barcosJugador.clear();
        this.barcosMaquina.clear();
    }
    
    //get y set
    public Board getTableroJugador() {
        return tableroJugador;
    }

    public Board getTableroMaquina() {
        return tableroMaquina;
    }

    public int getValorCeldaJugador(int fila, int columna) {
        return tableroJugador.getCell(fila, columna);
    }

    public int getValorCeldaMaquina(int fila, int columna) {
        return tableroMaquina.getCell(fila, columna);
    }

    public int getFilas() {
        return tableroJugador.getFilas();
    }

    public int getColumnas() {
        return tableroJugador.getColumnas();
    }

    

    public void sumarVictoriaJugador() {
        victoriasJugador++;
    }

    public void sumarVictoriaMaquina() {
        victoriasMaquina++;
    }

    public int getVictoriasJugador() {
        return victoriasJugador;
    }

    public int getVictoriasMaquina() {
        return victoriasMaquina;
    }

}
