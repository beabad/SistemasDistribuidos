import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Cliente {

	public static void main(String[] args) {

		try(Socket s = new Socket("localhost",8000);
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream teclado = new DataInputStream(System.in);)
		{
			System.out.println("------------------------------------------");
			System.out.println("---- BIENVENIDO AL JUEGO DEL AHORCADO ----");
			System.out.println("------------------------------------------");
			boolean salir = false;
		    String opcion;
		    long mejorTiempo = 1000;
		    int numPartidas = 0;
		    
		    while(!salir){
		    	   System.out.println("\n--- MENÚ ---");
		           System.out.println("1. Obtener una palabra aleatoria (1 jugador).");
		           System.out.println("2. Escribir palabra y adivinarla (2 jugadores).");
		           System.out.println("3. Enviar una palabra");
		           System.out.println("4. Quedarme a la espera de recibir una palabra");
		           System.out.println("5. Salir");
		           
		           System.out.print("Escribe una opción: ");
		           opcion = teclado.readLine();
		           out.writeBytes(opcion+"\n");//mando la opcion que he elegido
		           out.flush();
		           String linea;
		           
		           //if(Integer.valueOf(opcion)==1) {
		           switch(opcion.trim()){
		               case "1":
		            	   //jugar(in,out,teclado,numPartidas,mejorTiempo);
		                    System.out.println("\n¡El juego ha comenzado! \n");
		                   
		                    long tiempoInicial = System.currentTimeMillis();
		                    		       			
		       				System.out.println(in.readLine());//1
			       			linea = in.readLine();//2
			       			System.out.println(linea);
			       			
			       			while(linea!=null && !linea.equals(" ")) {
			       				String letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			       			
			       				out.writeBytes(letra+"\n");//3
			       				out.flush();
			       				
			       				System.out.println(in.readLine());//4
			       				linea = in.readLine();
			       				System.out.println(linea);
			       				
			       			}
			       		
			       			numPartidas++;
			       			long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			       			System.out.println("Has tardado " + tiempo + " segundos.\n");
			       			System.out.println("------------------------------------");
			       			System.out.println("---- Ha terminado la partida :( ----");
			       			System.out.println("------------------------------------");
			       			//System.out.println("Has tardado " + tiempo + " segundos.");
			       			//numPartidas++;
			       			//long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			       			if(mejorTiempo>tiempo) {
			       				mejorTiempo=tiempo;
			       			}
			       			if(numPartidas==1) {
			       				System.out.println("Llevas " + numPartidas + " partida jugada.");
			       			}
			       			else {
			       				System.out.println("Llevas " + numPartidas + " partidas jugadas. Tu record es " + mejorTiempo + " segundos.");
			       			}
			       			//System.out.println("Tu record es: " + mejorTiempo);
			       			System.out.println("Puedes volver a jugar si lo deseas.");
			       			
		                    break;
		               
		               case "2":   
		            	   //jugar(in,out,teclado,numPartidas,mejorTiempo);

		                    break;
		               case "3":
		            	   try {
		            		   Socket s1 = new Socket("localhost",5555);
			            	   System.out.println("Introduce una letra o la palabra:\n");
			            	   linea = teclado.readLine();
			            	   out.writeBytes(linea+"\n");
			            	   out.flush();
		            	   }
		            	   catch (IOException e) {
								e.printStackTrace();
		                   }

		            	   break;
		               case "4":
		            	   System.out.println("Has seleccionado: Quedarme a la espera de recibir una palabra");
		            	   try(ServerSocket ss = new ServerSocket(5555)){
		           				//ExecutorService pool = Executors.newCachedThreadPool();
		           				while(true) {
		           					try{
		           						Socket s2 = ss.accept();
		           						PeticionAhorcado ap=new PeticionAhorcado(s2);
		           						ap.start();
		           						ap.join();
		           						//jugar(in,out,teclado,numPartidas,mejorTiempo);
		           						
		           					}
		           					catch (IOException e){
		           						e.printStackTrace();
		           					} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		           				}
		           		   } 
		                   catch (IOException e) {
								e.printStackTrace();
		                   }
		            	   
		            	   
		            	   
		            	   
		            	   
		            	   
		            	   break;
		               case "5":	      
		            	   System.out.println("\nHasta pronto!!");
		                   salir=true;
		                   break;
		               default:
		                   System.out.println("Opción no válida. Elige otra.");
		            	   
		           }
		    }
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*private static void jugar(DataInputStream in, DataOutputStream out,DataInputStream teclado, int numPartidas, long mejorTiempo) {
		
		System.out.println("\n¡El juego ha comenzado! \n");
        
        long tiempoInicial = System.currentTimeMillis();
        try {		       			
			System.out.println(in.readLine());//1
			String linea = in.readLine();//2
			System.out.println(linea);
			
			while(linea!=null && !linea.equals(" ")) {
				String letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			
				out.writeBytes(letra+"\n");//3
				out.flush();
				
				System.out.println(in.readLine());//4
				linea = in.readLine();
				System.out.println(linea);
				
			}
		
			numPartidas++;
			long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			System.out.println("Has tardado " + tiempo + " segundos.\n");
			System.out.println("------------------------------------");
			System.out.println("---- Ha terminado la partida :( ----");
			System.out.println("------------------------------------");
			//System.out.println("Has tardado " + tiempo + " segundos.");
			//numPartidas++;
			//long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			if(mejorTiempo>tiempo) {
				mejorTiempo=tiempo;
			}
			if(numPartidas==1) {
				System.out.println("Llevas " + numPartidas + " partida jugada.");
			}
			else {
				System.out.println("Llevas " + numPartidas + " partidas jugadas. Tu record es " + mejorTiempo + " segundos.");
			}
			//System.out.println("Tu record es: " + mejorTiempo);
			System.out.println("Puedes volver a jugar si lo deseas.");
        }
        catch (IOException e){
			e.printStackTrace();
		}
	}*/
}
