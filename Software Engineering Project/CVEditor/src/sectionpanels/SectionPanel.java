package sectionpanels;

import javax.swing.JPanel;

import section.Section;

abstract public class SectionPanel extends JPanel{
	
	abstract public void updateSectionFields(Object section);
	abstract public void loadFromSection(Section section);
}