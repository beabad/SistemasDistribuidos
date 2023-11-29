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
