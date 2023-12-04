import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
		           System.out.println("3. Salir");
		           
		           System.out.print("Escribe una opción: ");
		           opcion = teclado.readLine();
		           out.writeBytes(opcion+"\n");//mando la opcion que he elegido
		           out.flush();
		           
		           //if(Integer.valueOf(opcion)==1) {
		           switch(opcion.trim()){
		               case "1":
		                    System.out.println("\n¡El juego ha comenzado! \n");
		                   
		                    long tiempoInicial = System.currentTimeMillis();
		                    		       			
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
			       			
		                    break;
		               
		               case "2":
		            	   System.out.println("\n¡El juego ha comenzado! \n");
		                   
		                    long tiempoInicial1 = System.currentTimeMillis();
		                    		       			
		       				System.out.println(in.readLine());//1
			       			String linea1 = in.readLine();//2
			       			System.out.println(linea1);
			       			
			       			while(linea1!=null && !linea1.equals(" ")) {
			       				String letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			       			
			       				out.writeBytes(letra+"\n");//3
			       				out.flush();
			       				
			       				System.out.println(in.readLine());//4
			       				linea = in.readLine();
			       				System.out.println(linea);
			       			}
			       			numPartidas++;
			       			long tiempo1 = (System.currentTimeMillis() - tiempoInicial1)/1000;
			       			System.out.println("Has tardado " + tiempo1 + " segundos.\n");
			       			System.out.println("------------------------------------");
			       			System.out.println("---- Ha terminado la partida :( ----");
			       			System.out.println("------------------------------------");
			       			//System.out.println("Has tardado " + tiempo + " segundos.");
			       			//numPartidas++;
			       			//long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			       			if(mejorTiempo>tiempo1) {
			       				mejorTiempo=tiempo1;
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
		            	   
		               case "3":	      
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
}
