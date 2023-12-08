import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		List<Socket> jugadores = new ArrayList<Socket>();
		
		try(ServerSocket ss = new ServerSocket(8000)){
			System.out.println("Servidor funcionando...");
			while(true) {	
				try{
					Socket s = ss.accept();
					PeticionAhorcado pa =new PeticionAhorcado(s,ss,jugadores);		
					//pa.start();
					//pa.join();
					pool.execute(pa);
				}
				catch (IOException e){
					e.printStackTrace();
				} /*catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
			}

}
