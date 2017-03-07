import java.io.*;
import java.net.*;


public class Sender {
	public static void main(String[] args) {
		try {
		
			Socket sock = new Socket("127.0.0.1", 2555);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter writer = new PrintWriter(sock.getOutputStream());
			
			while (true) {
				String message = reader.readLine();
				writer.println(message);
				writer.flush();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}