
import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	
	//windows size
	public static final int X_SIZE = 1590;
	public static final int Y_SIZE = 900;
	
	public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	//define all elements
    	NavBall navBall = new NavBall();
    	NavBallScene navBallScene = new NavBallScene(navBall, X_SIZE, Y_SIZE);
    	Timeline timeline = new Timeline();
    	TimelineSlider timelineSlider = new TimelineSlider(X_SIZE, Y_SIZE);
    	TopMenuBar topMenuBar = new TopMenuBar(X_SIZE, Y_SIZE);
    	TopInfoScene topInfoScene = new TopInfoScene(X_SIZE, Y_SIZE);
    	SideInfoScene sideInfoScene = new SideInfoScene(X_SIZE, Y_SIZE);
    	GraphScene graphScene = new GraphScene(X_SIZE, Y_SIZE);
    	String[] elements = {"timestamp", "accel", "accel_angles", "accel_mgtd", "gyro_angles", "gyro_mgtd", "mag_angles", "mag_mgtd", "temp"};
    	Hashtable<String, Label> mainLabels = new Hashtable<String, Label>();
    	
    	
    	for(String s: elements) {
    		mainLabels.put(s, new Label(s));
    	}
    
    	Pane mainPane = new Pane();
    	Scene mainScene = new Scene(mainPane, X_SIZE, Y_SIZE);

        
        for(int i = 0; i < mainLabels.size(); i++) {
        	mainLabels.get(elements[i]).setTranslateX(X_SIZE-300);
        	mainLabels.get(elements[i]).setTranslateY(150 + i * 50);
        	mainLabels.get(elements[i]).setId("label");
        }
        
        
        mainScene.setOnKeyPressed(e -> {
        	  switch (e.getCode()) {
        	  case W:
        		  navBall.getTransforms().add(new Rotate(5, Rotate.Z_AXIS));
        		  break;
        	  case S:
        		  navBall.getTransforms().add(new Rotate(-5, Rotate.Z_AXIS));
        		  break;
        	  case D:
        		  navBall.getTransforms().add(new Rotate(5, Rotate.X_AXIS));
        		  break;
        	  case A:
        		  navBall.getTransforms().add(new Rotate(-5, Rotate.X_AXIS));
        		  break;
        	  case Q:
        		  navBall.getTransforms().add(new Rotate(5, Rotate.Y_AXIS));
        		  break;
        	  case E:
        		  navBall.getTransforms().add(new Rotate(-5, Rotate.Y_AXIS));
        		  break;
        	  case P:
        		  break;
        	  default:
        	  }
        	  
        });
      
        timelineSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override 
            public void changed(ObservableValue observableValue, Number oldValue, Number newValue) {
            	System.out.println(timelineSlider.getValue());
            	
            }
          });
        
        topMenuBar.getOpenFile().setOnAction(
        	new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
            	try{
	            	List<File> fileList = new FileChooser().showOpenMultipleDialog(primaryStage);
	                timeline.generateTimeline(fileList);
	                for(int i = 0; i < mainLabels.size(); i++) {
	                	mainLabels.get(elements[i]).setText(mainLabels.get(elements[i]).getText() + " " + timeline.get(0).get(elements[i]).toString());
	                }
	                topInfoScene.setMode(2);
	                graphScene.generateGraph(timeline);
            	}catch(Exception f){
            		System.out.println(f);
            	}
            }

        });
        
       
        
       mainPane.getChildren().add(timelineSlider);
       mainPane.getChildren().add(topMenuBar);
       mainPane.getChildren().add(navBallScene);
       mainPane.getChildren().add(topInfoScene);
       mainPane.getChildren().add(sideInfoScene);
       mainPane.getChildren().add(graphScene);
       
       for(String s: elements) {
   			mainPane.getChildren().add(mainLabels.get(s));
       }
       
       
       mainScene.getStylesheets().add("style.css");

       
       primaryStage.setResizable(false);
       primaryStage.setScene(mainScene);
       primaryStage.setTitle("Launch UI");
       primaryStage.show();
       
    }

    public void updateLabel(Label label, double num){
    	label.setText("Temp: " + num);
    }
    
}