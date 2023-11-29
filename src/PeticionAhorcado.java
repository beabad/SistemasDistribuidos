import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PeticionAhorcado extends Thread{
	
	private Socket s;
	
	public PeticionAhorcado(Socket s) {
		super();
		this.s = s;
	}
	
	public void run() {
		try(DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream teclado = new DataInputStream(System.in);)
		{
			System.out.println("Bienvenido al juego del Ahorcado\n");
			System.out.println("-----------------------------------");
			System.out.println("Introduce una palabra:");
			String palabra = teclado.readLine().toLowerCase();//Transformamos la letra a miniscula.
//			palabra=palabra.toLowerCase();
			int numLetras = palabra.length();
			int numEspacios=0;
			
			String palabraOculta = "";
			for(int i=0; i<numLetras; i++) {
				if(palabra.charAt(i) == (" ").charAt(0)){
					palabraOculta = palabraOculta + " ";
					numEspacios++;
				}
				else {
					palabraOculta = palabraOculta + "-";
				}
			}
			System.out.println("Palabra oculta: " + palabraOculta);
			int intentos = 5;
			System.out.println("Tu adversario tiene " + intentos + " intentos.\n");		
			
			out.writeBytes("La palabra a adivinar tiene " + (numLetras-numEspacios) + " letras.\n");//1
			out.flush();
			while (palabraOculta.contains("-") && intentos>0) {
				
				out.writeBytes("Introduce una letra:\n");//2
				out.flush();
				String letra = in.readLine();//3
				
				if(palabra.contains(letra)) {
					StringBuilder sb = new StringBuilder(palabraOculta);
					
					if(palabraOculta.contains(letra)) {//Vemos si la letra está en la palabra.
						intentos--;
						out.writeBytes("La letra " + letra + " ya está. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
						out.flush();
						System.out.println("Estado de la palabra oculta: " + palabraOculta);
					}
					else {//sino la añade
						for(int i=0; i<numLetras; i++) {
							if(palabra.charAt(i) == letra.charAt(0)){
								sb.setCharAt(i, letra.charAt(0));
								//palabraOculta = palabraOculta.replace(palabra.charAt(i), letra.charAt(0));
							}
						}
						palabraOculta = sb.toString();
						System.out.println("Estado de la palabra oculta: " + palabraOculta);
						if(palabraOculta.equals(palabra)) {	//si ya has acertado la palabra
							out.writeBytes("La letra " + letra + " es correcta. La palabra oculta es: "+palabraOculta + ".	¡¡HAS GANADO!!\n");
							out.flush();
						}
						else {	//si faltan letras por adivinar
							out.writeBytes("La letra " + letra + " es correcta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");//4
							out.flush();
						}
					}		
				}
				else {
					intentos--;
					if(intentos==0) {//Si gastamos los intentos el juego termina
						out.writeBytes("La letra " + letra + " es incorrecta. ¡¡Lo siento. Has perdido!!, la palabra es: " + palabra + "\n");
						out.flush();
					}else {	//aun quedan intentos, el juego continua
						out.writeBytes("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
						out.flush();
					}
					
					
				}
				
			}
			out.writeBytes(" ");
			out.flush();
			
		} catch (IOException e){
			e.printStackTrace();
		} 
		
	}

}
