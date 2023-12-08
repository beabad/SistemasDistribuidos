import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PeticionAhorcado extends Thread{
	
	private Socket s;
	private ServerSocket ss;
	List<Socket> jugadores = new ArrayList<Socket>();
	
	public PeticionAhorcado(Socket s, ServerSocket ss,List<Socket> jugadores) {
		super();
		this.s=s;
		this.ss=ss;
		this.jugadores=jugadores;
	}
	
	public void run() {
		try(DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("./src/palabras.txt")));)
			//DataInputStream teclado = new DataInputStream(System.in);)
		{
			boolean salir = false;
		    String opcion;
		    int intentos = 5;
		    List<String> palabras = crearLista(br);

		    while(!salir){
		           opcion = in.readLine();
		           
		           //if(Integer.valueOf(opcion)==1) {
		           switch(opcion.trim()){
		               case "1":
		       			Random random = new Random();
		       			int indice = random.nextInt(palabras.size());
		       			String palabra = palabras.get(indice).toLowerCase();//Obtenemos la palabra random en minisculas.
		       					       			
		       			int numLetras = palabra.length();
		       			int numEspacios= numEspacios(palabra,numLetras);
		       			String palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
		       			
		       			out.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
		       			out.flush();
		       			jugada(palabra, palabraOculta, intentos, numLetras, out, in);

		       			break;
		            	   
		               case "2":
		            	   jugadores.add(s);
	            		   
							if (jugadores.size() == 2) {
		            	   
		            	   
		            			   try(Socket s1 = ss.accept();
		            					DataInputStream in1 = new DataInputStream(s1.getInputStream());
		            					DataOutputStream out1 = new DataOutputStream(s1.getOutputStream());
		            				){
		            				   //System.out.println(in.readLine());
		            				   //System.out.println("entra");
		            				    out.writeBytes("¡Eres el cliente que introduce una palabra! Introduce una letra o palabra: \n");
		            				   
		   		            	    	//out.writeBytes("Introduce una letra o palabra: \n");
		     		       				out.flush();
		     		       				palabra = in.readLine().toLowerCase();
		     		       				
		            				    out1.writeBytes("¡Eres el cliente que adivina una palabra! Pon OK: \n");
		            				    out1.flush();
		     		       				String linea = in.readLine();
		     		       				//System.out.println(palabra);
		     		       				
		     		       				numLetras = palabra.length();
		     		       				numEspacios= numEspacios(palabra,numLetras);
		     		       				palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
		       		       				out1.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
		       		       				out1.flush();
		       		       				jugada(palabra, palabraOculta, intentos, numLetras, out1, in1);
				            	  // }
		            		   
		            	   	}catch (IOException e){
	           					e.printStackTrace();
	           				}
							}
		            		break; 
		            
		               case "3":
		            	   jugadores.add(s);
	            		   
							if (jugadores.size() == 2) {
								Socket s2 = jugadores.get(0);
								Socket s1 = jugadores.get(1);
								try (//Socket s1 = ss.accept();
										DataOutputStream out2 = new DataOutputStream(s2.getOutputStream());
										DataOutputStream out1 = new DataOutputStream(s1.getOutputStream());
										DataInputStream in2 = new DataInputStream(s2.getInputStream());
										DataInputStream in1 = new DataInputStream(s1.getInputStream()))
								{
									out2.writeBytes("¡Eres el cliente 1! \n");
									out1.writeBytes("¡Eres el cliente 2! \n");
									//System.out.println(in2.readLine());
									palabra = obtenerPalabra(palabras);   
									numLetras = palabra.length();
									numEspacios= numEspacios(palabra,numLetras);
									palabraOculta = palabraOcultaConGuiones(palabra, numLetras);
									out2.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
									out2.flush();
									out1.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta+"\n");//1
						  			out1.flush();
						  			jugada(palabra, palabraOculta, intentos, numLetras, out2, in2);
						  			jugada(palabra, palabraOculta, intentos, numLetras, out1, in1);
						  			
						  			
						  			/*try {
						  				//System.out.println("hola");
						  				while (palabraOculta.contains("-") && intentos>0) {
						  					out2.writeBytes("Introduce una letra o la palabra:\n");//2
						  					out2.flush();
						  					System.out.println("hola");
						  					System.out.println(in2.readLine());
						  					System.out.println("hola2");
						  					String letra = in2.readLine();//3
						  					//String letra = in1.readLine();//3
						  					
						  					if(igualTamaño(palabra, letra)) {
						  						if(palabra.equals(letra)) {
						  							palabraOculta=modificarOcultaEntera(palabraOculta,palabra,numLetras);
						  							out2.writeBytes(mensajeGanarPalabra(letra, palabra));
						  							out2.flush();
						  						}
						  						else {
						  							intentos--;
						  							if(intentos==0) {//Si gastamos los intentos el juego termina
						  								out2.writeBytes(mensajePerderPalabra(letra, palabra, intentos));
						  								out2.flush();
						  							}else {	//aun quedan intentos, el juego continua
						  								out2.writeBytes(mensajePalabraIncorrecta(letra, palabraOculta, intentos));
						  								out2.flush();
						  							}
						  						}
						  					}
						  					
						  					else if(unaLetra(letra)) {//hay que introducir solo una letra
						  						
						  						if(palabra.contains(letra)) {
						  							if(palabraOculta.contains(letra)) {//Vemos si la letra está en la palabra.
						  								intentos--;
						  								if(intentos==0) {//Si gastamos los intentos el juego termina
						  									out2.writeBytes(mensajePerderLetra(letra, palabra, intentos));
						  									out2.flush();
						  								}else {	//aun quedan intentos, el juego continua
						  									out2.writeBytes(mensajeLetraYaEsta(letra, palabraOculta, intentos));
						  									out2.flush();
						  								}
						  							}
						  							else {//sino la añade
						  								palabraOculta= modificarOculta(palabraOculta,palabra,letra,numLetras);
						  								if(palabraOculta.equals(palabra)) {	//si ya has acertado la palabra
						  									out2.writeBytes(mensajeGanarLetra(letra, palabraOculta));
						  									out2.flush();
						  								}
						  								else {	//si faltan letras por adivinar
						  									out2.writeBytes(mensajeLetraCorrecta(letra, palabraOculta, intentos));
						  									out2.flush();
						  								}
						  							}		
						  						}
						  						else {
						  							intentos--;
						  							if(intentos==0) {//Si gastamos los intentos el juego termina
						  								out2.writeBytes(mensajePerderLetra(letra, palabra, intentos));
						  								out2.flush();
						  							}else {	//aun quedan intentos, el juego continua
						  								out2.writeBytes(mensajeLetraIncorrecta(letra, palabraOculta, intentos));
						  								out2.flush();
						  							}
						  						}
						  					}
						  					else {
						  						out2.writeBytes("ERROR: Introduce otra letra.\n");
						  						out2.flush();
						  					}
						  				}
						  				out2.writeBytes(" \n");
						  				out2.flush();
						  			} catch(IOException e) {
						  				e.printStackTrace();
						  			}*/
						  			
						  			
						  			
								}
								
								catch (IOException e){
									e.printStackTrace();
								}
					            
					        }
		            	   
		            		   
		            	   /*}
		            	   catch (IOException e){
	           					e.printStackTrace();
	           				}
		            	   
		            	   
	       		       				/*long tiempo, tiempo1;
	       		       				tiempo = in.readLong();	        //recibe tiempo el jug1
	       		       				tiempo1 = in1.readLong();		//recibe tiempo el jug2
	       		       				if(tiempo < tiempo1) {          //manda o que gana o que pierde a cada jugador
	       		       					out.writeBytes("Has ganado!! Tu adversario ha tardado " +  tiempo1 + " segundos. \n");
	       		       					out.flush();
	       		       					out1.writeBytes("Has perdido :( Tu adversario ha tardado " +  tiempo + " segundos. \n");
	       		       					out1.flush();
	       		       				}
	       		       				else {
		       		       				out.writeBytes("Has perdido :( Tu adversario ha tardado " +  tiempo1 + " segundos.  \n");
	       		       					out.flush();
	       		       					out1.writeBytes("Has ganado!! Tu adversario ha tardado " +  tiempo1 + " segundos.  \n");
	       		       					out1.flush();
	       		       				}
		            		   //}
		            	   }
		            	   catch (IOException e){
	           					e.printStackTrace();
	           				}*/
		            	   break;
		            	   
		              
		               case "4":
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
	   	//random = new Random();
		int indice = random.nextInt(palabras.size());
		String palabra = palabras.get(indice).toLowerCase();//Obtenemos la palabra random en minisculas.
		return palabra;
	}	
		
		
	private void jugada(String palabra, String palabraOculta, int intentos, int numLetras, DataOutputStream out, DataInputStream in) {
		try {
			//System.out.println("hola");
			while (palabraOculta.contains("-") && intentos>0) {
				out.writeBytes("Introduce una letra o la palabra:\n");//2
				out.flush();
				String letra = in.readLine();//3
				//System.out.println("hola");
				if(igualTamaño(palabra, letra)) {
					if(palabra.equals(letra)) {
						palabraOculta=modificarOcultaEntera(palabraOculta,palabra,numLetras);
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
	}
	
	
	private String mensajeLetraYaEsta(String letra, String palabraOculta, Integer intentos) {
		return("La letra " + letra + " ya está. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
	}
	
	private String mensajeGanarPalabra(String letra, String palabraOculta) {
		return("La palabra " + letra + " es correcta.	¡¡HAS GANADO!!\n");
	}
	
	private String mensajeGanarLetra(String letra, String palabraOculta) {
		return("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡HAS GANADO!!\n");
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