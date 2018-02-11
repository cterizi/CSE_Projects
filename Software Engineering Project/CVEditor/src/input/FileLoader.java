package input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;

import cv.CustomCV;
import output.TXTExporter;
import section.AdditionalInformationSection;
import section.CareerSummarySection;
import section.CoreStrengthsSection;
import section.EducationAndTrainingSection;
import section.FurtherCoursesSection;
import section.GeneralInformationSection;
import section.InterestsSection;
import section.ProfessionalExperienceSection;
import section.ProfessionalProfileSection;
import section.Section;
import section.SkillsAndExperienceSection;
import utils.EducationElement;
import utils.JobCareerElement;
import utils.ProfessionalExperience;
import utils.Skill;

import java.lang.reflect.*;
import java.util.*;

public abstract class FileLoader {
	private String fullPath;
	private HashMap<String, Method> methodMap = new HashMap<String, Method>();
	private HashMap<String, Section> sectionMap = new HashMap<String, Section>();
	private static ArrayList<Section> sectionList = new ArrayList<Section>();
	private static LineReader lineReader;
	private static FileLoader currentLoader;
	private static Field field = new Field("", "");
	private static int pendingElements = 0;
	private static boolean firstElement = true;
	private String lastSection = "";
	private ExtractedField lastExtractedField = new ExtractedField();
	private HashSet<String> currentSections = new HashSet<String>();
	
	
	abstract public ExtractedField extractField(String line);
	abstract public String getSectionName(String line);
	abstract public boolean skip(String line);
	
	public FileLoader(){
		try {
			init();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws NoSuchMethodException, SecurityException{
		methodMap.put("Additional Information", FileLoader.class.getMethod("readAdditionalInformation"));
		methodMap.put("Career Summary", FileLoader.class.getMethod("readCareerSummary"));
		methodMap.put("Core Strengths", FileLoader.class.getMethod("readCoreStrengths"));
		methodMap.put("Education And Training", FileLoader.class.getMethod("readEducationAndTraining"));
		methodMap.put("Further Courses", FileLoader.class.getMethod("readFurtherCourses"));
		methodMap.put("General Information", FileLoader.class.getMethod("readGeneralInformation"));
		methodMap.put("Interests", FileLoader.class.getMethod("readInterests"));
		methodMap.put("Professional Experience", FileLoader.class.getMethod("readProfessionalExperience"));
		methodMap.put("Professional Profile", FileLoader.class.getMethod("readProfessionalProfile"));
		methodMap.put("Skills And Experience", FileLoader.class.getMethod("readSkillsAndExperience"));
	}
	
	public boolean loadFile(FileLoader currentLoader, String fullPath){
		currentSections.clear();
		sectionList.clear();
		setFullPath(fullPath);
		this.currentLoader = currentLoader;
		lineReader = new LineReader(fullPath);
		String sectionName;
		while(lineReader.getCurrentLine() != null){
			sectionName = getNextSectionName();
			if(currentSections.contains(sectionName)){
				return(false);
			}
			currentSections.add(sectionName);
			if (!sectionName.equals("")){
				lastSection = sectionName;
				if(!readSection(sectionName)){
					return false;
				}
			}
			else if (lineReader.getCurrentLine() != null && !sectionName.equals("EOF")){
				return false;
			}
		}
		writeCV();
		return true;
	}
	
	public static boolean readGeneralInformation(){
		GeneralInformationSection generalInformationSection = new GeneralInformationSection();
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Name:", field, generalInformationSection, "setName", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Address:", field, generalInformationSection, "setAddress", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Telephone(Home):", field, generalInformationSection, "setHomeTelephone", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Telephone(Mobile):", field, generalInformationSection, "setMobileTelephone", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Email:", field, generalInformationSection, "setEmail", String.class)){
			return(false);
		}
		
		sectionList.add(generalInformationSection);
		return(true);
	}
	
	public static boolean readProfessionalProfile(){
		ProfessionalProfileSection professionalProfileSection = new ProfessionalProfileSection();
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Professional Profile", field, professionalProfileSection, "setText", String.class)){
			return(false);
		}
		sectionList.add(professionalProfileSection);
		return(true);
	}
	
	public static boolean readCoreStrengths(){
		CoreStrengthsSection coreStrengthsSection = new CoreStrengthsSection();
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Core Strengths", field, coreStrengthsSection, "setText", String.class)){
			return(false);
		}
		sectionList.add(coreStrengthsSection);
		return(true);
	}
	
	public static boolean readProfessionalExperience(){
		ProfessionalExperienceSection professionalExperienceSection = new ProfessionalExperienceSection();
		firstElement = true;
		boolean correctSection = readProfessionalExperienceElement(professionalExperienceSection);
		
		while(correctSection){
			correctSection = readProfessionalExperienceElement(professionalExperienceSection);
		}
		
		if (pendingElements > 0){
			return false;
		}
		
		sectionList.add(professionalExperienceSection);
		return(true);
	}
	
	public static boolean readEducationAndTraining(){
		EducationAndTrainingSection educationAndTrainingSection = new EducationAndTrainingSection();
		firstElement = true;
		boolean correctSection = readEducationSection(educationAndTrainingSection);
		while(correctSection){
			correctSection = readEducationSection(educationAndTrainingSection);
		}
		
		if (pendingElements > 0){
			return false;
		}
	
		sectionList.add(educationAndTrainingSection);
		return(true);
	}

	public static boolean readFurtherCourses(){
		FurtherCoursesSection furtherCoursesSection = new FurtherCoursesSection();
		firstElement = true;
		boolean correctSection = readEducationSection(furtherCoursesSection);
		
		while(correctSection){
			correctSection = readEducationSection(furtherCoursesSection);
		}
		
		if (pendingElements > 0){
			return false;
		}
	
		sectionList.add(furtherCoursesSection);
		return(true);
	}

	public static boolean readAdditionalInformation(){
		AdditionalInformationSection additionalInformationSection = new AdditionalInformationSection();
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Additional Information", field, additionalInformationSection, "setText", String.class)){
			return(false);
		}
		sectionList.add(additionalInformationSection);
		return(true);
	}
	
	public static boolean readInterests(){
		InterestsSection interestsSection = new InterestsSection();
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Interests", field, interestsSection, "setText", String.class)){
			return(false);
		}
		sectionList.add(interestsSection);
		return(true);
	}
	
	public static boolean readCareerSummary(){
		CareerSummarySection careerSummarySection = new CareerSummarySection();
		firstElement = true;
		boolean correctSection = readCareerSummarySection(careerSummarySection);
		
		while(correctSection){
			correctSection = readCareerSummarySection(careerSummarySection);
		}
		
		if (pendingElements > 0){
			return false;
		}
	
		sectionList.add(careerSummarySection);
		return(true);
	}
	
	public static boolean readSkillsAndExperience(){
		SkillsAndExperienceSection skillsAndExperienceSection = new SkillsAndExperienceSection();
		firstElement = true;
		boolean correctSection = readSkillsAndExperienceElement(skillsAndExperienceSection);
		
		while(correctSection){
			correctSection = readSkillsAndExperienceElement(skillsAndExperienceSection);
		}
		
		if (pendingElements > 0){
			return false;
		}
		
		sectionList.add(skillsAndExperienceSection);
		return(true);
	}
	
	public static boolean readSkillsAndExperienceElement(SkillsAndExperienceSection section){
		Skill skill = new Skill();
		int skillCounter = 1;
		if (firstElement){
			field = currentLoader.getNextExtractedField().getField();
		}
		
		if(!updateField("ExperienceOn:", field, skill, "setExperienceName", String.class)){
			return(false);
		}
		
		pendingElements = pendingElements + 1;
		
		field = currentLoader.getNextExtractedField().getField();
		while(updateField("Skill" + skillCounter + ":", field, skill, "addSkill", String.class)){
			skillCounter = skillCounter + 1;
			field = currentLoader.getNextExtractedField().getField();
		}
		firstElement = false;
		pendingElements = pendingElements - 1;
		((SkillsAndExperienceSection)section).addSkill(skill); 
		return(true);
	}
	
	public static boolean readCareerSummarySection(Section section){
		JobCareerElement jobCareerElement = new JobCareerElement("Career Summary");
		if (firstElement){
			field = currentLoader.getNextExtractedField().getField();
		}
		if(!updateField("Career Summary Company Name:", field, jobCareerElement, "setCompanyName", String.class)){
			return(false);
		}
		pendingElements = pendingElements + 1;
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Job:", field, jobCareerElement, "setJob", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Title:", field, jobCareerElement, "setTitle", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Date:", field, jobCareerElement, "setDate", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		
		firstElement = false;
		pendingElements = pendingElements - 1;
		((CareerSummarySection)section).addJobCareerElement(jobCareerElement); 
		return(true);
	}
	
	public static boolean readEducationSection(Section section){
		EducationElement educationElement = new EducationElement();
		if (firstElement){
			field = currentLoader.getNextExtractedField().getField();
		}
		if(!updateField("Name:", field, educationElement, "setName", String.class)){
			return(false);
		}
		pendingElements = pendingElements + 1;
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Establishment:", field, educationElement, "setEstablishment", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Location:", field, educationElement, "setLocation", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Date:", field, educationElement, "setDate", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		firstElement = false;
		pendingElements = pendingElements - 1;
		if (section.getSectionName().equals("Education And Training")){
			((EducationAndTrainingSection)section).addEducationAndTraining(educationElement);
		}
		else {
			((FurtherCoursesSection)section).addFurtherCourse(educationElement);
		}
		
		return(true);
	}
	
	public static boolean readProfessionalExperienceElement(ProfessionalExperienceSection section){
		ProfessionalExperience professionalExperience = new ProfessionalExperience();
		int achievementCounter = 1;
		if (firstElement){
			field = currentLoader.getNextExtractedField().getField();
		}
		
		if(!updateField("Professional Experience Company Name:", field, professionalExperience, "setCompanyName", String.class)){
			return(false);
		}
		pendingElements = pendingElements + 1;
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Job:", field, professionalExperience, "setJob", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Title:", field, professionalExperience, "setTitle", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Date:", field, professionalExperience, "setDate", String.class)){
			return(false);
		}
		field = currentLoader.getNextExtractedField().getField();
		if(!updateField("Responsibilities:", field, professionalExperience, "setParagraphOfResponsibilities", String.class)){
			return(false);
		}
		
		field = currentLoader.getNextExtractedField().getField();
		while(updateField("Achievement" + achievementCounter + ":", field, professionalExperience, "addAchievement", String.class)){
			achievementCounter = achievementCounter + 1;
			field = currentLoader.getNextExtractedField().getField();
		}
		firstElement = false;
		pendingElements = pendingElements - 1;
		section.addProfessionalExperience(professionalExperience); 
		return(true);
	}
	
	public void setFullPath(String fullPath){
		this.fullPath = fullPath;
	}
		
	public Set getSectionNames(){
		return(methodMap.keySet());
	}
	
	
	public static boolean updateField(String expectedField, Field field, Object updateObject, String methodName, Class argumentClass){
		if (!expectedField.equals(field.getLabel())){
			return false;
		}
		try {
			try {
				Class searchClass = getSearchClass(updateObject.getClass(), methodName, argumentClass);
				/*
				Class searchClass = updateObject.getClass();
				if (hasSuperClass(updateObject)){
					searchClass = searchClass.getSuperclass();
				}*/
				searchClass.getDeclaredMethod(methodName, argumentClass).invoke(updateObject, field.getField());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	public static boolean hasSuperClass(Object object){
		String superClassName = object.getClass().getSuperclass().getName();
		return !superClassName.equals("java.lang.Object") && !superClassName.equals("section.Section");
	}
	
	public static Class getSearchClass(Class c, String methodName, Class argumentClass){
		if (hasMethod(c, methodName)){
			if (hasMethod(c.getSuperclass(), methodName)){
				return c.getSuperclass();
			}
		}	
		
		return c;
	}
	
	public static boolean hasMethod(Class c, String methodName){
		
		Method[] methods = c.getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean readSection(String sectionName){
		try {
			return (boolean)methodMap.get(sectionName).invoke(null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getNextSectionName(){
		String sectionLine = readNextSectionLine();
		if (!isSection(sectionLine)){
			return "";
		}
		return currentLoader.getSectionName(sectionLine);	
	}
	
	public String readNextSectionLine() {
		
		if (!firstElement){
			firstElement = true;
			return lineReader.getCurrentLine();
		}
		
		lineReader.readLine();
		while(lineReader.getCurrentLine() != null){
			if(currentLoader.skip(lineReader.getCurrentLine()) || lineReader.getCurrentLine().trim().isEmpty()){
				lineReader.readLine();
				continue;
			}
			if (isSection(getSectionName(lineReader.getCurrentLine()))){
				return lineReader.getCurrentLine();
			}
			else if (lineReader.getCurrentLine() != null){

				return "";
			}
		}
		return "EOF";
	}
	
	public ExtractedField getNextExtractedField(){
		
		if (lastExtractedField.hasFieldsToRead()){
			lastExtractedField = extractField("~~~~~");
			return lastExtractedField;
		}
		
		lineReader.readLine();
		while(lineReader.getCurrentLine() != null){
			if(skip(lineReader.getCurrentLine()) || lineReader.getCurrentLine().trim().isEmpty()){
				lineReader.readLine();
				continue;
			}
			break;
		}
		
		String line = lineReader.getCurrentLine(); 
		if (line == null){
			lastExtractedField = new ExtractedField();
			return lastExtractedField;
		}
		lastExtractedField = extractField(line);
		return lastExtractedField;
	}
	
	public boolean isSection(String sectionLine){
		return getSectionNames().contains(currentLoader.getSectionName(sectionLine));
	}
	
	public ArrayList<Section> getSectionList(){
		return(sectionList);
	}
	
	public String getLastSection(){
		return lastSection;
	}
	
	public void writeCV(){
		CustomCV customCV = new CustomCV(sectionList);
		TXTExporter txtExporter = new TXTExporter();
		
		txtExporter.writeFile(customCV, "C:\\Users\\Chryssa\\Desktop\\customCV.txt");
	}
}
