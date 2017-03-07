import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.event.*;

public class ConnectApp extends Application {	
	
	public void start(Stage app) {
		
		VBox pane = new VBox(15); // создаем и редактируем нужные компоненты
		pane.setAlignment(Pos.CENTER);
		
		HBox addressBox = new HBox(10);
		addressBox.setAlignment(Pos.CENTER);
		
		HBox nickBox = new HBox(10);
		nickBox.setAlignment(Pos.CENTER);
		
		Label lAddress = new Label("Address:");
		lAddress.setFont(new Font(16));
		
		Label lNick = new Label("Nickname:");
		lNick.setFont(new Font(16));
		
		Label connectText = new Label("");
		connectText.setAlignment(Pos.CENTER);
		connectText.setFont(new Font(13));
		
		TextField addressField = new TextField();
		addressField.setPrefSize(120,20);
		
		TextField nickField = new TextField();
		nickField.setPrefSize(110,20);
		
		Button connectButton = new Button("Connect");
		connectButton.setAlignment(Pos.CENTER);
		connectButton.setFont(new Font(14));
		
		// заканчиваем создание и начинаем прописывать действия для подключения после нажатия кнопки 
		
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				String[] address = addressField.getText().split(":");
				int port = Integer.parseInt(address[1]);
				try {
					Thread.sleep(500);
					MainClass.address = address[0];
					MainClass.port = port;
					MainClass.nick = nickField.getText();
					app.close();
				} catch(Exception ex) {
					connectText.setText("I can't to connect :(");
					connectText.setTextFill(Color.FIREBRICK);
					ex.printStackTrace();
				}
			}
		});
		
		
		ObservableList<Node> vList = pane.getChildren(); // далее делаем список для графических компонентов и добавляем их в него
		
		vList.add(connectText);
		
		addressBox.getChildren().add(lAddress);
		addressBox.getChildren().add(addressField);
		
		nickBox.getChildren().add(lNick);
		nickBox.getChildren().add(nickField);
		
		vList.add(addressBox);
		vList.add(nickBox);
		vList.add(connectButton);
		
		app.setTitle("Connect application"); // даем назвение приложению, задаем размеры...
		app.setScene(new Scene(pane,300,200));
		app.setResizable(false);
		app.showAndWait(); // показываем его
		
	}
	
}