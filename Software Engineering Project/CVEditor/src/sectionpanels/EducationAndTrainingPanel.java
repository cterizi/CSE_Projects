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

import section.EducationAndTrainingSection;
import section.Section;
import utils.EducationElement;
import utils.EducationPanelElement;
import utils.JobCareerPanelElement;

public class EducationAndTrainingPanel extends SectionPanel{
	
	private JPanel educationAndTrainingPanel;
	private ArrayList<EducationPanelElement> educationElementList;
	private JScrollPane educationAndTrainingScrollPanel;
	private int numberOfRows, numberOfColumns;
	private FormLayout formLayout;
	private int deleteButtonId = 0;
	
	public EducationAndTrainingPanel(){
		setOpaque(false);
		setBackground(new Color(200, 200, 200));
		formLayout = new FormLayout();
		educationElementList = new ArrayList<EducationPanelElement>();
		educationAndTrainingPanel = new JPanel();
		educationAndTrainingPanel.setLayout(formLayout);
		educationAndTrainingScrollPanel = new JScrollPane(educationAndTrainingPanel);
		educationAndTrainingScrollPanel.setPreferredSize(new Dimension( 800, 400));
		add(educationAndTrainingScrollPanel);
		numberOfColumns = 35;
		createColumns();
		addEducationAndTrainingLabels();
		addRow();
		JButton addEducationAndTrainingButton = new JButton("Add Education");
		educationAndTrainingPanel.add(addEducationAndTrainingButton, "2, " + numberOfRows*2 + ", left, default");
		addEducationAndTrainingButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  addEducationAndTrainingLabels();
		    addRow();
		    educationAndTrainingPanel.add(addEducationAndTrainingButton, "2, " + numberOfRows*2 + ", left, default");
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
	
	public void addEducationAndTrainingLabels(){
		deleteButtonId = deleteButtonId + 1;
		educationElementList.add(new EducationPanelElement());
		int last = educationElementList.size() - 1;
		addRows();
		educationElementList.get(last).setNameLabel("Qualification Name");
		educationAndTrainingPanel.add(educationElementList.get(last).getNameLabel(), "2, " + numberOfRows*2  + ", right, default");
		educationAndTrainingPanel.add(educationElementList.get(last).getName(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getName().setColumns(30);
		educationElementList.get(last).createDeleteButton("Delete Education", deleteButtonId+"");
		JButton deleteEducationButton = educationElementList.get(last).getDeleteButton();
		educationAndTrainingPanel.add(deleteEducationButton, "6, " + numberOfRows*2  + ", left, default");
		deleteEducationButton.setForeground(new Color(200, 0, 0));
		deleteEducationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String searchingName = deleteEducationButton.getName();
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
		educationAndTrainingPanel.add(educationElementList.get(last).getEstablishmentLabel(), "2, " + numberOfRows*2  + ", right, default");
		educationAndTrainingPanel.add(educationElementList.get(last).getEstablishment(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getEstablishment().setColumns(30);
		addRows();
		educationAndTrainingPanel.add(educationElementList.get(last).getLocationLabel(), "2, " + numberOfRows*2  + ", right, default");
		educationAndTrainingPanel.add(educationElementList.get(last).getLocation(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getLocation().setColumns(30);
		addRows();
		educationAndTrainingPanel.add(educationElementList.get(last).getDateLabel(), "2, " + numberOfRows*2  + ", right, default");
		educationAndTrainingPanel.add(educationElementList.get(last).getDate(), "4, " + numberOfRows*2  + ", left, default");
		educationElementList.get(last).getDate().setColumns(30);
		addRow();
		educationAndTrainingPanel.add(educationElementList.get(last).getLineLabel(), "2, " + numberOfRows*2  + ", right, default");
		revalidate();
		repaint();
	}
	
	@Override
	public void updateSectionFields(Object section) {
		((EducationAndTrainingSection)section).updateAllFields(educationElementList);
		
	}

	@Override
	public void loadFromSection(Section section) {
		boolean first = true;
		for(EducationElement educationElement : ((EducationAndTrainingSection)section).getList() ){
			if (!first){
				addEducationAndTrainingLabels();
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
	
	public int getEducationElementListSize(){
		return(educationElementList.size());
	}
}
