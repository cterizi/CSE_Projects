package sectionpanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import section.AdditionalInformationSection;
import section.CoreStrengthsSection;
import section.ProfessionalProfileSection;
import section.Section;

public class CoreStrengthsPanel extends SectionPanel{
	
	private JTextArea textArea;

	public CoreStrengthsPanel(){
		JPanel coreStrengthsPanel = new JPanel();
		coreStrengthsPanel.setLayout(new BorderLayout());
		setOpaque(false);
		add(coreStrengthsPanel, "Center");
		textArea = new JTextArea("Fill core strengths...", 25, 35);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	    coreStrengthsPanel.add(new JScrollPane(textArea));
	}

	@Override
	public void updateSectionFields(Object section) {
		((CoreStrengthsSection)section).setText(textArea.getText());
	}

	public void loadFromSection(Section section) {
		textArea.setText(((CoreStrengthsSection)section).getText());
	}

}
