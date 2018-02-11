package sectionpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.FormLayout;

import section.ProfessionalExperienceSection;
import section.Section;
import section.SkillsAndExperienceSection;
import utils.ProfessionalExperience;
import utils.Skill;
import utils.SkillPanelElement;

public class SkillsAndExperiencePanel extends SectionPanel{

	private JPanel professionalExperiencePanel;
	private JLabel experienceLabel, lineLabel;
	private ArrayList<SkillPanelElement> skillPanelElementList;
	private JScrollPane professionalExperienceScrollPanel;
	private int numberOfRows, numberOfColumns, counterForSkill;
	private FormLayout formLayout;
	private int deleteButtonId = 0;
	private int deleteSkillButtonId = 0;
	
	public SkillsAndExperiencePanel(){
		setOpaque(false);
		setBackground(new Color(200, 200, 200));
		formLayout = new FormLayout();
		skillPanelElementList = new ArrayList<SkillPanelElement>();
		professionalExperiencePanel = new JPanel();
		professionalExperiencePanel.setLayout(formLayout);
		professionalExperienceScrollPanel = new JScrollPane(professionalExperiencePanel);
		professionalExperienceScrollPanel.setPreferredSize(new Dimension( 800, 400));
		add(professionalExperienceScrollPanel);
		numberOfColumns = 35;
		counterForSkill = 0;
		createColumns();
		addProfessionalExperienceLabels();
		addRow();
		JButton addprofessionalExperienceButton = new JButton("Add Experience");
		JButton addSkillButton = new JButton("Add Skill");
		professionalExperiencePanel.add(addprofessionalExperienceButton, "2, " + numberOfRows*2 + ", left, default");
		professionalExperiencePanel.add(addSkillButton, "4, " + numberOfRows*2 + ", left, default");
		addprofessionalExperienceButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
		    counterForSkill = 0;
		    addProfessionalExperience(lineLabel, "______________");
		    addProfessionalExperienceLabels();
		    addRow();
		    professionalExperiencePanel.add(addprofessionalExperienceButton, "2, " + numberOfRows*2 + ", left, default");
		    professionalExperiencePanel.add(addSkillButton, "4, " + numberOfRows*2 + ", left, default");
		  }
		});
		addSkillButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  counterForSkill = counterForSkill + 1;
				  addSkillLabels(counterForSkill);
				  addRow();
				  professionalExperiencePanel.add(addSkillButton, "4, " + numberOfRows*2 + ", left, default");
				  professionalExperiencePanel.add(addprofessionalExperienceButton, "2, " + numberOfRows*2 + ", left, default");
			  }
			});
	}
	
	public void createColumns() {
		for(int i = 0; i < numberOfColumns; i++){
			formLayout.appendColumn(FormFactory.RELATED_GAP_COLSPEC);
		    formLayout.appendColumn(FormFactory.DEFAULT_COLSPEC );
		}
	}
	
	public void addRows(){
		addRow();
		addRow();
		addRow();
	}
	
	public void addRow(){
		formLayout.appendRow(FormFactory.RELATED_GAP_ROWSPEC);
	    formLayout.appendRow(FormFactory.DEFAULT_ROWSPEC);
	    numberOfRows += 1;
	}
		
	public void addProfessionalExperience(JLabel label, String labelName){
		addRow();
		if(labelName.equals("______________")){
			label = new JLabel(labelName);
			professionalExperiencePanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		else{
			label = new JLabel(labelName);
			professionalExperiencePanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		revalidate();
		repaint();
	}
	
	public void addSkillLabels(int counterForAchievements){
		deleteSkillButtonId = deleteSkillButtonId + 1;
		addRow();
		int last = skillPanelElementList.size() - 1;
		skillPanelElementList.get(last).addSkillLabel(new JLabel("Skill " + Integer.toString(counterForAchievements)));
		professionalExperiencePanel.add(skillPanelElementList.get(last).getLastSkillLabel(), "2, " + numberOfRows*2  + ", right, default");
		skillPanelElementList.get(last).addSkill(new JTextField());
		professionalExperiencePanel.add(skillPanelElementList.get(last).getLastSkill(), "4, " + numberOfRows*2  + ", left, default");
		skillPanelElementList.get(last).getLastSkill().setColumns(30);
		skillPanelElementList.get(last).createDeleteSkillButton("x", deleteSkillButtonId+"");
		JButton deleteSkillButton = skillPanelElementList.get(last).getLastDeleteSkillButton();
		deleteSkillButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  String searchingName = deleteSkillButton.getName();
				  boolean found = false;
				  int i = 0, j = 0;
				  for (i = 0; i < skillPanelElementList.size(); i++){
					  j = 0;
					  for (JButton deleteButton : skillPanelElementList.get(i).getDeleteSkillButtons()){
						  if (deleteButton.getName().equals(deleteSkillButton.getName())){
							  skillPanelElementList.get(i).removeSkill(j);
							  found = true;
							  break;
						  }
						  j = j + 1;
					  }
					  if (found){
						  break;
					  }
						
				  }
			  }
		});
		deleteSkillButton.setForeground(new Color(200, 0, 0));
		professionalExperiencePanel.add(deleteSkillButton, "6, " + numberOfRows*2  + ", left, default");
		revalidate();
		repaint();
	}
	
	public void addProfessionalExperienceLabels(){
		deleteButtonId = deleteButtonId + 1;
		skillPanelElementList.add(new SkillPanelElement());
		int last = skillPanelElementList.size() - 1;
		skillPanelElementList.get(last).createDeleteButton("Delete Experience", deleteButtonId+"");
		JButton deleteExperienceButton = skillPanelElementList.get(last).getDeleteButton();
		addRows();
		professionalExperiencePanel.add(skillPanelElementList.get(last).getExperienceLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(skillPanelElementList.get(last).getExperience(), "4, " + numberOfRows*2  + ", left, default");
		skillPanelElementList.get(last).getExperience().setColumns(30);
		professionalExperiencePanel.add(deleteExperienceButton, "6, " + numberOfRows*2  + ", left, default");
		deleteExperienceButton.setForeground(new Color(200, 0, 0));
		deleteExperienceButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String searchingName = deleteExperienceButton.getName();
				int i = 0;
				for (i = 0; i < skillPanelElementList.size(); i++){
					if (skillPanelElementList.get(i).getDeleteButtonName().equals(searchingName)){
						for (Component component : skillPanelElementList.get(i).getComponents()){
							component.setEnabled(false);
						}	
						skillPanelElementList.remove(i);
						break;
					}
				}
			}
		});
	}

	public void updateSectionFields(Object section) {
		((SkillsAndExperienceSection)section).updateAllFields(skillPanelElementList);
		
	}

	public void loadFromSection(Section section) {
		boolean first = true;
		int skillCounter = 1;
		for (Skill skill : ((SkillsAndExperienceSection)section).getList()){
			skillCounter = 1;
			if (!first){
				addProfessionalExperienceLabels();
			}
			first = false;
			int last = skillPanelElementList.size()-1;
			skillPanelElementList.get(last).getExperience().setText(skill.getExperienceName());
			for (String skills : skill.getSkillsList()){
				addSkillLabels(skillCounter);
				skillPanelElementList.get(last).getLastSkill().setText(skills);
				skillCounter = skillCounter + 1;
			}
		}
		revalidate();
		repaint();
	}
	
	public ArrayList<SkillPanelElement> getSkillPanelElementList(){
		return(skillPanelElementList);
	}
	
	public SkillPanelElement getLastSkillPanelElement(){
		return(skillPanelElementList.get(skillPanelElementList.size() - 1));
	}
}
