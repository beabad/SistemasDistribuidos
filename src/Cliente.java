import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
		           System.out.println("2. 2 jugadores: uno escribe la palabra y otro adivina.");
		           System.out.println("3. 2 jugadores: los dos contra la misma palabra");
		           System.out.println("4. Salir");
		           
		           System.out.print("Escribe una opción: ");
		           opcion = teclado.readLine();
		           out.writeBytes(opcion+"\n");//mando la opcion que he elegido
		           out.flush();
		           String linea, letra;
		           long tiempo, tiempoInicial;
		           //if(Integer.valueOf(opcion)==1) {
		           switch(opcion.trim()){
		               case "1":
		                    System.out.println("\n¡El juego ha comenzado! \n");
		                    tiempoInicial = System.currentTimeMillis();
		       				System.out.println(in.readLine());//1
			       			linea = in.readLine();//2
			       			System.out.println(linea);
			       			System.out.println("entra");
			       			while(linea!=null && !linea.equals(" ")) {
			       				letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			       				out.writeBytes(letra+"\n");//3
			       				out.flush();
			       				System.out.println(in.readLine());//4
			       				linea = in.readLine();
			       				System.out.println(linea);
			       			}
			       			numPartidas++;
			       			tiempo = mostrarFinPartida(tiempoInicial);
			       			if(mejorTiempo>tiempo) {
			       				mejorTiempo=tiempo;
			       			}
			       			mejorTiempo(mejorTiempo, numPartidas);
		                    break;
		               
		               case "2":
		            	   System.out.println("\n¡El juego ha comenzado! Es necesario que ejecutes otro cliente. \n");
		            	   try(Socket s1 = new Socket("localhost", 8000);
		            		   DataInputStream in1 = new DataInputStream(s1.getInputStream());
		            		   DataOutputStream out1 = new DataOutputStream(s1.getOutputStream());
		            			   //PrintStream out1 = new PrintStream(s1.getOutputStream());
		            			   //PrintStream out2 = new PrintStream(s.getOutputStream());
		            			DataInputStream teclado1 = new DataInputStream(System.in);
		            		){
		            		   //System.out.println("\n¡El juego ha comenzado! \n");
		            		   
		            		   tiempoInicial = System.currentTimeMillis();
		            		   //System.out.println("entra");
		            		   System.out.println(in.readLine());
		            		   //out2.print(in.readLine());
		            		   System.out.println("entra2");
		            		   String palabraMandar = teclado.readLine();
		            		   out.writeBytes(palabraMandar+"\n");
		            		   out.flush();
		            		   
		            		   System.out.println(in.readLine());
		            		   System.out.println("entra3");
				       			linea = in1.readLine();//2
				       			System.out.println(linea);
				       			while(linea!=null && !linea.equals(" ")) {
				       				letra = teclado1.readLine().toLowerCase(); //Transformamos la letra a minuscula.
				       				out1.writeBytes(letra+"\n");//3
				       				out1.flush();
				       				System.out.println(in1.readLine());//4
				       				linea = in1.readLine();
				       				System.out.println(linea);
				       			}
				       			tiempo = mostrarFinPartida(tiempoInicial);
		            	   }catch (IOException e){
	           					e.printStackTrace();
	           				}
		            	   
		                    break;
		               case "3":
		            	   System.out.println("\n¡El juego ha comenzado! \n");
	            		   	tiempoInicial = System.currentTimeMillis();
	            		   	System.out.println(in.readLine());
	            		   	System.out.println(in.readLine());
	            		   	linea = in.readLine();//2
			       			System.out.println(linea);
			       			System.out.println("entra");
			       			while(linea!=null && !linea.equals(" ")) {
			       				letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			       				out.writeBytes(letra+"\n");//3
			       				out.flush();
			       				System.out.println("hola");
			       				System.out.println(in.readLine());//4
			       				linea = in.readLine();
			       				System.out.println(linea);
			       			}
			       			tiempo = mostrarFinPartida(tiempoInicial);
			       			if(mejorTiempo>tiempo) {
			       				mejorTiempo=tiempo;
			       			}
			       			mejorTiempo(mejorTiempo, numPartidas);
		            		   	
			                    
				       			//Jugador 2
				       			/*long tiempoInicial1 = System.currentTimeMillis();
				       			System.out.println(in.readLine());//1
				       			String linea1 = in.readLine();//2
				       			System.out.println(linea1);
				       			while(linea1!=null && !linea1.equals(" ")) {
				       				String letra1 = teclado1.readLine().toLowerCase(); //Transformamos la letra a minuscula.
				       				out1.writeBytes(letra1+"\n");//3
				       				out1.flush();
				       				System.out.println(in1.readLine());//4
				       				linea1 = in1.readLine();
				       				System.out.println(linea1);
				       			}
				       			long tiempo1 = mostrarFinPartida(tiempoInicial1);
				       			
				       			out.writeLong(tiempo);	//manda tiempo el jug1
		            		   	out.flush();
		            		   	out1.writeLong(tiempo1); //manda tiempo el jug2
		            		   	out1.flush();*/
		            		   	
		            		   	//System.out.println(in.readLine());		//jug1 recibe si gana o pierde
		            		   	//System.out.println(in1.readLine());		//jug2 recibe si gana o pierde
		            		   	
			            	   
		            	   
		                    break;
		               case "4":	      
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
	
	private static long mostrarFinPartida(long tiempoInicial) {
		long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
		System.out.println("Has tardado " + tiempo + " segundos.\n");
		System.out.println("------------------------------------");
		System.out.println("---- Ha terminado la partida :( ----");
		System.out.println("------------------------------------");
		return tiempo;
	}
	
	private static void mejorTiempo(long mejorTiempo, int numPartidas) {
		if(numPartidas==1) {
			System.out.println("Llevas " + numPartidas + " partida jugada.");
		}
		else {
			System.out.println("Llevas " + numPartidas + " partidas jugadas. Tu record es " + mejorTiempo + " segundos.");
		}
		System.out.println("Puedes volver a jugar si lo deseas.");
	}
	
}