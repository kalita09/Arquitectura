/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author b04732
 */
public class Controlador implements Runnable{
	int[][] colaEspera;
	int numeroHilos;
	int apuntadorCola;
    int hiloActual1;
    int hiloActual2;
    Memoria m;
    CyclicBarrier barrera;
    Nucleo nucleo1;
    Nucleo nucleo2;
    
	public Controlador(int tamanoCola) {
		colaEspera = new int[2][tamanoCola];
        numeroHilos = tamanoCola;
        apuntadorCola = 0;
        hiloActual1 = 1;
        hiloActual2 = 2;
	}
	
        void iniciar(){
			this.m = new Memoria();
            this.barrera = new CyclicBarrier(numeroHilos,this);
            this.nucleo1 = new Nucleo("Nucleo 1",barrera);
            this.nucleo2 = new Nucleo("Nucleo 2",barrera);
            Thread hilo1 = new Thread(nucleo1);
            Thread hilo2 = new Thread(nucleo2);
            hilo1.start();
            hilo2.start();

            Memoria m = new Memoria();
                    
            int bloque;
            for(int j = 1; j <= numeroHilos; j++ ){   
                bloque = m.leerArchivo(j);
                colaEspera[0][j-1]=bloque;
                colaEspera[1][j-1]=m.getPosicion();
                System.out.println(bloque+" "+m.getPosicion());
            }
            
            //aqui cargar contexto
            
            //fallo de cache nucleo 1 (falta el bus)
            if(!nucleo1.contenerBloque()) {            
	            //esto debe ir en un ciclo hasta q se acaben los ciclos
            	Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
            	nucleo1.cargarBloque(b1);
            }
            
          //fallo de cache nucleo 2 (falta el bus)
            if(!nucleo2.contenerBloque()) {    
	            //esto debe ir en un ciclo hasta q se acaben los ciclos	            	
            	Bloque b2 = m.getBloque(colaEspera[0][hiloActual2-1]+nucleo2.getPC()/4);
            	nucleo2.cargarBloque(b2);
            }
            
            //aqui se mandan a ejecutar los hilos
            
            
            /*
            for(int j = 0; j < numeroHilos; j++ ){   
                System.out.println(colaEspera[0][j]);
                System.out.println(colaEspera[1][j]);
            } 
            */
            m.imprimirMem();
            nucleo1.imprimirCache();
            nucleo2.imprimirCache();
            
            nucleo1.ejecutarInstruccion();
            nucleo2.ejecutarInstruccion();
        }

    @Override
     public void run() {
 
           System.out.println("Todos han llegado a la barrera");
           this.nucleo1.setPrueba(5);
           this.nucleo2.setPrueba(5);
 
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
}
