package application;

import java.io.File;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TopMenuBar extends Pane {

	private static MenuBar menuBar = new MenuBar();
	private static Menu fileMenu = new Menu("File");
	private static MenuItem openFile = new MenuItem("Open File(s)...");
	private static FileChooser fileChooser = new FileChooser();
	private static List<File> fileList;
	private static ImageView pspLogo = new ImageView("/application/PSP Logo.png");
	private static Button minButton = new Button("_");
	private static Button maxButton = new Button("O");
	private static Button closeButton = new Button("X");
	private static SerialConnector sc;

	public TopMenuBar(int x, int y) {
		super();
		fileMenu.getItems().add(openFile);
		fileChooser.setTitle("Open Telemetry File(s)");
		pspLogo.setFitWidth(30);
		pspLogo.setFitHeight(30);
		//fileMenu.setGraphic(pspLogo);
		//this.getChildren().add(pspLogo);
		menuBar.getMenus().add(fileMenu);
		menuBar.setStyle("-fx-font-size: 12pt;");
		menuBar.setMinWidth(x);

		closeButton.setPrefWidth(30);
		maxButton.setPrefWidth(30);
		minButton.setPrefWidth(30);

		closeButton.setLayoutX(1885);
		closeButton.setLayoutY(5);
		maxButton.setLayoutX(1850);
		maxButton.setLayoutY(5);
		minButton.setLayoutX(1815);
		minButton.setLayoutY(5);

		closeButton.setOnAction(event -> {
			sc.disconnectArduino();
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
		});

		this.setLayoutX(0);
		this.setLayoutY(0);
		this.getChildren().addAll(menuBar, minButton, maxButton, closeButton);
		//this.setMinWidth(x + 20);
		//this.setMinHeight(50);


		
	}

	public void setSc(SerialConnector sc){
		this.sc = sc;
	}
	
	public FileChooser getFileChooser(){
		return fileChooser;
	}
	
	public MenuItem getOpenFile() {
		return openFile;
	}
	
	public void inputFile(Stage stage) {
		fileList = this.getFileChooser().showOpenMultipleDialog(stage);
	}
	
	public List<File> getFileList() {
		return fileList;
	}
	
}
