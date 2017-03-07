import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;


public class ServerCore {

	private ServerSocket server;
	private ArrayList<Socket> sList = new ArrayList<Socket>();
	
	public ServerCore(int port) {
		try {
			server = new ServerSocket(port);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	public class SocketListener implements Runnable {
		
		private Socket client;
		private BufferedReader reader;
		
		public SocketListener(Socket client) {
			try {
				this.client = client;
				reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void run() {
			while (client.isClosed() != true) {
				try {
					String message = reader.readLine();
					if (message != null) {
						String[] mArr = message.split(": ");
						if (mArr.length != 1) {
							System.out.println("Message: "+message);
							new Thread(new SendEveryone(message)).start();
						}
					}
				} catch(Exception ex) {
					ex.printStackTrace();
					break;
				}
			}
			
			sList.remove(client);
		}
		
		
	}
	
	
	
	
	public class SendEveryone implements Runnable {
		
		private String message;
		
		public SendEveryone(String message) {
			this.message = message;
		}
		
		public void run() {
			sendEveryone(message);
		}
		
		public void sendEveryone(String message) {
			try {
				for(int i = 0; i < sList.size(); i++) {
					PrintWriter writer = new PrintWriter(sList.get(i).getOutputStream());
					writer.println(message);
					writer.flush();
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	
	public void start() {
		System.out.println("Server started");
		try {
			while (true) {
				Socket sock = server.accept();
				sList.add(sock);
				new Thread(new SocketListener(sock)).start();
				
				System.out.println("Got a connection!");
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}