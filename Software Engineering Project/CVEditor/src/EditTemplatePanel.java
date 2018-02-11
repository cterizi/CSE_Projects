import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorListener;

import cv.CV;
import cv.ChronologicalCV;
import cv.CombinedCV;
import cv.CustomCV;
import cv.FunctionalCV;
import section.Section;
import sectionpanels.AdditionalInformationPanel;
import sectionpanels.CareerSummaryPanel;
import sectionpanels.CoreStrengthsPanel;
import sectionpanels.EducationAndTrainingPanel;
import sectionpanels.FurtherCoursesPanel;
import sectionpanels.GeneralInformationPanel;
import sectionpanels.InterestsPanel;
import sectionpanels.ProfessionalExperiencePanel;
import sectionpanels.ProfessionalProfilePanel;
import sectionpanels.SectionPanel;
import sectionpanels.SkillsAndExperiencePanel;

public class EditTemplatePanel extends JPanel{
	
	private JButton sessionButtons[];
	private JButton clickedSession = null;
	private JPanel currentPanel = null;
	private GeneralInformationPanel generalInformationPanel;
	private ProfessionalProfilePanel professionalProfilePanel;
	private CoreStrengthsPanel coreStrengthsPanel;
	private ProfessionalExperiencePanel professionalExperiencePanel;
	private EducationAndTrainingPanel educationAndTrainingPanel;
	private FurtherCoursesPanel furtherCoursesPanel; 
	private AdditionalInformationPanel additionalInformationPanel;
	private InterestsPanel interestsPanel;
	private SkillsAndExperiencePanel skillsAndExperiencePanel;
	private CareerSummaryPanel careerSummaryPanel;
	private HashMap<String, SectionPanel> sectionPanelMap;
	private CV cv;
	
	public EditTemplatePanel(String templateName, ArrayList<Section> sectionList){
				
		sectionPanelMap = new HashMap<String, SectionPanel>();
		if(templateName.equals("chronological.jpg")){
			cv = new ChronologicalCV();
		}
		else if(templateName.equals("functional.jpg")){
			cv = new FunctionalCV();
		}
		else if(templateName.equals("combined.jpg")){
			cv = new CombinedCV();
		}
		else{
			cv = new CustomCV(sectionList);
		}
		MainWindow.setSaveEnabled(true);
		MainWindow.setSaveAsEnabled(true);
		
		ArrayList<Section> sections = cv.getSections();
		sessionButtons = new JButton[sections.size()];
		
		setBackground(new Color(81, 93, 107));
		setLayout(new BorderLayout());
		JPanel sessionPanel = new JPanel();
		sessionPanel.setOpaque(false);
		sessionPanel.setLayout(new GridLayout(sections.size(), 1));
		
		for (int i = 0; i < sections.size(); i++){
			JButton b = new JButton(sections.get(i).getSectionName());
			
			b.setBackground(new Color(46, 53, 62));
			b.setForeground(Color.WHITE);
			b.addMouseListener(new MouseAdapter() {
		
				@Override
				public void mouseEntered(MouseEvent arg0) {
					if (!b.equals(clickedSession)){
						b.setBackground(new Color(50, 57, 66));
						invalidate();
						repaint();
					}
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					if (!b.equals(clickedSession))
						refreshColor(b);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					refreshColor(clickedSession);
					clickedSession = b;
					changeCurrentPanel(b.getText());
					setClickedButtonColor();
				}
			
			});
			
			sessionButtons[i] = b;
			sessionPanel.add(b);
			
		}

		clickedSession = sessionButtons[0];
		setClickedButtonColor();
		
		add(sessionPanel, BorderLayout.WEST);
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		add(buttonsPanel, BorderLayout.PAGE_END);
		buttonsPanel.setBackground(Color.WHITE);
		JButton saveButton = new JButton("Save");
		buttonsPanel.add(saveButton);
		saveButton.addActionListener(MainWindow.getMenuActionListener());
		JButton mergeButton = new JButton("Merge");
		buttonsPanel.add(mergeButton);
		mergeButton.addActionListener(MainWindow.getMenuActionListener());
		
		generalInformationPanel = new GeneralInformationPanel();
		professionalProfilePanel = new ProfessionalProfilePanel();
		coreStrengthsPanel = new CoreStrengthsPanel();
		professionalExperiencePanel = new ProfessionalExperiencePanel();
		educationAndTrainingPanel = new EducationAndTrainingPanel();
		furtherCoursesPanel = new FurtherCoursesPanel(); 
		additionalInformationPanel = new AdditionalInformationPanel();
		interestsPanel = new InterestsPanel();
		skillsAndExperiencePanel = new SkillsAndExperiencePanel();
		careerSummaryPanel = new CareerSummaryPanel();
		createSectionPanelMap();
		loadPanelInformation();
		
		add(generalInformationPanel, BorderLayout.CENTER);
		currentPanel = generalInformationPanel;
		
	}
	
	public void createSectionPanelMap(){
		sectionPanelMap.put("Additional Information", additionalInformationPanel);
		sectionPanelMap.put("Career Summary", careerSummaryPanel);
		sectionPanelMap.put("Core Strengths", coreStrengthsPanel);
		sectionPanelMap.put("Education And Training", educationAndTrainingPanel);
		sectionPanelMap.put("Further Courses", furtherCoursesPanel);
		sectionPanelMap.put("General Information", generalInformationPanel);
		sectionPanelMap.put("Interests", interestsPanel);
		sectionPanelMap.put("Professional Experience", professionalExperiencePanel);
		sectionPanelMap.put("Professional Profile", professionalProfilePanel);
		sectionPanelMap.put("Skills And Experience", skillsAndExperiencePanel);
	}
	
	public void updateSections(){
		ArrayList<Section> sections = cv.getSections();
				
		for (Section section : sections){
			sectionPanelMap.get(section.getSectionName()).updateSectionFields(section);
		}
	}
	
	public void refreshColor(JButton b){
		b.setBackground(new Color(46, 53, 62));
		invalidate();
		repaint();
	}
	
	public void changeCurrentPanel(String sessionName){
		JPanel newPanel = generalInformationPanel;
		remove(currentPanel);
		if (sessionName.equals("General Information")) newPanel = generalInformationPanel;
		else if (sessionName.equals("Professional Profile")) newPanel = professionalProfilePanel;
		else if (sessionName.equals("Core Strengths")) newPanel = coreStrengthsPanel;
		else if(sessionName.equals("Professional Experience")) newPanel = professionalExperiencePanel;
		else if(sessionName.equals("Education And Training")) newPanel = educationAndTrainingPanel;
		else if(sessionName.equals("Further Courses")) newPanel = furtherCoursesPanel;
		else if(sessionName.equals("Additional Information")) newPanel = additionalInformationPanel;
		else if(sessionName.equals("Interests")) newPanel = interestsPanel;
		else if(sessionName.equals("Skills And Experience")) newPanel = skillsAndExperiencePanel;
		else if(sessionName.equals("Career Summary")) newPanel = careerSummaryPanel;
		
		if (!newPanel.equals(currentPanel)){
			
			currentPanel = newPanel;
		}
		add(newPanel, BorderLayout.CENTER);
		currentPanel = newPanel;
		revalidate();
		repaint();
	}
	
	public void loadPanelInformation(){
		for(Section section : cv.getSections()){
			sectionPanelMap.get(section.getSectionName()).loadFromSection(section);
		}
	}
	
	public void setClickedButtonColor(){
		clickedSession.setBackground(new Color(0, 153, 255));
		invalidate();
		repaint();
	}
	
	public CV getCV(){
		return(cv);
	}
	
	/*
	private JButton createSessionButton(String buttonText){
		JButton sessionButton = new JButton(buttonText);
		sessionButton.setBackground(new Color(46, 53, 62));
		sessionButton.setForeground(Color.WHITE);
		sessionButton.setBorder(new LineBorder(new Color(46, 53, 62), 12));
		return sessionButton;
	}*/

}
