package output;

import java.util.ArrayList;
import java.util.HashMap;

public class LATEXExporter extends FileExporter{
	
	private HashMap<String, String> headerMap = new HashMap<String, String>();
	private HashMap<String, String> footerMap = new HashMap<String, String>();
	private ArrayList<String> noVerbatimSections = new ArrayList<>();
	private boolean beginItemize = true;
	private String endItemize = "";
	private int experienceOnCounter = 0;
	private int skillCounter = 0;
	private int achievementCounter = 0;
	private int professionalExperienceCounter = 0;
	
	public LATEXExporter(){
		noVerbatimSections.add("Professional Experience");
		noVerbatimSections.add("Education And Training");
		noVerbatimSections.add("Further Courses");
		noVerbatimSections.add("Skills And Experience");
		noVerbatimSections.add("Career Summary");
		
		String header = "\\documentclass[a4paper,12pt]{article}\n\\pagestyle{empty}\n\\usepackage{color}\n\\usepackage{ifxetex}\n\\usepackage{enumitem}\n";
		
		headerMap.put("begin document", header + "\\begin{document}");
		headerMap.put("begin section", "\\begin{enumerate} \\bfseries");
		headerMap.put("first section field", "\\begin verbatim");
		footerMap.put("end document", "\\end{document}");
		footerMap.put("end section", "\\end{enumerate}");
		footerMap.put("last section field", "\\end{verbatim}");
	}
	
	public String getLineToWrite(String label, String field) {
		if(isNotVerbatimSection()){
			String lineToWrite = "";
			
			if(label.equals("Name:") || label.equals("Career Summary Company Name:")){
				endItemize = "\\end{itemize}\n";
				if(beginItemize == true){
					lineToWrite = lineToWrite + "\n\\begin{itemize}\n";
					beginItemize = false;
				}
				lineToWrite = lineToWrite + "\n\\item\\textbf{" + field + "}";
			}
			else if (label.equals("Professional Experience Company Name:")){
				professionalExperienceCounter = professionalExperienceCounter + 1;
				if(professionalExperienceCounter == 1){
					lineToWrite = "\n\\begin{itemize}\n";
				}
				if(achievementCounter > 0){
					lineToWrite = "\n\\end{itemize}\n";
				}
				achievementCounter = 0;
				lineToWrite = lineToWrite + "\n\\item\\textbf{" + field + "}";
			}
			else if(label.equals("Responsibilities:")){
				lineToWrite = "\n\\begin{verbatim}\n\to " + field + "\n\\end{verbatim}\n";
				endItemize = "\n\\end{itemize}\n";
			}
			else if(label.contains("Achievement")){
				achievementCounter = achievementCounter + 1;
				if(achievementCounter == 1){
					lineToWrite = "\\begin{verbatim}\n\to List of achievements:\n\\end{verbatim}\n";
					lineToWrite = lineToWrite + "\\begin{itemize}[label={$\\diamond$}]\n\\item " + field + "\n";
				}
				else{
					lineToWrite = "\\item " + field + "\n";
				}
			}
			else if(label.equals("ExperienceOn:")){
				experienceOnCounter = experienceOnCounter + 1;
				if(skillCounter > 0){
					lineToWrite = "\\end{itemize}\n";
				}
				skillCounter = 0;
								
				lineToWrite = lineToWrite + "\n\\begin{verbatim}\n3." + experienceOnCounter + " SKILLS AND EXPERIENCE ON " + field + "\n\\end{verbatim}\n";
			}
			else if(label.contains("Skill")){
				skillCounter = skillCounter + 1;
				if (skillCounter == 1){
					lineToWrite = "\\begin{itemize}\n";
				}
				
				lineToWrite = lineToWrite + "\n\\item " + field + "\n";
			}
			else{
				lineToWrite = ", " + field;
			}
			return(lineToWrite);
		}
		else{
			endItemize = "";
			if(label.equals("Name:") || label.equals("Address:") || label.equals("Telephone(Home):") || label.equals("Telephone(Mobile):") || label.equals("Email:")){
				return(label + " " + field + "\n");
			}
			return(field + "\n");
		}
	}

	public boolean isNotVerbatimSection(){
		return(headerMap.get("first section field").equals(""));
	}
	
	public String getSectionNameToWrite(String sectionName) {
		if(achievementCounter > 0){
			endItemize = "\\end{itemize}\\end{itemize}\n";
			achievementCounter = 0;
		}
		if(skillCounter > 0){
			endItemize = "\\end{itemize}\n";
			skillCounter = 0;
		}
		updateVerbatim(sectionName);
		professionalExperienceCounter = 0;
		beginItemize = true;
		experienceOnCounter = 0;
		return(endItemize + "\\item " + sectionName);
	}
	
	public void updateVerbatim(String sectionName){
		if(noVerbatimSections.contains(sectionName)){
			headerMap.put("first section field", "");
			footerMap.put("last section field", "");
		}
		else{
			headerMap.put("first section field", "\n\\begin{verbatim}");
			footerMap.put("last section field", "\n\\end{verbatim}");
		}
	}

	public String getHeader(String mode) {
		return(headerMap.get(mode));
	}

	public String getFooter(String mode) {
		return(footerMap.get(mode));
	}

}
