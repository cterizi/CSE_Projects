package compare;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import input.Field;
import section.Section;

public class MergeSectionPanel extends JPanel{
	
	private Section section;
	private ArrayList<Field> fields;
	
	public MergeSectionPanel(Section section, ArrayList<Field> fields){
		this.section = section;
		this.fields = new ArrayList<Field>();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		for (Field field : fields){
			if (field.getPassField()) {
				add(new CheckFieldPanel(field));
			}
			this.fields.add(field);
		}
	}
	
	public Section getSection(){
		return section;
	}
	
	public ArrayList<Field> getFields(){
		return this.fields;
	}
}
