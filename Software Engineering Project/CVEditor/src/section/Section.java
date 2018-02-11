package section;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import input.Field;

abstract public class Section {
	private String sectionName;
	private ArrayList<String> sectionFieldList = new ArrayList<>();
	
	abstract public void addFields();
	abstract public ArrayList<Field> compare(Section comparedSection);
	abstract public void updateFromFields(ArrayList<Field> fields);
	
	public Section(String sectionName){
		this.sectionName = sectionName;
	}
	
	public String getSectionName(){
		return(sectionName);
	}
	
	public void addField(String field){
		sectionFieldList.add(field);
	}
	
	public void clearSectionFieldList(){
		sectionFieldList.clear();
	}
	
	public ArrayList<String> getFieldList(){
		clearSectionFieldList();
		addFields();
		return(sectionFieldList);
	}
	
	public Field getTempField(String label, String field, String comparedField){
		Field tempField = new Field(label, comparedField);
		tempField.setPassField(!field.equals(comparedField));
		if(!field.equals(comparedField)){
			tempField.setComparedFieldValue(field);
		}
	
		return tempField;
	}
	
	public boolean sameList(ArrayList<String> list1, ArrayList<String> list2){
		Set<String> intersection = new HashSet<String>(list1);
		intersection.retainAll(list2);
		return list1.size() == intersection.size() && list2.size() == intersection.size();
	}
	
	public boolean checkDate(String date){
		if(date.contains("eg.")){
			return(false);
		}
		String dateSplit[] = date.split("-");
		if(dateSplit.length < 2){
			return(false);
		}
		dateSplit[0] = dateSplit[0].trim();
		dateSplit[1] = dateSplit[1].trim();
		if(stringToDate(dateSplit[0]) == null){
			return(false);
		}
		if(stringToDate(dateSplit[1]) == null){
			return(false);
		}
		return(stringToDate(dateSplit[0]).before(stringToDate(dateSplit[1])));
	}
	
	public Date stringToDate(String stringDate){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
		Date date = null;
		try {
		    date = df.parse(stringDate);
		} catch (ParseException e) {
			return(null);
		}
		return date;
	}
}
