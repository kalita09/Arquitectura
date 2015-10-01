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
public class Controlador implements Runnable{
	int[][] colaEspera;
	int numeroHilos;
    int apuntadorCola;
    int hiloActual1;
    int hiloActual2;
    
	public Controlador(int tamanoCola) {
		colaEspera = new int[2][tamanoCola];
        numeroHilos = tamanoCola;
        apuntadorCola = 0;
        hiloActual1 = 1;
        hiloActual2 = 2;
	}
	
        void iniciar(){
            Memoria m = new Memoria();
            Nucleo nucleo1 = new Nucleo("Nucleo 1");
            Nucleo nucleo2 = new Nucleo("Nucleo 2"); 
            Thread hilo1 = new Thread(nucleo1);
            Thread hilo2 = new Thread(nucleo2);
            hilo1.start();
            hilo2.start();
                    
            int bloque;
            for(int j = 1; j <= numeroHilos; j++ ){   
                bloque = m.leerArchivo(j);
                colaEspera[0][j-1]=bloque;
                colaEspera[1][j-1]=m.getPosicion();
                System.out.println(bloque+" "+m.getPosicion());
            }
            
            //aqui cargar contexto
            
            //fallo de cache nucleo 1 (falta el bus)
            //if(!nucleo1.contenerBloque()) {            
	            //esto debe ir en un ciclo hasta q se acaben los ciclos
	            for(int i=0; i<8; i++) {
	            	Bloque b1 = m.getBloque(colaEspera[0][hiloActual1-1]+i);
	            	nucleo1.cargarBloque(b1);
	            }
           // }
            
          //fallo de cache nucleo 2 (falta el bus)
           // if(!nucleo2.contenerBloque()) {    
	            //esto debe ir en un ciclo hasta q se acaben los ciclos
	            for(int i=0; i<8; i++) {	            	
	            	Bloque b2 = m.getBloque(colaEspera[0][hiloActual2-1]);
	            	nucleo2.cargarBloque(b2);
	            }
        //    }
            
            //aqui se mandan a ejecutar los hilos
            
            
            /*
            for(int j = 0; j < numeroHilos; j++ ){   
                System.out.println(colaEspera[0][j]);
                System.out.println(colaEspera[1][j]);
            } 
            */
            m.imprimirMem();
            nucleo1.imprimirCache();
        }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
