import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.text.Font;


public class ChatApp extends Application {
	
	private Button sendButton;
	private TextArea msgArea;
	private TextField sendField;
	private Client client;
	private Thread t;
	private static boolean isClosed = false;
	String nick;
	
	private final String NAME = "My Simple Graphic Chat"; 
	
	public ChatApp(String address, int port) {
		try {
			client = new Client(address, port);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void start(Stage app) {
		
		Font font = new Font(14);
		
		sendButton = new Button("Send");	// создаем части графического интерфейса и подгоняем под нужные размеры
		sendButton.setFont(font);
		sendButton.setPrefSize(60,30);
		
		msgArea = new TextArea();
		msgArea.setWrapText(true);
		msgArea.setPrefSize(480,260);
		msgArea.setFont(font);
		msgArea.setEditable(false);
		
		sendField = new TextField();
		sendField.setFont(font);
		sendField.setPrefSize(420,30);
		
		VBox box = new VBox(10);
		HBox msgBox = new HBox(10);
		
		box.setAlignment(Pos.CENTER);
		msgBox.setAlignment(Pos.CENTER);
												// заканчиваем делать фрагменты и начинаем объединять их в единый интерфейс
		ObservableList<Node> msgList = msgBox.getChildren();
		msgList.add(sendField);
		msgList.add(sendButton);		// тут создается панель отправки сообщений
		
		ObservableList<Node> vList = box.getChildren();
		vList.add(msgArea);
		vList.add(msgBox);		// а тут я совмещаю эту панельку с полем, где эти сообщения будут показываться
		
		
		sendButton.setOnAction(new MessageSender());
		
		t = new Thread(new MessageReader()); 
		t.start(); // запускаем класс, принимающий сообщения в новом потоке
		
		app.setTitle(NAME); // задаем имя, размеры окна, и т.д.
		app.setScene(new Scene(box, 640, 480));
		app.setResizable(false);
		app.setOnCloseRequest(new CloseRequest());
		app.addEventFilter(KeyEvent.KEY_PRESSED, new KeyListener());
		app.showAndWait(); // и показываем его
		
	}
	
	
	
	public class MessageReader implements Runnable {	// этот класс будет читать сообщения и отображать их
		
		public void run() {
			while (!isClosed) {
				try {
					String message = client.readMessage();
					msgArea.appendText(message+"\n");
				} catch(Exception ex) {
					ex.printStackTrace();
					break;
				}
			}
			if (isClosed) {
				System.exit(0);
			}
		}
		
	}
	
	
	
	public class MessageSender implements EventHandler<ActionEvent> { // а этот класс будет сообщения отправлять, его я привязал в кнопке
		
		public void handle(ActionEvent event) {
			try {
				client.sendMessage(nick + ": " + sendField.getText());
				sendField.clear();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	
	
	public class CloseRequest implements EventHandler<WindowEvent> {
		
		public void handle(WindowEvent event) {
			
			isClosed = true;
			//WindowEvent.WINDOW_CLOSE_REQUEST;
		}
		
	}
	
	
	
	public class KeyListener implements EventHandler<KeyEvent> {
		
		public void handle(KeyEvent event) {
			if (event.getCode() == KeyCode.ENTER) {
				try {
					client.sendMessage(nick + ": " + sendField.getText());
					sendField.clear();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
	}
} 