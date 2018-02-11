package sectionpanels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.FormLayout;

import section.GeneralInformationSection;
import section.Section;

public class GeneralInformationPanel extends SectionPanel{
	
	private FormLayout formLayout;
	private int numberOfRows;
	private int numberOfColumns;
	private JLabel nameLabel, addressLabel, homeTelephoneLabel, mobileTelephoneLabel, emailLabel;
	private JTextField nameTextField, addressTextField, homeTelephoneTextField;
	private JTextField mobileTelephoneTextField, emailTextField;
	private int labelCounter;
	
	public GeneralInformationPanel(){
		setBackground(new Color(200, 200, 200));
		formLayout = new FormLayout();
		setLayout(formLayout);
		labelCounter = 0;
		numberOfColumns = 25;
		createColumns();
		addLabel(nameLabel, "Name:");
		nameTextField = new JTextField();
		add(nameTextField, "4, " + numberOfRows*2  + ", left, default");
		nameTextField.setColumns(25);
		addLabel(addressLabel, "Address:");
		addressTextField = new JTextField();
		add(addressTextField, "4, " + numberOfRows*2  + ", left, default");
		addressTextField.setColumns(25);
		addLabel(homeTelephoneLabel, "Telephone(home):");
		homeTelephoneTextField = new JTextField();
		add(homeTelephoneTextField, "4, " + numberOfRows*2  + ", left, default");
		homeTelephoneTextField.setColumns(25);
		addLabel(mobileTelephoneLabel, "Telephone(mobile):");
		mobileTelephoneTextField = new JTextField();
		add(mobileTelephoneTextField, "4, " + numberOfRows*2  + ", left, default");
		mobileTelephoneTextField.setColumns(25);
		addLabel(emailLabel, "Email:");
		emailTextField = new JTextField();
		add(emailTextField, "4, " + numberOfRows*2  + ", left, default");
		emailTextField.setColumns(25);
		
	}
	
	public void createColumns(){
		for(int i=0;i<numberOfColumns;i++){
		    formLayout.appendColumn(FormFactory.RELATED_GAP_COLSPEC);
		    formLayout.appendColumn(FormFactory.DEFAULT_COLSPEC );
		}
	}
	
	public void addLabel(JLabel label, String labelName){
		addRow();
		addRow();
		addRow();
		addRow();
		label = new JLabel(labelName);
		add(label, "2, " + numberOfRows*2  + ", right, default");
	}
	
	public void addRow(){
		formLayout.appendRow(FormFactory.RELATED_GAP_ROWSPEC);
	    formLayout.appendRow(FormFactory.DEFAULT_ROWSPEC);
	    numberOfRows += 1;
	}
	
	public String getName(){
		return(nameTextField.getText());
	}

	public void updateSectionFields(Object section) {
		((GeneralInformationSection)section).updateAllFields(nameTextField.getText(), addressTextField.getText(), homeTelephoneTextField.getText(), mobileTelephoneTextField.getText(), emailTextField.getText());
	}

	public void loadFromSection(Section section) {
		nameTextField.setText(((GeneralInformationSection)section).getName());
		addressTextField.setText(((GeneralInformationSection)section).getAddress());
		homeTelephoneTextField.setText(((GeneralInformationSection)section).getHomeTelephone());
		mobileTelephoneTextField.setText(((GeneralInformationSection)section).getMobileTelephone());
		emailTextField.setText(((GeneralInformationSection)section).getEmail());
		
		revalidate();
		repaint();
	}
	
	public void setGeneralInformationPanel(String name, String address, String telephone, String mobile, String email){
		nameTextField.setText(name);
		addressTextField.setText(address);
		homeTelephoneTextField.setText(telephone);
		mobileTelephoneTextField.setText(mobile);
		emailTextField.setText(email);
	}
	
}
