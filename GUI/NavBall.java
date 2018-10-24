import org.fxyz3d.geometry.Point3D;
import org.fxyz3d.shapes.Spheroid;
import org.fxyz3d.shapes.primitives.SegmentedSphereMesh;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class NavBall extends SegmentedSphereMesh{
	private static final int size = 150; //radius
	private static final PhongMaterial material1 = new PhongMaterial(); //material 
	private static final String navBallTexture = "navball.png"; //image file name
	private static SegmentedSphereMesh sphere = new SegmentedSphereMesh(200,0,0,size,new Point3D(0f,0f,0f)); 
	
	public NavBall() {
		super(); 
		this.setRadius(size);
		material1.setDiffuseMap(new Image(navBallTexture)); //set texture image
		
        this.setMaterial(material1); //apply material to NavBall
        this.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        this.getTransforms().add(new Rotate(90, Rotate.Y_AXIS));
        this.setCache(true);
        //this.setCacheShape(true);
        //this.setCacheHint(CacheHint.ROTATE);
	}
}
