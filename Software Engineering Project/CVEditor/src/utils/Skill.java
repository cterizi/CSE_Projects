package utils;

import java.util.ArrayList;

public class Skill {

	private String experience;
	private ArrayList<String> skillsList;
	
	public Skill(){
		experience = "";
		skillsList = new ArrayList<String>();
	}
	
	public void addSkill(String skill){
		skillsList.add(skill);
	}
	
	public ArrayList<String> getFieldList(){
		ArrayList<String> fieldList = new ArrayList<>();
		
		fieldList.add("ExperienceOn:~" + experience);
		for(int i = 0; i < skillsList.size(); i++){
			fieldList.add("Skill" + (i + 1) + ":~" + skillsList.get(i));
		}
		return(fieldList);
	}
	
	public void setExperienceName(String experience){
		this.experience = experience;
	}
	
	public void setListOfSkills(ArrayList<String> skillsList){
		this.skillsList.clear();
		for (String skill : skillsList){
			this.skillsList.add(skill);
		}
	}
	
	public String getExperienceName(){
		return experience;
	}
	
	public ArrayList<String> getSkillsList(){
		return skillsList;
	}
	
}
