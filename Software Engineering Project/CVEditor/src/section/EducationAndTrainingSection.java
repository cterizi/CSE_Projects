package section;

import java.util.ArrayList;

import input.Field;
import utils.EducationElement;
import utils.EducationPanelElement;
import utils.JobCareerElement;
import utils.JobCareerPanelElement;

public class EducationAndTrainingSection extends Section{
	private ArrayList<EducationElement> listOfEducationAndTraining;
	
	public EducationAndTrainingSection() {
		super("Education And Training");
		listOfEducationAndTraining = new ArrayList<EducationElement>();
	}

	public void updateAllFields(ArrayList<EducationPanelElement> listOfEducationPanelElements){
		listOfEducationAndTraining.clear();
		for(EducationPanelElement item : listOfEducationPanelElements){
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
			listOfEducationAndTraining.add(newElement);
		}
	}
	
	public void addFields(){
		 clearSectionFieldList();
		 ArrayList<String> fieldList = new ArrayList<String>();
			
			for(EducationElement educationElement : listOfEducationAndTraining){
				fieldList = educationElement.getFieldList();
				for(String field : fieldList){
					addField(field);
				}
			}
	}
	
	public void addEducationAndTraining(EducationElement educationAndTraining){
		listOfEducationAndTraining.add(educationAndTraining);
	}
	
	public void deleteEducationAndTraining(int index){
		listOfEducationAndTraining.remove(index);
	}
	
	public ArrayList<EducationElement> getList(){
		return(listOfEducationAndTraining);
	}

	public ArrayList<Field> compare(Section comparedSection) {
		
		int i = 0;
		boolean sameExists = false;
		Field tempField;
		ArrayList<Field> fields = new ArrayList<Field>();
		ArrayList<EducationElement> comparedEducationElements = ((EducationAndTrainingSection)comparedSection).getList();
		
		for (EducationElement educationElement : listOfEducationAndTraining){
			sameExists = false;
			for (i = 0; i < comparedEducationElements.size(); i++){
				if (educationElement.getName().equals(comparedEducationElements.get(i).getName())){
					int index = fields.size();
					boolean allSame = true;
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

	@Override
	public void updateFromFields(ArrayList<Field> fields) {
		int i = 0, index = 0;
		while (i < fields.size()){
			if (!fields.get(i).getKeepComparedField()){
				listOfEducationAndTraining.remove(index);
				i = i + 4;
				continue;
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfEducationAndTraining.get(index).setEstablishment(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfEducationAndTraining.get(index).setLocation(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfEducationAndTraining.get(index).setDate(fields.get(i).getComparedFieldValue());
			}

			index = index + 1;
			i = i + 1;
		}
	}
}