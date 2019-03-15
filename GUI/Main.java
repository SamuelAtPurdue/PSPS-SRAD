package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jssc.SerialPort;
import jssc.SerialPortList;

public class Main extends Application {
	
	//windows size
	public static final int X_SIZE = 1920;
	public static final int Y_SIZE = 1040;
	private static final int SPLASH_WIDTH = 676;
	private static final int SPLASH_HEIGHT = 227;


	
	public static void main(String[] args) {
		launch(args);
		//LauncherImpl.launchApplication(Main.class, MyPreloader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.UNDECORATED);
    	//define all elements
		SerialPort arduinoPort = null;
		ObservableList<String> portList = FXCollections.observableArrayList();
    	NavBall navBall = new NavBall();
    	NavBallScene navBallScene = new NavBallScene(navBall, X_SIZE, Y_SIZE);
    	Timeline timeline = new Timeline();
    	TimelineSlider timelineSlider = new TimelineSlider(X_SIZE, Y_SIZE);
    	TopMenuBar topMenuBar = new TopMenuBar(X_SIZE, Y_SIZE);
    	TopInfoScene topInfoScene = new TopInfoScene(X_SIZE, Y_SIZE);
    	SideInfoScene sideInfoScene = new SideInfoScene(X_SIZE, Y_SIZE);
    	GraphScene graphScene = new GraphScene(X_SIZE, Y_SIZE);
    	ViewMap viewMap = new ViewMap();
		SerialConnector sc = new SerialConnector(timeline, graphScene, sideInfoScene);
		ComboBox ports = new ComboBox();
		InfoScene infoScene = new InfoScene();
    	String[] elements = {"timestamp", "accel", "accel_angles", "accel_mgtd", "gyro_angles", "gyro_mgtd", "mag_angles", "mag_mgtd", "temp"};
    
    	Pane mainPane = new Pane();
    	Scene mainScene = new Scene(mainPane, X_SIZE, Y_SIZE);

		graphScene.bindTopScene(topInfoScene);

		detectPort(ports, portList);
		ports.setLayoutX(1700);
		ports.setLayoutY(6);
		ports.valueProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable,
										String oldValue, String newValue) {

						System.out.println(newValue);
						sc.disconnectArduino();
						sc.connectArduino(newValue);
						topInfoScene.setMode(1);
					}
				});

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
            	//System.out.println(timelineSlider.getValue());
            	if (topInfoScene.getMode() == 2){
            		sideInfoScene.updateLabels(timelineSlider.getValue(), timeline);
            		topInfoScene.setTime(Double.parseDouble(timeline.get(timeline.getCurrentPos()).get("timestamp").toString()));
            		graphScene.moveMarker(timelineSlider.getValue());
            	}
            }
          });
        
        topMenuBar.getOpenFile().setOnAction(
        	new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
            	try{
	            	List<File> fileList = new FileChooser().showOpenMultipleDialog(primaryStage);
	                timeline.generateTimeline(fileList);
	                topInfoScene.setMode(2);
	                sideInfoScene.setMode(2);
	                graphScene.generateGraph(timeline);
	                timelineSlider.update();
	                sideInfoScene.updateLabels(timelineSlider.getValue(), timeline);
            		topInfoScene.setTime(Double.parseDouble(timeline.get(timeline.getCurrentPos()).get("timestamp").toString()));
            		graphScene.moveMarker(timelineSlider.getValue());
            	}catch(Exception f){
            		System.out.println(f);
            	}
            	
            }

        });


        
       topMenuBar.setSc(sc);
        
       //mainPane.getChildren().add(timelineSlider);
       mainPane.getChildren().add(topMenuBar);
       mainPane.getChildren().add(navBallScene);
       mainPane.getChildren().add(topInfoScene);
       mainPane.getChildren().add(sideInfoScene);
       mainPane.getChildren().add(graphScene);
       mainPane.getChildren().add(ports);
       mainPane.getChildren().add(infoScene);
       mainPane.getChildren().add(viewMap);
       

       mainScene.getStylesheets().add("/application/style.css");

       primaryStage.setResizable(false);
       primaryStage.setScene(mainScene);
       primaryStage.setTitle("Launch UI");
       primaryStage.show();
       
    }

	private void detectPort(ComboBox comboBoxPorts, ObservableList<String> portList){

		portList = FXCollections.observableArrayList();
		String[] serialPortNames = SerialPortList.getPortNames();
		for(String name: serialPortNames){
			System.out.println(name);
			portList.add(name);
			comboBoxPorts.getItems().add(name);
		}

	}
    
}