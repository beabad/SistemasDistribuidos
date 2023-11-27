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
			String palabra = teclado.readLine();
			int numLetras = palabra.length();
			
			String palabraOculta = "";
			for(int i=0; i<numLetras; i++) {
				palabraOculta = palabraOculta + "-";
			}
			System.out.println("Palabra oculta: " + palabraOculta);
			int intentos = 5;
			System.out.println("Tu adversario tiene " + intentos + " intentos.\n");
					
			
			out.writeBytes("La palabra a adivinar tiene " + numLetras + " letras.\n");
			
			while (palabraOculta.contains("-") || intentos>0) {
				
				out.writeBytes("Introduce una letra:\n");
				out.flush();
				
				String letra = in.readLine();
				//System.out.println(letra);
				
				if(palabra.contains(letra)) {
					StringBuilder sb = new StringBuilder(palabraOculta);
					for(int i=0; i<numLetras; i++) {
						if(palabra.charAt(i) == letra.charAt(0)){
							//palabraOculta.replace(palabra.charAt(i), letra.charAt(0));
							sb.setCharAt(i, letra.charAt(0));
						}
					}
					palabraOculta = sb.toString();
					System.out.println("Estado de la palabra oculta: " + palabraOculta);
					out.writeBytes("La letra " + letra + " es correcta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "\n");
//					out.writeBytes("Introduce una letra:\n");
					//out.flush();
					
				}
				else {
					intentos--;
					out.writeBytes("La letra " + letra + " es incorrecta. Tienes " + intentos + " intentos. La palabra oculta es: "+palabraOculta + "n");
					out.flush();
				}
				
			}
			
		}
		
		catch (IOException e){
			e.printStackTrace();
		} 
		
	}

}
