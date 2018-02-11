package utils;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JobCareerPanelElement {

	private JLabel companyNameLabel;
	private JLabel jobLabel;
	private JLabel titleLabel;
	private JLabel dateLabel;
	private JLabel lineLabel;
	
	private JTextField companyName;
	private JTextField job;
	private JTextField title;
	private JTextField date;
	private JButton deleteButton;
	
	
	public JobCareerPanelElement(){
		
		companyNameLabel = new JLabel();
		companyNameLabel.setText("Company Name");
		jobLabel = new JLabel();
		jobLabel.setText("Job");
		titleLabel = new JLabel();
		titleLabel.setText("Title");
		dateLabel = new JLabel();
		dateLabel.setText("Date");
		lineLabel = new JLabel();
		lineLabel.setText("______________");
		
		companyName = new JTextField();
		companyName.setText("");
		job = new JTextField();
		job.setText("");
		title = new JTextField();
		title.setText("");
		date = new JTextField();
		date.setText("eg.12/05/2015 - 20/03/2016");
	}
	
	public void createDeleteButton(String text, String name){
		deleteButton = new JButton(text);
		deleteButton.setName(name);
	}
	
	public void setDate(String date){
		this.date.setText(date);
	}
	
	public JLabel getCompanyNameLabel(){
		return companyNameLabel;
	}
	
	public JLabel getJobLabel(){
		return jobLabel;
	}
	
	public JLabel getTitleLabel(){
		return titleLabel;
	}
	
	public JLabel getDateLabel(){
		return dateLabel;
	}
	
	public JLabel getLineLabel(){
		return(lineLabel);
	}
	public JTextField getCompanyName(){
		return(companyName);
	}
	
	public JTextField getJob(){
		return(job);
	}
	
	public JTextField getTitle(){
		return(title);
	}
	
	public JTextField getDate(){
		return(date);
	}
	
	public JButton getDeleteButton(){
		return deleteButton;
	}
	
	public String getCompanyNameString(){
		return(companyName.getText());
	}
	
	public String getJobString(){
		return(job.getText());
	}
	
	public String getTitleString(){
		return(title.getText());
	}
	
	public String getDateString(){
		return(date.getText());
	}
	
	public String getDeleteButtonName(){
		return deleteButton.getName();
	}
	

	public ArrayList<Component> getComponents(){
		ArrayList<Component> components = new ArrayList<Component>();
		components.add(companyNameLabel);
		components.add(companyName);
		components.add(jobLabel);
		components.add(job);
		components.add(titleLabel);
		components.add(title);
		components.add(dateLabel);
		components.add(date);
		components.add(lineLabel);
		components.add(deleteButton);
		return components;
	}
	
}
