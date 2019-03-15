package application;

import java.io.File;
import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;


public class GraphScene extends SubScene{
	public static Pane pane = new Pane();
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 623;
	private static LineChart<Number,Number> lineChart;
	private static XYChart.Series accSeries = new XYChart.Series();
	private static XYChart.Series speedSeries = new XYChart.Series();
	private static XYChart.Series altSeries = new XYChart.Series();
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	private static Line markerLine;
	private static MultipleAxesLineChart chart;
	private static TopInfoScene topInfoScene;

	public GraphScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);
		
		this.setLayoutX(0);
        this.setLayoutY(117);
        pane.setId("graphScene");

        markerLine = new Line();
        markerLine.setStartX(0);
        markerLine.setStartY(15);
        markerLine.setEndX(0);
        markerLine.setEndY(613);
        markerLine.setLayoutX(57);
        markerLine.setStroke(Color.WHITE);
        markerLine.setStrokeWidth(2);
        markerLine.setVisible(false);
        
        xAxis = new NumberAxis(0, 10, 1);
        yAxis = new NumberAxis(0, 110, 10);
        yAxis.setLabel("altitude");
        yAxis.setStyle("-fx-font-size: 10pt;");
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        //lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        //XYChart.Series series = new XYChart.Series();
        //series.setName("My portfolio");
        //populating the series with data
        //accSeries.getData().add(new XYChart.Data(1, 23));
        //lineChart.setCreateSymbols(false);
        
        lineChart.getData().add(accSeries);
        lineChart.setPrefSize(990, 700);
        lineChart.setId("lineChart");
        
        LineChart baseChart = new LineChart(xAxis, yAxis);
        baseChart.getData().add(altSeries);
        
        accSeries.setName("acceleration");
		speedSeries.setName("speed");
		altSeries.setName("altitude");
        
        chart = new MultipleAxesLineChart(baseChart, Color.RED);
        chart.setLayoutX(0);
        chart.setLayoutY(0);
        chart.setPrefSize(X_SIZE, Y_SIZE);
        chart.addSeries(speedSeries, Color.BLUE);
        chart.addSeries(accSeries, Color.LIMEGREEN);

        
        pane.getChildren().addAll(chart);
       // pane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        
	}

	public void bindTopScene(TopInfoScene tc){
		topInfoScene = tc;
	}
	
	public void generateGraph(Timeline t) {
		accSeries.getData().clear();
		double x, y;
		xAxis.setAutoRanging(true);
		yAxis.setAutoRanging(true);
		for(int i = 0; i < t.size(); i++) {
			x = Double.parseDouble(t.get(i).get("timestamp").toString());
			accSeries.getData().add(new XYChart.Data(x, Double.parseDouble(t.get(i).get("accel_mgtd").toString())));
			speedSeries.getData().add(new XYChart.Data(x, Double.parseDouble(t.get(i).get("temp").toString())));
			altSeries.getData().add(new XYChart.Data(x, Double.parseDouble(t.get(i).get("alt").toString())));
			//System.out.println(Double.parseDouble(t.get(i).get("timestamp").toString()));
			//lineChart.getXAxis().setTickLength(20);
		}
		
		markerLine.setVisible(true);
		markerLine.toFront();
		fileUpdate();
	}

	public void addNewestPoint(Timeline t){

		if(t.size() > 0) {
			int i = t.size() - 1;
			try {
				double x = Double.parseDouble(t.get(i).get("timestamp").toString());
				altSeries.getData().add(new XYChart.Data(x, Double.parseDouble(t.get(i).get("alt").toString())));
				topInfoScene.setTime(x);
				chart.fileUpdate();
			}catch(NullPointerException e){
				System.out.println(e);
			}
		}

	}
		
	public void moveMarker(double sliderPos) {
		int xRange = 915;
		markerLine.setLayoutX(57 + xRange * sliderPos);
		System.out.println(sliderPos);
	}

	public void fileUpdate(){
		chart.fileUpdate();
	}
	

	
}
