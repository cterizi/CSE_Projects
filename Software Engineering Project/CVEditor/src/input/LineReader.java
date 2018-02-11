package input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LineReader {
	
	private FileReader file;
	private BufferedReader br;
	private String currentLine = "";
	
	public LineReader(String fullPath){
		try {
			file = new FileReader(fullPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(file);
	}
	
	public void readLine(){
		boolean empty = true;
		
		try {
			currentLine = br.readLine();
			if (currentLine != null){
				empty = currentLine.trim().isEmpty();
			}
			while(currentLine != null && empty){
				currentLine = br.readLine();
				if (currentLine != null){
					empty = currentLine.trim().isEmpty();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentLine(){
		return currentLine;
	}

}