import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		try(Socket s = new Socket("localhost",8000);
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());
				DataInputStream teclado = new DataInputStream(System.in))
			{
				
				System.out.println("Bienvenido al juego del Ahorcado\n");
				System.out.println(in.readLine());
				String linea = in.readLine();
				System.out.println(linea);
				
				while(linea!=null) {
					String letra = teclado.readLine();
					
					//System.out.println(linea);
					out.writeBytes(letra+"\n");
					out.flush();
					
					System.out.println(in.readLine());
					linea = in.readLine();
				}
				
				String letra = teclado.readLine();
								
				//System.out.println(linea);
				out.writeBytes(letra+"\n");
				out.flush();
				
				System.out.println(in.readLine());
				
				

				
				
			}
			catch (IOException e){
				e.printStackTrace();
			}

	}

}
