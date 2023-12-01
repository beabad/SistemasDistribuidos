import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PeticionAhorcado extends Thread{
	
	private Socket s;
	
	public PeticionAhorcado(Socket s) {
		super();
		this.s = s;
	}
	
	public void run() {
		try(DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("./src/palabras.txt")));)
			//DataInputStream teclado = new DataInputStream(System.in);)
		{
			boolean salir = false;
		    String opcion;
		    
		    List<String> palabras = crearLista(br);

		    while(!salir){
		           opcion = in.readLine();
		           
		           //if(Integer.valueOf(opcion)==1) {
		           switch(opcion.trim()){
		               case "1":
		       			
		       			Random random = new Random();
		       			int indice = random.nextInt(palabras.size());
		       			String palabra = palabras.get(indice).toLowerCase();//Transformamos la letra a miniscula.

		       			//String palabra = teclado.readLine().toLowerCase();
		       			int intentos = 5;
		       			int numLetras = palabra.length();
		       			int numEspacios= numEspacios(palabra,numLetras);
		       			String palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
		       			
		       			
		       			out.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
		       			out.flush();
		       			
		       			while (palabraOculta.contains("-") && intentos>0) {
		       				
		       				out.writeBytes("Introduce una letra o la palabra:\n");//2
		       				out.flush();
		       				String letra = in.readLine();//3
       								       				
       						if(igualTamaño(palabra, letra)) {
		       					
       							if(palabra.equals(letra)) {
       								palabraOculta=modificarOcultaEntera(palabraOculta,palabra,numLetras);
		       						out.writeBytes(mensajeGanarPalabra(letra, palabra));
		       						out.flush();
		       					}
		       					else {
		       						intentos--;
		       						if(intentos==0) {//Si gastamos los intentos el juego termina
		       							out.writeBytes(mensajePerder(letra, palabra, intentos));
		       							out.flush();
		       						}else {	//aun quedan intentos, el juego continua
		       							out.writeBytes(mensajePalabraIncorrecta(letra, palabraOculta, intentos));
		       							out.flush();
		       						}
		       					}
		       				}
		       				
		       				else if(unaLetra(letra)) {//hay que introducir solo una letra
		       					
		       					if(palabra.contains(letra)) {
		       						
		       						if(palabraOculta.contains(letra)) {//Vemos si la letra está en la palabra.
		       							intentos--;
		       							if(intentos==0) {//Si gastamos los intentos el juego termina
			       							out.writeBytes(mensajePerder(letra, palabra, intentos));
			       							out.flush();
			       						}else {	//aun quedan intentos, el juego continua
			       							out.writeBytes(mensajeLetraYaEsta(letra, palabraOculta, intentos));
			       							out.flush();
			       						}
		       						}
		       						else {//sino la añade
		       							palabraOculta= modificarOculta(palabraOculta,palabra,letra,numLetras);
		       							if(palabraOculta.equals(palabra)) {	//si ya has acertado la palabra
		       								out.writeBytes(mensajeGanar(letra, palabraOculta));
		       								out.flush();
		       							}
		       							else {	//si faltan letras por adivinar
		       								out.writeBytes(mensajeLetraCorrecta(letra, palabraOculta, intentos));
		       								out.flush();
		       							}
		       						}		
		       					}
		       					else {
		       						intentos--;
		       						if(intentos==0) {//Si gastamos los intentos el juego termina
		       							out.writeBytes(mensajePerder(letra, palabra, intentos));
		       							out.flush();
		       						}else {	//aun quedan intentos, el juego continua
		       							out.writeBytes(mensajeLetraIncorrecta(letra, palabraOculta, intentos));
		       							out.flush();
		       						}
		       					}
		       				}
		       				else {
		       					out.writeBytes("ERROR: Introduce otra letra.\n");
		       					out.flush();
		       				}
		       			}
		       			out.writeBytes(" \n");
		       			out.flush();
		            	
		            	break;
		            	   
		               case "2":
		            	   salir=true;
		            	   break;
			
		           }
		    }
			
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}
	
	
	private String mensajeLetraYaEsta(String letra, String palabraOculta, Integer intentos) {
		return("La letra " + letra + " ya está. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	private String mensajeGanarPalabra(String letra, String palabraOculta) {
		return("La palabra " + letra + " es correcta.	¡¡HAS GANADO!!\n");
	}
	
	private String mensajeGanar(String letra, String palabraOculta) {
		return("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡HAS GANADO!!\n");
	}
	
	private String mensajePerder(String letra, String palabra, Integer intentos) {
		return("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!! La palabra es: " + palabra + "\n");
	}
	
	private String mensajeLetraCorrecta(String letra, String palabraOculta, Integer intentos) {
		return("La letra " + letra + " es correcta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	private String mensajeLetraIncorrecta(String letra, String palabraOculta, Integer intentos) {
		//intentos--;
		return("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	private String mensajePalabraIncorrecta(String palabraIncorrecta, String palabraOculta, Integer intentos) {
		//intentos--;
		return("La palabra " + palabraIncorrecta + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	
	public boolean igualTamaño(String palabra, String palabraOculta) {
		if(palabra.length() == palabraOculta.length()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean unaLetra(String letra) {
		if(letra.length() == 1 && Character.isLetter(letra.charAt(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public String palabraOcultaConGuiones(String palabra, int numLetras) {
		String palabraOculta="";
		for(int i=0; i<numLetras; i++) {
				if(palabra.charAt(i) == (" ").charAt(0)){
					palabraOculta = palabraOculta + " ";
				}
				else {
					palabraOculta = palabraOculta + "-";
				}
		}
		return palabraOculta;
	}
	
	public Integer numEspacios(String palabra, int numLetras) {
		int numEspacios=0;
		for(int i=0; i<numLetras; i++) {
			if(palabra.charAt(i) == (" ").charAt(0)){
				numEspacios++;
			}
		}
		return numEspacios;
	}
	
	public List<String> crearLista (BufferedReader br){
		
		List<String> palabras = new ArrayList<String>(); //creamos una lista con las palabras del fichero palabra.txt
		try {
			String linea = br.readLine();
			while (linea!=null) {
				palabras.add(linea);
				linea = br.readLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return palabras;
	}
	
	public String modificarOcultaEntera(String palabraOculta, String palabra, int numLetras) {
		StringBuilder sb1 = new StringBuilder(palabraOculta);
			for(int i=0; i<numLetras; i++) {
				for(int j=0; j<numLetras; j++) {
					if(palabra.charAt(i) == palabra.charAt(j)){
						sb1.setCharAt(i, palabra.charAt(j));
					}
				}
			}
		palabraOculta = sb1.toString();
		return palabraOculta;
	}
	
	public String modificarOculta(String palabraOculta, String palabra, String letra, int numLetras) {
		StringBuilder sb = new StringBuilder(palabraOculta);
			for(int i=0; i<numLetras; i++) {
				if(palabra.charAt(i) == letra.charAt(0)){
					sb.setCharAt(i, letra.charAt(0));
				}
			}
		palabraOculta = sb.toString();
		return palabraOculta;
	}

}
