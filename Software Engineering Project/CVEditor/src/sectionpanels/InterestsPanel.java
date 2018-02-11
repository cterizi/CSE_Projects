package sectionpanels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import section.CoreStrengthsSection;
import section.InterestsSection;
import section.ProfessionalProfileSection;
import section.Section;

public class InterestsPanel extends SectionPanel{

	private JTextArea textArea;
	
	public InterestsPanel(){
		JPanel interestsPanel = new JPanel();
		interestsPanel.setLayout(new BorderLayout());
		setOpaque(false);
		add(interestsPanel, "Center");
		textArea = new JTextArea("Fill your interests...", 25, 35);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		interestsPanel.add(new JScrollPane(textArea));
	}

	@Override
	public void updateSectionFields(Object section) {
		((InterestsSection)section).setText(textArea.getText());
	}
	
	public void loadFromSection(Section section) {
		textArea.setText(((InterestsSection)section).getText());
	}
}
