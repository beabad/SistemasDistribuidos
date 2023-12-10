package trabajo;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class PeticionAhorcado extends Thread{
	
	private Socket s;
	private Socket s1;
	
	public PeticionAhorcado(Socket s, Socket s1) {
		super();
		this.s=s;
		this.s1=s1;
	}
	
	public void run() {
		try(DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in1 = new DataInputStream(s1.getInputStream());
			DataOutputStream out1 = new DataOutputStream(s1.getOutputStream());
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("./src/palabras.txt")));)
		{
			boolean salir = false;
		    String opcion,opcion1;
		    int intentos = 5;
		    List<String> palabras = crearLista(br);
		    while(!salir){
		           opcion = in.readLine();
		           opcion1 = in1.readLine();
		           switch(opcion.trim()){
		               case "1":
		            	   if (opcion1.equals(opcion)) {
		            		   out.writeBytes("¡Eres el cliente que introduce una palabra! Introduce una letra o palabra: \n");
		            		   out.flush();
		            		   String palabra = in.readLine().toLowerCase();
		            		   out.writeBytes(" \n");
		            		   out.writeBytes(" \n");
		            		   out1.writeBytes("¡Eres el cliente que adivina una palabra! Pon OK:\n");
		            		   out1.flush();
		            		   String linea = in1.readLine();
		            		   int numLetras = palabra.length();
		            		   int numEspacios= numEspacios(palabra,numLetras);
		            		   String palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
		            		   out1.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
		            		   out1.flush();
		            		   long tiempoInicial = System.currentTimeMillis();
		            		   boolean ganar = jugada(palabra, palabraOculta, intentos, numLetras, out1, in1);
		            		   long tiempo = (System.currentTimeMillis() - tiempoInicial)/1000;
		            		   out.writeBytes("\n");
		            		   out.writeBytes("------------------------------------\n");
		            		   out.writeBytes("---- Ha terminado la partida :( ----\n");
		            		   out.writeBytes("------------------------------------\n");
		            		   out.flush();
		            		   out1.writeBytes("Has tardado " + tiempo + " segundos.\n");
		            		   out1.writeBytes("------------------------------------\n");
		            		   out1.writeBytes("---- Ha terminado la partida :( ----\n");
		            		   out1.writeBytes("------------------------------------\n");
		            		   out1.flush();
		            		   salir=true;
		            	   }
		            	   break; 
		            
		               case "2":		            	   
		            	   if (opcion1.equals(opcion)) {
								out.writeBytes("¡Eres el cliente 1! \n");
								out1.writeBytes("¡Eres el cliente 2! \n");
								out.writeBytes("Introduce tu nombre: \n");
								out.flush();
								out1.writeBytes("Introduce tu nombre: \n");
								out1.flush();
								String nom1 = in.readLine();
								String nom2 = in1.readLine();
								
								String palabra = obtenerPalabra(palabras);   
								int numLetras = palabra.length();
								int numEspacios= numEspacios(palabra,numLetras);
								String palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
								out1.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
						  		out1.flush();
								out.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
								out.flush();
								boolean ganar =jugada(palabra, palabraOculta, intentos, numLetras, out, in);
								boolean ganar1 =jugada(palabra, palabraOculta, intentos, numLetras, out1, in1);
						  		long tiempo, tiempo1;
       		       				tiempo = in.readLong();	        //recibe tiempo el jug1
       		       				tiempo1 = in1.readLong();		//recibe tiempo el jug2
       		       				if(ganar == true && ganar1==true) {
	       		       				if(tiempo < tiempo1) {          //manda o que gana o que pierde a cada jugador
	       		       					out.writeBytes(nom1 + " , has ganado!! Tu adversario ha tardado " +  tiempo1 + " segundos. \n");
	       		       					out.flush();
	       		       					out1.writeBytes(nom2 +  " , has perdido :( Tu adversario ha tardado " +  tiempo + " segundos. \n");
	       		       					out1.flush();
	       		       				}
	       		       				else {
		       		       				out.writeBytes(nom1 + " , has perdido :( Tu adversario ha tardado " +  tiempo1 + " segundos.  \n");
	       		       					out.flush();
	       		       					out1.writeBytes(nom2 + " , has ganado!!! Tu adversario ha tardado " +  tiempo + " segundos.  \n");
	       		       					out1.flush();
	       		       				}
       		       				}
       		       				if(ganar == false && ganar1 == true) {
	       		       				out.writeBytes(nom1 + " , has perdido :( Tu adversario ha acertado la palabra.  \n");
	   		       					out.flush();
	   		       					out1.writeBytes(nom2 + " , has ganado!!! Tu adversario no ha acertado la palabra.  \n");
	   		       					out1.flush();
       		       				}
	       		       			if(ganar == true && ganar1 == false) {
	       		       				out1.writeBytes(nom2 + " , has perdido :( Tu adversario ha acertado la palabra.  \n");
	       		       				out1.flush();
	       		       				out.writeBytes(nom1 + " , has ganado!!! Tu adversario no ha acertado la palabra.  \n");
	       		       				out.flush();
	   		       				}
		       		       		if(ganar == false && ganar1 == false) {
	       		       				out1.writeBytes(nom2 + " , habeis perdido :( Tu adversario tampoco ha acertado la palabra.  \n");
	       		       				out1.flush();
	       		       				out.writeBytes(nom1 + " , habeis perdido :( Tu adversario tampoco ha acertado la palabra.  \n");
	       		       				out.flush();
	   		       				}
       		       				
						  		salir=true;
		            	   }
		            	   break;
		            	    
		               case "3":
		            	   salir=true;
		            	   break;
		           }
		    }
			
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}
	
	private String obtenerPalabra(List<String> palabras) {
		Random random = new Random();
		int indice = random.nextInt(palabras.size());
		String palabra = palabras.get(indice).toLowerCase();//Obtenemos la palabra random en minisculas.
		return palabra;
	}	
		
		
	private boolean jugada(String palabra, String palabraOculta, int intentos, int numLetras, DataOutputStream out, DataInputStream in) {
		boolean ganar = false;
		try {
			while (palabraOculta.contains("-") && intentos>0) {
				out.writeBytes("Introduce una letra o la palabra:\n");//2
				out.flush();
				String letra = in.readLine();//3
				if(igualTamaño(palabra, letra)) {
					if(palabra.equals(letra)) {
						palabraOculta=modificarOcultaEntera(palabraOculta,palabra,numLetras);
						ganar=true;
						out.writeBytes(mensajeGanarPalabra(letra, palabra));
						out.flush();
					}
					else {
						intentos--;
						if(intentos==0) {//Si gastamos los intentos el juego termina
							out.writeBytes(mensajePerderPalabra(letra, palabra, intentos));
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
								out.writeBytes(mensajePerderLetra(letra, palabra, intentos));
								out.flush();
							}else {	//aun quedan intentos, el juego continua
								out.writeBytes(mensajeLetraYaEsta(letra, palabraOculta, intentos));
								out.flush();
							}
						}
						else {//sino la añade
							palabraOculta= modificarOculta(palabraOculta,palabra,letra,numLetras);
							if(palabraOculta.equals(palabra)) {	//si ya has acertado la palabra
								ganar=true;
								out.writeBytes(mensajeGanarLetra(letra, palabraOculta));
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
							out.writeBytes(mensajePerderLetra(letra, palabra, intentos));
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
		} catch(IOException e) {
			e.printStackTrace();
		}
		return ganar;	
	}
	
	
	private String mensajeLetraYaEsta(String letra, String palabraOculta, Integer intentos) {
		return("La letra " + letra + " ya está. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	private String mensajeGanarPalabra(String letra, String palabraOculta) {
		return("La palabra " + letra + " es correcta.	¡¡LO HAS CONSEGUIDO!!\n");
	}
	
	private String mensajeGanarLetra(String letra, String palabraOculta) {
		return("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡LO HAS CONSEGUIDO!!\n");
	}
	
	private String mensajePerderPalabra(String letra, String palabra, Integer intentos) {
		return("La palabra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!! La palabra es: " + palabra + "\n");
	}
	
	private String mensajePerderLetra(String letra, String palabra, Integer intentos) {
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
	
	
	private boolean igualTamaño(String palabra, String palabraOculta) {
		if(palabra.length() == palabraOculta.length()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean unaLetra(String letra) {
		if(letra.length() == 1 && Character.isLetter(letra.charAt(0))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private String palabraOcultaConGuiones(String palabra, int numLetras) {
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
	
	private Integer numEspacios(String palabra, int numLetras) {
		int numEspacios=0;
		for(int i=0; i<numLetras; i++) {
			if(palabra.charAt(i) == (" ").charAt(0)){
				numEspacios++;
			}
		}
		return numEspacios;
	}
	
	private List<String> crearLista (BufferedReader br){
		
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
	
	private String modificarOcultaEntera(String palabraOculta, String palabra, int numLetras) {
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
	
	private String modificarOculta(String palabraOculta, String palabra, String letra, int numLetras) {
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