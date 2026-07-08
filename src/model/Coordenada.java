package model;

import java.util.Objects;

/**
 * clase Coordenada
 * representa las posiciones en el tablero del juego
 * mediante filas y columnas
 */
public class Coordenada {

    private int fila;
    private int columna;

    /**
     * Coordenada
     * crea una coordenada con la fila y columna
     * @param fila la posicion vertial en el tablero
     * @param columna la posicon horizontal en el tablero
     */
    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    
    /**
     * compara esta coordenada entre objetos para verificar sin son iguales
     * @param o el objeto a comparar
     * @return true si las coordenadas son iguales, false si no
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordenada)) return false;
        Coordenada that = (Coordenada) o;
        return fila == that.fila && columna == that.columna;
    }
    /**
     * calcula el codigo hash basado en la fila y la columna
     * @return el codigo hash de la coordenada 
     */
    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    /** 
    *devuelve una representacino en cadena de la coordenada
    *@return una cadena  
    */
    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ")";
    }

    //get y set
    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    public void setFila(int fila) {
        this.fila = fila;
    }
}
