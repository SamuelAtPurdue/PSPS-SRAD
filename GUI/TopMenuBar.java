

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TopMenuBar extends MenuBar{

	private static Menu fileMenu = new Menu("File");
	private static MenuItem openFile = new MenuItem("Open File(s)...");
	private static FileChooser fileChooser = new FileChooser();
	private static List<File> fileList;
	
	public TopMenuBar(int x, int y) {
		super();
		fileMenu.getItems().add(openFile);
		fileChooser.setTitle("Open Telemetry File(s)");
		this.getMenus().addAll(fileMenu);
		this.setMinWidth(x + 20);
	
		
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
