package utils;

import java.util.ArrayList;

public class ProfessionalExperience extends JobCareerElement{
	
	private String paragraphOfResponsibilities;
	private ArrayList<String> listOfAchievements;

	public ProfessionalExperience(){
		super("Professional Experience");
		paragraphOfResponsibilities = "";
		listOfAchievements = new ArrayList<String>();	
	}
	
	public ArrayList<String> getFieldList(){
		ArrayList<String> fieldList = new ArrayList<>();
		
		for(String field : super.getFieldList()){
			fieldList.add(field);
		}
		fieldList.add("Responsibilities:~" + paragraphOfResponsibilities);

		for(int i = 0; i < listOfAchievements.size(); i++){
			fieldList.add("Achievement" + (i + 1) + ":~" + listOfAchievements.get(i));
		}
		return(fieldList);
	}
	
	public void setParagraphOfResponsibilities(String paragraphOfResponsibilities){
		this.paragraphOfResponsibilities = paragraphOfResponsibilities;
	}
	
	public void addAchievement(String achievement){
		listOfAchievements.add(achievement);
	}
	
	public void setListOfAchievements(ArrayList<String> listOfAchievements){
		this.listOfAchievements.clear();
		for (String achievement : listOfAchievements){
			this.listOfAchievements.add(achievement);
		}
	}
		
	public String getParagraphOfResponsibilities(){
		return paragraphOfResponsibilities;
	}
	
	public ArrayList<String> getListOfAchievements(){
		return listOfAchievements;
	}
}
