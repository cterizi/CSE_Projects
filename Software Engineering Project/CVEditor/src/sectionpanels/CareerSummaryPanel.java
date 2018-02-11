package sectionpanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.CellConstraints.Alignment;
import com.jgoodies.forms.layout.FormLayout;

import section.CareerSummarySection;
import section.EducationAndTrainingSection;
import section.GeneralInformationSection;
import section.Section;
import utils.EducationElement;
import utils.JobCareerElement;
import utils.JobCareerPanelElement;

public class CareerSummaryPanel extends SectionPanel{
	
	private JPanel careerSummaryPanel;
	private ArrayList<JobCareerPanelElement> jobCareerPanelElementList;
	private JScrollPane careerSummaryScrollPanel;
	private int numberOfRows, numberOfColumns;
	private FormLayout formLayout;
	private JButton addCareerSummaryButton;
	private int deleteButtonId = 0;
	private int removedRows = 30;
	
	public CareerSummaryPanel(){
		setOpaque(false);
		setBackground(new Color(200, 200, 200));
		jobCareerPanelElementList = new ArrayList<JobCareerPanelElement>();
		formLayout = new FormLayout();
		careerSummaryPanel = new JPanel();
		careerSummaryPanel.setLayout(formLayout);
		careerSummaryScrollPanel = new JScrollPane(careerSummaryPanel);
		careerSummaryScrollPanel.setPreferredSize(new Dimension( 800, 400));
		add(careerSummaryScrollPanel);
		numberOfColumns = 35;
		createColumns();
		addCareerSummaryLabels();
		addRow();
		addCareerSummaryButton = new JButton("Add Career");
		careerSummaryPanel.add(addCareerSummaryButton, "2, " + numberOfRows*2 + ", left, default");
		addCareerSummaryButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			addCareerSummaryLabels();
		    addRow();
		    careerSummaryPanel.add(addCareerSummaryButton, "2, " + numberOfRows*2 + ", left, default");
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
	
	public void deleteRows(int index){
		deleteRow(index);
		index = index - 1;
		deleteRow(index);
		index = index - 1;
		deleteRow(index);
		numberOfRows = numberOfRows - 1;
		numberOfRows = numberOfRows - 1;
		numberOfRows = numberOfRows - 1;
	}
	
	public void deleteRow(int index){
		formLayout.removeRow(index);
	}
	
	public void addCareerSummary(JLabel label, String labelName){
		addRow();
		if(labelName.equals("______________")){
			label = new JLabel(labelName);
			careerSummaryPanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		else{
			label = new JLabel(labelName);
			careerSummaryPanel.add(label, "2, " + numberOfRows*2  + ", right, default");
		}
		revalidate();
		repaint();
	}	
	
	public void addCareerSummaryLabels(){
		jobCareerPanelElementList.add(new JobCareerPanelElement());
		int last = jobCareerPanelElementList.size() - 1;
		deleteButtonId = deleteButtonId + 1;
		addRows();
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getCompanyNameLabel(), "2, " + numberOfRows*2  + ", right, default");
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getCompanyName(), "4, " + numberOfRows*2  + ", left, default");
		jobCareerPanelElementList.get(last).getCompanyName().setColumns(30);
		jobCareerPanelElementList.get(last).createDeleteButton("Delete Career", deleteButtonId+"");
		JButton deleteCareerButton = jobCareerPanelElementList.get(last).getDeleteButton();
		careerSummaryPanel.add(deleteCareerButton, "6, " + numberOfRows*2  + ", left, default");
		deleteCareerButton.setForeground(new Color(200, 0, 0));
		deleteCareerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String searchingName = deleteCareerButton.getName();
				int i = 0;
				for (i = 0; i < jobCareerPanelElementList.size(); i++){
					if (jobCareerPanelElementList.get(i).getDeleteButtonName().equals(searchingName)){
						for (Component component : jobCareerPanelElementList.get(i).getComponents()){
							component.setEnabled(false);
							jobCareerPanelElementList.remove(i);
						}		
						break;
					}
				}
			}
		});
		addRows();
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getJobLabel(), "2, " + numberOfRows*2  + ", right, default");
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getJob(), "4, " + numberOfRows*2  + ", left, default");
		jobCareerPanelElementList.get(last).getJob().setColumns(30);
		addRows();
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getTitleLabel(), "2, " + numberOfRows*2  + ", right, default");
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getTitle(), "4, " + numberOfRows*2  + ", left, default");
		jobCareerPanelElementList.get(last).getTitle().setColumns(30);
		addRows();
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getDateLabel(), "2, " + numberOfRows*2  + ", right, default");
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getDate(), "4, " + numberOfRows*2  + ", left, default");
		jobCareerPanelElementList.get(last).getDate().setColumns(30);
		addRows();
		careerSummaryPanel.add(jobCareerPanelElementList.get(last).getLineLabel(), "2, " + numberOfRows*2  + ", right, default");
		revalidate();
		repaint();
	}
	
	public void refreshAddCareerButton(){
		int x, y;
		Alignment vAllign, hAllign;
		x = formLayout.getConstraints(addCareerSummaryButton).gridX;
		y = formLayout.getConstraints(addCareerSummaryButton).gridY;
		vAllign = formLayout.getConstraints(addCareerSummaryButton).vAlign;
		hAllign = formLayout.getConstraints(addCareerSummaryButton).hAlign;
		formLayout.setConstraints(addCareerSummaryButton, formLayout.getConstraints(addCareerSummaryButton).rc(y-removedRows, x, vAllign, hAllign));
	}

	@Override
	public void updateSectionFields(Object section) {
		((CareerSummarySection)section).updateAllFields(jobCareerPanelElementList);
	}

	@Override
	public void loadFromSection(Section section) {
		boolean first = true;
		for(JobCareerElement jobCareerElement : ((CareerSummarySection)section).getList() ){
			if (!first){
				addCareerSummaryLabels();
			}
			first = false;
			int last = jobCareerPanelElementList.size()-1;
			jobCareerPanelElementList.get(last).getCompanyName().setText(jobCareerElement.getCompanyName());
			jobCareerPanelElementList.get(last).getJob().setText(jobCareerElement.getJob());
			jobCareerPanelElementList.get(last).getTitle().setText(jobCareerElement.getTitle());
			jobCareerPanelElementList.get(last).getDate().setText(jobCareerElement.getDate());
		}
		revalidate();
		repaint();
	}
	
	public int getCareerElementListSize(){
		return(jobCareerPanelElementList.size());
	}
}
