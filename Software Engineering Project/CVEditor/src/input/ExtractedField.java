package input;

public class ExtractedField {

	private Field field;
	private boolean hasFieldsToRead;
	
	public ExtractedField(){
		field = new Field("_", "_");
		hasFieldsToRead = false;
	}
	
	public void setField(Field field){
		this.field = field;
	}
	
	public void setHasFieldsToRead(boolean hasFieldsToRead){
		this.hasFieldsToRead = hasFieldsToRead;
	}
	
	public Field getField(){
		return field;
	}
	
	public boolean hasFieldsToRead(){
		return hasFieldsToRead;
	}
}
