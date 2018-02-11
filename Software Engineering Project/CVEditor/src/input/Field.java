package input;

public class Field {
	private String label;
	private String field;
	private boolean passField;
	private boolean keepComparedField = false;
	private String comparedFieldValue;
	
	public Field(String label, String field){
		this.label = label;
		this.field = field;
	}
	
	public void setField(String field){
		this.field = field;
	}
	
	public void setKeepComparedField(boolean keepComparedField){
		this.keepComparedField = keepComparedField;
	}
	
	public void setPassField(boolean passField){
		this.passField = passField;
	}
	
	public void setComparedFieldValue(String comparedFieldValue){
		this.comparedFieldValue = comparedFieldValue;
	}
	
	public boolean getKeepComparedField(){
		return(keepComparedField);
	}
	
	public String getComparedFieldValue(){
		return(comparedFieldValue);
	}
	
	public String getLabel(){
		return(label);
	}
	
	public String getField(){
		return(field);
	}
	
	public boolean getPassField(){
		return(passField);
	}
	
	public String toString(){
		return label + " " + field;
	}
	
}
