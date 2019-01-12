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
import javafx.scene.paint.Paint;


public class GraphScene extends SubScene{
	public static Pane pane = new Pane();
	private static final int X_SIZE = 1002;
	private static final int Y_SIZE = 706;
	private static LineChart<Number,Number> lineChart;
	private static XYChart.Series accSeries = new XYChart.Series();
	private static NumberAxis xAxis;
	private static NumberAxis yAxis;
	
	public GraphScene(int x, int y) {
		super(pane, X_SIZE, Y_SIZE);
		
		this.setLayoutX(0);
        this.setLayoutY(104);
        pane.setId("graphScene");

        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
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
        pane.getChildren().add(lineChart);
        
	}
	
	public void generateGraph(Timeline t) {
		accSeries.getData().clear();
		double maxX = 0, minX = Double.MAX_VALUE;
		double maxY = 0, minY = Double.MAX_VALUE;
		double x, y;
		for(int i = 0; i < t.size(); i++) {
			x = Double.parseDouble(t.get(i).get("timestamp").toString());
			y = Double.parseDouble(t.get(i).get("accel_mgtd").toString());
			if (x > maxX) maxX = x;
			if (y > maxY) maxY = y;
			if (x < minX) minX = x;
			if (y < minY) minY = y;
			accSeries.getData().add(new XYChart.Data(x, y));
			accSeries.setName("acceleration");
			
			//System.out.println(Double.parseDouble(t.get(i).get("timestamp").toString()));
			//lineChart.getXAxis().setTickLength(20);
			
			
		}
		xAxis.setAutoRanging(true);
		yAxis.setAutoRanging(true);
		
		
	}
	
	
	

	
}
