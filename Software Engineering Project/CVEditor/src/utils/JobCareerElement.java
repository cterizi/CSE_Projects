package utils;

import java.util.ArrayList;

public class JobCareerElement {

	private String type;
	private String companyName;
	private String job;
	private String title;
	private String date;
	
	public JobCareerElement(String type){
		this.type = type;
		companyName = "";
		job = "";
		title = "";
		date = "";	
	}
	
	public ArrayList<String> getFieldList(){
		ArrayList<String> fieldList = new ArrayList<>();
		fieldList.add(type + " Company Name:~" + companyName);
		fieldList.add("Job:~" + job);
		fieldList.add("Title:~" + title);
		fieldList.add("Date:~" + date);
		return(fieldList);
	}
	
	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}
	public void setJob(String job){
		this.job = job;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	public String getJob(){
		return job;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getDate(){
		return date;
	}	
}
