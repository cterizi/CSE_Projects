package utils;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SkillPanelElement {

	private JLabel experienceLabel;
	private JLabel lineLabel;
	private ArrayList<JLabel> skillsListLabels;
	private JTextField experience;
	private ArrayList<JTextField> skillsList;
	private ArrayList<JButton> deleteSkillButtons = new ArrayList<JButton>();
	private JButton deleteButton;
	
	public SkillPanelElement(){
		lineLabel = new JLabel("______________");
		experienceLabel = new JLabel("Experience on");
		experience = new JTextField("");
		skillsListLabels = new ArrayList<JLabel>();
		skillsList = new ArrayList<JTextField>();
	}
	
	public void createDeleteButton(String text, String name){
		deleteButton = new JButton(text);
		deleteButton.setName(name);
	}
	
	public void createDeleteSkillButton(String text, String name){
		JButton deleteButton = new JButton(text);
		deleteButton.setName(name);
		deleteSkillButtons.add(deleteButton);
	}
	
	public void addSkillLabel(JLabel skillLabel){
		skillsListLabels.add(skillLabel);
	}
	
	public void addSkill(JTextField skill){
		skillsList.add(skill);
	}
	
	public JLabel getLineLabel(){
		return lineLabel;
	}
	
	public JLabel getExperienceLabel(){
		return experienceLabel;
	}
	
	public JLabel getSkillLabel(int index){
		return skillsListLabels.get(index);
	}
	
	public JLabel getLastSkillLabel(){
		return skillsListLabels.get(skillsListLabels.size() - 1);
	}
	
	public JTextField getSkill(int index){
		return skillsList.get(index);
	}
	
	public JTextField getLastSkill(){
		return skillsList.get(skillsList.size() - 1);
	}
	
	public JTextField getExperience(){
		return experience;
	}
	
	public ArrayList<JTextField> getSkillsList(){
		return skillsList;
	}
	
	public ArrayList<JButton> getDeleteSkillButtons(){
		return deleteSkillButtons;
	}
	
	public JButton getLastDeleteSkillButton(){
		return deleteSkillButtons.get(deleteSkillButtons.size()-1);
	}
	
	public String getExperienceString(){
		return experience.getText();
	}
	
	public JButton getDeleteButton(){
		return deleteButton;
	}
	
	public String getDeleteButtonName(){
		return deleteButton.getName();
	}
	
	public ArrayList<JLabel> getSkillsListLabels(){
		return(skillsListLabels);
	}
	
	public ArrayList<String> getSkillsListString(){
		ArrayList<String> skillsListString = new ArrayList<String>();
		
		for (JTextField skill : skillsList){
			skillsListString.add(skill.getText());
		}
		
		return skillsListString;
	}
	
	public void removeSkill(int index){
		skillsListLabels.get(index).setEnabled(false);
		skillsListLabels.remove(index);
		skillsList.get(index).setEnabled(false);
		skillsList.remove(index);
		deleteSkillButtons.get(index).setEnabled(false);
		deleteSkillButtons.remove(index);
	}
	
	public ArrayList<Component> getComponents(){
		ArrayList<Component> components = new ArrayList<Component>();
		components.add(experienceLabel);
		components.add(lineLabel);
		components.add(experience);
		components.add(deleteButton);
		for (int i = 0; i < skillsList.size(); i++){
			components.add(skillsListLabels.get(i));
			components.add(skillsList.get(i));
			components.add(deleteSkillButtons.get(i));
		}
		return components;
	}
}
