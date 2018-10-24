import java.awt.Rectangle;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;

public class NavBallScene extends SubScene{
	private static Group root = new Group(); //group of nodes
	private static PerspectiveCamera camera = new PerspectiveCamera(true); //camera node
	private static final int X_SIZE = 400;
	private static final int Y_SIZE = 400;
	private static double sceneX;
	private static double sceneY;
	
	private static final String level_indicator_texture = "level_indicator.png";
	//private static Image = new Image(level_indicator_texture);
	
	public NavBallScene(NavBall navball, int x, int y) {
		super(root, X_SIZE, Y_SIZE, true, SceneAntialiasing.BALANCED); //send group to scene, apply dimensions
		//super(root, X_SIZE, Y_SIZE);
		//sceneX = x;
		//sceneY = y;
		
		this.setFill(Color.WHITE); //set color to white
		
		camera.getTransforms().addAll(new Translate(0, 0, -800)); //move camera back 800
	    camera.setFarClip(10000);
	    this.setCamera(camera);
	    
	    Image levelIndicator = new Image(level_indicator_texture, 60, 24, true, true);
	    ImageView imageView = new ImageView(levelIndicator);
	    imageView.setTranslateX(0 - (levelIndicator.getWidth() / 2));
	    imageView.setTranslateY(0 - (levelIndicator.getHeight() / 2));
	    //System.out.println(levelIndicator.getWidth());
	    imageView.setTranslateZ(-200);
	    
	    root.getChildren().add(navball);
	    root.getChildren().add(imageView);
	    
	    this.setTranslateX(x - X_SIZE);
        this.setTranslateY(y - Y_SIZE);
	   
	    
	}
	
	public void resizeAdjust(double x, double y) {
		this.setTranslateX(x - X_SIZE);
        this.setTranslateY(y - Y_SIZE);
	}

}
