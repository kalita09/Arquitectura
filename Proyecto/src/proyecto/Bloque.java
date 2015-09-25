/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

/**
 *
 * @author b04732
 */
public class Bloque {
    
    int[][] instrucciones;
    int FILAS = 4;
    int COLUMNAS = 4;
    
    Bloque(){
        this.instrucciones = new int[FILAS][COLUMNAS];

    }
    void inicializar(){
        
        for(int i=0; i<FILAS; i++) {

            for(int j=0; j<COLUMNAS; j++) {

            instrucciones [i][j] = 1;

           }

        }
    
    }
}
