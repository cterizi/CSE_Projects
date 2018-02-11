package utils;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ProfessionalExperiencePanelElement extends JobCareerPanelElement{

	private ArrayList<JLabel> listOfAchievementLabels;
	private JLabel paragraphOfResponsibilitiesLabel;
	private JTextArea paragraphOfResponsibilities;
	private ArrayList<JTextField> listOfAchievements;
	private ArrayList<JButton> deleteAchievementButtons = new ArrayList<JButton>();

	public ProfessionalExperiencePanelElement(){
		super();
		paragraphOfResponsibilities = new JTextArea("Responsibilities...", 5, 35);
		paragraphOfResponsibilities.setLineWrap(true);
		paragraphOfResponsibilities.setWrapStyleWord(true);
		listOfAchievements = new ArrayList<JTextField>();
		listOfAchievementLabels = new ArrayList<JLabel>();
		paragraphOfResponsibilitiesLabel = new JLabel("Responsibilities");
	}
	
	public void createDeleteAchievementButton(String text, String name){
		JButton deleteButton = new JButton(text);
		deleteButton.setName(name);
		deleteAchievementButtons.add(deleteButton);
	}
	
	public void addAchievementLabel(JLabel achievenemtLabel){
		listOfAchievementLabels.add(achievenemtLabel);
	}
		
	public JLabel getAchievementLabel(int index){
		return (listOfAchievementLabels.get(index));
	}
	
	public ArrayList<JLabel> getAchievementLabels(){
		return(listOfAchievementLabels);
	}
	
	public JLabel getLastAchievementLabel(){
		return (listOfAchievementLabels.get(listOfAchievementLabels.size() - 1));
	}
	
	public JLabel getParagraphOfResponsibilitiesLabel(){
		return (paragraphOfResponsibilitiesLabel);
	}
	
	public void addAchievement(JTextField achievement){
		listOfAchievements.add(achievement);
	}
	
	public JTextField getAchievement(int index){
		return listOfAchievements.get(index);
	}
	
	public JTextField getLastAchievement(){
		return listOfAchievements.get(listOfAchievements.size() - 1);
	}
		
	public JTextArea getParagraphOfResponsibilities(){
		return paragraphOfResponsibilities;
	}
	
	public ArrayList<JTextField> getListOfAchievements(){
		return listOfAchievements;
	}
	
	public String getParagraphOfResponsibilitiesString(){
		return paragraphOfResponsibilities.getText();
	}
	
	public ArrayList<String> getListOfAchievementsString(){
		ArrayList<String> achievementList= new ArrayList<String>();
		for (JTextField achievement : listOfAchievements){
			achievementList.add(achievement.getText());
		}
		return achievementList;
	}
	
	public ArrayList<JButton> getDeleteAchievementButtons(){
		return deleteAchievementButtons;
	}
	
	public JButton getAchievementDeleteButton(int index){
		return deleteAchievementButtons.get(index);
	}
	
	public void removeAchievement(int index){
		listOfAchievementLabels.get(index).setEnabled(false);
		listOfAchievementLabels.remove(index);
		listOfAchievements.get(index).setEnabled(false);
		listOfAchievements.remove(index);
		deleteAchievementButtons.get(index).setEnabled(false);
		deleteAchievementButtons.remove(index);
	}
	
	public ArrayList<Component> getComponents(){
		ArrayList<Component> components = new ArrayList<Component>();
		for (Component component : super.getComponents()){
			components.add(component);
		}
		
		components.add(paragraphOfResponsibilitiesLabel);
		components.add(paragraphOfResponsibilities);
		
		for (int i = 0; i < listOfAchievements.size(); i++){
			components.add(listOfAchievementLabels.get(i));
			components.add(listOfAchievements.get(i));
		}

		return components;
	}
}
