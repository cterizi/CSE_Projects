package section;

import java.util.ArrayList;

import input.Field;
import utils.JobCareerElement;
import utils.JobCareerPanelElement;

public class CareerSummarySection extends Section{
	private ArrayList<JobCareerElement> listOfJobCareerElements;
	
	public CareerSummarySection() {
		super("Career Summary");
		listOfJobCareerElements = new ArrayList<JobCareerElement>();
	}
	
	public void updateAllFields(ArrayList<JobCareerPanelElement> listOfJobCareerPanelElements){
		listOfJobCareerElements.clear();
		for(JobCareerPanelElement item : listOfJobCareerPanelElements){
			JobCareerElement newElement = new JobCareerElement("Career Summary");
			newElement.setCompanyName(item.getCompanyNameString());
			newElement.setJob(item.getJobString());
			newElement.setTitle(item.getTitleString());
			if(checkDate(item.getDateString())){
				newElement.setDate(item.getDateString());
			}
			else{
				newElement.setDate("");
				item.setDate("eg.12/05/2015 - 20/03/2016");
			}
			listOfJobCareerElements.add(newElement);
		}
	}
		
	public void addFields(){
		clearSectionFieldList();
		ArrayList<String> fieldList = new ArrayList<String>();
		
		for(JobCareerElement jobCareerElement : listOfJobCareerElements){
			fieldList = jobCareerElement.getFieldList();
			for(String field : fieldList){
				addField(field);
			}
		}
	}
	
	public void addJobCareerElement(JobCareerElement jobCareerElement){
		listOfJobCareerElements.add(jobCareerElement);
	}
	
	public void deleteJobCareerElement(int index){
		listOfJobCareerElements.remove(index);
	}
	
	public ArrayList<JobCareerElement> getList(){
		return(listOfJobCareerElements);
	}

	
	public ArrayList<Field> compare(Section comparedSection) {
		int i = 0;
		boolean sameExists = false;
		Field tempField;
		ArrayList<Field> fields = new ArrayList<Field>();
		ArrayList<JobCareerElement> comparedjobCareerElements = ((CareerSummarySection)comparedSection).getList();
		
		for (JobCareerElement jobCareerElement : listOfJobCareerElements){
			sameExists = false;
			for (i = 0; i < comparedjobCareerElements.size(); i++){
				if (jobCareerElement.getCompanyName().equals(comparedjobCareerElements.get(i).getCompanyName())){
					int index = fields.size();
					boolean allSame = true;
					tempField = getTempField("Career Summary Company Name", jobCareerElement.getCompanyName(), "\n");
					tempField.setField(jobCareerElement.getCompanyName());
					fields.add(tempField);
					
					if (!jobCareerElement.getJob().equals(comparedjobCareerElements.get(i).getJob())){
						allSame = false;
						tempField = getTempField("Job", jobCareerElement.getJob(), comparedjobCareerElements.get(i).getJob());
					}
					else{
						tempField = getTempField("Job", jobCareerElement.getJob(), jobCareerElement.getJob());
					}
					fields.add(tempField);
					
					if (!jobCareerElement.getTitle().equals(comparedjobCareerElements.get(i).getTitle())){
						allSame = false;
						tempField = getTempField("Title", jobCareerElement.getTitle(), comparedjobCareerElements.get(i).getTitle());
					}
					else{
						tempField = getTempField("Title", jobCareerElement.getTitle(), jobCareerElement.getTitle());
					}
					fields.add(tempField);
					
					if (!jobCareerElement.getDate().equals(comparedjobCareerElements.get(i).getDate())){
						allSame = false;
						tempField = getTempField("Date", jobCareerElement.getDate(), comparedjobCareerElements.get(i).getDate());
					}
					else{
						tempField = getTempField("Date", jobCareerElement.getDate(), jobCareerElement.getDate());
					}
					fields.add(tempField);
					
					if (allSame){
						fields.get(index).setPassField(false);
					}
					
					comparedjobCareerElements.remove(i);
					sameExists = true;
					break;
				}
			}
	
			if (!sameExists){
				fields.add(getTempField("Career Summary Company Name", jobCareerElement.getCompanyName(), jobCareerElement.getCompanyName()));
				fields.add(getTempField("Job", jobCareerElement.getJob(), jobCareerElement.getJob()));
				fields.add(getTempField("Title", jobCareerElement.getTitle(), jobCareerElement.getTitle()));
				fields.add(getTempField("Date", jobCareerElement.getDate(), jobCareerElement.getDate()));
			}
		}
		
		
		return fields;
	}

	@Override
	public void updateFromFields(ArrayList<Field> fields) {
		int i = 0, index = 0;
		while (i < fields.size()){
			if (!fields.get(i).getKeepComparedField()){
				listOfJobCareerElements.remove(index);
				i = i + 4;
				continue;
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfJobCareerElements.get(index).setJob(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfJobCareerElements.get(index).setTitle(fields.get(i).getComparedFieldValue());
			}
			i = i + 1;
			if (fields.get(i).getComparedFieldValue() != null && fields.get(i).getKeepComparedField()){
				listOfJobCareerElements.get(index).setDate(fields.get(i).getComparedFieldValue());
			}
		
			index = index + 1;
			i = i + 1;
		}
		
	}
}
