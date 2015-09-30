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
	int numeroHilos;
        int BLOQUES = 128;
        int bloque = 0;
        int posInicio;
        int inst = 0;
        
        
	public Memoria() {
		this.memoria = new Bloque[BLOQUES];
                this.inicializar();
                
                
	}
	
	private void inicializar() { 
            
                for(int i=0; i<BLOQUES; i++) {
                    memoria[i] = new Bloque();
	
		}
            
		for(int i=0; i<BLOQUES; i++) {
                    memoria[i].inicializar();
	
		}
	}
	
	//Copiar instrucciones del archivo a la memoria
        
	public int leerArchivo(int hiloNum) {
            numeroHilos = numeroHilos;
            int bloqueInicio =bloque;
            posInicio = inst;
           // for(int j = 1; j <= numeroHilos; j++ ){           
                File archivo = null;
                FileReader fr = null;
                BufferedReader br = null;
                

                try {
                     archivo = new File ("Hilos/"+hiloNum+".txt");
                     
                     boolean seguir = true;         
                     fr = new FileReader (archivo);
                     br = new BufferedReader(fr);

                     // Lectura del fichero
                     String linea;
                     String[] codificacion;
                     
                     while(seguir){


                       
                        while(inst < 4 && seguir){
                            if((linea=br.readLine())!=null){
                            codificacion = linea.split(" ");
                           
                            memoria[bloque].guardarDatos(inst, codificacion);
                            inst++;
                            }else{
                                seguir = false;
                            
                            }
                            
                            if(inst==4){
				bloque++;
                                inst = 0;
                            }

                        }
                     }
                    // this.imprimirMem();
                    
                     
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

            //}
         
        return bloqueInicio;   
	}
        void imprimirMem(){
            for(int bloque = 0; bloque < 40; bloque++ ){
                System.out.print("BLoque "+bloque +" ");
                this.memoria[bloque].imprimir();
                
            }
        }
        int getPosicion(){
            return this.posInicio;
        }
}

