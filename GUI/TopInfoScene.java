package application;

import java.io.File;
import java.io.FileInputStream;

import javafx.application.Platform;
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
	private static final int X_SIZE = 1918;
	private static final int Y_SIZE = 80;
	private static Image sat_red;
	private static Image sat_green;
	private static Image replay;
	private static ImageView statusImage;
	private static Label modeLabel;
	private static Label time;
	private static Label timeS;
	private static int mode = 0;
	
	public TopInfoScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);
		
		this.setLayoutX(0);
        this.setLayoutY(38);
        pane.setId("topInfoScene");

        try {
			sat_red = new Image("/application/sat_red.png");
			sat_green = new Image("/application/sat_green.png");
			replay = new Image("/application/rewind_arrow.png");
		}catch(Exception e){
        	System.out.println(e);
		}
        
        statusImage = new ImageView();
        statusImage.setScaleX(0.6);
        statusImage.setScaleY(0.6);
        statusImage.setTranslateX(-10);
        statusImage.setTranslateY(-10);
        statusImage.setImage(sat_red);

        modeLabel = new Label();
        modeLabel.setId("modeLabel");
        modeLabel.setText("Disconnected");
        modeLabel.setTranslateX(80);
        modeLabel.setTranslateY(12);
        modeLabel.setTextFill(Paint.valueOf("FF6666"));
        
        time = new Label();
        time.setId("timeLabel");
        time.setText("T = 00:00");
        time.setLayoutX(1700);
        time.setLayoutY(18);
        
        timeS = new Label();
        timeS.setId("timeSLabel");
        timeS.setText(".00");
        timeS.setLayoutX(1865);
        timeS.setLayoutY(29);

        pane.getChildren().addAll(statusImage, modeLabel, time, timeS);
        
	}
	
	public void setMode(int mode){
		if (mode == 2) {
			statusImage.setImage(replay);
			modeLabel.setText("Replay Mode");
			modeLabel.setTextFill(Paint.valueOf("FFFFFF"));
		}else if(mode == 1){
			statusImage.setImage(sat_green);
			modeLabel.setText("Connected");
			modeLabel.setTextFill(Paint.valueOf("66FF66"));
		}
		this.mode = mode;
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setTime(double t) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Update UI here.
				if (t > 0) {
					time.setText(String.format("T + %02d:%02d", Math.round(t/60), Math.round(t)%60));
					timeS.setText(String.format(".%02d", Math.round((t - Math.floor(t)) * 100)));
				}else if(t < 0) {
					time.setText(String.format("T - %02d:%02d", Math.round(t/60), Math.round(t)%60));
					timeS.setText(String.format(".%02d", Math.round((t - Math.floor(t)) * 100)));
				}else{
					time.setText("T = 00:00");
					timeS.setText(".00");
				}
			}
		});
	}
	
}
