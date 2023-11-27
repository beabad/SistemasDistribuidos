import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {

	public static void main(String[] args) {
		
		try(ServerSocket ss = new ServerSocket(8000)){
			System.out.println("Servidor funcionando...");
			while(true) {
				
				try{
					Socket s = ss.accept();
					PeticionAhorcado pa =new PeticionAhorcado(s);		
					pa.start();
				}

				//PRUEBA PARA VER SI LEE ALGO
				/*try(Socket s = ss.accept();
					DataInputStream in = new DataInputStream(s.getInputStream());
					Writer out = new OutputStreamWriter(s.getOutputStream()))
				{
					System.out.println("La linea es:");
					String linea = in.readLine();
					System.out.println("La linea es:" + linea);
					while(linea!=null) {
						out.write("Recibido\n");
						out.flush();
						linea = in.readLine();
						
					}
					
					
				}*/
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		

	}

}
