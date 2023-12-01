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

		    while(!salir){

		    	   //System.out.println("Vuelvo a entrar");
		           opcion = in.readLine();
		           //System.out.println(opcion);
		           switch(opcion.trim()){
		               case "1":
		            	
		       			List<String> palabras = new ArrayList<String>(); //creamos una lista con las palabras del fichero palabra.txt
		       			String linea = br.readLine();
		       			while (linea!=null) {
		       				palabras.add(linea);
		       				linea = br.readLine();
		       			}
		       			Random random = new Random();
		       			int indice = random.nextInt(palabras.size());
		       			String palabra = palabras.get(indice).toLowerCase();//Transformamos la letra a miniscula.
		       			//String palabra = teclado.readLine().toLowerCase();
		       			
		       			int numLetras = palabra.length();
		       			int numEspacios=0;
		       			/*String palabraOculta = "";// en este bucle creamos la palabra oculta con guiones
		       			for(int i=0; i<numLetras; i++) {
		       				if(palabra.charAt(i) == (" ").charAt(0)){
		       					palabraOculta = palabraOculta + " ";
		       					numEspacios++;
		       				}
		       				else {
		       					palabraOculta = palabraOculta + "-";
		       				}
		       			}*/
		       			String palabraOculta = palabraOcultaConGuiones(palabra, numLetras, numEspacios);
		       			int intentos = 5;
		       				
		       			out.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
		       			out.flush();
		       			while (palabraOculta.contains("-") && intentos>0) {
		       				out.writeBytes(mensajeIntroducir());//2
		       				out.flush();
		       				String letra = in.readLine();//3
       						StringBuilder sb = new StringBuilder(palabraOculta);
		       				if(igualTamaño(palabra, letra)) {
		       					if(palabra.equals(letra)) {
		       						out.writeBytes(mensajeGanar(letra, palabra));
		       						out.flush();
		       					}
		       					else {
		       						intentos--;
		       						if(intentos==0) {//Si gastamos los intentos el juego termina
		       							//out.writeBytes("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!!, la palabra es: " + palabra + "\n");
		       							out.writeBytes(mensajePerder(letra, palabra, intentos));
		       							out.flush();
		       						}else {	//aun quedan intentos, el juego continua
		       							//out.writeBytes("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
		       							out.writeBytes(mensajePalabraIncorrecta(letra, palabraOculta, intentos));
		       							out.flush();
		       						}
		       					}
		       				}
		       				//else if(Character.isLetter(letra.charAt(0))&&(letra.length()==1)) {//hay que introducir solo una letra
		       				else if(unaLetra(letra)) {
		       					if(palabra.contains(letra)) {
		       						//StringBuilder sb = new StringBuilder(palabraOculta);
		       						if(palabraOculta.contains(letra)) {//Vemos si la letra está en la palabra.
		       							intentos--;
		       							if(intentos==0) {//Si gastamos los intentos el juego termina
			       							//out.writeBytes("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!!, la palabra es: " + palabra + "\n");
			       							out.writeBytes(mensajePerder(letra, palabra, intentos));
			       							out.flush();
			       						}else {	//aun quedan intentos, el juego continua
			       							//out.writeBytes("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
			       							out.writeBytes(mensajeLetraYaEsta(letra, palabraOculta, intentos));
			       							out.flush();
			       						}
		       						}
		       						else {//sino la añade
		       							for(int i=0; i<numLetras; i++) {
		       								if(palabra.charAt(i) == letra.charAt(0)){
		       									sb.setCharAt(i, letra.charAt(0));
		       								}
		       							}
		       							palabraOculta = sb.toString();
		       							if(palabraOculta.equals(palabra)) {	//si ya has acertado la palabra
		       								//out.writeBytes("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡HAS GANADO!!\n");
		       								out.writeBytes(mensajeGanar(letra, palabraOculta));
		       								out.flush();
		       							}
		       							else {	//si faltan letras por adivinar
		       								//out.writeBytes("La letra " + letra + " es correcta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");//4
		       								out.writeBytes(mensajeLetraCorrecta(letra, palabraOculta, intentos));
		       								out.flush();
		       							}
		       						}		
		       					}
		       					else {
		       						intentos--;
		       						if(intentos==0) {//Si gastamos los intentos el juego termina
		       							//out.writeBytes("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!!, la palabra es: " + palabra + "\n");
		       							out.writeBytes(mensajePerder(letra, palabra, intentos));
		       							out.flush();
		       						}else {	//aun quedan intentos, el juego continua
		       							//out.writeBytes("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
		       							out.writeBytes(mensajeLetraIncorrecta(letra, palabraOculta, intentos));
		       							out.flush();
		       						}
		       					}
		       				}
		       				else {
		       					//out.writeBytes("ERROR: Introduce otra letra.\n");
		       					out.writeBytes(mensajeLetraError());
		       					out.flush();
		       				}
		       			}
		       			out.writeBytes(" ");
		       			out.flush();
		            	//System.out.println("fin");   
		       			
  
		            	//break;//Si comento esto si funciona pero la conexión se anula luego
		            	   
		               case "2":
		            	   salir=true;
		            	   break;
			
		           }
		    }
			//
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}
	
	public String mensajeIntroducir() {
		return ("Introduce una letra o la palabra:\n");
	}
	
	public String mensajeLetraYaEsta(String letra, String palabraOculta, Integer intentos) {
		//intentos--;
		return("La letra " + letra + " ya está. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	public String mensajeGanar(String letra, String palabraOculta) {
		return("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡HAS GANADO!!\n");
	}
	
	public String mensajePerder(String letra, String palabra, Integer intentos) {
		//intentos --;
		return("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!! La palabra es: " + palabra + "\n");
	}
	
	public String mensajeLetraCorrecta(String letra, String palabraOculta, Integer intentos) {
		return("La letra " + letra + " es correcta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	public String mensajeLetraIncorrecta(String letra, String palabraOculta, Integer intentos) {
		//intentos--;
		return("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	public String mensajePalabraIncorrecta(String palabraIncorrecta, String palabraOculta, Integer intentos) {
		//intentos--;
		return("La palabra " + palabraIncorrecta + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	public String mensajeLetraError() {
		return("ERROR: Introduce otra letra o la palabra.\n");
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
	
	public String palabraOcultaConGuiones(String palabra, int numLetras, int numEspacios) {
		String palabraOculta="";
		//int numEspacios=0;
		for(int i=0; i<numLetras; i++) {
				if(palabra.charAt(i) == (" ").charAt(0)){
					palabraOculta = palabraOculta + " ";
					numEspacios++;
				}
				else {
					palabraOculta = palabraOculta + "-";
				}
		}
		return palabraOculta;
	}

}
