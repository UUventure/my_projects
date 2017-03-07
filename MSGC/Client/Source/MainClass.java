import javafx.stage.Stage;
import javafx.application.Application;


public class MainClass extends Application {
	
	static String address;
	static int port;
	static String nick;
	
	public void start(Stage stage) {
		Stage chatApp = new Stage();
		
		new ConnectApp().start(new Stage());
		if (MainClass.address != null & MainClass.port != 0) {
			ChatApp app = new ChatApp(MainClass.address, MainClass.port);
			app.nick = nick;
			app.start(chatApp);
			chatApp.close();
		}
		stage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}