package sectionpanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import section.AdditionalInformationSection;
import section.ProfessionalProfileSection;
import section.Section;

public class AdditionalInformationPanel extends SectionPanel{
	
	private JTextArea textArea;
	
	public AdditionalInformationPanel(){
		JPanel additionalInformationPanel = new JPanel();
		additionalInformationPanel.setLayout(new BorderLayout());
		setOpaque(false);
		add(additionalInformationPanel, "Center");
		textArea = new JTextArea("Fill your additional information...", 25, 35);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		additionalInformationPanel.add(new JScrollPane(textArea));
	}

	@Override
	public void updateSectionFields(Object section) {
		((AdditionalInformationSection)section).setText(textArea.getText());
	}

	public void loadFromSection(Section section) {
		textArea.setText(((AdditionalInformationSection)section).getText());
	}
	
	public void setAdditionalInformationPanel(String text){
		textArea.setText(text);
	}
}
