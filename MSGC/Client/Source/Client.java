import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	 Socket connection;
	 BufferedReader reader;
	 PrintWriter writer;
	
	public Client(String address, int port) throws IOException {
		connect(address, port);
	}
	
	public void connect(String address, int port) throws IOException {
		connection = new Socket(address, port);
		reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		writer = new PrintWriter(connection.getOutputStream());
	}
	
	public void sendMessage(String message) {
		writer.println(message);
		writer.flush();
	}
	
	public String readMessage() throws IOException {
		String message = reader.readLine();
		return message;
	}
	
}