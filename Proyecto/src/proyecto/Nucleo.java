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

public class Nucleo {
	
	int[][] cacheInstrucciones;
	int[] registros;
	
	public Nucleo() {
		//Primera columna indica numero de bloque: [x][0]
		this.cacheInstrucciones = new int[8][5];
		this.registros = new int[32];
	}
	
	private void inicializarCaches() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<5; j++) {
				cacheInstrucciones[i][j] = 0;
			}
		}
	}
	
	
	
}

