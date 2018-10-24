

import javafx.scene.Node;
import org.fxyz3d.model.WelcomePage;

/**
 *
 * @author Jason Pollastrini aka jdub1581
 */
public class HomePage extends WelcomePage{
    
    private static final String fxyzSamplesTitle = "F(X)yz Samples";
    private static final Node homePageContent = buildHomePageContent();
    
    public HomePage() {
        this(fxyzSamplesTitle, homePageContent);
    }

    private HomePage(String title, Node content) {
        super(title, content);
    }

    private static Node buildHomePageContent() {
        return null;
    }
    
}