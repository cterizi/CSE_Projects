package junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import section.AdditionalInformationSection;
import section.GeneralInformationSection;
import sectionpanels.AdditionalInformationPanel;
import sectionpanels.GeneralInformationPanel;

public class SectionPanelTests {

	@Test
	public void testGeneralInformation() {
		String name = "Chryssa Terizi";
		String address = "Panousi";
		String telephone = "";
		String mobile = "6984368861";
		String email = "chryssa.terizi@gmail.com";
				
		GeneralInformationPanel gip = new GeneralInformationPanel();
		gip.setGeneralInformationPanel(name, address, telephone, mobile, email);
		GeneralInformationSection gis = new GeneralInformationSection(); 
		gip.updateSectionFields(gis);
		assertEquals(name, gis.getName());
		assertEquals(address, gis.getAddress());
		assertEquals(telephone, gis.getHomeTelephone());
		assertEquals(mobile, gis.getMobileTelephone());
		assertEquals(email, gis.getEmail());
	}
	
	public void testAdditionalInformation() {
		String text = "I have a dog";
		AdditionalInformationPanel aip = new AdditionalInformationPanel();
		aip.setAdditionalInformationPanel(text);
		AdditionalInformationSection ais = new AdditionalInformationSection(); 
		aip.updateSectionFields(ais);
		assertEquals(text, ais.getText());
	}
}
