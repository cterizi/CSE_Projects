package cv;
import java.util.ArrayList;

import section.Section;

public class CustomCV extends CV{

	public CustomCV(ArrayList<Section> sections){
		clearSections();
		for(Section section : sections){
			addSection(section);
		}
	}
	
	
	public void addSections() {
	}
	
}
