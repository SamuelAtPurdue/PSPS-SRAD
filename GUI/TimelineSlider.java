import javafx.scene.control.Slider;

public class TimelineSlider extends Slider{

	public TimelineSlider(int x, int y) {
		super(0, 1, 0);
		
		this.setShowTickLabels(true);
		this.setShowTickMarks(true);
		this.setMinWidth(600);
		this.setScaleY(1.5);
		this.setScaleX(1.5);
		this.setLayoutX(200);
		this.setLayoutY(y-50);
	}
	
	public void updateTimeline() {
		
	}
	
	
}
