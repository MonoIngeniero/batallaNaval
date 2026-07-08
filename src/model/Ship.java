package model;

/**
 * Clase Ship
 * Representa un barco en el tablero del juego
 * cada barco tiene una posicion inicial, un tamnaño, una orientacion
 * y una cantidad de toques recibidos
 */
public class Ship {
   
   private int fila;
   private int columna;
   private int tamaño;
   private boolean esHorizontal;
   private int toques; 

   /**
    * Ship
    * Crea un nuevo barco
    * @param fila fila inicial del barco
    * @param columna columna inicial del barco
    * @param tamaño longitud del barco
    * @param esHorizontal true si el barco esta horizontal, false si esta vertical
    */
   public Ship(int fila, int columna, int tamaño, boolean esHorizontal){
    this.fila= fila;
    this.columna=columna;
    this.tamaño= tamaño;
    this.esHorizontal = esHorizontal;
    this.toques= 0;
   }

   /**
    * occupies
    * Verifica si una posicion del tablero esta ocupada
    * @param f fila en donde se va a verificar
    * @param c columna en donde se va a verificar
    * @return true si la cordenada esta ocupada, false si no esta ocupada
    */
    
   public boolean occupies(int f, int c){
    for(int i =0; i < tamaño; i++){
        int filaAct;
        int columAct;
        if (esHorizontal) {
            filaAct = fila;
            columAct = columna + i;
        }else{
            columAct = columna;
            filaAct = fila + i;
        }

        if (filaAct == f && columAct == c) {
            return true;
        }
    }return false;}


   /**
    * registrarToques
    *Registra un toque recibido por el barco 
    */ 
   public void registrarToques(){
    toques++;
   }

   /**
    * hundido
    *Indica si el barco ha sido hundido
    * @return true si el barco esta hundido, false si aun no
    */
   public boolean hundido(){
    return toques >=  tamaño;
   }

   //get y set
   public int getToques() {
    return toques;
    }
   public void setToques(int toques) {
    this.toques = toques;
   }
   public int getFila() {
    return fila;
   }
   public void setFila(int fila) {
    this.fila = fila;
   }
   public int getColumna() {
    return columna;
}
   public void setColumna(int columna) {
    this.columna = columna;
   }
   public int getTamaño() {
    return tamaño;
}
   public void setTamaño(int tamaño) {
    this.tamaño = tamaño;
   }
   public boolean isEsHorizontal() {
    return esHorizontal;
}
   public void setEsHorizontal(boolean esHorizontal) {
    this.esHorizontal = esHorizontal;
   }

}
