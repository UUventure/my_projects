import java.util.Scanner;


public class MainClass {
	public static void main(String[] args) {
		try {
			System.out.print("Port: ");
			int port = new Scanner(System.in).nextInt();
			new ServerCore(port).start();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}