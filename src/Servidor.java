package trabajo;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Servidor {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try(ServerSocket ss = new ServerSocket(8000)){
			System.out.println("Servidor funcionando...");
			while(true) {	
				try{
					Socket s = ss.accept();
					Socket s1 = ss.accept();
					PeticionAhorcado pa =new PeticionAhorcado(s,s1);
					pool.execute(pa);
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
