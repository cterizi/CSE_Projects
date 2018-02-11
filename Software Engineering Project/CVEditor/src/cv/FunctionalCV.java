package cv;

import section.CareerSummarySection;
import section.SkillsAndExperienceSection;

public class FunctionalCV extends CV{
	
	public FunctionalCV(){
		super();
	}
	
	public void addSections(){
		addSection(new SkillsAndExperienceSection());
		addSection(new CareerSummarySection());
	}

}
