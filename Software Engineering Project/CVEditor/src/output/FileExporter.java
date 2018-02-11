package output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import cv.CV;
import section.Section;

abstract public class FileExporter {
	
	private String fullPath;
	private FileWriter fileWriter;
	private PrintWriter printWriter;
	
	abstract public String getSectionNameToWrite(String sectionName);
	abstract public String getLineToWrite(String label, String field);
	
	public void writeFile(CV cv, String fullPath){
		setFullPath(fullPath);
		try {
			fileWriter = new FileWriter(fullPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		printWriter = new PrintWriter(fileWriter);
		
		
		ArrayList<String> fieldList = new ArrayList<>();
		String label;
		String fieldValue = "";
		
		
		printWriter.print(getHeader("begin document") + "\n");
		printWriter.print(getHeader("begin section") + "\n");
		for(Section section : cv.getSections()){
			printWriter.print(getSectionNameToWrite(section.getSectionName()) + "\n");
			printWriter.print(getHeader("first section field") + "\n");
			fieldList = section.getFieldList();
			for(String field : fieldList){
				label = field.split("~")[0];
				if(field.split("~").length > 1){
					fieldValue = field.split("~")[1];
				}
				else{
					fieldValue = "";
				}
				printWriter.print(getLineToWrite(label, fieldValue));
			}
			printWriter.print(getFooter("last section field") + "\n");
		}
		printWriter.print(getFooter("end section") + "\n");
		printWriter.print(getFooter("end document") + "\n");		
		printWriter.close();
	}
	
	public void setFullPath(String fullPath){
		this.fullPath = fullPath;
	}
	
	public String getHeader(String mode) {
		return("");
	}

	public String getFooter(String mode) {
		return("");
	}
}
