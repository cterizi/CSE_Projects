package junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import sectionpanels.ProfessionalExperiencePanel;
import utils.ProfessionalExperiencePanelElement;

public class DeleteElementTest {

	@Test
	public void deleteAchievementTest() {
		ProfessionalExperiencePanel pep = new ProfessionalExperiencePanel();
		ProfessionalExperiencePanelElement pepe = pep.getLastProfessionalExperience();
		pep.addAchievementLabels(1);
		pep.addAchievementLabels(2);
		pep.addAchievementLabels(3);
		pep.deleteAchievement("2");
		assertEquals(2, pepe.getDeleteAchievementButtons().size());
		assertEquals(2, pepe.getListOfAchievements().size());
		assertEquals(2, pepe.getAchievementLabels().size());
		pep.deleteAchievement("1");
		assertEquals(1, pepe.getDeleteAchievementButtons().size());
		assertEquals(1, pepe.getListOfAchievements().size());
		assertEquals(1, pepe.getAchievementLabels().size());
		pep.deleteAchievement("1");
		assertEquals(1, pepe.getDeleteAchievementButtons().size());
		assertEquals(1, pepe.getListOfAchievements().size());
		assertEquals(1, pepe.getAchievementLabels().size());
	}
	
}
