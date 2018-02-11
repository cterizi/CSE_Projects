package utils;

import java.util.ArrayList;

public class EducationElement {
	
	private String name;
	private String establishment;
	private String location;
	private String date;
	
	public EducationElement(){
		name = "";
		establishment = "";
		location = "";
		date = "";
	}
	
	public ArrayList<String> getFieldList(){
		ArrayList<String> fieldList = new ArrayList<>();
		fieldList.add("Name:~" + name);
		fieldList.add("Establishment:~" + establishment);
		fieldList.add("Location:~" + location);
		fieldList.add("Date:~" + date);
		return(fieldList);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setEstablishment(String establishment){
		this.establishment = establishment;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getName(){
		return(name);
	}
	
	public String getEstablishment(){
		return(establishment);
	}
	
	public String getLocation(){
		return(location);
	}
	
	public String getDate(){
		return(date);
	}
}
