package input;

import java.util.ArrayList;

public class LATEXLoader extends FileLoader{
	
	private String skipString = "o List of achievements: \\documentclass[a4paper,12pt]{article} \\pagestyle{empty} \\usepackage{color} \\usepackage{ifxetex} \\usepackage{enumitem}";
	private String txtAreaSections = "Professional Profile Core Strengths Additional Information Interests";
	private ArrayList<String> skipStringList = new ArrayList<String>();
	private ArrayList<String> blockStartList = new ArrayList<String>();
	private ArrayList<Field> pendingFields = new ArrayList<Field>();
	private int counter = 0;
	
	
	public LATEXLoader(){
		skipStringList.add("\\begin{document}");
		skipStringList.add("\\begin{enumerate} \\bfseries");
		skipStringList.add("\\begin{verbatim}");
		skipStringList.add("\\begin{itemize}");
		skipStringList.add("\\begin{itemize}[label={$\\diamond$}]");
		skipStringList.add("\\end{itemize}");
		skipStringList.add("\\end{verbatim}");
		skipStringList.add("\\end{enumerate}");
		skipStringList.add("\\end{document}");	
	}

	@Override
	public ExtractedField extractField(String line) {
		ExtractedField extractedField = new ExtractedField();
		String label = "__", textField = "__";
		if (getLastSection().equals("General Information")){
			label = line.substring(0,line.indexOf(":")+1);
			textField = line.substring(line.indexOf(":")+1, line.length()).trim();
		}
		else if (txtAreaSections.contains(getLastSection())){
			label = getLastSection();
			textField = line;
		}
		else if (line.contains("SKILLS AND EXPERIENCE ON")){
			label = "ExperienceOn:";
			textField = line.split("SKILLS AND EXPERIENCE ON")[1].trim();
			counter = 0;
		}
		else if (line.contains("\\item") && getLastSection().equals("Skills And Experience")){
			counter = counter + 1;
			textField = line.replace("\\item", "").trim();
			if (getSectionNames().contains(textField)){
				textField = "";
			}
			else{
				label = "Skill" + counter + ":";
			}	
		}
		else if((line.contains("\\item\\textbf{") || line.equals("~~~~~")) && getLastSection().equals("Career Summary")){
			if (pendingFields.size() > 0){
				Field field = pendingFields.get(0);
				pendingFields.remove(0);
				extractedField.setField(field);
				extractedField.setHasFieldsToRead(pendingFields.size() > 0);
				return extractedField;
			}
			else{
				label = "Career Summary Company Name:";
				textField = line.split("\\{")[1];
				if ((""+textField.charAt(0)).equals("\\}")){
					textField = "";
				}
				else{
					textField = textField.split("\\}")[0].trim();
				}
				String splittedLine[] = line.split(",");
				pendingFields.add(new Field("Job:", splittedLine[1]));
				pendingFields.add(new Field("Title:", splittedLine[2]));
				pendingFields.add(new Field("Date:", splittedLine[3]));	
			}
		}
		else if ((line.contains("\\item\\textbf{") || line.equals("~~~~~")) && isEducationSection()){
			if (pendingFields.size() > 0){
				Field field = pendingFields.get(0);
				pendingFields.remove(0);
				extractedField.setField(field);
				extractedField.setHasFieldsToRead(pendingFields.size() > 0);
				return extractedField;
			}
			else{
				label = "Name:";
				textField = line.split("\\{")[1];
				if ((""+textField.charAt(0)).equals("\\}")){
					textField = "";
				}
				else{
					textField = textField.split("\\}")[0].trim();
				}
				String splittedLine[] = line.split(",");
				pendingFields.add(new Field("Establishment:", splittedLine[1]));
				pendingFields.add(new Field("Location:", splittedLine[2]));
				pendingFields.add(new Field("Date:", splittedLine[3]));	
			}
		}
		else if ((line.contains("\\item\\textbf{") || line.equals("~~~~~")) && getLastSection().equals("Professional Experience")){
			counter = 0;
			if (pendingFields.size() > 0){
				Field field = pendingFields.get(0);
				pendingFields.remove(0);
				extractedField.setField(field);
				extractedField.setHasFieldsToRead(pendingFields.size() > 0);
				return extractedField;
			}
			else{
				label = "Professional Experience Company Name:";
				textField = line.split("\\{")[1];
				if ((""+textField.charAt(0)).equals("\\}")){
					textField = "";
				}
				else{
					textField = textField.split("\\}")[0].trim();
				}
				String splittedLine[] = line.split(",");
				pendingFields.add(new Field("Job:", splittedLine[1]));
				pendingFields.add(new Field("Title:", splittedLine[2]));
				pendingFields.add(new Field("Date:", splittedLine[3]));	
			}
		}
		else if(isResponsibilities(line)){
			label = "Responsibilities:";
			textField = line.substring(line.indexOf("o")+1, line.length());
		}
		else if (isAchievement(line)){
			counter = counter + 1;
			label = "Achievement" + counter + ":";
			textField = line.replace("\\item", "").trim();
		}
		extractedField.setField(new Field(label, textField));
		extractedField.setHasFieldsToRead(pendingFields.size() > 0);
		return extractedField;
	}

	@Override
	public String getSectionName(String line) {
		return line.replace("\\item ", "");
	}
	
	public boolean skip(String line){
		line = line.replace("\n", "").trim();
		if (skipString.contains(line)){
			return true;
		}
		if (skipStringList.contains(line)){
			if (line.contains("begin")){
				blockStartList.add(getAttribute(line));
				return true;
			}
			else{
				if (correctEndBlock(line)){
					blockStartList.remove(blockStartList.size()-1);
					return true;
				}
				return false;
			}
		}
		
		return false;
	}
	
	public boolean isEducationSection(){
		return getLastSection().equals("Education And Training") || getLastSection().equals("Further Courses");
	}
	
	public boolean isResponsibilities(String line){
		return !line.contains("List of achievements:") && blockStartList.get(blockStartList.size()-1).equals("verbatim") && getLastSection().equals("Professional Experience");
	}
	
	public boolean isAchievement(String line){
		return blockStartList.get(blockStartList.size()-1).equals("itemize") && line.contains("\\item") && getLastSection().equals("Professional Experience");
	}
	
	public String getAttribute(String line){
		return line.split("\\{")[1].split("\\}")[0];
	}
	
	public boolean correctEndBlock(String line){
		return blockStartList.get(blockStartList.size()-1).contains(getAttribute(line));
	}

}
