package sectionpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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

import section.EducationAndTrainingSection;
import section.FurtherCoursesSection;
import section.Section;
import utils.EducationElement;
import utils.EducationPanelElement;

public class FurtherCoursesPanel extends SectionPanel{
	
	private JPanel furtherCoursesPanel;
	private ArrayList<EducationPanelElement> educationElementList;
	private JScrollPane furtherCoursesScrollPanel;
	private int numberOfRows, numberOfColumns;
	private FormLayout formLayout;
	private int deleteButtonId = 0;
	
	public FurtherCoursesPanel(){
		setOpaque(false);
		setBackground(new Color(200, 200, 200));
		educationElementList = new ArrayList<EducationPanelElement>();
		formLayout = new FormLayout();
		furtherCoursesPanel = new JPanel();
		furtherCoursesPanel.setLayout(formLayout);
		furtherCoursesScrollPanel = new JScrollPane(furtherCoursesPanel);
		furtherCoursesScrollPanel.setPreferredSize(new Dimension(800, 400));
		add(furtherCoursesScrollPanel);
		numberOfColumns = 35;
		createColumns();
		addCoursesLabels();
		addRow();
		JButton addCourseButton = new JButton("Add Course");
		furtherCoursesPanel.add(addCourseButton, "2, " + numberOfRows*2 + ", left, default");
		addCourseButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
		    addCoursesLabels();
		    addRow();
		    furtherCoursesPanel.add(addCourseButton, "2, " + numberOfRows*2 + ", left, default");
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
	
	public void addCourse(JLabel label, String labelName){
		addRow();
		addRow();
		addRow();
		if(labelName.equals("______________")){
			label = new JLabel(labelName);
			furtherCoursesPanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		else{
			label = new JLabel(labelName);
			furtherCoursesPanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		revalidate();
		repaint();
	}	
	
	public void addCoursesLabels(){
		deleteButtonId = deleteButtonId + 1;
		educationElementList.add(new EducationPanelElement());
		int last = educationElementList.size() - 1;
		addRows();
		educationElementList.get(last).setNameLabel("Course Name");
		furtherCoursesPanel.add(educationElementList.get(last).getNameLabel(), "2, " + numberOfRows*2  + ", right, default");
		furtherCoursesPanel.add(educationElementList.get(last).getName(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getName().setColumns(30);
		educationElementList.get(last).createDeleteButton("Delete Course", deleteButtonId+"");
		JButton deleteCourseButton = educationElementList.get(last).getDeleteButton();
		furtherCoursesPanel.add(deleteCourseButton, "6, " + numberOfRows*2  + ", left, default");
		deleteCourseButton.setForeground(new Color(200, 0, 0));
		deleteCourseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String searchingName = deleteCourseButton.getName();
				int i = 0;
				for (i = 0; i < educationElementList.size(); i++){
					if (educationElementList.get(i).getDeleteButtonName().equals(searchingName)){
						for (Component component : educationElementList.get(i).getComponents()){
							component.setEnabled(false);
						}	
						educationElementList.remove(i);
						break;
					}
				}
			}
		});
		addRows();
		furtherCoursesPanel.add(educationElementList.get(last).getEstablishmentLabel(), "2, " + numberOfRows*2  + ", right, default");
		furtherCoursesPanel.add(educationElementList.get(last).getEstablishment(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getEstablishment().setColumns(30);
		addRows();
		furtherCoursesPanel.add(educationElementList.get(last).getLocationLabel(), "2, " + numberOfRows*2  + ", right, default");
		furtherCoursesPanel.add(educationElementList.get(last).getLocation(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getLocation().setColumns(30);
		addRows();
		furtherCoursesPanel.add(educationElementList.get(last).getDateLabel(), "2, " + numberOfRows*2  + ", right, default");
		furtherCoursesPanel.add(educationElementList.get(last).getDate(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getDate().setColumns(30);
		addRow();
		furtherCoursesPanel.add(educationElementList.get(last).getLineLabel(), "2, " + numberOfRows*2  + ", right, default");
		revalidate();
		repaint();
	}

	@Override
	public void updateSectionFields(Object section) {
		((FurtherCoursesSection)section).updateAllFields(educationElementList);
	}

	@Override
	public void loadFromSection(Section section) {
		boolean first = true;
		for(EducationElement educationElement : ((FurtherCoursesSection)section).getList() ){
			if (!first){
				addCoursesLabels();
			}
			first = false;
			int last = educationElementList.size()-1;
			educationElementList.get(last).getName().setText(educationElement.getName());
			educationElementList.get(last).getEstablishment().setText(educationElement.getEstablishment());
			educationElementList.get(last).getLocation().setText(educationElement.getLocation());
			educationElementList.get(last).getDate().setText(educationElement.getDate());
		}
		revalidate();
		repaint();
	}
	
	public int getCoursesElementListSize(){
		return(educationElementList.size());
	}
}