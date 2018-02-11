package output;

public class TXTExporter extends FileExporter{

	public String getLineToWrite(String label, String field) {
		return(label + "~~~~~" + field + "\n");
	}

	public String getSectionNameToWrite(String sectionName) {
		return("-----" + sectionName + "-----");
	}
}
