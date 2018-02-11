package section;

import java.util.ArrayList;

import input.Field;
import utils.EducationElement;
import utils.EducationPanelElement;

public class FurtherCoursesSection extends Section{
	private ArrayList<EducationElement> listOfFurtherCourses;
	
	public FurtherCoursesSection() {
		super("Further Courses");
		listOfFurtherCourses = new ArrayList<EducationElement>();
	}
	
	public void updateAllFields(ArrayList<EducationPanelElement> listOfFurtherCourse){
		listOfFurtherCourses.clear();
		for(EducationPanelElement item : listOfFurtherCourse){
			EducationElement newElement = new EducationElement();
			newElement.setName(item.getNameString());
			newElement.setEstablishment(item.getEstablishmentString());
			newElement.setLocation(item.getLocationString());
			if(checkDate(item.getDateString())){
				newElement.setDate(item.getDateString());
			}
			else{
				newElement.setDate("");
				item.setDate("eg.12/05/2015 - 20/03/2016");
			}
			listOfFurtherCourses.add(newElement);
		}
	}
	
	public void addFields(){
		 clearSectionFieldList();
		 ArrayList<String> fieldList = new ArrayList<String>();
			
			for(EducationElement educationElement : listOfFurtherCourses){
				fieldList = educationElement.getFieldList();
				for(String field : fieldList){
					addField(field);
				}
			}
	}
	
	public void addFurtherCourse(EducationElement furtherCourse){
		listOfFurtherCourses.add(furtherCourse);
	}
	
	public void deleteFurtherCourses(int index){
		listOfFurtherCourses.remove(index);
	}
	
	public ArrayList<EducationElement> getList(){
		return(listOfFurtherCourses);
	}

	@Override
	public ArrayList<Field> compare(Section comparedSection) {
		int i = 0;
		boolean sameExists = false;
		Field tempField;
		ArrayList<Field> fields = new ArrayList<Field>();
		ArrayList<EducationElement> comparedEducationElements = ((FurtherCoursesSection)comparedSection).getList();
		
		for (EducationElement educationElement : listOfFurtherCourses){
			sameExists = false;
			for (i = 0; i < comparedEducationElements.size(); i++){
				int index = fields.size();
				boolean allSame= true;
				if (educationElement.getName().equals(comparedEducationElements.get(i).getName())){
					tempField = getTempField("Name", educationElement.getName(), "\n");
					tempField.setField(educationElement.getName());
					fields.add(tempField);
					
					if (!educationElement.getEstablishment().equals(comparedEducationElements.get(i).getEstablishment())){
						allSame = false;
						tempField = getTempField("Establishment", educationElement.getEstablishment(), comparedEducationElements.get(i).getEstablishment());
					}
					else{
						tempField = getTempField("Establishment", educationElement.getEstablishment(), educationElement.getEstablishment());
					}
					fields.add(tempField);
					
					if (!educationElement.getLocation().equals(comparedEducationElements.get(i).getLocation())){
						allSame = false;
						tempField = getTempField("Location", educationElement.getLocation(), comparedEducationElements.get(i).getLocation());
					}
					else{
						tempField = getTempField("Location", educationElement.getLocation(), educationElement.getLocation());
					}
					fields.add(tempField);
					
					if (!educationElement.getDate().equals(comparedEducationElements.get(i).getDate())){
						allSame = false;
						tempField = getTempField("Date", educationElement.getDate(), comparedEducationElements.get(i).getDate());
					}
					else{
						tempField = getTempField("Date", educationElement.getDate(), educationElement.getDate());
					}
					fields.add(tempField);
					
					if (allSame){
						fields.get(index).setPassField(false);
					}
					
					comparedEducationElements.remove(i);
					sameExists = true;
					break;
				}
			}
	
			if (!sameExists){
				fields.add(getTempField("Name", educationElement.getName(), educationElement.getName()));
				fields.add(getTempField("Establishment", educationElement.getEstablishment(), educationElement.getEstablishment()));
				fields.add(getTempField("Location", educationElement.getLocation(), educationElement.getLocation()));
				fields.add(getTempField("Date", educationElement.getDate(), educationElement.getDate()));
			}
		}
		
		
		return fields;
	}

	public void updateFromFields(ArrayList<Field> fields) {
		int i = 0, index = 0;
		while (i < fields.size()){
			if (!fields.get(i).getKeepComparedField()){
				listOfFurtherCourses.remove(index);
				i = i + 4;
				continue;
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfFurtherCourses.get(index).setEstablishment(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfFurtherCourses.get(index).setLocation(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfFurtherCourses.get(index).setDate(fields.get(i).getComparedFieldValue());
			}

			index = index + 1;
			i = i + 1;
		}
	}
}
