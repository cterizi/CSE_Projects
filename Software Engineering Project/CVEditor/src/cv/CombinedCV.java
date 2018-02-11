package cv;

import section.ProfessionalExperienceSection;
import section.SkillsAndExperienceSection;

public class CombinedCV extends CV{

	public CombinedCV(){
		super();
	}
	
	public void addSections() {
		addSection(new SkillsAndExperienceSection());
		addSection(new ProfessionalExperienceSection());
	}

}
