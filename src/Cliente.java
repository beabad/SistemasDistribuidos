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

			System.out.println("Bienvenido al juego del Ahorcado");
			
			boolean salir = false;
		    String opcion;
		    long mejorTiempo = 1000;
		    
		    while(!salir){
		    	   System.out.println("\n--- MENÚ ---");
		           System.out.println("1. Iniciar el juego.");
		           System.out.println("2. Salir");
		           
		           
		           System.out.println("Escribe una opción:");
		           opcion = teclado.readLine();
		           out.writeBytes(opcion+"\n");//mando la opcion que he elegido
		           out.flush();
		           
		           //if(Integer.valueOf(opcion)==1) {

		           switch(opcion){
		               case "1":
		                    System.out.println("El juego ha comenzado\n");
		                   
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
			       			System.out.println("-----------------------------------");
			       			System.out.println("EL JUEGO HA TERMINADO");
			       			System.out.println("-----------------------------------");
			       			long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
			       			System.out.println("Has tardado " + tiempo + " segundos.");
			       			System.out.println("Puedes volver a jugar si lo desea");

			       			if(mejorTiempo>tiempo) {
			       				mejorTiempo=tiempo;
			       			}
			       			System.out.println("Tu record es: " + mejorTiempo);
		
		                    break;
		               
		               case "2":
		                   salir=true;
		                   break;
		               default:
		                   System.out.println("Solo números entre 1 y 2");
		            	   
		           }
		           /*if(Integer.valueOf(opcion)==2) {
		        	   salir=true;
		           }*/
		           
		    }
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

}
