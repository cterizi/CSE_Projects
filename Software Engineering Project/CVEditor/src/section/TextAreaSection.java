package section;

import java.util.ArrayList;

import input.Field;

public class TextAreaSection extends Section{
	private String text;
	
	public TextAreaSection(String sectionName) {
		super(sectionName);
		text = "";
	}
	
	public void setText(String text){
		this.text = text;
	}

	public void addFields() {
		clearSectionFieldList();
		addField(getSectionName() + "~" + text);
	}
	
	public String getText(){
		return text;
	}

	
	public ArrayList<Field> compare(Section comparedSection) {
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(getTempField(getSectionName(), text, ((TextAreaSection)comparedSection).getText()));
		return fields;
	}

	@Override
	public void updateFromFields(ArrayList<Field> fields) {
		if (fields.get(0).getComparedFieldValue() == null){
			return;
		}
		if (fields.get(0).getKeepComparedField()){
			text = fields.get(0).getComparedFieldValue();
		}
	}
}