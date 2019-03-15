package application;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Indicator extends Circle {

    public Indicator(){
        super(20, Color.RED);
        //this.setLayoutX(x);
        //this.setLayoutY(y);
    }

    public void setStatus(boolean isGreen){
        if(isGreen){
            this.setStroke(Color.GREEN);
        }
    }

}
