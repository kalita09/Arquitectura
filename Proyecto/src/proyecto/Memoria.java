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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Memoria {
	Bloque[] memoria;
	int numeroHilos = 1;
        int BLOQUES = 128;
        
        
	public Memoria() {
		this.memoria = new Bloque[BLOQUES];
                
                
	}
	
	private void inicializar() {
            
           
            
		for(int i=0; i<BLOQUES; i++) {
                    memoria[i].inicializar();
	
		}
	}
	
	//Copiar instrucciones del archivo a la memoria
        
	public void leerArchivo() {
            for(int j = 1; j <= numeroHilos; j++ ){           
                File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;

                try {
                     archivo = new File ("Hilos/"+j+".txt");
                             
                     fr = new FileReader (archivo);
                     br = new BufferedReader(fr);

                     // Lectura del fichero
                     String linea;
                     String[] codificacion;
                     
                     for(int bloque = 0; bloque < 40; bloque++ ) {
                         if((linea=br.readLine())!=null){
                        //System.out.println(linea);
                             for(int i = 0; i<=3; i++) {
                                     codificacion = linea.split(" ");
                                     //memoria[0][i]= Integer.parseInt(codificacion[i]);
                                    // System.out.print(memoria[0][i] + " ");
                             }
                             
                         }
                              System.out.print( "Bloque "+bloque);
                     System.out.print("\n");
                     }
                  }
                  catch(Exception e){
                     e.printStackTrace();
                  } finally{
                             try{                    
                                if( null != fr ){   
                                   fr.close();     
                                }                  
                             } catch (Exception e2){ 
                                e2.printStackTrace();
                             }
                  }
                
                
            }
                
	}
}

