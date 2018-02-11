package utils;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EducationPanelElement {
	
	private JLabel nameLabel;
	private JLabel establishmentLabel;
	private JLabel locationLabel;
	private JLabel dateLabel;
	private JLabel lineLabel;
	
	private JTextField name;
	private JTextField establishment;
	private JTextField location;
	private JTextField date;
	private JButton deleteButton;
	
	public EducationPanelElement(){
		nameLabel = new JLabel();
		nameLabel.setText("");
		establishmentLabel = new JLabel();
		establishmentLabel.setText("Establishment");
		locationLabel = new JLabel();
		locationLabel.setText("Location");
		dateLabel = new JLabel();
		dateLabel.setText("Date:");
		lineLabel = new JLabel();
		lineLabel.setText("______________");
		
		name = new JTextField();
		name.setText("");
		establishment = new JTextField();
		establishment.setText("");
		location = new JTextField();
		location.setText("");
		date = new JTextField();
		date.setText("eg.12/05/2015 - 20/03/2016");
	}
	
	public void createDeleteButton(String text, String name){
		deleteButton = new JButton(text);
		deleteButton.setName(name);
	}
	
	public void setNameLabel(String labelName){
		nameLabel.setText(labelName);
	}
	
	public void setDate(String date){
		this.date.setText(date);
	}
	
	public JLabel getNameLabel(){
		return(nameLabel);
	}
	
	public JLabel getEstablishmentLabel(){
		return(establishmentLabel);
	}
	
	public JLabel getLocationLabel(){
		return(locationLabel);
	}
	
	public JLabel getDateLabel(){
		return(dateLabel);
	}
	
	public JLabel getLineLabel(){
		return(lineLabel);
	}
	
	public JTextField getName(){
		return(name);
	}
	
	public JTextField getEstablishment(){
		return(establishment);
	}
	
	public JTextField getLocation(){
		return(location);
	}
	
	public JTextField getDate(){
		return(date);
	}
	
	public String getNameString(){
		return(name.getText());
	}
	
	public String getEstablishmentString(){
		return(establishment.getText());
	}
	
	public String getLocationString(){
		return(location.getText());
	}
	
	public String getDateString(){
		return(date.getText());
	}
	
	public JButton getDeleteButton(){
		return deleteButton;
	}
	
	public String getDeleteButtonName(){
		return deleteButton.getName();
	}
	
	public ArrayList<Component> getComponents(){
		ArrayList<Component> components = new ArrayList<Component>();
		components.add(nameLabel);
		components.add(name);
		components.add(establishmentLabel);
		components.add(establishment);
		components.add(locationLabel);
		components.add(location);
		components.add(dateLabel);
		components.add(date);
		components.add(lineLabel);
		components.add(deleteButton);
		return components;
	}
	
}
