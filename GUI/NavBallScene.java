package application;

import java.awt.Rectangle;

import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;

public class NavBallScene extends SubScene{
	private static Pane root = new Pane(); //Pane of nodes
	private static ParallelCamera camera = new ParallelCamera(); //camera node
	private static double sceneX;
	private static double sceneY;
	
	private static final String level_indicator_texture = "/application/level_indicator.png";
	//private static Image = new Image(level_indicator_texture);
	
	public NavBallScene(NavBall navball, int x, int y) {
		super(root, 300, 300, true, SceneAntialiasing.BALANCED); //send Pane to scene, apply dimensions
		
		this.setLayoutX(500);
	    this.setLayoutY(739);
		
		camera.getTransforms().addAll(new Translate(0, 0, -150)); //move camera back 800
	    camera.setFarClip(100000);
	    //camera.setFieldOfView(1);
	    navball.setTranslateX(150);
	    navball.setTranslateY(150);
	    this.setCamera(camera);
	    
	    Image levelIndicator = new Image(level_indicator_texture, 60, 24, true, true);
	    ImageView imageView = new ImageView(levelIndicator);
	    imageView.setLayoutX(150 - levelIndicator.getWidth() / 2);
	    imageView.setLayoutY(150 - levelIndicator.getHeight() / 2);
	    //System.out.println(levelIndicator.getWidth());
	    imageView.setTranslateZ(-200);
	    
	    root.getChildren().add(navball);
	    root.getChildren().add(imageView);
	    root.setId("navBallScene");
	    
	    

	    
	}
	

}
