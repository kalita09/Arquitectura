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
    Contexto [] vectorContextos;
    int numeroHilos;
    int apuntadorCola;
    int apuntadorCola2;
    int hiloActual1;
    int hiloActual2;
    Memoria m;
    CyclicBarrier barrera;
    Nucleo nucleo1;
    Nucleo nucleo2;
    
	public Controlador(int tamanoCola) {
            colaEspera = new int[3][tamanoCola];
            this.vectorContextos = new Contexto [tamanoCola];
            numeroHilos = tamanoCola;
            apuntadorCola = 0;
            apuntadorCola2 = 1;
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
            
            //inicializo vector de contextos
            for(int i=0; i<numeroHilos; i++) {
                    vectorContextos[i] = new Contexto();
            }
                    
            int bloque;
            
            //Guarda en cola donde inicia cada hilo
            for(int j = 1; j <= numeroHilos; j++ ){   
                bloque = m.leerArchivo(j);
                colaEspera[0][j-1]=bloque;
                colaEspera[1][j-1]=m.getPosicion();
                //ningun hilo en este momento ha terminado su ejecucion
                colaEspera[2][j-1]=0;
                System.out.println("Hilo "+j+" comienza en "+bloque+" "+m.getPosicion());
            }
            
            //aqui cargar contexto
            nucleo1.setContexto(this.vectorContextos[this.apuntadorCola].PC,this.vectorContextos[this.apuntadorCola].registros);
            nucleo1.imprimirRegistros();
            
            
            
            //fallo de cache nucleo 1 (falta el bus)
            if(!nucleo1.contenerBloque()) {   
                
	            //esto debe ir en un ciclo hasta q se acaben los ciclos
            	Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
            	nucleo1.cargarBloque(b1);
            }
            /*
          //fallo de cache nucleo 2 (falta el bus)
            if(!nucleo2.contenerBloque()) {    
	            //esto debe ir en un ciclo hasta q se acaben los ciclos	            	
            	Bloque b2 = m.getBloque(colaEspera[0][hiloActual2-1]+nucleo2.getPC()/4);
            	nucleo2.cargarBloque(b2);
            }
            */
            
            //aqui se mandan a ejecutar los hilos
            
            
            //Imprimo lo que esta en cola
            for(int j = 0; j < numeroHilos; j++ ){   
                System.out.println(colaEspera[0][j]);
                System.out.println(colaEspera[1][j]);
            } 
            
            
            m.imprimirMem();
            
            
            nucleo1.imprimirCache();
            nucleo2.imprimirCache();
            
            nucleo1.ejecutarInstruccion();
            System.out.print("Registros despues de ejecutar ");
            nucleo1.imprimirRegistros();
            /*
            nucleo2.ejecutarInstruccion();
             */
        }

    @Override
     public void run() {
 
           System.out.println("Todos han llegado a la barrera");
           this.nucleo1.setPrueba(5);
           this.nucleo2.setPrueba(5);
 
       
     }
}
