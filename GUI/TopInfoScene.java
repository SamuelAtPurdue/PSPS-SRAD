import java.io.File;
import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Paint;


public class TopInfoScene extends SubScene{
	public static Pane pane = new Pane();
	private static final int X_SIZE = 1600;
	private static final int Y_SIZE = 80;
	private static Image sat_red;
	private static Image sat_green;
	private static Image replay;
	private static ImageView statusImage;
	private static Label modeLabel;
	private static Label time;
	private static int mode = 0;
	
	public TopInfoScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);
		
		this.setLayoutX(0);
        this.setLayoutY(25);
        pane.setId("topInfoScene");
        
        sat_red = new Image("sat_red.png");
        sat_green = new Image("sat_green.png");
        replay = new Image("rewind_arrow.png");
        
        statusImage = new ImageView();
        statusImage.setScaleX(0.6);
        statusImage.setScaleY(0.6);
        statusImage.setTranslateX(-10);
        statusImage.setTranslateY(-10);
        statusImage.setImage(sat_red);

        modeLabel = new Label();
        modeLabel.setId("modeLabel");
        modeLabel.setText("Unconnected");
        modeLabel.setTranslateX(80);
        modeLabel.setTranslateY(12);
        modeLabel.setTextFill(Paint.valueOf("FF6666"));
        
        time = new Label();
        time.setId("timeLabel");
        time.setText("T = 00:00");
        time.setLayoutX(1300);
        time.setLayoutY(18);
        

        pane.getChildren().addAll(statusImage, modeLabel, time);
        
	}
	
	public void setMode(int mode){
		if (mode == 2) {
			statusImage.setImage(replay);
			modeLabel.setText("Replay Mode");
			modeLabel.setTextFill(Paint.valueOf("FFFFFF"));
		}
	}
	
}
