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
	public Controlador(int tamanoCola) {
                
		colaEspera = new int[2][tamanoCola];
                numeroHilos = tamanoCola;
                apuntadorCola =0;
	}
        void iniciar(){
            Memoria m = new Memoria();
            Nucleo nucleo1 = new Nucleo();
            Nucleo nucleo2 = new Nucleo(); 
            
            
                    
            int bloque;
            for(int j = 1; j <= numeroHilos; j++ ){   
                bloque = m.leerArchivo(j);
                colaEspera[0][j-1]=bloque;
                colaEspera[1][j-1]=m.getPosicion();
                System.out.println(bloque);
            }  
            
            
            
            
            /*
            for(int j = 0; j < numeroHilos; j++ ){   
                System.out.println(colaEspera[0][j]);
                System.out.println(colaEspera[1][j]);
            } 
            */
            m.imprimirMem();
        }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
