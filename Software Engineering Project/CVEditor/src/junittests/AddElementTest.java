package junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import sectionpanels.CareerSummaryPanel;
import sectionpanels.EducationAndTrainingPanel;
import sectionpanels.FurtherCoursesPanel;
import sectionpanels.SkillsAndExperiencePanel;
import utils.SkillPanelElement;

public class AddElementTest {

	@Test
	public void testAddCareer() {
		CareerSummaryPanel csp = new CareerSummaryPanel();
		assertEquals(1, csp.getCareerElementListSize()); 
		csp.addCareerSummaryLabels();
		csp.addCareerSummaryLabels();
		assertEquals(3, csp.getCareerElementListSize());
	}
	
	@Test
	public void testAddEducation() {
		EducationAndTrainingPanel eatp = new EducationAndTrainingPanel();
		assertEquals(1, eatp.getEducationElementListSize()); 
		eatp.addEducationAndTrainingLabels();
		eatp.addEducationAndTrainingLabels();
		assertEquals(3, eatp.getEducationElementListSize());
	}
	
	@Test
	public void testAddCourses() {
		FurtherCoursesPanel fcp = new FurtherCoursesPanel();
		assertEquals(1, fcp.getCoursesElementListSize()); 
		fcp.addCoursesLabels();
		fcp.addCoursesLabels();
		assertEquals(3, fcp.getCoursesElementListSize());
	}
	
	public void testAddSkill() {
		SkillsAndExperiencePanel saep = new SkillsAndExperiencePanel();
		SkillPanelElement spe = saep.getLastSkillPanelElement();
		saep.addSkillLabels(1);
		saep.addSkillLabels(2);
		saep.addSkillLabels(3);
		saep.addSkillLabels(4);
		assertEquals(4, spe.getSkillsList().size());
		assertEquals(4, spe.getDeleteSkillButtons().size());
		assertEquals(4, spe.getSkillsListLabels().size());
	}
}