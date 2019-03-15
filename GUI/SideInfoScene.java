package application;

import java.io.File;
import java.io.FileInputStream;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Paint;


public class SideInfoScene extends SubScene{
	public static Pane pane = new Pane();
	private static final int X_SIZE = 150;
	private static final int Y_SIZE = 922;
	private static Label accMagLabel;
	private static Label accMagValLabel;
	private static Label tempLabel;
	private static Label tempValLabel;
	private static int mode = 0;
	private static Image background;
	private static ImageView backgroundView;
	private static ImageView rocketView;
	private static NumberAxis heightAxis = new NumberAxis(0, 50000, 5000);

	public SideInfoScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);

		try {
			background = new Image("/application/sideSceneBackground.png");
			backgroundView = new ImageView(background);
			backgroundView.setLayoutX(1);
			backgroundView.setLayoutY(1);
			rocketView = new ImageView(new Image ("/application/rocket.png"));
			rocketView.setFitWidth(100);
			rocketView.setFitHeight(100);
			rocketView.setX(60);
			rocketView.setY(800);
		}catch(Exception e){
			System.out.println(e);
		}



		this.setLayoutX(1768);
        this.setLayoutY(117);
        pane.setId("sideInfoScene");
        
		heightAxis.setPrefHeight(860);
		heightAxis.setLayoutX(0);
		heightAxis.setLayoutY(20);
		heightAxis.setTickLength(20);
		heightAxis.setMinorTickLength(10);
		heightAxis.setTickLabelFill(Color.WHITE);
		heightAxis.setId("sideInfoSceneAxis");

		//heightAxis.setTickLabelRotation(90);
		heightAxis.setSide(Side.RIGHT);

        pane.getChildren().addAll(backgroundView, heightAxis, rocketView);


        
	}
	
	public void updateLabels(double sliderPos, Timeline t) {
		if(mode == 2) {
			int currentPos = t.getClosestMoment(sliderPos);
			accMagValLabel.setText(t.get(currentPos).get("accel_mgtd").toString());
			tempValLabel.setText(t.get(currentPos).get("temp").toString());
		}
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}

	public void update(Timeline t){

	}

}
