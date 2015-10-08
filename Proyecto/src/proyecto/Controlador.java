/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 *
 * @author b04732
 */
public class Controlador implements Runnable{
    int[][] colaEspera;
    Contexto [] vectorContextos;
    int numeroHilos;
    //int apuntadorCola;
    //int apuntadorCola2;
    int hiloActual1;
    int hiloActual2;
    Memoria m;
    CyclicBarrier barrera;
    Nucleo nucleo1;
    Nucleo nucleo2;
    static Semaphore busInstrucciones;
    boolean enEspera;
    int ciclosReloj;
    int ciclosBus;
    
	public Controlador(int tamanoCola, int ciclosReloj, int ciclosBus) {
		this.colaEspera = new int[3][tamanoCola];
        this.vectorContextos = new Contexto [tamanoCola];
        this.numeroHilos = tamanoCola;
        //this.apuntadorCola = 0;
        //this.apuntadorCola2 = 1;
        this.hiloActual1 = 1;        
        this.hiloActual2 = 2;
        this.busInstrucciones = new Semaphore(1);
        this.ciclosReloj = ciclosReloj;
        this.ciclosBus = ciclosBus;          
	}
	
        void iniciar() /*throws InterruptedException */{
            this.m = new Memoria();
            this.barrera = new CyclicBarrier(numeroHilos,this);
            this.nucleo1 = new Nucleo("Nucleo 1",barrera);
            this.nucleo2 = new Nucleo("Nucleo 2",barrera);

            //Memoria m = new Memoria();
            
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
            
            for(int ciclos=0; ciclos<ciclosReloj; ciclos++) {
	            //aqui cargar contexto
	            //nucleo1.setContexto(this.vectorContextos[this.hiloActual1-1].PC,this.vectorContextos[this.hiloActual1-1].registros);
	            System.out.println("Nucleo 1: ");
	            nucleo1.imprimirRegistros();
	            //ver si hay fallo de cache en el nucleo 1
	            Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
	            if(nucleo1.contenerBloque(b1.getID())) {
	            	Thread hilo1 = new Thread(nucleo1);
	                hilo1.start();
	                //hilo1.join();
		            //esto debe ir en un ciclo hasta q se acaben los ciclos
	            	/*Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
	            	nucleo1.cargarBloque(b1);*/
	            } else {
	            	try {
	            		  busInstrucciones.acquire();
	            		  try {
	            		    // cargar bloque de memoria a cache (hay que simular los ciclos que dura cargando la instruccion)
	            			//Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
	                      	nucleo1.cargarBloque(b1);
	                      	/*if(hiloActual1 <= hiloActual2) {
	                      		if(hiloActual2+1 <= numeroHilos) { //ESTO SE HACE CUANDO SE LE ACABA EL QUANTUM
	                      			hiloActual1++;
	                      		} else {
	                      			hiloActual1 = 1;
	                      		}
	                      	} else {
	                      		if(hiloActual1+1 <= numeroHilos) { //ESTO SE HACE CUANDO SE LE ACABA EL QUANTUM
	                      			hiloActual1++;
	                      		} else {
	                      			hiloActual1 = 1;
	                      		}
	                      	}*/
	            		  } finally {
	            			  busInstrucciones.release();
	            		  }
	            		} catch(InterruptedException ie) {
	            			System.out.println (" — Interrupted…");
	            			ie.printStackTrace ();
	            		}
	            }
	            
	            //aqui cargar contexto
	            //nucleo2.setContexto(this.vectorContextos[this.hiloActual2-1].PC,this.vectorContextos[this.hiloActual2-1].registros);
	            System.out.println("Nucleo 2: ");
	            nucleo2.imprimirRegistros();            	            
	            //ver si hay fallo de cache en el nucleo 2
	            Bloque b2 = m.getBloque(colaEspera[0][hiloActual2-1]+nucleo2.getPC()/4);
	            if(nucleo2.contenerBloque(b2.getID())) {
	            	Thread hilo2 = new Thread(nucleo2);
	            	hilo2.start();
	            	//hilo2.join();
		            //esto debe ir en un ciclo hasta q se acaben los ciclos
	            	/*Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+nucleo1.getPC()/4);
	            	nucleo1.cargarBloque(b1);*/
	            } else {
	            	try {
	            		  busInstrucciones.acquire();
	            		  try {
	            		    // cargar bloque de memoria a cache (hay que simular los ciclos que dura cargando la instruccion)
	            			//Bloque b2 = m.getBloque(colaEspera[0][hiloActual2-1]+nucleo2.getPC()/4);
	                      	nucleo2.cargarBloque(b2);
	                      	/*if(hiloActual1 <= hiloActual2) {
	                      		if(hiloActual2+1 <= numeroHilos) { //ESTO SE HACE CUANDO SE LE ACABA EL QUANTUM
	                      			hiloActual2++;
	                      		} else {
	                      			hiloActual2 = 1;
	                      		}
	                      	} else {
	                      		if(hiloActual1+1 <= numeroHilos) { //ESTO SE HACE CUANDO SE LE ACABA EL QUANTUM
	                      			hiloActual2++;
	                      		} else {
	                      			hiloActual2 = 1;
	                      		}
	                      	}*/
	            		  } finally {
	            			  busInstrucciones.release();
	            		  }
	            		} catch(InterruptedException ie) {
	            			System.out.println (" — Interrupted…");
	            			ie.printStackTrace ();
	            		}
	            }
	            
	            //hilo1.join();
	            //hilo2.join();
	            
	          /*  if(ciclos > 0) {
		            System.out.println("Registros despues de ejecutar ciclo :" + ciclos);
		            System.out.println("Nucleo 1: ");
		            nucleo1.imprimirRegistros();
		            System.out.println("Nucleo 2: ");
		            nucleo2.imprimirRegistros();
	            }*/
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
            /*for(int j = 0; j < numeroHilos; j++ ){   
                System.out.println(colaEspera[0][j]);
                System.out.println(colaEspera[1][j]);
            } */
            
            
            //m.imprimirMem();
            
            
            /*nucleo1.imprimirCache();
            nucleo2.imprimirCache();
            
            nucleo1.ejecutarInstruccion();
            System.out.println("Registros despues de ejecutar ");
            System.out.println("Nucleo 1: ");
            nucleo1.imprimirRegistros();
            System.out.println("Nucleo 2: ");
            nucleo2.imprimirRegistros();
            
            nucleo2.ejecutarInstruccion();
             */
        }

    @Override
     public void run() {
 
           System.out.println("Todos han llegado a la barrera");
           this.nucleo1.setPrueba(5);
           this.nucleo2.setPrueba(5);
           
           System.out.println("Registros despues de ejecutar ciclo :");
           System.out.println("Nucleo 1: ");
           nucleo1.imprimirRegistros();
           System.out.println("Nucleo 2: ");
           nucleo2.imprimirRegistros();
       
     }
}
