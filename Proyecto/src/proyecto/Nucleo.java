/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author b04732
 */

public class Nucleo implements Runnable {
	int PC;
    int IR;
	Bloque[] cacheInstrucciones;
	int[] registros;
	int BLOQUES;
	int apuntadorCache;
	String nombreNucleo;
    private CyclicBarrier barrier;
    int pruebaHilo;
	
	public Nucleo(String nombre, CyclicBarrier barrier) {
		this.nombreNucleo = nombre;
        this.barrier = barrier;
		this.cacheInstrucciones = new Bloque[8];
		this.registros = new int[32];
		this.BLOQUES = 8;
		this.apuntadorCache = 0;
		this.cacheInstrucciones = new Bloque[BLOQUES];
		this.pruebaHilo = 1;
        this.inicializarCaches();
	}
	
	private void inicializarCaches() {
		for(int i=0; i<BLOQUES; i++) {
			cacheInstrucciones[i] = new Bloque(-1); //-1 para distinguir estos bloques "vacios"
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
			if(cacheInstrucciones[i].getID() == PC/4) { // PC/4 nos da el número de bloque
				return true;
			}
		}
		return false;
	}
	
	public void setPrueba(int num){
        this.pruebaHilo = num;
        
    }

    @Override
    public void run() {
        System.out.println(this.nombreNucleo);
            try {
            System.out.println(Thread.currentThread().getName() + this.pruebaHilo);
            this.barrier.await();
            System.out.println(Thread.currentThread().getName() + this.pruebaHilo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Nucleo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(Nucleo.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
	
	public void ejecutarInstruccion() {
		Bloque b = cacheInstrucciones[PC/4];
		String instruccion = b.getInstruccion(PC%4);
		String[] codificacion = instruccion.split(" ");
		System.out.println(codificacion[0]);
		switch(codificacion[0]) {
			case "8": //DADDI
				registros[Integer.parseInt(codificacion[2])] =
					Integer.parseInt(codificacion[1]) + Integer.parseInt(codificacion[3]);
			break;
			
			case "32": //DADD
				registros[Integer.parseInt(codificacion[3])] =
					Integer.parseInt(codificacion[1]) + Integer.parseInt(codificacion[2]);
			break;
			
			case "34": //DSUB
				registros[Integer.parseInt(codificacion[3])] =
					Integer.parseInt(codificacion[1]) - Integer.parseInt(codificacion[2]);
			break;
			
			case "12": //DMUL
				registros[Integer.parseInt(codificacion[3])] =
					Integer.parseInt(codificacion[1]) * Integer.parseInt(codificacion[2]);
			break;
			
			case "14": //DDIV
				registros[Integer.parseInt(codificacion[3])] =
					Integer.parseInt(codificacion[1]) / Integer.parseInt(codificacion[2]);
			break;
			
			case "4": //BEQZ
				if(Integer.parseInt(codificacion[1]) == 0) {
					PC += Integer.parseInt(codificacion[3]); //multiplicado*4???????????????
				}
			break;
			
			case "5": //BNEZ
				if(Integer.parseInt(codificacion[1]) != 0) {
					PC += Integer.parseInt(codificacion[3]); //multiplicado*4???????????????
				}
			break;
			
			case "3": //JAL
				registros[31] = PC;
				PC += Integer.parseInt(codificacion[3]);
			break;
			
			case "2": //JR
				PC = Integer.parseInt(codificacion[1]);
			break;
		}
		PC++;
	}
	
}

