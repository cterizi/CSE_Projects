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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import section.ProfessionalExperienceSection;
import section.Section;
import utils.ProfessionalExperience;
import utils.ProfessionalExperiencePanelElement;

public class ProfessionalExperiencePanel extends SectionPanel{
	
	private JPanel professionalExperiencePanel;
	private ArrayList<ProfessionalExperiencePanelElement> professionalExperienceList;
	private JScrollPane professionalExperienceScrollPanel;
	private int numberOfRows, numberOfColumns, counterForAchievements;
	private FormLayout formLayout;
	private int deleteButtonId = 0;
	private int deleteAchievementId = 0;
	
	public ProfessionalExperiencePanel(){
		setOpaque(false);
		setBackground(new Color(200, 200, 200));
		professionalExperienceList = new ArrayList<ProfessionalExperiencePanelElement>();
		formLayout = new FormLayout();
		professionalExperiencePanel = new JPanel();
		professionalExperiencePanel.setLayout(formLayout);
		professionalExperienceScrollPanel = new JScrollPane(professionalExperiencePanel);
		professionalExperienceScrollPanel.setPreferredSize(new Dimension( 800, 400));
		add(professionalExperienceScrollPanel);
		numberOfColumns = 35;
		counterForAchievements = 0;
		createColumns();
		addProfessionalExperienceLabels();
		addRow();
		JButton addprofessionalExperienceButton = new JButton("Add Experience");
		JButton addAchievementButton = new JButton("Add Achievement");
		professionalExperiencePanel.add(addprofessionalExperienceButton, "2, " + numberOfRows*2 + ", left, default");
		professionalExperiencePanel.add(addAchievementButton, "4, " + numberOfRows*2 + ", left, default");
		addprofessionalExperienceButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			int last = professionalExperienceList.size() - 1;
			if (last >= 0) professionalExperiencePanel.add(professionalExperienceList.get(last).getLineLabel(), "2, " + numberOfRows*2 + ", left, default");
			addProfessionalExperienceLabels();
		    addRow();
		    counterForAchievements = 0;
		    professionalExperiencePanel.add(addprofessionalExperienceButton, "2, " + numberOfRows*2 + ", left, default");
		    professionalExperiencePanel.add(addAchievementButton, "4, " + numberOfRows*2 + ", left, default");
		    revalidate();
			repaint();
		  }
		});
		addAchievementButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  counterForAchievements = counterForAchievements + 1;
				  addAchievementLabels(counterForAchievements);
				  addRow();
				  professionalExperiencePanel.add(addAchievementButton, "4, " + numberOfRows*2 + ", left, default");
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
	
	public void addRow(){
		formLayout.appendRow(FormFactory.RELATED_GAP_ROWSPEC);
	    formLayout.appendRow(FormFactory.DEFAULT_ROWSPEC);
	    numberOfRows += 1;
	}
	
	public void addRows(){
		addRow();
		addRow();
		addRow();
	}
	
	public void addProfessionalExperience(JLabel label, String labelName){
		addRow();
		addRow();
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
	
	public void addAchievementLabels(int counterForAchievements){
		deleteAchievementId = deleteAchievementId + 1;
		addRow();
		int last = professionalExperienceList.size() - 1;
		
		professionalExperienceList.get(last).createDeleteAchievementButton("x", deleteAchievementId+"");
		JButton deleteAchievementButton = professionalExperienceList.get(last).getAchievementDeleteButton(last);
		deleteAchievementButton.setForeground(new Color(200, 0, 0));
		JLabel achievementLabel = new JLabel("Achievement " + Integer.toString(counterForAchievements));
		JTextField achievementTextField = new JTextField("");
		professionalExperienceList.get(last).addAchievementLabel(achievementLabel);
		professionalExperienceList.get(last).addAchievement(achievementTextField);
		professionalExperiencePanel.add(achievementLabel, "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(professionalExperienceList.get(last).getLastAchievement(), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getLastAchievement().setColumns(30);
		
		professionalExperiencePanel.add(deleteAchievementButton, "6, " + numberOfRows*2 + ", left, default");
		addRow();
		JButton addAchievementButton = new JButton("Add Achievement");
		professionalExperiencePanel.add(addAchievementButton, "4, " + numberOfRows*2 + ", left, default");
		deleteAchievementButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  String searchingName = deleteAchievementButton.getName();
				  boolean found = false;
				  int i = 0;
				  for (i = 0; i < professionalExperienceList.size(); i++){
					  for (JButton deleteButton : professionalExperienceList.get(i).getDeleteAchievementButtons()){
						  if (deleteButton.getName().equals(deleteAchievementButton.getName())){
							  professionalExperienceList.get(i).removeAchievement(i);
							  found = true;
							  break;
						  }
					  }
					  if (found){
						  break;
					  }
						
				  }
			  }
			});
		revalidate();
		repaint();
	}
	
	public void addProfessionalExperienceLabels(){
		deleteButtonId = deleteButtonId + 1;
		professionalExperienceList.add(new ProfessionalExperiencePanelElement());
		int last = professionalExperienceList.size() - 1;
		addRows();
		professionalExperiencePanel.add(professionalExperienceList.get(last).getCompanyNameLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(professionalExperienceList.get(last).getCompanyName(), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getCompanyName().setColumns(30);
		professionalExperienceList.get(last).createDeleteButton("Delete Experience", deleteButtonId+"");
		JButton deleteExperienceButton = professionalExperienceList.get(last).getDeleteButton();
		professionalExperiencePanel.add(deleteExperienceButton, "6, " + numberOfRows*2  + ", left, default");
		deleteExperienceButton.setForeground(new Color(200, 0, 0));
		deleteExperienceButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e){
				  String searchingName = deleteExperienceButton.getName();
					int i = 0;
					for (i = 0; i < professionalExperienceList.size(); i++){
						if (professionalExperienceList.get(i).getDeleteButtonName().equals(searchingName)){
							for (Component component : professionalExperienceList.get(i).getComponents()){
								component.setEnabled(false);
							}	
							professionalExperienceList.remove(i);
							break;
						}
					}
			  }
			});
		addRows();
		professionalExperiencePanel.add(professionalExperienceList.get(last).getJobLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(professionalExperienceList.get(last).getJob(), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getJob().setColumns(30);
		addRows();
		professionalExperiencePanel.add(professionalExperienceList.get(last).getTitleLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(professionalExperienceList.get(last).getTitle(), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getTitle().setColumns(30);
		addRows();
		professionalExperiencePanel.add(professionalExperienceList.get(last).getDateLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(professionalExperienceList.get(last).getDate(), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getDate().setColumns(30);
		addRows();
		professionalExperiencePanel.add(professionalExperienceList.get(last).getParagraphOfResponsibilitiesLabel(), "2, " + numberOfRows*2  + ", right, default");
		professionalExperiencePanel.add(new JScrollPane(professionalExperienceList.get(last).getParagraphOfResponsibilities()), "4, " + numberOfRows*2  + ", left, default");
		professionalExperienceList.get(last).getParagraphOfResponsibilities().setColumns(30);
		
	}

	@Override
	public void updateSectionFields(Object section) {
		((ProfessionalExperienceSection)section).updateAllFields(professionalExperienceList);
		
	}

	public void loadFromSection(Section section) {
		boolean first = true;
		int achievementCounter = 1;
		for (ProfessionalExperience professionalExperience : ((ProfessionalExperienceSection)section).getProfessionalExperienceList()){
			achievementCounter = 1;
			if (!first){
				addProfessionalExperienceLabels();
			}
			first = false;
			int last = professionalExperienceList.size()-1;
			professionalExperienceList.get(last).getCompanyName().setText(professionalExperience.getCompanyName());
			professionalExperienceList.get(last).getJob().setText(professionalExperience.getJob());
			professionalExperienceList.get(last).getTitle().setText(professionalExperience.getTitle());
			professionalExperienceList.get(last).getDate().setText(professionalExperience.getDate());
			professionalExperienceList.get(last).getParagraphOfResponsibilities().setText(professionalExperience.getParagraphOfResponsibilities());
			for (String achievement : professionalExperience.getListOfAchievements()){
				addAchievementLabels(achievementCounter);
				professionalExperienceList.get(last).getLastAchievement().setText(achievement);
				achievementCounter = achievementCounter + 1;
			}
		}
		revalidate();
		repaint();
	}
	
	public void deleteAchievement(String searchingName){
		  boolean found = false;
		  int i = 0, j = 0;
		  for (i = 0; i < professionalExperienceList.size(); i++){
			  j = 0;
			  for (JButton deleteButton : professionalExperienceList.get(i).getDeleteAchievementButtons()){
				  if (deleteButton.getName().equals(searchingName)){
					  professionalExperienceList.get(i).removeAchievement(j);
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
	
	 public ProfessionalExperiencePanelElement getLastProfessionalExperience(){
		 return(professionalExperienceList.get(professionalExperienceList.size() - 1));
	 }
}
