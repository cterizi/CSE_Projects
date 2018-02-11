import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class ResourceLoader {
	
	public static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String fileName){

		Image image = Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(fileName));
		return image;
	}
}