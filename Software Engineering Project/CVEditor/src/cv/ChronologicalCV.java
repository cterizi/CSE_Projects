package cv;

import section.CoreStrengthsSection;
import section.ProfessionalExperienceSection;

public class ChronologicalCV extends CV{
	
	public ChronologicalCV(){
		super();
	}
	
	public void addSections(){
		addSection(new CoreStrengthsSection());
		addSection(new ProfessionalExperienceSection());
	}

}
