import java.io.File;
import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Paint;


public class SideInfoScene extends SubScene{
	public static Pane pane = new Pane();
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 806;
	
	public SideInfoScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);
		
		this.setLayoutX(1300);
        this.setLayoutY(104);
        pane.setId("sideInfoScene");
        
        
        
        
	}
	

	
}
