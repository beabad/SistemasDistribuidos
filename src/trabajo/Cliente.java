package trabajo;
import java.io.*;
import java.net.Socket;


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
		    while(!salir){
		    	   System.out.println("\nElige una de las siguientes opciones para jugar: ");
		           System.out.println("1. Un cliente escribe la palabra y otro adivina.");
		           System.out.println("2. Los dos clientes contra la misma palabra.");
		           System.out.println("3. Salir.");
		           System.out.print("\nEscribe una opción: ");
		           opcion = teclado.readLine();
		           out.writeBytes(opcion+"\n");//mando la opcion que he elegido
		           out.flush();
		           String linea, letra;
		           long tiempo, tiempoInicial;
		           switch(opcion.trim()){
		               case "1":
		           		   System.out.println("\n¡El juego ha comenzado! \n");
		           		   tiempoInicial = System.currentTimeMillis();
		           		   System.out.println(in.readLine());
		          		   String palabraMandar = teclado.readLine();
		           		   out.writeBytes(palabraMandar+"\n");
		            	   out.flush();
		            	   System.out.println(in.readLine());
		            	   linea = in.readLine();
		            	   System.out.println(linea);
		            	   while(linea!=null && !linea.equals(" ")) {
		            		   letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
				       	       out.writeBytes(letra+"\n");
				       		   out.flush();
				       	       System.out.println(in.readLine());
				       		   linea = in.readLine();
				       		   System.out.println(linea);
				       		}
		            		System.out.println(in.readLine());
		            		System.out.println(in.readLine());
		            		System.out.println(in.readLine());
		            		System.out.println(in.readLine());
				       		salir=true;

		                    break;
		               case "2":
		            	    System.out.println("\n¡El juego ha comenzado! \n");
		            	    System.out.println(in.readLine());
	            		   	System.out.println(in.readLine());
	            		    out.writeBytes(teclado.readLine()+"\n");
		            	    out.flush();
	            		   	System.out.println(in.readLine());
	            		   	linea = in.readLine();//2
			       			System.out.println(linea);
			       			tiempoInicial = System.currentTimeMillis();
			       			while(linea!=null && !linea.equals(" ")) {
			       				letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			       				out.writeBytes(letra+"\n");//3
			       				out.flush();
			       				System.out.println(in.readLine());//4
			       				linea = in.readLine();
			       				System.out.println(linea);
			       			}
			       			tiempo = mostrarFinPartida(tiempoInicial);
			       			out.writeLong(tiempo);	//manda tiempo el jugador
	            		   	out.flush();
	            		   	System.out.println(in.readLine());
			       			salir=true;
		                    break;
		               case "3":
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
	
}