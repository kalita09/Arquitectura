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
    int ID;
    
    Bloque(int id){
    	this.ID = id;
        this.instrucciones = new int[FILAS][COLUMNAS];
    }
    
    void inicializarMemoria(){
        
        for(int i=0; i<FILAS; i++) {

            for(int j=0; j<COLUMNAS; j++) {

            instrucciones [i][j] = 1;

           }

        }
    
    }
    
    public int getID() {
    	return this.ID;
    }
    
	void inicializarCache(){
	        
	        for(int i=0; i<FILAS; i++) {
	
	            for(int j=0; j<COLUMNAS; j++) {
	
	            instrucciones [i][j] = 0;
	
	           }
	
	        }
	    
	    }
    
    void guardarDatos(int numeroInstruccion,String[] codificacion){

            for(int j=0; j<COLUMNAS; j++) {

            instrucciones [numeroInstruccion][j] = Integer.parseInt(codificacion[j]);
           

           }
 

    }
    
    void imprimir(){
        for(int i=0; i<FILAS; i++) {

            for(int j=0; j<COLUMNAS; j++) {

            System.out.print(" "+instrucciones [i][j]);

           }
        }
        System.out.print("\n");
    }

    public String getInstruccion(int numInstruccion) {
    	String instruccion = "";
    	for(int i=0; i<4; i++) {
			instruccion += instrucciones[numInstruccion + i]+" ";
		}
    	return instruccion;
    }
}
