
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LaunchUI extends Application {
	
	public static final int X_SIZE = 1600;
	public static final int Y_SIZE = 900;
	public static int testVar = 10;
	
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	NavBall navBall = new NavBall();
    	NavBallScene navBallScene = new NavBallScene(navBall, X_SIZE, Y_SIZE);
    	Timeline timeline = new Timeline();
    	TimelineSlider timelineSlider = new TimelineSlider(X_SIZE, Y_SIZE);
    	TopMenuBar topMenuBar = new TopMenuBar(X_SIZE, Y_SIZE);
    	String[] elements = {"timestamp", "accel", "accel_angles", "accel_mgtd", "gyro_angles", "gyro_mgtd", "mag_angles", "mag_mgtd", "temp"};
    	Hashtable<String, Label> mainLabels = new Hashtable<String, Label>();

    	for(String s: elements) {
    		mainLabels.put(s, new Label(s));
    	}
    
    	Group mainGroup = new Group(navBallScene);
    	Scene mainScene = new Scene(mainGroup, X_SIZE, Y_SIZE);
        mainScene.setFill(Color.WHITE);
        
        for(int i = 0; i < mainLabels.size(); i++) {
        	mainLabels.get(elements[i]).setTranslateX(X_SIZE-400);
        	mainLabels.get(elements[i]).setTranslateY(100 + i * 50);
        }
        
        /*
        tempLabel.setTranslateX(X_SIZE - 400);
        tempLabel.setTranslateY(100);
        */
        
        
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
        	  }
        	  
        });
      
        timelineSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue observableValue, Number oldValue, Number newValue) {
              System.out.println(timelineSlider.getValue());
              
            }
          });
        
        topMenuBar.getOpenFile().setOnAction(
        	new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
            	List<File> fileList = new FileChooser().showOpenMultipleDialog(primaryStage);
                timeline.generateTimeline(fileList);
                for(int i = 0; i < mainLabels.size(); i++) {
                	mainLabels.get(elements[i]).setText(mainLabels.get(elements[i]).getText() + " " + timeline.get(0).get(elements[i]).toString());
                }
            }
        });
        
       
        
        /*
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        
        lineChart.getData().add(series);
        
        */
       primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
             navBallScene.resizeAdjust(mainScene.getWidth(), mainScene.getHeight());
       });

       primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
    	    navBallScene.resizeAdjust(mainScene.getWidth(), mainScene.getHeight());
       });
        
     //mainGroup.getChildren().add(lineChart);
       mainGroup.getChildren().add(timelineSlider);
       mainGroup.getChildren().add(topMenuBar);
       
       for(String s: elements) {
   			mainGroup.getChildren().add(mainLabels.get(s));
       }
       
       primaryStage.setResizable(true);
       primaryStage.setScene(mainScene);
       primaryStage.setTitle("Launch UI");
       primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void updateLabel(Label label, double num){
    	label.setText("Temp: " + num);
    }
    
}