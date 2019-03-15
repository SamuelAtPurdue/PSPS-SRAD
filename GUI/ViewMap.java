package application;

import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import javax.swing.*;


public class ViewMap extends SubScene{

    public static Pane pane = new Pane();
    private static final int X_SIZE = 501;
    private static final int Y_SIZE = 300;
    private static ImageView mapView;
    private static ImageView border;
    private static Rectangle borderRect;

    public ViewMap(){
        super(pane, X_SIZE, Y_SIZE);

        //pane.setId("topInfoScene");
        //pane.setPadding(new Insets(1, 1, 1, 1));

        mapView = new ImageView(new Image("/application/map.png"));
        mapView.setTranslateX(-250);
        mapView.setTranslateY(-150);
        border = new ImageView(new Image("/application/border.png"));



        this.setLayoutX(0);
        this.setLayoutY(739);
        //pane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(10))));
        pane.getChildren().addAll(mapView, border);
    }

}
