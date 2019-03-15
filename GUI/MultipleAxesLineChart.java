package application;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleAxesLineChart extends StackPane {

    private final LineChart baseChart;
    private final ObservableList<LineChart> backgroundCharts = FXCollections.observableArrayList();
    private final Map<LineChart, Color> chartColorMap = new HashMap<>();

    private final double yAxisWidth = 50;
    private final AnchorPane detailsWindow;

    private final double yAxisSeparation = 10;
    private double strokeWidth = 2;

    public MultipleAxesLineChart(LineChart baseChart, Color lineColor) {
        this(baseChart, lineColor, null);
    }

    public MultipleAxesLineChart(LineChart baseChart, Color lineColor, Double strokeWidth) {
        if (strokeWidth != null) {
            this.strokeWidth = strokeWidth;
        }
        this.baseChart = baseChart;

        baseChart.getXAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 14pt;");
        baseChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 14pt;");



        chartColorMap.put(baseChart, lineColor);

        styleBaseChart(baseChart);
        styleChartLine(baseChart, lineColor);
        setFixedAxisWidth(baseChart);

        setAlignment(Pos.CENTER_LEFT);

        backgroundCharts.addListener((Observable observable) -> rebuildChart());

        detailsWindow = new AnchorPane();
        bindMouseEvents(baseChart, this.strokeWidth);

        rebuildChart();
    }

    public void fileUpdate(){
        for(LineChart lineChart : backgroundCharts){

            //double maxY = 1000;
            List<XYChart.Data> dataList = ((XYChart.Series)lineChart.getData().get(0)).getData();
            double maxY = getYMax(dataList);

            System.out.println("maxY " + maxY);
            if (maxY < 1000) {
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 14pt;");
            } else if (maxY < 10000){
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 9pt;");
            } else {
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 9pt;");
            }
        }
    }

    public void fileUpdateLive(){
        NumberAxis xBAxis;
        NumberAxis yAxis;
        double xSpan = 10;
        double max = 0;
        for(LineChart lineChart : backgroundCharts){

            lineChart.getXAxis().setAutoRanging(false);
            xBAxis = (NumberAxis)baseChart.getXAxis();
            yAxis = (NumberAxis)lineChart.getYAxis();
            yAxis.setAutoRanging(false);

            //double maxY = 1000;
            List<XYChart.Data> dataList = ((XYChart.Series)lineChart.getData().get(0)).getData();
            max = getXMax(dataList);
            max = max >= 10 ? max : 10;
            xBAxis.setLowerBound(max - xSpan);
            xBAxis.setUpperBound(max);
            double maxY = getYMaxInRange(dataList, max - xSpan, max);
            yAxis.setUpperBound(maxY);
            //System.out.println("maxYY " + maxY);
            if (maxY < 1000) {
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 14pt;");
            } else if (maxY < 10000){
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 9pt;");
            } else {
                lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 9pt;");
            }
        }
    }

    private double getYMax(List<XYChart.Data> chart){
        double max = 0;
        for(XYChart.Data data : chart){
            if (Double.parseDouble(data.getYValue().toString()) > max){
                max = (Double.parseDouble(data.getYValue().toString()));
            }
        }
        return max;
    }

    private double getYMaxInRange(List<XYChart.Data> chart, double minTime, double maxTime){
        double max = 0;
        for(XYChart.Data data : chart){
            if (Double.parseDouble(data.getYValue().toString()) > max && Double.parseDouble(data.getXValue().toString()) > minTime && Double.parseDouble(data.getXValue().toString()) < maxTime){
                max = (Double.parseDouble(data.getYValue().toString()));

            }
        }
        return max;
    }

    private double getXMax(List<XYChart.Data> chart){
        double max = 0;
        for(XYChart.Data data : chart){
            if (Double.parseDouble(data.getXValue().toString()) > max){
                max = (Double.parseDouble(data.getXValue().toString()));
            }
        }
        return max;
    }

    private void bindMouseEvents(LineChart baseChart, Double strokeWidth) {
        final DetailsPopup detailsPopup = new DetailsPopup();
        getChildren().add(detailsWindow);
        detailsWindow.getChildren().add(detailsPopup);
        detailsWindow.prefHeightProperty().bind(heightProperty());
        detailsWindow.prefWidthProperty().bind(widthProperty());
        detailsWindow.setMouseTransparent(true);

        setOnMouseMoved(null);
        setMouseTransparent(false);

        final Axis xAxis = baseChart.getXAxis();
        final Axis yAxis = baseChart.getYAxis();

        final Line xLine = new Line();
        final Line yLine = new Line();
        yLine.setFill(Color.GRAY);
        xLine.setFill(Color.GRAY);
        yLine.setStrokeWidth(strokeWidth/2);
        xLine.setStrokeWidth(strokeWidth/2);
        xLine.setVisible(false);
        yLine.setVisible(false);

        final Node chartBackground = baseChart.lookup(".chart-plot-background");
        for (Node n: chartBackground.getParent().getChildrenUnmodifiable()) {
            if (n != chartBackground && n != xAxis && n != yAxis) {
                n.setMouseTransparent(true);
            }
        }
        chartBackground.setCursor(Cursor.CROSSHAIR);
        chartBackground.setOnMouseEntered((event) -> {
            chartBackground.getOnMouseMoved().handle(event);
            detailsPopup.setVisible(true);
            xLine.setVisible(true);
            yLine.setVisible(true);
            detailsWindow.getChildren().addAll(xLine, yLine);
        });
        chartBackground.setOnMouseExited((event) -> {
            detailsPopup.setVisible(false);
            xLine.setVisible(false);
            yLine.setVisible(false);
            detailsWindow.getChildren().removeAll(xLine, yLine);
        });
        chartBackground.setOnMouseMoved(event -> {
            double x = event.getX() + chartBackground.getLayoutX() + backgroundCharts.size() * (yAxisWidth + yAxisSeparation);
            double y = event.getY() + chartBackground.getLayoutY();



            xLine.setStartX(10);
            xLine.setEndX(detailsWindow.getWidth()-10);
            xLine.setStartY(y+5);
            xLine.setEndY(y+5);

            yLine.setStartX(x+5);
            yLine.setEndX(x+5);
            yLine.setStartY(10);
            yLine.setEndY(detailsWindow.getHeight()-10);

            detailsPopup.showChartDescrpition(event);

            //setMaxWidth(800);

            if (y + detailsPopup.getHeight() + 10 < getHeight()) {
                AnchorPane.setTopAnchor(detailsPopup, y+10);
            } else {
               //AnchorPane.setTopAnchor(detailsPopup, y-10-detailsPopup.getHeight());
            }

            if (x + detailsPopup.getWidth() + 10 < getWidth()) {
                AnchorPane.setLeftAnchor(detailsPopup, x+10);
            } else {
               //AnchorPane.setLeftAnchor(detailsPopup, x-10-detailsPopup.getWidth());
            }

            System.out.println("getH: " + getHeight());
            System.out.println("getW: " + getWidth());
            System.out.println("x: " + x);
            System.out.println("y: " + y + "\n");


        });
    }

    private void styleBaseChart(LineChart baseChart) {
        baseChart.setCreateSymbols(false);
        baseChart.setLegendVisible(false);
        baseChart.getXAxis().setAutoRanging(false);
        baseChart.getXAxis().setAnimated(false);
        baseChart.getYAxis().setAnimated(false);
    }

    private void setFixedAxisWidth(LineChart chart) {
        chart.getYAxis().setPrefWidth(yAxisWidth);
        chart.getYAxis().setMaxWidth(yAxisWidth);
    }

    private void rebuildChart() {
        getChildren().clear();

        getChildren().add(resizeBaseChart(baseChart));
        for (LineChart lineChart : backgroundCharts) {
            getChildren().add(resizeBackgroundChart(lineChart));
        }
        getChildren().add(detailsWindow);


    }

    private Node resizeBaseChart(LineChart lineChart) {
        HBox hBox = new HBox(lineChart);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.prefHeightProperty().bind(heightProperty());
        hBox.prefWidthProperty().bind(widthProperty());

        lineChart.minWidthProperty().bind(widthProperty().subtract((yAxisWidth+yAxisSeparation)*backgroundCharts.size()));
        lineChart.prefWidthProperty().bind(widthProperty().subtract((yAxisWidth+yAxisSeparation)*backgroundCharts.size()));
        lineChart.maxWidthProperty().bind(widthProperty().subtract((yAxisWidth+yAxisSeparation)*backgroundCharts.size()));
        //System.out.println(this.getWidth()-((yAxisWidth+yAxisSeparation)*backgroundCharts.size()));
        //lineChart.setMaxWidth(lineChart.getWidth() - (yAxisWidth+yAxisSeparation)*backgroundCharts.size());

        //lineChart.translateXProperty().bind(baseChart.getYAxis().widthProperty());
        lineChart.setTranslateX((yAxisWidth+yAxisSeparation)*backgroundCharts.size());

        return lineChart;
    }

    private Node resizeBackgroundChart(LineChart lineChart) {
        HBox hBox = new HBox(lineChart);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.prefHeightProperty().bind(heightProperty());
        hBox.prefWidthProperty().bind(widthProperty());
        hBox.setMouseTransparent(true);

        lineChart.minWidthProperty().bind(widthProperty().subtract((yAxisWidth + yAxisSeparation) * backgroundCharts.size()));
        lineChart.prefWidthProperty().bind(widthProperty().subtract((yAxisWidth + yAxisSeparation) * backgroundCharts.size()));
        lineChart.maxWidthProperty().bind(widthProperty().subtract((yAxisWidth + yAxisSeparation) * backgroundCharts.size()));

        //lineChart.translateXProperty().bind(baseChart.getYAxis().widthProperty());
        lineChart.setTranslateX((yAxisWidth+yAxisSeparation)*backgroundCharts.size());
        //lineChart.getYAxis().setTranslateX((yAxisWidth + yAxisSeparation) * backgroundCharts.indexOf(lineChart) * -1);
        //lineChart.getYAxis().setTranslateX(-1 * (yAxisWidth + yAxisSeparation) * (backgroundCharts.indexOf(lineChart) + 1) * 0.8);
        //lineChart.getYAxis().setLayoutX(0);
        lineChart.getYAxis().setTranslateX((yAxisWidth + yAxisSeparation) * (backgroundCharts.indexOf(lineChart) + 1) * -1 * 1);

        lineChart.setMaxHeight(610);

        //lineChart.maxHeightProperty().bind(baseChart.heightProperty());


        //System.out.println(baseChart.getYAxis());

        return hBox;
    }

    public void addSeries(XYChart.Series series, Color lineColor) {
        NumberAxis yAxis = new NumberAxis();
        NumberAxis xAxis = new NumberAxis();


        // style x-axis
        xAxis.setAutoRanging(false);
        xAxis.setVisible(false);
        xAxis.setOpacity(0.0); // somehow the upper setVisible does not work
        xAxis.lowerBoundProperty().bind(((NumberAxis) baseChart.getXAxis()).lowerBoundProperty());
        xAxis.upperBoundProperty().bind(((NumberAxis) baseChart.getXAxis()).upperBoundProperty());
        xAxis.tickUnitProperty().bind(((NumberAxis) baseChart.getXAxis()).tickUnitProperty());

        // style y-axis
        yAxis.setSide(Side.LEFT);
        yAxis.setLabel(series.getName());
        //yAxis.setStyle("-fx-font-size: 20pt;");



        // create chart
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setAnimated(false);
        lineChart.setLegendVisible(false);
        lineChart.getData().add(series);
        lineChart.getYAxis().setStyle("-fx-font-family: \"Arial\"; -fx-tick-label-font-size: 14pt;");

        styleBackgroundChart(lineChart, lineColor);
        setFixedAxisWidth(lineChart);

        chartColorMap.put(lineChart, lineColor);
        backgroundCharts.add(lineChart);
    }



    private void styleBackgroundChart(LineChart lineChart, Color lineColor) {
        styleChartLine(lineChart, lineColor);

        Node contentBackground = lineChart.lookup(".chart-content").lookup(".chart-plot-background");
        contentBackground.setStyle("-fx-background-color: transparent;");

        lineChart.setVerticalZeroLineVisible(false);
        lineChart.setHorizontalZeroLineVisible(false);
        lineChart.setVerticalGridLinesVisible(false);
        lineChart.setHorizontalGridLinesVisible(false);
        lineChart.setCreateSymbols(false);
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void styleChartLine(LineChart chart, Color lineColor) {
        chart.getYAxis().lookup(".axis-label").setStyle("-fx-text-fill: " + toRGBCode(lineColor) + "; -fx-font-weight: bold;");
        Node seriesLine = chart.lookup(".chart-series-line");
        seriesLine.setStyle("-fx-stroke: " + toRGBCode(lineColor) + "; -fx-stroke-width: " + strokeWidth + ";");
    }

    public Node getLegend() {
        HBox hBox = new HBox();

        final CheckBox baseChartCheckBox = new CheckBox(baseChart.getYAxis().getLabel());
        baseChartCheckBox.setSelected(true);
        baseChartCheckBox.setStyle("-fx-text-fill: " + toRGBCode(chartColorMap.get(baseChart)) + "; -fx-font-weight: bold;");
        baseChartCheckBox.setDisable(true);
        baseChartCheckBox.getStyleClass().add("readonly-checkbox");
        baseChartCheckBox.setOnAction(event -> baseChartCheckBox.setSelected(true));
        hBox.getChildren().add(baseChartCheckBox);

        for (final LineChart lineChart : backgroundCharts) {
            CheckBox checkBox = new CheckBox(lineChart.getYAxis().getLabel());
            checkBox.setStyle("-fx-text-fill: " + toRGBCode(chartColorMap.get(lineChart)) + "; -fx-font-weight: bold");
            checkBox.setSelected(true);
            checkBox.setOnAction(event -> {
                if (backgroundCharts.contains(lineChart)) {
                    backgroundCharts.remove(lineChart);
                } else {
                    backgroundCharts.add(lineChart);
                }
            });
            hBox.getChildren().add(checkBox);
        }

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setStyle("-fx-padding: 0 10 20 10");

        return hBox;
    }

    private class DetailsPopup extends VBox {

        private DetailsPopup() {
            setStyle("-fx-border-width: 1px; -fx-padding: 5 5 5 5px; -fx-border-color: gray; -fx-background-color: whitesmoke; -fx-font-weight: bold;");
            setVisible(false);
        }

        public void showChartDescrpition(MouseEvent event) {
            getChildren().clear();

            Long xValueLong = Math.round((double)baseChart.getXAxis().getValueForDisplay(event.getX()));

            HBox baseChartPopupRow = buildPopupRow(event, xValueLong, baseChart);
            if (baseChartPopupRow != null) {
                getChildren().add(baseChartPopupRow);
            }

            for (LineChart lineChart : backgroundCharts) {
                HBox popupRow = buildPopupRow(event, xValueLong, lineChart);
                if (popupRow == null) {
                    //System.out.println("popup row is null");
                    continue;
                }
                getChildren().add(popupRow);
            }
        }

        private HBox buildPopupRow(MouseEvent event, Long xValueLong, LineChart lineChart) {
            Label seriesName = new Label(lineChart.getYAxis().getLabel());
            Label valueNum;
            seriesName.setTextFill(chartColorMap.get(lineChart));

            Number yValueForChart = getYValueForX(lineChart, xValueLong.intValue());
            if (yValueForChart == null) {
                //.println("yValueForChart row is null");
                return null;
            }
            Number yValueLower = Math.round(normalizeYValue(lineChart, event.getY() - 10));
            Number yValueUpper = Math.round(normalizeYValue(lineChart, event.getY() + 10));
            Number yValueUnderMouse = Math.round((double) lineChart.getYAxis().getValueForDisplay(event.getY()));

            // make series name bold when mouse is near given chart's line
            if (isMouseNearLine(yValueForChart, yValueUnderMouse, Math.abs(yValueLower.doubleValue()-yValueUpper.doubleValue()))) {
                //seriesName.setStyle("-fx-font-weight: bold");
            }

            valueNum = new Label("["+yValueForChart+"]");
            valueNum.setTextFill(chartColorMap.get(lineChart));

            HBox popupRow = new HBox(10, seriesName, valueNum);
            
            seriesName.setStyle("-fx-font-weight: bold");




            return popupRow;
        }

        private double normalizeYValue(LineChart lineChart, double value) {
            Double val = (Double) lineChart.getYAxis().getValueForDisplay(value);
            if (val == null) {
                return 0;
            } else {
                return val;
            }
        }

        private boolean isMouseNearLine(Number realYValue, Number yValueUnderMouse, Double tolerance) {
            return (Math.abs(yValueUnderMouse.doubleValue() - realYValue.doubleValue()) < tolerance);
        }

        public Number getYValueForX(LineChart chart, Number xValue) {
            //System.out.println(xValue);
            List<XYChart.Data> dataList = ((List<XYChart.Data>)((XYChart.Series)chart.getData().get(0)).getData());
            for (XYChart.Data data : dataList) {
                //System.out.println(data.getXValue() + " " + data.getYValue());
                if (Double.parseDouble(data.getXValue().toString()) == xValue.doubleValue()) {
                    //System.out.println("true");
                    return (Number)data.getYValue();
                }
            }
            return 0;
        }
    }
}
