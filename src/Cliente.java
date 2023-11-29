import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		try(Socket s = new Socket("localhost",8000);
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream teclado = new DataInputStream(System.in);)
		{
			
			System.out.println("Bienvenido al juego del Ahorcado\n");
			long tiempoInicial = System.currentTimeMillis();
			
			System.out.println(in.readLine());//1
			String linea = in.readLine();//2
			System.out.println(linea);
			
			while(linea!=null && !linea.equals(" ")) {
				String letra = teclado.readLine().toLowerCase(); //Transformamos la letra a minuscula.
			
				//System.out.println(linea);
				out.writeBytes(letra+"\n");//3
				out.flush();
				
				System.out.println(in.readLine());//4
				linea = in.readLine();
				System.out.println(linea);
			}
			
			System.out.println("-----------------------------------");
			System.out.println("EL JUEGO HA TERMINADO");
			System.out.println("-----------------------------------\n");
			long tiempo = System.currentTimeMillis() - tiempoInicial;
			System.out.println("Has tardado " + tiempo/1000 + " segundos.");
		
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

}
