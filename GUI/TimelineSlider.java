import javafx.scene.control.Slider;

public class TimelineSlider extends Slider{

	public TimelineSlider(int x, int y) {
		super(0, 1, 0);
		
		this.setShowTickLabels(true);
		//this.setShowTickMarks(true);
		this.setMajorTickUnit(50);
		this.setMinorTickCount(5);
		this.setBlockIncrement(10);
		this.setMinWidth(500);
		this.setScaleY(1.5);
		this.setScaleX(1.5);
		this.setTranslateX(250);
		this.setTranslateY(y-100);
	}
	
	public void updateTimeline() {
		
	}
	
	
}
