package application;

import javafx.scene.control.Slider;

public class TimelineSlider extends Slider{

	public TimelineSlider(int x, int y) {
		super(0, 1, 0);
		
		this.setShowTickLabels(true);
		this.setShowTickMarks(true);
		this.setMinWidth(630);
		this.setScaleY(1.5);
		this.setScaleX(1.5);
		this.setLayoutX(190);
		this.setLayoutY(y-50);
		
	}
	
	public void update() {
		
	}
	
	
}
