package sectionpanels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import section.InterestsSection;
import section.ProfessionalProfileSection;
import section.Section;

public class ProfessionalProfilePanel extends SectionPanel{
	
	private JTextArea textArea;

	public ProfessionalProfilePanel(){
		JPanel professionalProfilePanel = new JPanel();
		professionalProfilePanel.setLayout(new BorderLayout());
		setOpaque(false);
		add(professionalProfilePanel, "Center");
		textArea = new JTextArea("Fill your professional profile...", 25, 35);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	    professionalProfilePanel.add(new JScrollPane(textArea));
	}

	@Override
	public void updateSectionFields(Object section) {
		((ProfessionalProfileSection)section).setText(textArea.getText());
	}

	public void loadFromSection(Section section) {
		textArea.setText(((ProfessionalProfileSection)section).getText());
	}
	
}
