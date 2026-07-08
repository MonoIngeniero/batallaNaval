package model;

/**
 * clase Board
 * Representa el tablero de los jugadores
 * puede estar: 0 agua, 1 parte de un barco, 2 tocado, 3 hundido
 */
public class Board {
    
    private int[][] matriz;
    private int filas;
    private int columnas;

    /**
     * Board
     * Crea un tablero nuevo 10*10
     * @param filas numero de filas(10)
     * @param columnas numero de columnas (10)
     */
    public Board(int filas, int columnas){
        this.filas = filas;
        this.columnas= columnas;
        matriz= new int[filas][columnas];
    }

    

    // get y set
    
    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }
    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getCell(int filas, int columnas){
        return matriz[filas][columnas];
    }

    public void setCell(int filas, int columnas, int valor){
        matriz[filas][columnas]= valor;
    }


}
