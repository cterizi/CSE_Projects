package input;

public class TXTLoader extends FileLoader{
	
	public ExtractedField extractField(String line) {
		ExtractedField extractedField = new ExtractedField();
		if(line == null){
			extractedField.setField(new Field("", ""));
			return(extractedField);
		}
		line = line.replace("\n", "");
		String label = line.split("~~~~~")[0];
		if(line.split("~~~~~").length == 1){
			extractedField.setField(new Field(label, ""));
			return(extractedField);
		}
		String field = line.split("~~~~~")[1];
		extractedField.setField(new Field(label, field));
		return(extractedField);
	}

	public String getSectionName(String line){
		return(line.replace("-", ""));
	}
	
	public boolean skip(String line) {
		return false;
	}
}
