package section;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import input.Field;
import sectionpanels.SectionPanel;
import utils.EducationElement;
import utils.EducationPanelElement;
import utils.ProfessionalExperience;
import utils.ProfessionalExperiencePanelElement;

public class ProfessionalExperienceSection extends Section{
	private ArrayList<ProfessionalExperience> listOfProfessionalExperience;
	
	public ProfessionalExperienceSection() {
		super("Professional Experience");
		listOfProfessionalExperience = new  ArrayList<ProfessionalExperience>();
	}	
	
	public void updateAllFields(ArrayList<ProfessionalExperiencePanelElement> listOfProfessionalExperiencePanel){
		listOfProfessionalExperience.clear();
		for(ProfessionalExperiencePanelElement item : listOfProfessionalExperiencePanel){
			ProfessionalExperience newElement = new ProfessionalExperience();
			newElement.setCompanyName(item.getCompanyNameString());
			newElement.setJob(item.getJobString());
			newElement.setTitle(item.getTitleString());
			newElement.setParagraphOfResponsibilities(item.getParagraphOfResponsibilitiesString());
			if(checkDate(item.getDateString())){
				newElement.setDate(item.getDateString());
			}
			else{
				newElement.setDate("");
				item.setDate("eg.12/05/2015 - 20/03/2016");
			}
			newElement.setListOfAchievements(item.getListOfAchievementsString());
			listOfProfessionalExperience.add(newElement);
		}
	}
	
	public void addFields() {
		clearSectionFieldList();
		ArrayList<String> fieldList = new ArrayList<String>();

		for(ProfessionalExperience professionalExperience : listOfProfessionalExperience){
			fieldList = professionalExperience.getFieldList();

			for(String field : fieldList){
				addField(field);
			}
		}
	}
	
	public void addProfessionalExperience(ProfessionalExperience professionalExperience){
		listOfProfessionalExperience.add(professionalExperience);
	}
	
	public void deleteProfessionalExperience(int index){
		listOfProfessionalExperience.remove(index);
	}
	
	public ArrayList<ProfessionalExperience> getProfessionalExperienceList(){
		return listOfProfessionalExperience;
	}
	
	public ArrayList<Field> compare(Section comparedSection) {
		ArrayList<Field> fields = new ArrayList<Field>();
		ArrayList<ProfessionalExperience> comparedProfessionalExperienceList = ((ProfessionalExperienceSection)comparedSection).getProfessionalExperienceList();
		Field tempField;
		boolean sameExists = false;
		int i = 0;
		for (ProfessionalExperience professionalExperience : listOfProfessionalExperience){
			sameExists = false;
			for (i = 0; i < comparedProfessionalExperienceList.size(); i++){
				if (professionalExperience.getCompanyName().equals(comparedProfessionalExperienceList.get(i).getCompanyName())){
					sameExists = true;
					
					int index = fields.size();
					boolean allSame = true;
					tempField = getTempField("Professional Experience Company Name", professionalExperience.getCompanyName(), "\n");
					tempField.setField(professionalExperience.getCompanyName());
					fields.add(tempField);
					
					if (!professionalExperience.getJob().equals(comparedProfessionalExperienceList.get(i).getJob())){
						allSame = false;
						tempField = getTempField("Job", professionalExperience.getJob(), comparedProfessionalExperienceList.get(i).getJob());
					}
					else{
						tempField = getTempField("Job", professionalExperience.getJob(), professionalExperience.getJob());
					}
					fields.add(tempField);
					
					if (!professionalExperience.getTitle().equals(comparedProfessionalExperienceList.get(i).getTitle())){
						allSame = false;
						tempField = getTempField("Title", professionalExperience.getTitle(), comparedProfessionalExperienceList.get(i).getTitle());
					}
					else{
						tempField = getTempField("Title", professionalExperience.getTitle(), professionalExperience.getTitle());
					}
					fields.add(tempField);
					
					if (!professionalExperience.getDate().equals(comparedProfessionalExperienceList.get(i).getDate())){
						allSame = false;
						tempField = getTempField("Date", professionalExperience.getDate(), comparedProfessionalExperienceList.get(i).getDate());
					}
					else{
						tempField = getTempField("Date", professionalExperience.getDate(), professionalExperience.getDate());
					}
					fields.add(tempField);
					
					if (allSame){
						fields.get(index).setPassField(false);
					}
					
					if (professionalExperience.getParagraphOfResponsibilities().equals(comparedProfessionalExperienceList.get(i).getParagraphOfResponsibilities())){
						fields.add(getTempField("Responsibilities", professionalExperience.getParagraphOfResponsibilities(), professionalExperience.getParagraphOfResponsibilities()));
					}
					else{
						fields.get(index).setPassField(true);
						fields.add(getTempField("Responsibilities", professionalExperience.getParagraphOfResponsibilities(), comparedProfessionalExperienceList.get(i).getParagraphOfResponsibilities()));
					}
					
					
					
					if (sameList(comparedProfessionalExperienceList.get(i).getListOfAchievements(), professionalExperience.getListOfAchievements())){
						comparedProfessionalExperienceList.remove(i);
						break;
					}
					else{
						fields.get(index).setPassField(true);
					}
					
					comparedProfessionalExperienceList.get(i).getListOfAchievements().addAll(professionalExperience.getListOfAchievements());
					
					for (String achievementString : new HashSet<String>(comparedProfessionalExperienceList.get(i).getListOfAchievements())){
						tempField = getTempField("Achievement", achievementString, "");
						tempField.setField(achievementString);
						fields.add(tempField);
					}
					comparedProfessionalExperienceList.remove(i);
					break;
				}
			}
			
			if (!sameExists){
				fields.add(getTempField("Responsibilities", professionalExperience.getParagraphOfResponsibilities(), professionalExperience.getParagraphOfResponsibilities()));
				for (String achievementString : professionalExperience.getListOfAchievements()){
					fields.add(getTempField("Achievement", achievementString, achievementString));
				}
			}
		}

		for (i = 0; i < comparedProfessionalExperienceList.size(); i++){
			tempField = getTempField("Responsibilities", comparedProfessionalExperienceList.get(i).getParagraphOfResponsibilities(), "");
			tempField.setField(comparedProfessionalExperienceList.get(i).getParagraphOfResponsibilities());
			fields.add(tempField);
			for (String achievementString : comparedProfessionalExperienceList.get(i).getListOfAchievements()){
				tempField = getTempField("Achievement", achievementString, "");
				tempField.setField(achievementString);
				fields.add(tempField);
			}
		}
		
		return fields;
	}

	@Override
	public void updateFromFields(ArrayList<Field> fields) {
		
	}
}
