package cv;

import java.util.ArrayList;

import section.AdditionalInformationSection;
import section.EducationAndTrainingSection;
import section.FurtherCoursesSection;
import section.GeneralInformationSection;
import section.InterestsSection;
import section.ProfessionalProfileSection;
import section.Section;

public abstract class CV {
	
	private ArrayList<Section> sections;
	
	public CV(){
		sections = new ArrayList<Section>();
		addSection(new GeneralInformationSection());
		addSection(new ProfessionalProfileSection());
		addSections();
		addSection(new EducationAndTrainingSection());
		addSection(new FurtherCoursesSection());
		addSection(new AdditionalInformationSection());
		addSection(new InterestsSection());
	}
	
	public abstract void addSections();
	
	public void addSection(Section section){
		sections.add(section);
	}
	
	public ArrayList<Section> getSections(){
		return sections;
	}
	
	public ArrayList<String> getSectionsNames(){
		ArrayList<String> names = new ArrayList<String>();
		
		for(Section section : sections){
			names.add(section.getSectionName());
		}
		return(names);
	}
	
	public void clearSections(){
		sections.clear();
	}
	
	public int getNumberOfSections(){
		return sections.size();
	}

}
