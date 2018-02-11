package compare;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cv.CV;
import input.Field;
import section.GeneralInformationSection;
import section.Section;

public class CVCompare {

	public boolean compareCVs(CV cv1, CV cv2, String lastModified1, String lastModified2){
		CV referenceCV, comparedCV;
		if(!sameSections(cv1.getSectionsNames(), cv2.getSectionsNames())){
			return(false);
		}
		if(!sameName(cv1.getSections(), cv2.getSections())){
			return(false);
		}
		if (lastModifiedCV(lastModified1, lastModified2) == 1){
			referenceCV = cv1;
			comparedCV = cv2;
		}
		else{
			referenceCV = cv2;
			comparedCV = cv1;
		}
		
		ArrayList<Field> fieldList = new ArrayList<Field>();
		MergeWindow mergeWindow = new MergeWindow();
		for (Section section : referenceCV.getSections()){
			fieldList = section.compare(findSection(section.getSectionName(), comparedCV.getSections()));
			if (fieldList != null) mergeWindow.addMergeSection(section, fieldList);
		}
		
		mergeWindow.setVisible(true);
		
		return(true);
	}
	
	public Section findSection(String sectionName, ArrayList<Section> sections){
		for(Section section : sections){
			if(section.getSectionName().equals(sectionName)){
				return(section);
			}
		}
		return(null);
	}
	
	public boolean sameSections(ArrayList<String> sections1, ArrayList<String> sections2){
		for(String name : sections1){
			if(!sections2.contains(name)){
				return(false);
			}
		}
		return(true);
	}
	
	public boolean sameName(ArrayList<Section> l1, ArrayList<Section> l2){
		for(int i = 0; i < l1.size(); i++){
			if(l1.get(i).getSectionName().equals("General Information")){
				String cv1Name = ((GeneralInformationSection)l1.get(i)).getName();
				String cv2Name = ((GeneralInformationSection)l2.get(i)).getName();
				if(!cv1Name.equals(cv2Name)){
					return(false);
				}
			}
		}
		return(true);
	}
	
	public int lastModifiedCV(String stringDate1, String stringDate2){
		Date date1 = stringToDate(stringDate1);
		Date date2 = stringToDate(stringDate2);
		if (date1.before(date2)){
			return 2;
		}
		return 1;
	}
	
	public Date stringToDate(String stringDate){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); 
		Date date = null;
		try {
		    date = df.parse(stringDate);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		return date;
	}
}
