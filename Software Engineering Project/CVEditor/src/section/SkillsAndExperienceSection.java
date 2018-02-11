package section;

import java.util.ArrayList;
import java.util.HashSet;

import input.Field;
import utils.ProfessionalExperience;
import utils.ProfessionalExperiencePanelElement;
import utils.Skill;
import utils.SkillPanelElement;

public class SkillsAndExperienceSection extends Section{	
	private ArrayList<Skill> listOfSkills;
	
	public SkillsAndExperienceSection() {
		super("Skills And Experience");
		listOfSkills = new ArrayList<Skill>();
	}
	
	public void updateAllFields(ArrayList<SkillPanelElement> listOfSkillsPanelElements){
		listOfSkills.clear();
		for (SkillPanelElement item : listOfSkillsPanelElements){
			Skill newItem = new Skill();
			newItem.setExperienceName(item.getExperienceString());
			newItem.setListOfSkills(item.getSkillsListString());
			listOfSkills.add(newItem);
		}
	}
	
	public void addFields(){
		clearSectionFieldList();
		ArrayList<String> fieldList = new ArrayList<String>();
		
		for(Skill skill : listOfSkills){
			fieldList = skill.getFieldList();
			for(String field : fieldList){
				addField(field);
			}
		}
	}
	
	public void addSkill(Skill skill){
		listOfSkills.add(skill);
	}
	
	public void deleteSkill(int index){
		listOfSkills.remove(index);
	}
	
	public ArrayList<Skill> getList(){
		return(listOfSkills);
	}

	public ArrayList<Field> compare(Section comparedSection) {
		int i, j;
		boolean sameExists = false;
		ArrayList<Field> fields = new ArrayList<Field>();
		ArrayList<Skill> comparedSkills = ((SkillsAndExperienceSection)comparedSection).getList();
		Skill skill;
		Field tempField;
		for (i = 0; i < listOfSkills.size(); i++){
			skill = listOfSkills.get(i);
			sameExists = false;
			for (j = 0; j < comparedSkills.size(); j++){
				if (skill.getExperienceName().equals(comparedSkills.get(j).getExperienceName())){
					if (sameList(skill.getSkillsList(), listOfSkills.get(i).getSkillsList())){
						comparedSkills.remove(j);
						break;
					}
					
					tempField = getTempField("ExperienceOn", skill.getExperienceName(), "\n");
					tempField.setField(skill.getExperienceName());
					fields.add(tempField);
					comparedSkills.get(j).getSkillsList().addAll(skill.getSkillsList());
					for (String skillString : new HashSet<String>(comparedSkills.get(j).getSkillsList())){
						tempField = getTempField("Skill", skillString, "");
						tempField.setField(skillString);
						fields.add(tempField);
					}
					comparedSkills.remove(j);
					sameExists = true;
					break;
				}
			}
			
			if (!sameExists){
				fields.add(getTempField("ExperienceOn", skill.getExperienceName(), skill.getExperienceName()));
				for (String skillString : skill.getSkillsList()){
					fields.add(getTempField("Skill", skillString, skillString));
				}
			}
		}
		
		for (j = 0; j < comparedSkills.size(); j++){
			tempField = getTempField("ExperienceOn", comparedSkills.get(j).getExperienceName(), "\n");
			tempField.setField(comparedSkills.get(j).getExperienceName());
			fields.add(tempField);
			for (String skillString : comparedSkills.get(j).getSkillsList()){
				tempField = getTempField("Skill", skillString, "\n");
				tempField.setField(skillString);
				fields.add(tempField);	
			}
		}
		
		return fields;
	}

	@Override
	public void updateFromFields(ArrayList<Field> fields) {
		
	}
	
}
