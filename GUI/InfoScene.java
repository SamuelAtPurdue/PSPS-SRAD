package application;

import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoScene extends TabPane {
    private static Tab tab1 = new Tab("Launch Pad");
    private static Tab tab2 = new Tab("Ascent");
    private static Tab tab3 = new Tab("Descent");
    private static Tab tab4 = new Tab("Landed");
    private static Tab tab5 = new Tab("Map");
    private static Pane pane1 = new Pane();
    private static ArrayList<Label> tab1Labelsl = new ArrayList<Label>();
    private static HashMap<String, Label> tab1Labels = new HashMap<String, Label>();
    private static HashMap<String, Label> tab1ValLabels = new HashMap<String, Label>();
    private static HashMap<String, Indicator> tab1Indicators = new HashMap<String, Indicator>();
    private static String[] tab1LabelsText = {"Battery Voltage", "Apogee Igniter Voltage Main", "Main Igniter Voltage", "GPS Sats Locked", "Pad Latitude", "Pad Longitude", "Height"};

    public InfoScene(){

        pane1.setPrefSize(968, 889);
        pane1.setId("infoTab");

        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab4.setClosable(false);
        tab5.setClosable(false);
        tab1.setId("tab");
        tab2.setId("tab");
        tab3.setId("tab");
        tab4.setId("tab");
        tab5.setId("tab");
        //this.setScaleX(1.1);
        //this.setScaleY(1.1);

        for (int i = 0; i < tab1LabelsText.length; i++){
            tab1Labels.put(tab1LabelsText[i],  new Label(tab1LabelsText[i]));
            tab1ValLabels.put(tab1LabelsText[i], new Label());
            tab1Indicators.put(tab1LabelsText[i], new Indicator());
            pane1.getChildren().addAll(tab1Labels.get(tab1LabelsText[i]), tab1ValLabels.get(tab1LabelsText[i]), tab1Indicators.get(tab1LabelsText[i]));
            tab1Labels.get(tab1LabelsText[i]).setId("specName");
            tab1Labels.get(tab1LabelsText[i]).setLayoutX(120);
            tab1Labels.get(tab1LabelsText[i]).setLayoutY(50 + 80 * i);
            tab1ValLabels.get(tab1LabelsText[i]).setLayoutX(510);
            tab1ValLabels.get(tab1LabelsText[i]).setLayoutY(50 + 80 * i);
            tab1ValLabels.get(tab1LabelsText[i]).setId("valLabel");
            tab1ValLabels.get(tab1LabelsText[i]).setMinWidth(400);
            tab1Indicators.get(tab1LabelsText[i]).setLayoutX(60);
            tab1Indicators.get(tab1LabelsText[i]).setLayoutY(70 + 80 * i);
        }

        tab1.setContent(pane1);
        this.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
        this.setLayoutX(799);
        this.setLayoutY(117);
        this.setId("topInfoScene");
    }

}
