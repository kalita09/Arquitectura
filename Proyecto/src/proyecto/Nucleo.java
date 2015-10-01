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
	int PC;
    int IR;
	Bloque[] cacheInstrucciones;
	int[] registros;
	int BLOQUES;
	int apuntadorCache;
	
	public Nucleo() {
		//Primera columna indica numero de bloque: [x][0]
		this.cacheInstrucciones = new Bloque[8];
		this.registros = new int[32];
		this.BLOQUES = 8;
		this.apuntadorCache = 0;
		this.cacheInstrucciones = new Bloque[BLOQUES];
        this.inicializarCaches();
	}
	
	private void inicializarCaches() {
		for(int i=0; i<BLOQUES; i++) {
			cacheInstrucciones[i] = new Bloque(0); //0 para distinguir estos bloques "vacios"
			cacheInstrucciones[i].inicializarCache();
		}
	}
	
	public void cargarBloque(Bloque b) {
		cacheInstrucciones[apuntadorCache] = b;
		if(apuntadorCache<=7) {
			apuntadorCache++;
		} else {
			apuntadorCache = 0;
		}
	}
        void setPC(int miPC){
            PC = miPC;
        }
        int getPC(){
            return this.PC;
        } 
	
	public void imprimirCache(){
        for(int bloque = 0; bloque < 8; bloque++ ){
            System.out.print("BLoque "+bloque +" ");
            this.cacheInstrucciones[bloque].imprimir();
            
        }
    }
	
	public boolean contenerBloque() {
		for(int i=0; i<BLOQUES; i++) {
			if(cacheInstrucciones[i].getID() == PC/4) { // PC/4 nos da el n�mero de bloque
				return true;
			}
		}
		return false;
	}
	
}

